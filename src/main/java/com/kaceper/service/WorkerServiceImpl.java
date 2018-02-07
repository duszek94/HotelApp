package com.kaceper.service;

import com.kaceper.dto.WorkerDto;
import com.kaceper.model.Role;
import com.kaceper.model.Worker;
import com.kaceper.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

@Service("workerService")
@Transactional
public class WorkerServiceImpl implements WorkerService{

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    public WorkerServiceImpl(WorkerRepository workerRepository){
        this.workerRepository = workerRepository;
    }

    public Worker findByUsername(String username)
    {
        return workerRepository.findByUsername(username);
    }

    public Worker findByConfirmationToken(String confirmationToken){
        return workerRepository.findByConfirmationToken(confirmationToken);
    }

    public Worker findByEmail(String email){
        return workerRepository.findByEmail(email);
    }

    @Override
    public List<WorkerDto> getAllWorkers(){
        List<WorkerDto> list = new ArrayList<>();
        for(Worker r : workerRepository.findAll()){
            list.add(WorkerDto.make(r));
        }

        return list;
    }

    public boolean updateWorker(WorkerDto dto){
        Worker w = workerRepository.findOne(dto.getWorkerId());

        w.setUsername(dto.getUsername());
        w.setFirstname(dto.getFirstname());
        w.setAdress(dto.getAdress());
        w.setPhone(dto.getPhone());
        w.setEmail(dto.getEmail());
        w.setLastname(dto.getLastname());

        workerRepository.save(w);

        return true;
    }

    public boolean updateEnabled(Worker worker){
        Query q = entityManager.createQuery("update Worker w " +
                "set w.enabled = TRUE " +
                "where w.workerId = :workerId")
                .setParameter("workerId", worker.getWorkerId());
        q.executeUpdate();

        return true;
    }

    public boolean updatePassword(Worker worker, String password){
        Query q2 = entityManager.createQuery("update Worker w " +
                "set w.password = :password " +
                "where w.username = :username")
                .setParameter("password", password)
                .setParameter("username", worker.getUsername());
        q2.executeUpdate();

        return true;
    }

    public boolean deleteWorker(Integer id){
        Worker w = entityManager.createQuery("select w from Worker w " +
                "where w.workerId = :id", Worker.class)
                .setParameter("id", id)
                .getSingleResult();

        entityManager.remove(w);

        return true;
    }

    public Worker findByUname(String login){
        Worker worker = null;
        try{
            worker = entityManager.createQuery("select w from Worker w " +
                    "where w.username = :login ", Worker.class)
                    .setParameter("login", login)
                    .getSingleResult();
        }catch (Exception e){
            System.out.println("Nie znaleziono użytkownika o podanym loginie");
        }

        return worker;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Worker worker = workerRepository.findByUsername(username);
        if (worker == null){
            System.out.println("USER NOT FOUND !");
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(worker.getUsername(),
                worker.getPassword(),
                mapRolesToAuthorities(worker.getRoles()));
    }


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

}