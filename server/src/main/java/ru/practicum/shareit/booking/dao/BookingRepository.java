package ru.practicum.shareit.booking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.Collection;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("SELECT b FROM Booking b WHERE b.item.id = :itemId")
    Collection<Booking> findByItemId(@Param("itemId") Integer itemId);

    Collection<Booking> findByStartBeforeAndEndAfter(LocalDateTime start, LocalDateTime end);

    Collection<Booking> findByEndBefore(LocalDateTime end);

    Collection<Booking> findByStartAfter(LocalDateTime start);

    Collection<Booking> findByStatus(BookingStatus status);
}
