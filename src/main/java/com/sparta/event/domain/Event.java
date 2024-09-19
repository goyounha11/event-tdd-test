package com.sparta.event.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String content;

	@Enumerated(EnumType.STRING)
	private EventStatus status;

	private LocalDateTime applyStartAt;

	private LocalDateTime applyEndAt;

	private Long createdBy;
	private Long updatedBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Event(String name, String content, EventStatus status, LocalDateTime applyStartAt,
		LocalDateTime applyEndAt) {

		if(Strings.isBlank(name)) {
			throw new IllegalArgumentException("이름은 공백 또는 null일 수 없습니다.");
		}

		if(Strings.isBlank(content)) {
			throw new IllegalArgumentException("내용은 공백 또는 null일 수 없습니다.");
		}

		if(status == null) {
			throw new IllegalStateException("상태는 null일 수 없습니다.");
		}

		if(applyStartAt == null) {
			throw new IllegalArgumentException("신청 시작일은 null일 수 없습니다.");
		}

		if(applyEndAt == null) {
			throw new IllegalArgumentException("신청 마감일은 null일 수 없습니다.");
		}

		if(applyEndAt.isBefore(applyStartAt)) {
			throw new IllegalArgumentException("신청 마감일은 신청 시작일보다 빠를 수 없습니다.");
		}

		this.name = name;
		this.content = content;
		this.status = status;
		this.applyStartAt = applyStartAt;
		this.applyEndAt = applyEndAt;
	}
}
