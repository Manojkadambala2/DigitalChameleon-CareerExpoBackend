package com.digital.JobSite.service;

import com.digital.JobSite.dto.Application;
import com.digital.JobSite.dto.UserDto;
import com.digital.JobSite.entity.Job;
import com.digital.JobSite.entity.User;

import java.util.List;

public interface UserService {

    User save(UserDto user);

    User saveRecruiter(UserDto user);

    User saveCandidate(UserDto user);

    List<User> findAll();

    User updateUser(UserDto user);

    void deleteUser(long id);

    User findOne(String username);

    boolean applyJob(long userId, long jobId);

    List<Application> getApplications(String username);

    boolean isJobAlreadyApplied(long id);

    List<Job> getJobsOfUser();
}
