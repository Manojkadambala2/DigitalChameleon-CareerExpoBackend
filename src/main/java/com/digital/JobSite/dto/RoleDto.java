package com.digital.JobSite.dto;

import com.digital.JobSite.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private String name;
    private String description;

}
