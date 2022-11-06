package com.digital.JobSite.service;

import com.digital.JobSite.dto.RoleDto;
import com.digital.JobSite.entity.Role;

import java.util.List;

public interface RoleService {

    Role findByName(String name);

    Role createNewRole(RoleDto role);

    List<Role> getAllRoles();
}
