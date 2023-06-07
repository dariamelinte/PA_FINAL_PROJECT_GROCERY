package com.example.grocery.api.product;

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
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    JwtUtils jwtUtils;
    
    @GetMapping
    public ResponseEntity<Response<List<Product>>> getAll(){
        Response<List<Product>> response = productService.getAll();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Product>> getById(@PathVariable String id) {
        Response<Product> response = productService.getById(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping
    public ResponseEntity<Response<Product>> create(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody ProductDTO productDTO) {
        if (!jwtUtils.isAuthorized(bearerToken)) {
            Response<Product> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }

        Response<Product> response = productService.create(productDTO);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, path = "/{id}")
    public ResponseEntity<Response<Product>> update(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id,
            @RequestBody ProductDTO productDTO) {
        if (jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN) ||
                jwtUtils.isRoleAuthorized(bearerToken, RoleType.SHOP_OWNER)) {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String httpMethod = requestAttributes.getRequest().getMethod();
            boolean override = httpMethod.equals("PUT");

            Response<Product> response = productService.update(id, productDTO, override);
            return new ResponseEntity<>(response, response.getStatus());
        }

        Response<Product> response = new Response<>();
        response.setStatus(HttpStatus.UNAUTHORIZED);
        response.setMessage(noAccessAllowed);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Product>> delete(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id) {
        if (jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN) ||
                jwtUtils.isRoleAuthorized(bearerToken, RoleType.SHOP_OWNER)) {
            Response<Product> response = productService.delete(id);
            return new ResponseEntity<>(response, response.getStatus());

        }

        Response<Product> response = new Response<>();
        response.setStatus(HttpStatus.UNAUTHORIZED);
        response.setMessage(noAccessAllowed);

        return new ResponseEntity<>(response, response.getStatus());
    }
}
