package com.rocketseat.project.dto;

import java.time.LocalDateTime;

public record AttendeeDetailDTO(String id, String name, String email, LocalDateTime createAt,
		LocalDateTime checkedInAt) {
}