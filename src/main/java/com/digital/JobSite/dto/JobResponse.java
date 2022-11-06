package com.digital.JobSite.dto;

import com.digital.JobSite.entity.Job;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {
    List<Job> jobs;
}
