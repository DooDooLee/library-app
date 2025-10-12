package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {
    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 함수가 시작될 때 start transaction; 을 해줌
    // 함수가 예외 없이 잘 끝나면 commit;
    // 문제가 생기면 rollback;  근데 IOException이 일어나면 rollback을 안한다(자바의 체크예외기 때문에)
    @Transactional
    public void saveUser(UserCreateRequest request) {
        userRepository.save(new User(request.getName(),request.getAge()));
        //save메소드로 insert sql를 자동으로!
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream() //select * from user;가 자동으로!
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getAge()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(UserUpdateRequest request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalStateException::new);

        user.updateName(request.getName());
        //userRepository.save(user); 영속성 컨텍스트 쓰니깐 변경 감지를 해줘서 save 명시적으로 안해도됨
    }

    @Transactional
    public void deleteUser(String name){
        User user = userRepository.findByName(name).orElseThrow(IllegalArgumentException::new);

        userRepository.delete(user);
    }

}
