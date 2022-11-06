package com.digital.JobSite.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column
    @JsonIgnore
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String phone;

    @Column
    private String name;

    private String skills;

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public User(Long id, String username, String password, String email, String phone, String name, String skills, String resume, Set<Role> roles, Set<Job> jobPosts, Set<Job> applications) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.skills = skills;
        this.resume = resume;
        this.roles = roles;
        this.jobPosts = jobPosts;
        this.applications = applications;
    }

    public User(Long id, String username, String password, String email, String phone, String name, String resume, Set<Role> roles, Set<Job> jobPosts, Set<Job> applications) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.resume = resume;
        this.roles = roles;
        this.jobPosts = jobPosts;
        this.applications = applications;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }


    private String resume;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES", joinColumns = {
            @JoinColumn(name = "USER_ID")
        }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID")
        }
    )
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonBackReference
    private Set<Job> jobPosts;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_JOB", joinColumns = {
            @JoinColumn(name = "USER_ID")
    }, inverseJoinColumns = {
            @JoinColumn(name = "JOB_ID")
    }
    )
    @JsonBackReference
    private Set<Job> applications;

    public User(Long id, String username, String password, String email, String phone, String name, Set<Role> roles, Set<Job> jobPosts, Set<Job> applications) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.roles = roles;
        this.jobPosts = jobPosts;
        this.applications = applications;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", roles=" + roles +
                ", jobPosts=" + jobPosts +
                ", applications=" + applications +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Job> getJobPosts() {
        return jobPosts;
    }

    public void setJobPosts(Set<Job> jobPosts) {
        this.jobPosts = jobPosts;
    }

    public Set<Job> getApplications() {
        return applications;
    }

    public void setApplications(Set<Job> applications) {
        this.applications = applications;
    }
}
