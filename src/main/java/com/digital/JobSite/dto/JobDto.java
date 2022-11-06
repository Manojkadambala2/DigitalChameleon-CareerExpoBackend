package com.digital.JobSite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDto {

    private String jobRole;

    private String company;

    private double experience;

    private double salary;

    private String location;

    private String skills;

    private String description;
}
