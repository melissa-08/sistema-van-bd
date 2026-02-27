package com.vanvan.service;

import com.vanvan.dto.RegisterDTO;
import com.vanvan.exception.UnderageUserException;
import com.vanvan.exception.CnhAlreadyExistsException;
import com.vanvan.exception.UnderageDriverException;
import com.vanvan.model.Driver;
import com.vanvan.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vanvan.exception.CpfAlreadyExistsException;
import com.vanvan.exception.EmailAlreadyExistsException;
import com.vanvan.model.Administrator;
import com.vanvan.model.Passenger;
import com.vanvan.repository.AdministratorRepository;
import com.vanvan.repository.DriverRepository;
import com.vanvan.repository.PassengerRepository;
import com.vanvan.repository.UserRepository;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final PassengerRepository passengerRepository;
    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterDTO data) {

        var user = data.toEntity();

        validateUser(user);

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        return switch (user.getRole()) {
            case PASSENGER -> {
                assert user instanceof Passenger;
                yield passengerRepository.save((Passenger) user);
            }
            case ADMIN -> {
                assert user instanceof Administrator;
                yield administratorRepository.save((Administrator) user);
            }
            case DRIVER -> {
                assert user instanceof Driver;
                yield driverRepository.save((Driver) user);
            }
        };
    }

    private void validateUser(User user) {
        validateAge(user);

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException(user.getName());
        }
        else if (userRepository.findByCpf(user.getCpf()) != null) {
            throw new CpfAlreadyExistsException(user.getCpf());
        } else if (user instanceof Driver driver && driverRepository.existsByCnh(driver.getCnh())) {
            throw new CnhAlreadyExistsException(driver.getCnh());
        }
    }

    private void validateAge(User user){
        int age = Period.between(user.getBirthDate(), LocalDate.now()).getYears();

        if (user instanceof Driver && age < 21) {
             throw new UnderageDriverException();
        }
        if (age < 18){
            throw new UnderageUserException();
        }
    }
}