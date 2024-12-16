package com.level.tech.controller;

import com.level.tech.dto.RoleDTO;
import com.level.tech.dto.request.RoleRequest;
import com.level.tech.dto.response.ResponseData;
import com.level.tech.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/role")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDTO> addRole(
            @RequestBody @Valid RoleRequest roleRequest
    ) {
        return new ResponseEntity<>(
                roleService.addRole(roleRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RoleDTO> updateRole(
            @RequestParam(name = "roleId") Integer roleId,
            @RequestBody @Valid RoleRequest roleRequest
    ) {
        return new ResponseEntity<>(
                roleService.updateRole(roleId, roleRequest), HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDTO> getRoleById(
            @PathVariable(name = "roleId") Integer roleId
    ) {
        return new ResponseEntity<>(
                roleService.getRoleById(roleId), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return new ResponseEntity<>(
                roleService.getAllRoles(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseData> deleteRoles(
            @RequestBody List<Integer> roleIds
    ) {
        roleService.deleteRoleByIds(roleIds);
        return new ResponseEntity<>(
               new ResponseData("Roles deleted successfully") , HttpStatus.OK);
    }

}
