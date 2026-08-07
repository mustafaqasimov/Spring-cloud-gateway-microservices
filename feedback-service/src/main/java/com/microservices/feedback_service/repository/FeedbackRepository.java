package com.microservices.feedback_service.repository;

import com.microservices.feedback_service.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
