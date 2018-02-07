package com.kaceper.repository;


import com.kaceper.model.Worker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, Integer> {

    Worker findByEmail(String email);
    Worker findByConfirmationToken(String confirmationToken);
    Worker findByUsername(String username);
}
