package com.sparta.event.service;

import com.sparta.event.domain.Event;
import com.sparta.event.domain.EventStatus;
import com.sparta.event.repository.EventRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;

	@Override
	public void create(String name, String content, LocalDateTime applyStartAt, LocalDateTime applyEndAt) {
		eventRepository.save(new Event(name, content, EventStatus.decideStatus(applyStartAt, applyEndAt), applyStartAt, applyEndAt));
	}

	@Override
	public Page<Event> getEvents(int page, int size) {

		return eventRepository.findAll(PageRequest.of(page - 1, size));
	}
}
