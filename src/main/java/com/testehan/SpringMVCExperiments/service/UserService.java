package com.testehan.SpringMVCExperiments.service;

import com.testehan.SpringMVCExperiments.dto.UserRegistrationDTO;
import com.testehan.SpringMVCExperiments.model.UserEntity;

public interface UserService {

    void saveUser(UserRegistrationDTO userRegistrationDTO);


    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}
