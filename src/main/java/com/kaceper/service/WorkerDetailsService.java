package com.kaceper.service;

import com.kaceper.model.Role;
import com.kaceper.model.Worker;
import com.kaceper.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service("workerDetailsService")
public class WorkerDetailsService implements UserDetailsService {

    @Autowired
    private WorkerRepository workerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws AuthenticationServiceException {
        Worker worker = workerRepository.findByUsername(username);
        if (worker == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                worker.getUsername(),
                worker.getPassword(),
                worker.isEnabled(),
                worker.isAccountNonExpired(),
                worker.isCredentialsNonExpired(),
                worker.isAccountNonLocked(),
                mapRolesToAuthorities(worker.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }
}
