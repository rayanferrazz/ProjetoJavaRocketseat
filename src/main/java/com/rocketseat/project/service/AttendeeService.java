package com.rocketseat.project.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.rocketseat.project.domain.Attendee;
import com.rocketseat.project.domain.CheckIn;
import com.rocketseat.project.dto.AttendeeBadgeDTO;
import com.rocketseat.project.dto.AttendeeBadgeResponseDTO;
import com.rocketseat.project.dto.AttendeeDetailDTO;
import com.rocketseat.project.dto.AttendeeListResponseDTO;
import com.rocketseat.project.exception.AttendeeAlreadyExistException;
import com.rocketseat.project.exception.AttendeeNotFoundException;
import com.rocketseat.project.repository.AttendeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendeeService {

	@Autowired
	private AttendeeRepository attendeeRepository;
	
	private CheckInService checkInService;

	public List<Attendee> getAllAttendeesFromEvent(String eventId) {
		return this.attendeeRepository.findByEventId(eventId);
	}
	
	public AttendeeListResponseDTO getEventsAttendee(String eventId) {
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetailDTO> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
            return new AttendeeDetailDTO(attendee.getId(), attendee.getName(), attendee.getEmail(), 
            		attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeeListResponseDTO(attendeeDetailsList);
    }
	
	public void verifyAttendeeSubscription(String email, String eventId) {
		Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
		if(isAttendeeRegistered.isPresent()) throw new AttendeeAlreadyExistException("Attendee is already registered!");
	}
	
	public Attendee registerAttendee(Attendee newAttendee) {
		this.attendeeRepository.save(newAttendee);
		return newAttendee;
	}
	
	public void checkInAttendee(String attendeeId) {
        Attendee attendee = this.getAttendee(attendeeId);
        this.checkInService.registerCheckIn(attendee);
    }

    private Attendee getAttendee(String attendeeId) {
        return this.attendeeRepository.findById(attendeeId).orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with ID: " + attendeeId));
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        Attendee attendee = this.getAttendee(attendeeId);
    
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();

        AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri, attendee.getEvent().getId());
        return new AttendeeBadgeResponseDTO(attendeeBadgeDTO);
    }
}