package com.example.grocery.api.grocery;

import com.example.grocery.utils.JwtUtils;
import com.example.grocery.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static com.example.grocery.utils.Messages.noAccessAllowed;

@RestController
@RequestMapping("/groceries")
public class GroceryController {
    @Autowired
    private GroceryService groceryService;
    @Autowired
    JwtUtils jwtUtils;
    
    @GetMapping
    public ResponseEntity<Response<List<Grocery>>> getAll(){
        Response<List<Grocery>> response = groceryService.getAll();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Grocery>> getById(@PathVariable String id) {
        Response<Grocery> response = groceryService.getById(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping
    public ResponseEntity<Response<Grocery>> create(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody GroceryDTO groceryDTO) {
        if (!jwtUtils.isUserAuthorized(bearerToken)) {
            Response<Grocery> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }

        Response<Grocery> response = groceryService.create(groceryDTO);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, path = "/{id}")
    public ResponseEntity<Response<Grocery>> update(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id,
            @RequestBody GroceryDTO groceryDTO) {
        if (!jwtUtils.isGroceryAuthorized(id, bearerToken)) {
            Response<Grocery> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String httpMethod = requestAttributes.getRequest().getMethod();
        boolean override = httpMethod.equals("PUT");

        Response<Grocery> response = groceryService.update(id, groceryDTO, override);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Grocery>> delete(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id) { 
        if (!jwtUtils.isGroceryAuthorized(id, bearerToken)) {
            Response<Grocery> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }
        Response<Grocery> response = groceryService.delete(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
