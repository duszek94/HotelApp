package com.kaceper.service;

import com.kaceper.dto.WorkerDto;
import com.kaceper.model.Worker;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface WorkerService extends UserDetailsService{

    Worker findByEmail(String email);
    Worker findByUname(String username);
    List<WorkerDto> getAllWorkers();
    boolean updatePassword(Worker worker, String password);
    Worker findByConfirmationToken(String confirmationToken);
    boolean updateEnabled(Worker worker);
    boolean updateWorker(WorkerDto dto);
    boolean deleteWorker(Integer id);
}
