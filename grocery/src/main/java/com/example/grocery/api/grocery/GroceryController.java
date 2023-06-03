package com.example.grocery.api.grocery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@RequestMapping("/groceries")
public class GroceryController {
    @Autowired
    private GroceryService groceryService;

    @GetMapping
    public ResponseEntity<List<Grocery>> getAll() {
        List<Grocery> groceries = groceryService.getAll();
        return new ResponseEntity<>(groceries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grocery> getById(@PathVariable String id) {
        Grocery grocery = groceryService.getById(id);
        return new ResponseEntity<>(grocery, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Grocery> create(@RequestBody GroceryDTO groceryDTO) {
        groceryService.create(groceryDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, path = "/{id}")
    public ResponseEntity update(@PathVariable String id, @RequestBody GroceryDTO groceryDTO) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String httpMethod = requestAttributes.getRequest().getMethod();
        boolean override = httpMethod.equals("PUT");

        groceryService.update(id, groceryDTO, override);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        groceryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
