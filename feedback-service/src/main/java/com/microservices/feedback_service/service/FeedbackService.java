package com.microservices.feedback_service.service;

import com.microservices.feedback_service.dto.FeedbackRequest;
import com.microservices.feedback_service.dto.FeedbackResponse;

import java.util.List;

public interface FeedbackService {
    FeedbackResponse create(Long userId, FeedbackRequest request);
    List<FeedbackResponse> getAll();
}
