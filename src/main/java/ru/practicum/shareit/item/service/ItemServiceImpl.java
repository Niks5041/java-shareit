package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dao.BookingRepository;
import ru.practicum.shareit.booking.dto.mapper.BookingMapper;
import ru.practicum.shareit.booking.dto.response.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dao.CommentRepository;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.mapper.CommentMapper;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.dto.response.ItemGetAllResponse;
import ru.practicum.shareit.item.dto.response.ItemGetByIdResponse;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    @Override
    public Collection<ItemGetAllResponse> getAllItems(Integer userId) {
        log.info("Получаем список всех итемов из хранилища");
        return itemRepository.findByOwnerId(userId)
                .stream()
                .map(item -> {

                    LocalDateTime lastBookingDate = getLastBookingDate(item);
                    LocalDateTime nextBookingDate = getNextBookingDate(item);

                    ItemGetAllResponse itemDto = ItemMapper.toItemGetAllresponse(item);
                    itemDto.setStartDate(lastBookingDate);
                    itemDto.setEndDate(nextBookingDate);

                    return itemDto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ItemDto addNewItem(Item item, Integer userId) {
        log.info("Добавляем новый итем в хранилище");

        if (item.getAvailable() == null) {
            throw new ValidationException("Статус доступности итема не может быть пустым");
        }
        if (item.getName() == null || item.getName().isBlank()) {
            throw new ValidationException("Название итема не может быть пустым");
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new ValidationException("Описание итема не может быть пустым");
        }
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь с ID " + userId + " не найден");
        }

        item.setOwnerId(userId);

        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Transactional
    @Override
    public ItemDto modifyItem(Item item, Integer itemId, Integer userId) {
        log.info("Обновляем итем в хранилище");
        Optional<Item> existingItemOpt = itemRepository.findById(itemId);
        if (existingItemOpt.isEmpty()) {
            throw new NotFoundException("Итем с ID " + itemId + " не найден");
        }
        Item existingItem = existingItemOpt.get();
        if (!existingItem.getOwnerId().equals(userId)) {
            throw new NotFoundException("Вы не можете обновить этот итем, так как вы не являетесь его владельцем");
        }
        if (item.getName() != null) {
            existingItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            existingItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            existingItem.setAvailable(item.getAvailable());
        }

        return ItemMapper.toItemDto(itemRepository.save(existingItem));
    }

    @Transactional(readOnly = true)
    @Override
    public ItemGetByIdResponse getInfoOfItemById(Integer id) {
        log.info("Получаем итем с ID {}", id);

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Итем не найден"));

        Collection<Booking> bookings = bookingRepository.findByItemId(id);
        Collection<BookingDto> bookingDtos = bookings.stream()
                .filter(booking -> !booking.getStatus().equals(BookingStatus.APPROVED))
                .map(BookingMapper::toBookingDto)
                .toList();

        ItemGetByIdResponse response = ItemMapper.toItemGetByIdResponse(item);
        bookingDtos.stream().filter(b -> b.getStart().isBefore(LocalDateTime.now()))
                .max(Comparator.comparing(BookingDto::getStart))
                .ifPresent(response::setLastBooking);

        bookingDtos.stream().filter(b -> b.getEnd().isAfter(LocalDateTime.now()))
                .min(Comparator.comparing(BookingDto::getEnd))
                .ifPresent(response::setNextBooking);

        Collection<Comment> comments = commentRepository.findByItemId(id);
        List<CommentDto> commentDtoList = comments.stream().map(CommentMapper::toCommentDto).toList();

        response.setComments(commentDtoList);

        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<ItemDto> findItemByText(String text) {
        log.info("Получаем итем с текстом {}", text);

        return itemRepository.findItemByText(text)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CommentDto addComment(Integer itemId, Integer userId, CommentCreateDto commentDto) {
        Optional<Item> existingItemOpt = itemRepository.findById(itemId);
        if (existingItemOpt.isEmpty()) {
            throw new NotFoundException("Итем с ID " + itemId + " не найден");
        }
        Collection<Booking> bookingDtoList = bookingRepository.findByItemId(existingItemOpt.get().getId());
        if (bookingDtoList.stream().filter(it ->
                Objects.equals(it.getBooker().getId(), userId) && it.getEnd().isBefore(LocalDateTime.now())).toList().isEmpty()) {
            throw new ValidationException("Невозможно оставить отзыв");
        }
        User author = userRepository.findById(userId).orElseThrow();
        Comment comment = new Comment();
        comment.setItem(existingItemOpt.get());
        comment.setText(commentDto.getText());
        comment.setAuthor(author);

        commentRepository.save(comment);

        return CommentMapper.toCommentDto(comment);
    }

    private LocalDateTime getLastBookingDate(Item item) {
        return bookingRepository.findByItemId(item.getId())
                .stream()
                .filter(booking -> !booking.getStatus().equals(BookingStatus.REJECTED))
                .map(Booking::getEnd)
                .max(LocalDateTime::compareTo)
                .orElse(null);
    }

    private LocalDateTime getNextBookingDate(Item item) {
        return bookingRepository.findByItemId(item.getId())
                .stream()
                .filter(booking -> !booking.getStatus().equals(BookingStatus.REJECTED))
                .map(Booking::getStart)
                .filter(date -> date.isAfter(LocalDateTime.now()))
                .min(LocalDateTime::compareTo)
                .orElse(null);
    }
}
