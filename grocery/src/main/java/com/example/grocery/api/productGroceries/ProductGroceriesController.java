package com.example.grocery.api.productGroceries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@RequestMapping("/productGroceries")
public class ProductGroceriesController {
    @Autowired ProductGroceriesService service;

    @PostMapping
    public ResponseEntity<ProductGroceries> create(@RequestBody ProductGroceriesDTO dto){
        service.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductGroceries>> getAll(){ return new ResponseEntity<>(service.getAll(), HttpStatus.OK);}

    @GetMapping("/{id}")
    public ResponseEntity<ProductGroceries> getById(@PathVariable String id) { return new ResponseEntity<>(service.getById(id), HttpStatus.OK);}


    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, path = "/{id}")
    public  ResponseEntity update(@PathVariable String id, @RequestBody ProductGroceriesDTO dto){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String httpMethod = requestAttributes.getRequest().getMethod();
        boolean override = httpMethod.equals("PUT");
        service.update(id, dto, override);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
