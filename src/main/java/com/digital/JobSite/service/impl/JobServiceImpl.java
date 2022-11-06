package com.digital.JobSite.service.impl;

import com.digital.JobSite.dto.JobDto;
import com.digital.JobSite.entity.Job;
import com.digital.JobSite.entity.User;
import com.digital.JobSite.exception.ResourceNotFoundException;
import com.digital.JobSite.repository.JobRepository;
import com.digital.JobSite.service.JobService;
import com.digital.JobSite.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserService userService;

    @Override
    public Job createJob(JobDto jobDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findOne(username);
        Job job = new Job();
        job.setUser(user);
        BeanUtils.copyProperties(jobDto, job);
        return jobRepository.save(job);
    }

    @Override
    public Job updateJob(long id, JobDto jobDto) {
        Job job = jobRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Job with id " + id + " not found"));
        BeanUtils.copyProperties(jobDto, job);
        return jobRepository.save(job);
    }

    @Override
    public boolean deleteJob(long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Job with id " + id + " not found"));
        jobRepository.delete(job);
        return true;
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public Job getJobById(long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job with id " + id + " not found"));
    }

    @Override
    public List<Job> searchJobs(String skillsText) {
        return jobRepository.search(skillsText);
    }
}
