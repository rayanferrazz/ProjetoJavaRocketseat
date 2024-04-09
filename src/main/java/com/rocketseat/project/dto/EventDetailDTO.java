package com.rocketseat.project.dto;

public record EventDetailDTO(String id, String title, String details, 
		String slug, Integer maximumAttendees, Integer attendeesAmount) {
}