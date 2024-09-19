package com.sparta.event.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.event.domain.Event;
import com.sparta.event.domain.EventStatus;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EventRepositoryTest {

	@Autowired
	private EventRepository eventRepository;

	@Test
	void 펜딩상태의_행사들을_조회할_수_있다() {
		var event1 = eventRepository.save(new Event(
			"철호의 TDD1",
			"테스트 주도 개발1",
			EventStatus.PENDING,
			LocalDateTime.now().plusDays(1),
			LocalDateTime.now().plusDays(2)));

		var event2 = eventRepository.save(new Event(
			"철호의 TDD2",
			"테스트 주도 개발2",
			EventStatus.PENDING,
			LocalDateTime.now().plusDays(3),
			LocalDateTime.now().plusDays(5)));

		var event3 = eventRepository.save(new Event(
			"철호의 TDD3",
			"테스트 주도 개발3",
			EventStatus.PENDING,
			LocalDateTime.now().plusDays(6),
			LocalDateTime.now().plusDays(8)));

		var event4 = eventRepository.save(new Event(
			"지나간 행사",
			"종료됨",
			EventStatus.CLOSED,
			LocalDateTime.now().minusDays(2),
			LocalDateTime.now().minusDays(1)));

		var foundedEvents = eventRepository.findAllByStatus(EventStatus.PENDING);

		assertThat(foundedEvents).containsExactly(event1, event2, event3);
		assertThat(foundedEvents).doesNotContain(event4);
	}
}