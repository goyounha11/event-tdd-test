package com.sparta.event.controller;

import com.sparta.event.controller.dto.EventRequest;
import com.sparta.event.controller.dto.EventResponse;
import com.sparta.event.controller.dto.EventResponse.List;
import com.sparta.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

	private final EventService eventService;

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody EventRequest.Create request) {

		eventService.create(request.name(), request.content(), request.applyStartAt(), request.applyEndAt());

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping
	public ResponseEntity<Page<List>> getEvents(@RequestParam int page, @RequestParam(defaultValue = "10") int size) {
		var events = eventService.getEvents(page, size);
		return ResponseEntity.ok(events.map(List::new));
	}
}
