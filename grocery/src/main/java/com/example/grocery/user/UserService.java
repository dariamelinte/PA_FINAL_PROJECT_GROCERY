package com.example.grocery.user;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    void create(UserDTO userDTO) {
        userRepository.save(UserMapper.dtoToEntity(userDTO));
    }

    List<User> getAll() {
        return userRepository.findAll();
    }

    User getById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    void update(String id, UserDTO userDTO){
        User oldUser = this.getById(id);
        if (oldUser == null) return;

        if (userDTO.getFirstName() != null) {
            oldUser.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            oldUser.setLastName(userDTO.getLastName());
        }
        if (userDTO.getEmail() != null) {
            oldUser.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPhone() != null) {
            oldUser.setPhone(userDTO.getPhone());
        }
        if (userDTO.getBirthDate() != null) {
            oldUser.setBirthDate(userDTO.getBirthDate());
        }
        if (userDTO.getJwt() != null) {
            oldUser.setJwt(userDTO.getJwt());
        }
        if (!userDTO.getRoles().isEmpty()) {
            oldUser.setRoles(userDTO.getRoles());
        }

        userRepository.save(oldUser);
    }

    void delete(String id){
        User user = this.getById(id);
        userRepository.delete(user);
    }
}
