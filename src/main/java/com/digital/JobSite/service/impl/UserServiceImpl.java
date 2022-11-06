package com.digital.JobSite.service.impl;

import com.digital.JobSite.dto.Application;
import com.digital.JobSite.dto.UserDto;
import com.digital.JobSite.entity.Job;
import com.digital.JobSite.entity.Role;
import com.digital.JobSite.entity.User;
import com.digital.JobSite.exception.ResourceAlreadyExistsException;
import com.digital.JobSite.exception.ResourceNotFoundException;
import com.digital.JobSite.repository.UserRepository;
import com.digital.JobSite.service.JobService;
import com.digital.JobSite.service.RoleService;
import com.digital.JobSite.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JobService jobService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    @Override
    public User save(UserDto user) {

        User existingUser = userRepository.findByUsername(user.getUsername());
        if(existingUser != null) {
            throw new ResourceAlreadyExistsException("User with username: " + user.getUsername() + " already exists");
        }

        User nUser = new User();
        BeanUtils.copyProperties(user, nUser);
        nUser.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        nUser.setRoles(roleSet);

        return userRepository.save(nUser);
    }

    @Override
    public User saveRecruiter(UserDto user) {

        User existingUser = userRepository.findByUsername(user.getUsername());
        if(existingUser != null) {
            throw new ResourceAlreadyExistsException("User with username: " + user.getUsername() + " already exists");
        }

        User nUser = new User();
        BeanUtils.copyProperties(user, nUser);
        nUser.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleService.findByName("RECRUITER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        nUser.setRoles(roleSet);

        return userRepository.save(nUser);
    }


    @Override
    public User saveCandidate(UserDto user) {

        User existingUser = userRepository.findByUsername(user.getUsername());
        if(existingUser != null) {
            throw new ResourceAlreadyExistsException("User with username: " + user.getUsername() + " already exists");
        }

        User nUser = new User();
        BeanUtils.copyProperties(user, nUser);
        nUser.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleService.findByName("CANDIDATE");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        nUser.setRoles(roleSet);

        return userRepository.save(nUser);
    }



    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User updateUser(UserDto userDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        user.setUsername(userDto.getUsername() == null ? user.getUsername() : userDto.getUsername());
        user.setEmail(userDto.getEmail() == null ? user.getEmail() : userDto.getEmail());
        user.setName(userDto.getName() == null ? user.getName() : userDto.getName());
        user.setPhone(userDto.getPhone() == null ? user.getPhone() : userDto.getPhone());
        user.setSkills(userDto.getSkills() == null ? user.getSkills() : userDto.getSkills());
        return userRepository.save(user);
    }

    @Override
    public User findOne(String username) {
        return Optional.of(userRepository.findByUsername(username))
                .orElseThrow(() -> new ResourceNotFoundException("User with username: " + username + " not found"));
    }

    @Override
    public boolean applyJob(long userId, long jobId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with userid: " + userId + " not found"));
        Job job = jobService.getJobById(jobId);
        if(user.getApplications().contains(job)) {
            return false;
        }
        user.getApplications().add(job);
        userRepository.save(user);
        return true;
    }

    @Override
    public List<Application> getApplications(String username) {
        User user = findOne(username);
        return new ArrayList(user.getApplications());
    }

    @Override
    public boolean isJobAlreadyApplied(long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        Job job = jobService.getJobById(id);
        return user.getApplications().contains(job);
    }

    @Override
    public List<Job> getJobsOfUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        List<Job> jobs = new ArrayList<>(user.getJobPosts());
        return jobs;
    }

    @Override
    public void deleteUser(long id) {
        if(userRepository.findById(id).isPresent()) {
            Set<Role> roles = userRepository.findById(id).get().getRoles();
            for(Role role: roles) {
                if(role.getName().equals("Admin")) {
                    return;
                }
            }
            userRepository.delete(userRepository.findById(id).get());
        }

    }
}
