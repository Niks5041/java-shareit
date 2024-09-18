package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dao.BookingRepository;
import ru.practicum.shareit.booking.dto.request.AddNewBookingRequest;
import ru.practicum.shareit.booking.dto.response.BookingDto;
import ru.practicum.shareit.booking.dto.mapper.BookingMapper;
import ru.practicum.shareit.booking.dto.request.BookingStatusDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public BookingDto addNewBooking(AddNewBookingRequest bookingRequest, Integer userId) {
        log.info("Добавляем новое бронирование в хранилище");

        if (bookingRequest.getStart() == null || bookingRequest.getEnd() == null) {
            throw new ValidationException("Дата начала и окончания бронирования не могут быть пустыми");
        }
        if (bookingRequest.getStart().isAfter(bookingRequest.getEnd())) {
            throw new ValidationException("Дата окончания должна быть позже даты начала");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден"));
        Item item = itemRepository.findById(bookingRequest.getItemId())
                .orElseThrow(() -> new NotFoundException("Итем с ID " + bookingRequest.getItemId() + " не найден"));

        if (!item.getAvailable()) {
            throw new RuntimeException("Итем с ID" + bookingRequest.getItemId() + " недоступен для бронирования");
        }
        if (Objects.equals(item.getOwnerId(), userId)) {
            throw new NotFoundException("item с id = " + item.getId() + " недоступен для бронирования владельцем");
        }
        Booking booking = BookingMapper.toBooking(bookingRequest, user, item, BookingStatus.WAITING);
        Booking savedBooking = bookingRepository.save(booking);
        return BookingMapper.toBookingDto(savedBooking);
    }

    @Transactional
    @Override
    public BookingDto confirmOrNotBooking(Integer bookingId, Boolean approved, Integer userId) {
        log.info("Подтверждаем или отклоняем бронирование с ID {}", bookingId);

        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование с ID " + bookingId + " не найдено"));
        if (!existingBooking.getItem().getOwnerId().equals(userId)) {
            throw new ValidationException("Вы не можете подтверждать это бронирование, так как вы не являетесь владельцем предмета");
        }
        if (approved) {
            existingBooking.setStatus(BookingStatus.APPROVED);
        } else {
            existingBooking.setStatus(BookingStatus.REJECTED);
        }
        return BookingMapper.toBookingDto(bookingRepository.save(existingBooking));
    }

    @Override
    public BookingDto getInfoOfBookingById(Integer bookingId) {
        log.info("Получаем информацию о бронировании с ID {}", bookingId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование с ID " + bookingId + " не найдено"));
        if (booking.getItem().getOwnerId().equals(booking.getBooker().getId())) {
            throw new ValidationException("Вы не можете получить информацию об этом бронирование," +
                    " так как вы не являетесь владельцем предмета");
        }
        return BookingMapper.toBookingDto(booking);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<BookingDto> getAllBookings(BookingStatusDto state, Integer userId) {
        log.info("Получаем все бронирования со статусом {}", state);

        return getBookingsByState(state).stream()
                .filter(booking -> booking.getBooker().getId().equals(userId))
                .map(BookingMapper::toBookingDto)
                .sorted((b1, b2) -> b2.getStart().compareTo(b1.getStart()))
                .collect(Collectors.toList());
    }

    private Collection<Booking> getBookingsByState(BookingStatusDto state) {
        LocalDateTime now = LocalDateTime.now();
        return switch (state) {
            case ALL -> bookingRepository.findAll();
            case CURRENT -> bookingRepository.findByStartBeforeAndEndAfter(now, now);
            case PAST -> bookingRepository.findByEndBefore(now);
            case FUTURE -> bookingRepository.findByStartAfter(now);
            case WAITING -> bookingRepository.findByStatus(BookingStatus.WAITING);
            case REJECTED -> bookingRepository.findByStatus(BookingStatus.REJECTED);
            default -> throw new ValidationException("Некорректный статус бронирования: " + state);
        };
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<BookingDto> getBookingsForOwner(BookingStatusDto state, Integer userId) {
        log.info("Получаем бронирования для владельца со статусом {}", state);
        Collection<Booking> bookings = getBookingsByState(state);
        List<BookingDto> bookingDtos = bookings.stream()
                .filter(booking -> booking.getBooker().getId().equals(userId))
                .map(BookingMapper::toBookingDto)
                .sorted((b1, b2) -> b2.getStart().compareTo(b1.getStart()))
                .toList();
        if (bookingDtos.isEmpty()) {
            throw new NotFoundException("Не найдено бронирований");
        }
        return bookingDtos;
    }
}
