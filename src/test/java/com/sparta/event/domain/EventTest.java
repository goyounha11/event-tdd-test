package com.sparta.event.domain;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class EventTest {

	@Test
	void 행사_생성_성공() {
		var event = new Event(
			"철호의 TDD",
			"테스트 주도 개발",
			EventStatus.PENDING,
			LocalDateTime.now().minusDays(2),
			LocalDateTime.now().plusDays(2));

		assertThat(event.getId()).isNull();
		assertThat(event.getName()).isEqualTo("철호의 TDD");
		assertThat(event.getContent()).isEqualTo("테스트 주도 개발");
		assertThat(event.getStatus()).isEqualTo(EventStatus.PENDING);
		assertThat(event.getApplyStartAt()).isNotNull();
		assertThat(event.getApplyEndAt()).isNotNull();
	}

	@ParameterizedTest
	@NullAndEmptySource
	void 행사_생성_실패__이름이_공백이거나_null(String name) {
		assertThatIllegalArgumentException().isThrownBy(() -> {
			new Event(
				name,
				"테스트 주도 개발",
				EventStatus.PENDING,
				LocalDateTime.now().minusDays(2),
				LocalDateTime.now().plusDays(2));
		});
	}

	@ParameterizedTest
	@NullAndEmptySource
	void 행사_생성_실패__내용이_공백이거나_null(String content) {
		assertThatIllegalArgumentException().isThrownBy(() -> {
			new Event(
				"철호의 TDD",
				content,
				EventStatus.PENDING,
				LocalDateTime.now().minusDays(2),
				LocalDateTime.now().plusDays(2));
		});
	}

	@Test
	void 행사_생성_실패__상태가_null() {
		assertThatIllegalStateException().isThrownBy(() -> {
			new Event(
				"철호의 TDD",
				"테스트 주도 개발",
				null,
				LocalDateTime.now().minusDays(2),
				LocalDateTime.now().plusDays(2));
		});
	}

	@Test
	void 행사_생성_실패__행사_시작일이_null() {
		assertThatIllegalArgumentException().isThrownBy(() -> {
			new Event(
				"철호의 TDD",
				"테스트 주도 개발",
				EventStatus.PENDING,
				null,
				LocalDateTime.now().plusDays(2));
		});
	}

	@Test
	void 행사_생성_실패__행사_마감일이_null() {
		assertThatIllegalArgumentException().isThrownBy(() -> {
			new Event(
				"철호의 TDD",
				"테스트 주도 개발",
				EventStatus.PENDING,
				LocalDateTime.now().plusDays(1),
				null);
		});
	}

	@Test
	void 행사_생성_실패__마감일이_신청일보다_이전() {
		assertThatIllegalArgumentException().isThrownBy(() -> {
			new Event(
				"철호의 TDD",
				"테스트 주도 개발",
				EventStatus.PENDING,
				LocalDateTime.now().plusDays(1),
				LocalDateTime.now());
		});
	}
}