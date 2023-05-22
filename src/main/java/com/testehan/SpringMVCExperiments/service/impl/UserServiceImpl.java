package com.testehan.SpringMVCExperiments.service.impl;

import com.testehan.SpringMVCExperiments.dto.UserRegistrationDTO;
import com.testehan.SpringMVCExperiments.model.Role;
import com.testehan.SpringMVCExperiments.model.UserEntity;
import com.testehan.SpringMVCExperiments.repository.RoleRepository;
import com.testehan.SpringMVCExperiments.repository.UserRepository;
import com.testehan.SpringMVCExperiments.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserRegistrationDTO userRegistrationDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegistrationDTO.getUsername());
        userEntity.setEmail(userRegistrationDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        // WHEN A new user is created, the role of it will be USER
        Role role = roleRepository.findByName("USER");
        userEntity.setRoles(Arrays.asList(role));

        userRepository.save(userEntity);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
