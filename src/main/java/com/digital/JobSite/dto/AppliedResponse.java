package com.digital.JobSite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppliedResponse {
    private boolean applied;
    private String message;
}
