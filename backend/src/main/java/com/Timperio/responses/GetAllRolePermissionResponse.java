package com.Timperio.responses;

import com.Timperio.enums.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetAllRolePermissionResponse {
    private String action;
    private List<Role> role;
}
