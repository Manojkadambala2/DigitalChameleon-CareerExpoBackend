package com.digital.JobSite.controller;

import com.digital.JobSite.dto.*;
import com.digital.JobSite.entity.Job;
import com.digital.JobSite.entity.Role;
import com.digital.JobSite.entity.User;
import com.digital.JobSite.repository.RoleRepository;
import com.digital.JobSite.service.JobService;
import com.digital.JobSite.service.RoleService;
import com.digital.JobSite.service.UserService;
import com.digital.JobSite.util.FileUtil;
import com.digital.JobSite.util.JwtUtil;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JobService jobService;

    @Value("${storage.path}")
    private String path;

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateToken(authentication);

        User user = userService.findOne(loginUser.getUsername());

        LoginResponse response = new LoginResponse();
        response.setUser(user);
        response.setAuthToken(token);
        Set<String> roles = new HashSet<>();
        user.getRoles().forEach((role) -> roles.add(role.getName()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/recruiter/authenticate")
    public ResponseEntity<?> generateTokenForRecruiter(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_Recruiter");
        if(!authentication.getAuthorities().contains(authority)) {
            throw new AuthenticationException("Recruiter not found") {};
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateToken(authentication);

        User user = userService.findOne(loginUser.getUsername());

        LoginResponse response = new LoginResponse();
        response.setUser(user);
        response.setAuthToken(token);
        Set<String> roles = new HashSet<>();
        user.getRoles().forEach((role) -> roles.add(role.getName()));
        return ResponseEntity.ok(response);
    }


    @PostMapping("/candidate/authenticate")
    public ResponseEntity<?> generateTokenForCandidate(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_Candidate");
        if(!authentication.getAuthorities().contains(authority)) {
            throw new AuthenticationException("Candidate not found") {};
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateToken(authentication);

        User user = userService.findOne(loginUser.getUsername());

        LoginResponse response = new LoginResponse();
        response.setUser(user);
        response.setAuthToken(token);
        Set<String> roles = new HashSet<>();
        user.getRoles().forEach((role) -> roles.add(role.getName()));
        return ResponseEntity.ok(response);
    }


    @PostMapping("/recruiter/register")
    public ResponseEntity<User> saveRecruiter(@RequestBody UserDto userDto) {
        User user = userService.saveRecruiter(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/candidate/register")
    public ResponseEntity<User> saveCandidate(@RequestBody UserDto userDto) {
        User user = userService.saveCandidate(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping
    public User updateUser(@RequestBody UserDto user) {
        return userService.updateUser(user);
    }

//    @DeleteMapping("/{userId}")
//    public void deleteUser(@PathVariable("userId") long id) {
//        userService.deleteUser(id);
//    }

    @PreAuthorize("hasRole('Candidate')")
    @PostMapping("/candidate/{jobId}")
    public ResponseEntity<AppliedResponse> applyToJob(@PathVariable("jobId") long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findOne(username);
        boolean applied = userService.applyJob(user.getId(), id);
        return applied ?
                new ResponseEntity<>(new AppliedResponse(true, "Applied Successfully"),
                        HttpStatus.OK) :
                new ResponseEntity<>(new AppliedResponse(true, "Already applied"),
                        HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasRole('Candidate')")
    @GetMapping("/candidate_ping")
    public String candidatePing(){
        return "Any User Can Read This";
    }

    @PreAuthorize("hasRole('Recruiter')")
    @GetMapping("/recruiter_ping")
    public String recruiterPing(){
        return "Only admin User Can Read This";
    }


    @PreAuthorize("hasRole('Candidate')")
    @GetMapping("/candidate/applied_jobs")
    public ResponseEntity<ApplicationsResponse> getApplications() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Application> applications = userService.getApplications(username);
        ApplicationsResponse response = new ApplicationsResponse(applications);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Candidate')")
    @GetMapping("/candidate/isApplied/{jobId}")
    public ResponseEntity<?> isAlreadyApplied(@PathVariable("jobId") long id) {
        boolean res = userService.isJobAlreadyApplied(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Candidate')")
    @PostMapping("/candidate/upload")
    public ResponseEntity<?> uploadResume(@RequestBody MultipartFile file) throws IOException {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        FileUtil.saveResume(path, file, username);
//        TODO upload resume
        return null;
    }

    @PreAuthorize("hasRole('Recruiter')")
    @GetMapping("/jobs")
    public ResponseEntity<JobResponse> getJobsOfRecruiter() {
        List<Job> jobs = userService.getJobsOfUser();
        return new ResponseEntity<>(new JobResponse(jobs), HttpStatus.OK);
    }
}
