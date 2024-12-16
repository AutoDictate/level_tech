package com.level.tech.service;

import com.level.tech.dto.RoleDTO;
import com.level.tech.dto.request.RoleRequest;
import com.level.tech.entity.Role;
import com.level.tech.exception.AlreadyExistsException;
import com.level.tech.exception.BadCredentialException;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public RoleDTO addRole(final RoleRequest roleRequest) {
        if (roleRepository.findByName(roleRequest.getName().toUpperCase()) != null) {
            throw new AlreadyExistsException("Role already exists");
        }
        Role role = new Role(roleRequest.getName());
        var savedRole = roleRepository.save(role);
        return toRoleDto(savedRole);
    }

    @Override
    @Transactional
    public RoleDTO updateRole(
            final Integer roleId,
            final RoleRequest roleRequest
    ) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(()-> new EntityNotFoundException("Role not found"));
        role.setName(roleRequest.getName());
        var savedRole = roleRepository.save(role);
        return toRoleDto(savedRole);
    }

    @Override
    public RoleDTO getRoleById(final Integer roleId) {
        return roleRepository.findById(roleId)
                .map(this::toRoleDto)
                .orElseThrow(()-> new EntityNotFoundException("Role not found"));
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(this::toRoleDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteRole(final Integer roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(()-> new EntityNotFoundException("Role not found"));
        roleRepository.delete(role);
    }

    @Override
    public void deleteRoleByIds(final List<Integer> roleIds) {
        if (roleIds.isEmpty()) {
            throw new BadCredentialException("Atleast select one role to delete");
        }
        roleIds.forEach(this::deleteRole);
    }

    private RoleDTO toRoleDto(final Role role) {
        return new RoleDTO(
                role.getId(),
                role.getName()
        );
    }
}
