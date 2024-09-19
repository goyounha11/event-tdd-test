package com.sparta.event.controller.dto;

import com.sparta.event.controller.dto.EventRequest.Create;
import java.time.LocalDateTime;

public sealed interface EventRequest permits Create {
	record Create(
		String name,
		String content,
		LocalDateTime applyStartAt,
		LocalDateTime applyEndAt) implements EventRequest { }

}
