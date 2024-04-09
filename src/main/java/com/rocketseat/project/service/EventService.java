package com.rocketseat.project.service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.project.domain.Attendee;
import com.rocketseat.project.domain.Event;
import com.rocketseat.project.dto.AttendeeIdDTO;
import com.rocketseat.project.dto.AttendeeRequestDTO;
import com.rocketseat.project.dto.EventIdDTO;
import com.rocketseat.project.dto.EventRequestDTO;
import com.rocketseat.project.dto.EventResponseDTO;
import com.rocketseat.project.exception.EventFullException;
import com.rocketseat.project.exception.EventNotFoundException;
import com.rocketseat.project.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	private AttendeeService attendeeService;
	
	public EventResponseDTO getEventDetail(String eventId) {
		Event event = this.getEventById(eventId);
		List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);
		return new EventResponseDTO(event, attendeeList.size());
	}
	
	public EventIdDTO createEvent(EventRequestDTO eventDTO) {
		Event newEvent = new Event();
		
		newEvent.setTitle(eventDTO.title());
		newEvent.setDetails(eventDTO.details());
		newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
		newEvent.setSlug(this.createSlug(eventDTO.title()));
		
		this.eventRepository.save(newEvent);
		return new EventIdDTO(newEvent.getId());
	}
	
	public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO) {
		 this.attendeeService.verifyAttendeeSubscription(attendeeRequestDTO.email(), eventId);
		 
		 Event event = this.getEventById(eventId);
		 List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);
		 
		 if(event.getMaximumAttendees() <= attendeeList.size()) throw new EventFullException("Event is full!");

		 Attendee newAttendee = new Attendee();
		 newAttendee.setName(attendeeRequestDTO.name());
		 newAttendee.setEmail(attendeeRequestDTO.email());
		 newAttendee.setEvent(event);
		 newAttendee.setCreatedAt(LocalDateTime.now());
		 this.attendeeService.registerAttendee(newAttendee);
		 
		 return new AttendeeIdDTO(newAttendee.getId());
	}
	
	private Event getEventById(String eventId) {
		return this.eventRepository.findById(eventId).orElseThrow(
				 () -> new EventNotFoundException("Event not found with ID: " + eventId));
	}
	
	private String createSlug(String text) {
		String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
		return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
				.replaceAll("[^\\w\\s]", "")
				.replaceAll("\\s+", "-")
				.toLowerCase();
	}
}