package com.example.grocery.api.category;

import com.example.grocery.api.user.User;
import com.example.grocery.api.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CategoryDTO dto) {
        categoryService.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Category>> getAll(){
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable String id) {
        var entity = categoryService.getById(id);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable String id, @RequestBody CategoryDTO dto){
        categoryService.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id){
        categoryService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
