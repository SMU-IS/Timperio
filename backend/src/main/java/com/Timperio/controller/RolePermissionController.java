package com.Timperio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Timperio.constant.UrlConstant;
import com.Timperio.enums.Role;
import com.Timperio.enums.SuccessMessage;
import com.Timperio.exceptions.AdminAccountUpdateException;
import com.Timperio.exceptions.InvalidRoleException;
import com.Timperio.models.Permission;
import com.Timperio.models.RolePermission;
import com.Timperio.service.impl.RolePermissionService;
import com.Timperio.responses.GetAllRolePermissionResponse;

@RequestMapping(UrlConstant.API_VERSION + "/permission")
@RestController

public class RolePermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @PreAuthorize("hasAuthority('MANAGE USER ACCOUNTS')")
    @GetMapping()
    public ResponseEntity<List<GetAllRolePermissionResponse>> getRolePermissions() {
        List<GetAllRolePermissionResponse> permissionList = new ArrayList<>();
        Map<String, Set<Role>> roleMap = new HashMap<>();

        List<RolePermission> rpList = this.rolePermissionService.findAll();
        for (RolePermission rp: rpList) {
            Set<Role> roleSet;
            Role role = rp.getRole();
            Permission perm = rp.getPermission();
            String permName = perm.getName();
            
            if (roleMap.containsKey(permName)) {
                roleSet = roleMap.get(permName);                
            } else {
                roleSet = new HashSet<>(); 
            }

            roleSet.add(role);
            roleMap.put(permName, roleSet);
        }

        roleMap.forEach((key, value) -> {
            GetAllRolePermissionResponse row = new GetAllRolePermissionResponse();
            row.setAction(key);
            row.setRole(new ArrayList<>(value));
            permissionList.add(row);
        });

        return ResponseEntity.status(HttpStatus.OK).body(permissionList);
    }

}
