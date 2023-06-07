package com.example.grocery.api.category;

import com.example.grocery.api.user.User;
import com.example.grocery.api.user.UserDTO;
import com.example.grocery.enums.RoleType;
import com.example.grocery.utils.JwtUtils;
import com.example.grocery.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

import static com.example.grocery.utils.Messages.noAccessAllowed;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping
    public ResponseEntity<Response<Category>> create(
        @RequestHeader("Authorization") String bearerToken,
        @RequestBody CategoryDTO dto
    ) {
        if (!jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN)) {
            Response<Category> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }

        Response<Category> response = categoryService.create(dto);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @GetMapping
    public ResponseEntity<Response<List<Category>>> getAll(){
        Response<List<Category>> response = categoryService.getAll();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Category>> getById(@PathVariable String id) {
        Response<Category> response = categoryService.getById(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response<Category>> update(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id,
            @RequestBody CategoryDTO dto){
        if (!jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN)) {
            Response<Category> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }

        Response<Category> response = categoryService.update(id, dto);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Category>> delete(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id){
        if (!jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN)) {
            Response<Category> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }

        Response<Category> response = categoryService.delete(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
