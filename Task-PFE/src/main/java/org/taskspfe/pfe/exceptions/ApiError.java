package org.taskspfe.pfe.exceptions;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ApiError {
    private HttpStatus status;
    private boolean statusString = false;
    private LocalDateTime timestamp;
    private String message;
    private List errors;
}
