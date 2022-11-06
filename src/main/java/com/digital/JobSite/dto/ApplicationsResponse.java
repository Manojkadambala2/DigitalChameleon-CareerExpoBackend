package com.digital.JobSite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApplicationsResponse {

    private List<Application> applications;

}
