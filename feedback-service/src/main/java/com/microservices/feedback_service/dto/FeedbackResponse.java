package com.microservices.feedback_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Feedback response")
public class FeedbackResponse {
    @Schema(description = "Feedback ID")
    Long id;
    @Schema(description = "User ID")
    Long userId;
    @Schema(description = "Feedback message")
    String message;
    @Schema(description = "Creation timestamp")
    LocalDateTime createdAt;
}
