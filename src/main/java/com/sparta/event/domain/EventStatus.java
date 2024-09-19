package com.sparta.event.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventStatus {
	PENDING("대기"),
	OPEN("오픈"),
	CLOSED("마감");

	private final String description;

	public static EventStatus decideStatus(LocalDateTime applyStartAt, LocalDateTime applyEndAt) {
		if(applyStartAt.isAfter(LocalDateTime.now())) {
			return EventStatus.PENDING;
		} else if(applyStartAt.isBefore(LocalDateTime.now()) && applyEndAt.isAfter(LocalDateTime.now())) {
			return EventStatus.OPEN;
		} else {
			return EventStatus.CLOSED;
		}
	}
}
