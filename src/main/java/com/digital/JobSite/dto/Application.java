package com.digital.JobSite.dto;

import com.digital.JobSite.entity.Job;
import com.digital.JobSite.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    private User user;
    private Job job;

}
