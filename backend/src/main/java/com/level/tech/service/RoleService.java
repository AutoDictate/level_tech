package com.level.tech.service;

import com.level.tech.dto.RoleDTO;
import com.level.tech.dto.request.RoleRequest;

import java.util.List;

public interface RoleService {

    RoleDTO addRole(RoleRequest roleRequest);

    RoleDTO updateRole(Integer roleId, RoleRequest roleRequest);

    RoleDTO getRoleById(Integer roleId);

    List<RoleDTO> getAllRoles();

    void deleteRole(Integer roleId);

    void deleteRoleByIds(List<Integer> roleIds);

}
