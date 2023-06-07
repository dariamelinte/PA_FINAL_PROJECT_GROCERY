package com.example.grocery.api.user;

import com.example.grocery.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.grocery.utils.Messages.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Response<User> create(UserDTO userDTO) {
        Response<User> response = new Response<>();
        userRepository.save(UserMapper.dtoToEntity(userDTO));

        response.setStatus(HttpStatus.CREATED);
        response.setMessage(createSuccessful("User"));
        return response;
    }

    Response<List<User>> getAll() {
        Response<List<User>> response = new Response<>();
        List<User> categories = userRepository.findAll();

        response.setStatus(HttpStatus.OK);
        response.setMessage(fetchSuccessful("User"));
        response.setData(categories);
        return response;
    }

    public Response<User> getById(String id) {
        Response<User> response = new Response<>();
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage(nonExistingResource("user"));
            return response;
        }


        response.setStatus(HttpStatus.OK);
        response.setMessage(fetchSuccessful("User"));
        response.setData(user);
        return response;
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    Response<List<User>> getByRole(String role) {
        Response<List<User>> response = new Response<>();
        List<User> users = userRepository.findByRole(role).orElse(null);

        if (users == null || users.size() == 0) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage(nonExistingResource("user"));
            return response;
        }


        response.setStatus(HttpStatus.OK);
        response.setMessage(fetchSuccessful("Users"));
        response.setData(users);
        return response;
    }
    
    public Response<User> update(String id, UserDTO userDTO, Boolean override){
        Response<User> response = new Response<>();

        User oldUser = userRepository.findById(id).orElse(null);

        if (oldUser == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage(nonExistingResource("user"));
            return response;
        }

        if (Boolean.TRUE.equals(override) || userDTO.getFirstName() != null) {
            oldUser.setFirstName(userDTO.getFirstName());
        }
        if (Boolean.TRUE.equals(override) || userDTO.getLastName() != null) {
            oldUser.setLastName(userDTO.getLastName());
        }
        if (Boolean.TRUE.equals(override) || userDTO.getEmail() != null) {
            oldUser.setEmail(userDTO.getEmail());
        }
        if (Boolean.TRUE.equals(override) || userDTO.getPhone() != null) {
            oldUser.setPhone(userDTO.getPhone());
        }
        if (Boolean.TRUE.equals(override) || userDTO.getBirthDate() != null) {
            oldUser.setBirthDate(userDTO.getBirthDate());
        }
        if (Boolean.TRUE.equals(override) || userDTO.getJwt() != null) {
            oldUser.setJwt(userDTO.getJwt());
        }
        if (Boolean.TRUE.equals(override) || (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) ) {
            oldUser.setRoles(userDTO.getRoles());
        }


        userRepository.save(oldUser);

        User user = userRepository.findById(id).orElse(null);
        response.setStatus(HttpStatus.OK);
        response.setMessage(updateSuccessful("User"));
        response.setData(user);

        return response;
    }

    Response<User> delete(String id){
        Response<User> response = new Response<>();
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage(nonExistingResource("user"));
            return response;
        }

        userRepository.delete(user);

        user = userRepository.findById(id).orElse(null);
        response.setStatus(user != null ? HttpStatus.INTERNAL_SERVER_ERROR :HttpStatus.OK);
        response.setMessage(user != null ? somethingWentWrong : deleteSuccessful("User"));
        return response;
    }
}
