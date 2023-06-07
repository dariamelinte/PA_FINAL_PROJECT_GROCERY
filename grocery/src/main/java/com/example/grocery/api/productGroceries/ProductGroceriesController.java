package com.example.grocery.api.productGroceries;

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

import static com.example.grocery.utils.Messages.noAccessAllowed;

@RestController
@RequestMapping("/productGroceries")
public class ProductGroceriesController {
    @Autowired ProductGroceriesService service;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping
    public ResponseEntity<Response<ProductGroceries>> create(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody ProductGroceriesDTO dto){

        if (!jwtUtils.isRoleAuthorized(bearerToken, RoleType.SHOP_OWNER) && !jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN) ) {
            Response<ProductGroceries> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }

        Response<ProductGroceries> response = service.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<List<ProductGroceries>>> getAll(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ProductGroceries>> getById(@PathVariable String id) {
        var response = service.getById(id);
        return new ResponseEntity<>(response, response.getStatus());
    }


    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, path = "/{id}")
    public  ResponseEntity<Response<ProductGroceries>> update(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id,
            @RequestBody ProductGroceriesDTO dto){
        if (!jwtUtils.isProductGroceryAuthorize(bearerToken, id)) {
            Response<ProductGroceries> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String httpMethod = requestAttributes.getRequest().getMethod();
        boolean override = httpMethod.equals("PUT");

        var response = service.update(id, dto, override);
        return new ResponseEntity(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<ProductGroceries>> delete(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id) {
        if (!jwtUtils.isProductGroceryAuthorize(bearerToken, id)) {
            Response<ProductGroceries> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }
        var response = service.delete(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
