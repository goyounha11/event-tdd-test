package com.sparta.event.controller.dto;

import static com.sparta.event.controller.dto.EventResponse.*;

import com.sparta.event.domain.Event;
import com.sparta.event.domain.EventStatus;

public sealed interface EventResponse permits List {

	record List(
		Long id,
		String name,
		EventStatus status
	) implements EventResponse {

		public List(Event event) {
			this(
				event.getId(),
				event.getName(),
				event.getStatus()
			);
		}
	}
}
