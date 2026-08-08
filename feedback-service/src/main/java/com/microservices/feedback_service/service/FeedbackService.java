package com.microservices.feedback_service.service;

import com.microservices.feedback_service.dto.FeedbackRequest;
import com.microservices.feedback_service.dto.FeedbackResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {
    FeedbackResponse create(Long userId, FeedbackRequest request);
    Page<FeedbackResponse> getAll(Pageable pageable);
}
