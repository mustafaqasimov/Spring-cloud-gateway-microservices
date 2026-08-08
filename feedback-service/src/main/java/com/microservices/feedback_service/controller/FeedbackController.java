package com.microservices.feedback_service.controller;

import com.microservices.feedback_service.dto.FeedbackRequest;
import com.microservices.feedback_service.dto.FeedbackResponse;
import com.microservices.feedback_service.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "Submit and list feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Operation(summary = "Submit feedback", description = "Submits a new feedback entry")
    @PostMapping
    public ResponseEntity<FeedbackResponse> create(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody FeedbackRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.create(userId, request));
    }

    @Operation(summary = "List all feedback entries", description = "Retrieves all feedback entries")
    @GetMapping
    public Page<FeedbackResponse> getAll(
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable
    ) {
        return feedbackService.getAll(pageable);
    }
}
