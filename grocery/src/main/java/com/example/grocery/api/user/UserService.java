package com.example.grocery.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void create(UserDTO userDTO) {
        userRepository.save(UserMapper.dtoToEntity(userDTO));
    }

    List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    List<User> getByRole(String roleType) {
        return userRepository.findByRole(roleType).orElse(null);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public void update(String id, UserDTO userDTO, Boolean override){
        User oldUser = this.getById(id);
        if (oldUser == null) return;

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
    }

    void delete(String id){
        User user = this.getById(id);
        userRepository.delete(user);
    }
}
