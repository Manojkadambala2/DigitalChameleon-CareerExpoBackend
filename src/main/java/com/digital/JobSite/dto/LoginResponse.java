package com.digital.JobSite.dto;

import com.digital.JobSite.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginResponse {

    private User user;
    private String authToken;
}
