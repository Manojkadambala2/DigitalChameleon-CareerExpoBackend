package com.digital.JobSite.service;


import com.digital.JobSite.dto.JobDto;
import com.digital.JobSite.entity.Job;

import java.util.List;

public interface JobService {

    Job createJob(JobDto jobDto);

    Job updateJob(long id, JobDto jobDto);

    boolean deleteJob(long id);

    List<Job> getAllJobs();

    Job getJobById(long id);

    List<Job> searchJobs(String skillsText);
}
