package com.example.grocery.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAll() {
        List<Role> roles = roleService.getAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Role> getById(@PathVariable String id) {
        var role = roleService.getById(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Role> create(@RequestBody RoleDTO roleDTO) {
        roleService.create(roleDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable String id,@RequestBody RoleDTO dto){
        roleService.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/id")
    public ResponseEntity delete(@PathVariable String id){
        roleService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
