package com.digital.JobSite.controller;

import com.digital.JobSite.dto.JobDto;
import com.digital.JobSite.dto.JobResponse;
import com.digital.JobSite.entity.Job;
import com.digital.JobSite.entity.User;
import com.digital.JobSite.service.JobService;
import com.digital.JobSite.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('Candidate')")
    @GetMapping
    public ResponseEntity<JobResponse> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        JobResponse jobResponse = new JobResponse(jobs);
        return new ResponseEntity<>(jobResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Candidate')")
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable("id") long id) {
        Job job = jobService.getJobById(id);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Recruiter')")
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody JobDto jobDto) {
        Job job = jobService.createJob(jobDto);
        return new ResponseEntity<>(job, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('Recruiter')")
    @PutMapping("/{jobId}")
    public ResponseEntity<Job> updateJob(@PathVariable("jobId") long id, @RequestBody JobDto jobDto) {
        Job job = jobService.updateJob(id, jobDto);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Recruiter')")
    @DeleteMapping("/{jobId}")
    public ResponseEntity<?> deleteJob(@PathVariable("jobId") long id) {
        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Candidate')")
    @GetMapping("/search")
    public ResponseEntity<JobResponse> searchJobs(@RequestParam String q) {
        JobResponse jobResponse = new JobResponse(jobService.searchJobs(q));
        return new ResponseEntity<>(jobResponse, HttpStatus.OK);
    }

}
