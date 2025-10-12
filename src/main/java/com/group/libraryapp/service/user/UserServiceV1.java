package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserServiceV1 {
    private final UserJdbcRepository userRepository;

    public UserServiceV1(UserJdbcRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserCreateRequest request) {
        userRepository.saveUser(request);
    }

    public List<UserResponse> getUsers() {
        return  userRepository.getUsers();
    }

    public void deleteUser(String name) {
        //exception
        boolean isUserNotExist = userRepository.isUserNotExist(name);
        if (isUserNotExist) {
            throw new IllegalArgumentException();
        }

        //update
        userRepository.deleteUser(name);
    }

    public void updateUser( UserUpdateRequest request) {
        //exception
        boolean isUserNotExist = userRepository.isUserNotExist( request.getId());
        if(isUserNotExist){
            throw new IllegalArgumentException();
        }

        //update
        userRepository.updateUserName(request.getName(),  request.getId());
    }


}
