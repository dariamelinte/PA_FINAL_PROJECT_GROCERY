package com.example.grocery.api.user;

import com.example.grocery.enums.RoleType;
import com.example.grocery.utils.JwtUtils;
import com.example.grocery.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static com.example.grocery.utils.Messages.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    JwtUtils jwtUtils;
    
    @GetMapping
    public ResponseEntity<Response<List<User>>> getAll(
            @RequestHeader("Authorization") String bearerToken){
        if (!jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN)) {
            Response<List<User>> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }
        
        Response<List<User>> response = userService.getAll();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<User>> getById(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id) {
        if (!jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN) &&
                !jwtUtils.isUserAuthorized(id, bearerToken)) {
            Response<User> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }
        
        Response<User> response = userService.getById(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/roles/{roleType}")
    public ResponseEntity<Response<List<User>>> getByRole(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String roleType) {
        if (!jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN)) {
            Response<List<User>> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }

        Response<List<User>> response = userService.getByRole(roleType);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping
    public ResponseEntity<Response<User>> create(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody UserDTO userDTO) {
        if (!jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN)) {
            Response<User> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }

        Response<User> response = userService.create(userDTO);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, path = "/{id}")
    public ResponseEntity<Response<User>> update(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id,
            @RequestBody UserDTO userDTO) {
        if (jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN) ||
                jwtUtils.isUserAuthorized(id, bearerToken)) {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String httpMethod = requestAttributes.getRequest().getMethod();
            boolean override = httpMethod.equals("PUT");

            Response<User> response = userService.update(id, userDTO, override);
            return new ResponseEntity<>(response, response.getStatus());
        }

        Response<User> response = new Response<>();
        response.setStatus(HttpStatus.UNAUTHORIZED);
        response.setMessage(noAccessAllowed);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<User>> delete(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id) {
        if (jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN) ||
                jwtUtils.isUserAuthorized(id, bearerToken)) {
            Response<User> response = userService.delete(id);
            return new ResponseEntity<>(response, response.getStatus());

        }

        Response<User> response = new Response<>();
        response.setStatus(HttpStatus.UNAUTHORIZED);
        response.setMessage(noAccessAllowed);

        return new ResponseEntity<>(response, response.getStatus());
    }
}
