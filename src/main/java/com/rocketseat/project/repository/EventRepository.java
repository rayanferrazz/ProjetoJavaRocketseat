package com.rocketseat.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rocketseat.project.domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
}