package com.project.fitness.service;

import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.model.User;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    // constructor dependency injecty

//    public User register(RegisterRequest request){ /// returning user needs to return user response
        public UserResponse register(RegisterRequest request){
            User user = User.builder()
                    .email(request.getEmail())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .password(request.getPassword())
                    .build();
//        User user = new User(
//                null,
//                request.getEmail(),
//                request.getPassword(),
//                request.getFirstName(),
//                request.getLastName(),
//                Instant.parse("2026-01-19T12:20:00+00:00") // could pass null to
//                        .atZone(ZoneOffset.UTC).toLocalDateTime(),
//                null,
//                List.of(),
//                List.of()
//
//        );
            User savedUser = userRepository.save(user) ;
            
            return mapToResponse(savedUser) ;
       // return userRepository.save(user) ;
    }

    private UserResponse mapToResponse(User savedUser) {
            UserResponse response = new UserResponse();
            response.setId(savedUser.getId());
            response.setEmail(savedUser.getEmail());
            response.setPassword(savedUser.getPassword());
            response.setFirstName(savedUser.getFirstName());
            response.setLastName(savedUser.getLastName());
            response.setCreatedAt(savedUser.getCreatedAt()) ;

            return response;

    }
}
