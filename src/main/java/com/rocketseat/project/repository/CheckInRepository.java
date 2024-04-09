package com.rocketseat.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rocketseat.project.domain.CheckIn;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
	
	Optional<CheckIn> findByAttendeeId(String attendeeId);
}