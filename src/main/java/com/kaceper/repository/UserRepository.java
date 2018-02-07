package com.kaceper.repository;

/**
 * Created by Kaceper on 2017-11-29.
 */

import com.kaceper.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}
