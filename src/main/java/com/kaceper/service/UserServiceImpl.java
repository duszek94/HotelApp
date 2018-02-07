package com.kaceper.service;

import com.kaceper.dto.UserDto;
import com.kaceper.model.User;
import com.kaceper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by Kaceper on 2017-11-29.
 */

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(UserDto userDto){
        User user = new User();

        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());

        System.out.println("GIT");

        userRepository.save(user);

        return user;
    }

    public User updateUser(UserDto userDto){
        User u = userRepository.findOne(userDto.getUserId());

        if(u != null){
            u.setFirstname(userDto.getFirstname());
            u.setLastname(userDto.getLastname());
            u.setPhone(userDto.getPhone());
            u.setEmail(userDto.getEmail());

            userRepository.save(u);
        }

        return u;
    }


}
