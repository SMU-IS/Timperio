package com.Timperio.service.impl;

import java.util.*;
import com.Timperio.models.RolePermission;
import org.springframework.stereotype.Service;

@Service
public interface RolePermissionService {
    public List<RolePermission> findAll();
}
