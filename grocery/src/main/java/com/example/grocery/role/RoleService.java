package com.example.grocery.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    void create(RoleDTO entity){
        roleRepository.save(RoleMapper.dtoToEntity(entity));
    }

    List<Role> getAll(){
        return roleRepository.findAll();
    }

    Role getById(String id){
        var ans = roleRepository.findById(id);
        return ans.orElse(null);
    }

    void update(String id, RoleDTO dto){
        var oldRole = this.getById(id);
        if(oldRole == null) return;

        oldRole.setName(dto.getName());
        roleRepository.save(oldRole);
    }

    void delete(String id){
        var role = this.getById(id);
        roleRepository.delete(role);
    }
}
