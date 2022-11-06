package com.digital.JobSite.service.impl;

import com.digital.JobSite.dto.RoleDto;
import com.digital.JobSite.entity.Role;
import com.digital.JobSite.exception.ResourceAlreadyExistsException;
import com.digital.JobSite.exception.ResourceNotFoundException;
import com.digital.JobSite.repository.RoleRepository;
import com.digital.JobSite.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        Role role = roleRepository.findRoleByName(name);
        if(role == null) {
            throw new ResourceNotFoundException("Role with name: " + name + " not found");
        }
        return role;
    }

    // It may not be needed in future
    // For testing purposes only
    @Override
    public Role createNewRole(RoleDto role) {
        Role existedRole = roleRepository.findRoleByName(role.getName());
        if(existedRole != null) {
            throw new ResourceAlreadyExistsException("Role with name: "+ role.getName() + " already exists");
        }
        Role nRole = new Role();
        BeanUtils.copyProperties(role, nRole);
        return roleRepository.save(nRole);
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> list = new ArrayList<>();
        roleRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }


    @PostConstruct
    public void initRoles() {
        Role role = roleRepository.findRoleByName("Recruiter");
        if(role == null) {
            role = Role.builder().name("Recruiter").description("Recruiter Role").build();
            roleRepository.save(role);
        }
        role = roleRepository.findRoleByName("Candidate");
        if(role == null) {
            role = Role.builder().name("Candidate").description("Candidate Role").build();
            roleRepository.save(role);
        }
    }
}
