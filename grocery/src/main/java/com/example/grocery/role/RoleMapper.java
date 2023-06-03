package com.example.grocery.role;

import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public static Role dtoToEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        return role;
    }

    public static RoleDTO entityToDto(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(role.getName());
        return roleDTO;
    }
}
