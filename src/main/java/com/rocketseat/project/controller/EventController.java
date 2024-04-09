package com.rocketseat.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.rocketseat.project.dto.AttendeeListResponseDTO;
import com.rocketseat.project.dto.EventIdDTO;
import com.rocketseat.project.dto.EventRequestDTO;
import com.rocketseat.project.dto.EventResponseDTO;
import com.rocketseat.project.service.AttendeeService;
import com.rocketseat.project.service.EventService;

@RestController
@RequestMapping("/events")
public class EventController {

	private EventService eventService;
	private AttendeeService attendeeService;
	
	public EventController(EventService eventService, AttendeeService attendeeService) {
		this.eventService = eventService;
		this.attendeeService = attendeeService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {
		EventResponseDTO event = this.eventService.getEventDetail(id);
		return ResponseEntity.ok(event);
	}
	
	@PostMapping
	public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
		EventIdDTO eventIdDTO = this.eventService.createEvent(body);
		
		var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();
		
		return ResponseEntity.created(uri).body(eventIdDTO);
	}
	
	@GetMapping("/attendees/{id}")
	public ResponseEntity<AttendeeListResponseDTO> getEventsAttendee(@PathVariable String id) {
		AttendeeListResponseDTO attendee= this.attendeeService.getEventsAttendee(id);
		return ResponseEntity.ok(attendee);
	}
}