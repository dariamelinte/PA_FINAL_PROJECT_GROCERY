package com.example.grocery.api.tranzaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@RequestMapping("/tranzactions")
public class TranzactionController {
    @Autowired
    private TranzactionService tranzactionService;

    @GetMapping
    public ResponseEntity<List<Tranzaction>> getAll() {
        List<Tranzaction> tranzactions = tranzactionService.getAll();
        return new ResponseEntity<>(tranzactions, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Tranzaction> getById(@PathVariable String id) {
        Tranzaction tranzaction = tranzactionService.getById(id);
        return new ResponseEntity<>(tranzaction, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Tranzaction> create(@RequestBody TranzactionDTO tranzactionDTO) {
        tranzactionService.create(tranzactionDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, path = "/{id}")
    public ResponseEntity update(@PathVariable String id, @RequestBody TranzactionDTO tranzactionDTO) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String httpMethod = requestAttributes.getRequest().getMethod();
        boolean override = httpMethod.equals("PUT");

        tranzactionService.update(id, tranzactionDTO, override);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        tranzactionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
