package com.vanvan.service;

import com.vanvan.dto.RegisterDTO;
import com.vanvan.exception.UnderageUserException;
import com.vanvan.exception.CnhAlreadyExistsException;
import com.vanvan.exception.UnderageDriverException;
import com.vanvan.exception.CpfAlreadyExistsException;
import com.vanvan.exception.EmailAlreadyExistsException;
import com.vanvan.model.Driver;
import com.vanvan.model.Passenger;
import com.vanvan.model.User;
import com.vanvan.repository.DriverRepository;
import com.vanvan.repository.PassengerRepository;
import com.vanvan.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final PassengerRepository passengerRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterDTO data) {

        var user = data.toEntity();

        // Faz todas as validações aqui e retorna exceptions personalizadas caso falhe
        validateUser(user);

        // Criptografa a senha
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // Faz o switch pelo tipo de usuário (Admin foi removido daqui!)
        return switch (user.getRole()) {
            case PASSENGER -> {
                assert user instanceof Passenger;
                yield passengerRepository.save((Passenger) user);
            }
            case DRIVER -> {
                assert user instanceof Driver;
                yield driverRepository.save((Driver) user);
            }
            default -> throw new IllegalArgumentException("Operação não permitida: Tipo de usuário inválido para esta rota.");
        };
    }

    // Método extraído para cá para melhor separação de responsabilidades
    private void validateUser(User user) {

        // Valida idade
        validateAge(user);

        // Verifica se o e-mail já está cadastrado
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException(user.getName());
        }
        // Verifica se o CPF já está cadastrado
        else if (userRepository.findByCpf(user.getCpf()) != null) {
            throw new CpfAlreadyExistsException(user.getCpf());
        } 
        // Verifica CNH em caso de driver
        else if (user instanceof Driver driver && driverRepository.existsByCnh(driver.getCnh())) {
            throw new CnhAlreadyExistsException(driver.getCnh());
        }
    }

    // Validação de idade
    private void validateAge(User user) {
        int age = Period.between(user.getBirthDate(), LocalDate.now()).getYears();

        if (user instanceof Driver && age < 21) {
             throw new UnderageDriverException();
        }
        if (age < 18) {
            throw new UnderageUserException();
        }
    }
}