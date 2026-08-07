package com.microservices.feedback_service.service;

import com.microservices.feedback_service.dto.FeedbackRequest;
import com.microservices.feedback_service.dto.FeedbackResponse;
import com.microservices.feedback_service.entity.Feedback;
import com.microservices.feedback_service.mapper.FeedbackMapper;
import com.microservices.feedback_service.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    @Override
    public FeedbackResponse create(Long userId, FeedbackRequest request) {
        Feedback feedback = feedbackMapper.toEntity(request);
        feedback.setUserId(userId);
        feedback = feedbackRepository.save(feedback);
        return feedbackMapper.toResponse(feedback);
    }

    @Override
    public List<FeedbackResponse> getAll() {

        List<Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks.stream().map(feedbackMapper::toResponse).toList();
    }
}
