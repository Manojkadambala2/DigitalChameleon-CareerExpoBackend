package com.digital.JobSite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String jobRole;

    private String company;

    private double experience;

    private double salary;

    private String location;

    private String skills;

    private String description;

    @ManyToOne
    @JsonManagedReference
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "applications")
    @JsonManagedReference
    @LazyToOne(LazyToOneOption.NO_PROXY)
    private Set<User> applicants;
}
