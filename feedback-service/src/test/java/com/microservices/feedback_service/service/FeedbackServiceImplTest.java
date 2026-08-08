package com.microservices.feedback_service.service;

import com.microservices.feedback_service.dto.FeedbackRequest;
import com.microservices.feedback_service.dto.FeedbackResponse;
import com.microservices.feedback_service.entity.Feedback;
import com.microservices.feedback_service.mapper.FeedbackMapper;
import com.microservices.feedback_service.repository.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceImplTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private FeedbackMapper feedbackMapper;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    @Test
    void create_shouldSaveFeedback() {

        Long userId = 1L;

        FeedbackRequest request =
                new FeedbackRequest("Very good!");

        Feedback feedback = new Feedback();

        FeedbackResponse response =
                new FeedbackResponse();

        when(feedbackMapper.toEntity(request))
                .thenReturn(feedback);

        when(feedbackRepository.save(feedback))
                .thenReturn(feedback);

        when(feedbackMapper.toResponse(feedback))
                .thenReturn(response);

        FeedbackResponse result =
                feedbackService.create(userId, request);

        verify(feedbackMapper).toEntity(request);
        verify(feedbackRepository).save(feedback);
        verify(feedbackMapper).toResponse(feedback);

        assertEquals(response, result);
        assertEquals(userId, feedback.getUserId());
    }

    @Test
    void getAll_shouldReturnPagedFeedbackList() {
        Pageable pageable = PageRequest.of(0, 20);
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();
        FeedbackResponse response1 = new FeedbackResponse();
        FeedbackResponse response2 = new FeedbackResponse();

        Page<Feedback> feedbackPage = new PageImpl<>(List.of(feedback1, feedback2), pageable, 2);

        when(feedbackRepository.findAll(pageable)).thenReturn(feedbackPage);
        when(feedbackMapper.toResponse(feedback1)).thenReturn(response1);
        when(feedbackMapper.toResponse(feedback2)).thenReturn(response2);

        Page<FeedbackResponse> result = feedbackService.getAll(pageable);

        verify(feedbackRepository).findAll(pageable);
        assertEquals(2, result.getTotalElements());
        assertEquals(response1, result.getContent().get(0));
        assertEquals(response2, result.getContent().get(1));
    }
}
