package com.testehan.SpringMVCExperiments.repository;

import com.testehan.SpringMVCExperiments.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
