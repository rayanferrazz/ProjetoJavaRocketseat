package com.rocketseat.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rocketseat.project.domain.Attendee;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, String> {
	
	List<Attendee> findByEventId(String eventId);
	Optional<Attendee> findByEventIdAndEmail(String eventId, String email);
}