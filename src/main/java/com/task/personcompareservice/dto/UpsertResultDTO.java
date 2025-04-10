package com.task.personcompareservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertResultDTO {

    @NotBlank(message = "Person id must not be blank")
    private String personId;

    @NotBlank(message = "Task id must not be blank")
    private String taskId;

    @NotBlank(message = "Message must not be blank")
    private String message;
}
