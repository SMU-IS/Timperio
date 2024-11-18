package com.Timperio.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Timperio.service.impl.RolePermissionService;
import com.Timperio.repository.RolePermissionRepository;

import java.util.*;
import com.Timperio.models.RolePermission;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    public List<RolePermission> findAll() {
        return rolePermissionRepository.findAll();
    };

    
}
