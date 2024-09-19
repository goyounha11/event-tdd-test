package com.sparta.event.service;

import com.sparta.event.domain.Event;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;

public interface EventService {
	void create(String name, String content, LocalDateTime applyStartAt, LocalDateTime applyEndAt);

	Page<Event> getEvents(int page, int size);
}
