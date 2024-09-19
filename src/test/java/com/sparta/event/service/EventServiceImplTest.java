package com.sparta.event.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.event.domain.Event;
import com.sparta.event.domain.EventStatus;
import com.sparta.event.repository.EventRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EventServiceImplTest {

	@Autowired
	private EventService eventService;

	@Autowired
	private EventRepository eventRepository;

	@Test
	void 행사_생성_성공__상태가_대기() {
		eventService.create("철호의 TDD", "테스트 주도 개발", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));

		var foundedEvent = eventRepository.findAll().stream().findFirst().get();

		assertThat(foundedEvent.getId()).isNotNull();
		assertThat(foundedEvent.getName()).isEqualTo("철호의 TDD");
		assertThat(foundedEvent.getContent()).isEqualTo("테스트 주도 개발");
		assertThat(foundedEvent.getApplyStartAt()).isNotNull();
		assertThat(foundedEvent.getApplyEndAt()).isNotNull();
		assertThat(foundedEvent.getStatus()).isEqualTo(EventStatus.PENDING);
	}

	@Test
	void 행사_생성_성공__상태가_오픈() {
		eventService.create("철호의 TDD", "테스트 주도 개발", LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(2));

		var foundedEvent = eventRepository.findAll().stream().findFirst().get();

		assertThat(foundedEvent.getId()).isNotNull();
		assertThat(foundedEvent.getName()).isEqualTo("철호의 TDD");
		assertThat(foundedEvent.getContent()).isEqualTo("테스트 주도 개발");
		assertThat(foundedEvent.getApplyStartAt()).isNotNull();
		assertThat(foundedEvent.getApplyEndAt()).isNotNull();
		assertThat(foundedEvent.getStatus()).isEqualTo(EventStatus.OPEN);
	}

	@Test
	void 행사_생성_성공__상태가_마감() {
		eventService.create("철호의 TDD", "테스트 주도 개발", LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1));

		var foundedEvent = eventRepository.findAll().stream().findFirst().get();

		assertThat(foundedEvent.getId()).isNotNull();
		assertThat(foundedEvent.getName()).isEqualTo("철호의 TDD");
		assertThat(foundedEvent.getContent()).isEqualTo("테스트 주도 개발");
		assertThat(foundedEvent.getApplyStartAt()).isNotNull();
		assertThat(foundedEvent.getApplyEndAt()).isNotNull();
		assertThat(foundedEvent.getStatus()).isEqualTo(EventStatus.CLOSED);
	}

	@Test
	void 행사_리스트_조회_성공() {
		var event1 = eventRepository.save(new Event("철호의 TDD1", "테스트 주도 개발1", EventStatus.PENDING, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)));
		var event2 = eventRepository.save(new Event("철호의 TDD2", "테스트 주도 개발2", EventStatus.PENDING, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(5)));

		eventRepository.save(new Event("철호의 TDD3", "테스트 주도 개발3", EventStatus.PENDING, LocalDateTime.now().plusDays(6), LocalDateTime.now().plusDays(8)));
		eventRepository.save(new Event("철호의 TDD4", "테스트 주도 개발4", EventStatus.PENDING, LocalDateTime.now().plusDays(6), LocalDateTime.now().plusDays(8)));
		eventRepository.save(new Event("철호의 TDD5", "테스트 주도 개발5", EventStatus.PENDING, LocalDateTime.now().plusDays(6), LocalDateTime.now().plusDays(8)));
		eventRepository.save(new Event("철호의 TDD6", "테스트 주도 개발6", EventStatus.PENDING, LocalDateTime.now().plusDays(6), LocalDateTime.now().plusDays(8)));
		eventRepository.save(new Event("철호의 TDD7", "테스트 주도 개발7", EventStatus.PENDING, LocalDateTime.now().plusDays(6), LocalDateTime.now().plusDays(8)));

		var events = eventService.getEvents(1, 2);

		행사_리스트_검증_성공(0, events.getContent(), event1);
		행사_리스트_검증_성공(1, events.getContent(), event2);
	}

	private void 행사_리스트_검증_성공(int index, List<Event> events, Event event) {
		assertThat(events.get(index).getId()).isEqualTo(event.getId());
		assertThat(events.get(index).getName()).isEqualTo(event.getName());
		assertThat(events.get(index).getContent()).isEqualTo(event.getContent());
		assertThat(events.get(index).getApplyStartAt()).isEqualTo(event.getApplyStartAt());
		assertThat(events.get(index).getApplyEndAt()).isEqualTo(event.getApplyEndAt());
	}
}