package com.vanvan.service;

import com.vanvan.model.Administrator;
import com.vanvan.model.User;
import com.vanvan.repository.AdministratorRepository;
import com.vanvan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final AdministratorRepository administratorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // tenta achar um admin primeiro
        Administrator admin = administratorRepository.findByEmail(email);
        if (admin != null) {
            return admin;
        }

        //se não for admin, tenta achar um usuário comum
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return user;
        }

        throw new UsernameNotFoundException("Usuário não encontrado com o email: " + email);
    }
}