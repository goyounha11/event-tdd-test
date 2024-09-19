package com.sparta.event.repository;

import com.sparta.event.domain.Event;
import com.sparta.event.domain.EventStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
	List<Event> findAllByStatus(EventStatus status);
}
