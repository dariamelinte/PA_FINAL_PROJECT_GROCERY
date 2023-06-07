package com.example.grocery.api.tranzaction;

import com.example.grocery.api.user.User;
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
@RequestMapping("/tranzactions")
public class TranzactionController {
    @Autowired
    private TranzactionService tranzactionService;
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<Response<List<Tranzaction>>> getAll(
            @RequestHeader("Authorization") String bearerToken
        ) {
        if (!jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN)) {
            Response<List<Tranzaction>> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }
        Response<List<Tranzaction>> response = tranzactionService.getAll();
        return new ResponseEntity<>(response, response.getStatus());
    }
    @PostMapping("/user/{id}")
    public ResponseEntity<Response<List<Tranzaction>>> getByDateAndUser(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id,
            @RequestBody DateInterval interval) {
        if (!jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN) &&
                !jwtUtils.isUserAuthorized(id, bearerToken)){
            Response<List<Tranzaction>> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }
        Response<List<Tranzaction>> response = tranzactionService.getByDateAndUser(id, interval.getStartDate(), interval.getEndDate());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/grocery/{id}")
    public ResponseEntity<Response<List<Tranzaction>>> getByDateAndGrocery(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id,
            @RequestBody DateInterval interval) {
        if (!jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN) &&
                !jwtUtils.isGroceryAuthorized(id, bearerToken)){
            Response<List<Tranzaction>> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }
        Response<List<Tranzaction>> response = tranzactionService.getByDateAndGrocery(id, interval.getStartDate(), interval.getEndDate());
        return new ResponseEntity<>(response, response.getStatus());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response<Tranzaction>> getById(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id) {
        if (!jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN) &&
                !jwtUtils.isTranzactionAuthorized(id, bearerToken)){
            Response<Tranzaction> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }

        Response<Tranzaction> response = tranzactionService.getById(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @PostMapping
    public ResponseEntity<Response<Tranzaction>> create(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody TranzactionDTO tranzactionDTO) {
        if (!jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN) &&
                !jwtUtils.isUserAuthorized(tranzactionDTO.getUserId(), bearerToken)){
            Response<Tranzaction> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }
        Response<Tranzaction> response = tranzactionService.create(tranzactionDTO);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, path = "/{id}")
    public ResponseEntity<Response<Tranzaction>> update(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id,
            @RequestBody TranzactionDTO tranzactionDTO) {
        if (!jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN) &&
                !jwtUtils.isTranzactionAuthorized(id, bearerToken) &&
                !jwtUtils.isUserAuthorized(tranzactionDTO.getUserId(), bearerToken)){
            Response<Tranzaction> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String httpMethod = requestAttributes.getRequest().getMethod();
        boolean override = httpMethod.equals("PUT");

        Response<Tranzaction> response = tranzactionService.update(id, tranzactionDTO, override);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Tranzaction>> delete(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id) {
        if (!jwtUtils.isRoleAuthorized(bearerToken, RoleType.ADMIN) &&
                !jwtUtils.isTranzactionAuthorized(id, bearerToken)){
            Response<Tranzaction> response = new Response<>();
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage(noAccessAllowed);

            return new ResponseEntity<>(response, response.getStatus());
        }

        Response<Tranzaction> response = tranzactionService.delete(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
