package com.sparta.event.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.event.controller.dto.EventRequest;
import com.sparta.event.domain.Event;
import com.sparta.event.domain.EventStatus;
import com.sparta.event.repository.EventRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

// 인수테스트 -> E2E 테스트
// mockMvc
@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private EventRepository eventRepository;

	@Test
	void 행사_생성_성공() throws Exception {
		var request = new EventRequest.Create(
			"철호의 TDD",
			"테스트 주도 개발",
			LocalDateTime.now().plusDays(1),
			LocalDateTime.now().plusDays(2)
		);

		mockMvc.perform(post("/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
			)
			.andDo(print())
			.andExpect(status().isCreated());

		var foundedEvents = eventRepository.findAll();

		assertThat(foundedEvents).hasSize(1);
	}

	@Test
	void 행사_목록_조회_성공() throws Exception{
		var event1 = eventRepository.save(new Event("철호의 TDD1", "테스트 주도 개발1", EventStatus.PENDING, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)));
		var event2 = eventRepository.save(new Event("철호의 TDD2", "테스트 주도 개발2", EventStatus.PENDING, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(5)));

		eventRepository.save(new Event("철호의 TDD3", "테스트 주도 개발3", EventStatus.PENDING, LocalDateTime.now().plusDays(6), LocalDateTime.now().plusDays(8)));
		eventRepository.save(new Event("철호의 TDD4", "테스트 주도 개발4", EventStatus.PENDING, LocalDateTime.now().plusDays(6), LocalDateTime.now().plusDays(8)));
		eventRepository.save(new Event("철호의 TDD5", "테스트 주도 개발5", EventStatus.PENDING, LocalDateTime.now().plusDays(6), LocalDateTime.now().plusDays(8)));
		eventRepository.save(new Event("철호의 TDD6", "테스트 주도 개발6", EventStatus.PENDING, LocalDateTime.now().plusDays(6), LocalDateTime.now().plusDays(8)));
		eventRepository.save(new Event("철호의 TDD7", "테스트 주도 개발7", EventStatus.PENDING, LocalDateTime.now().plusDays(6), LocalDateTime.now().plusDays(8)));

		mockMvc.perform(get("/events")
				.param("page", "1")
				.param("size", "2")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpectAll(행사_리스트_검증(0, event1))
			.andExpectAll(행사_리스트_검증(1, event2))
		;
	}

	private ResultMatcher[] 행사_리스트_검증(int index, Event event) {
		return List.of(
			jsonPath("$.content[" + index + "].id").value(event.getId()),
		jsonPath("$.content[" + index + "].name").value(event.getName()),
		jsonPath("$.content[" + index + "].status").value(event.getStatus().name())
		).toArray(ResultMatcher[]::new);
	}
}