package com.projeto.tracker.controller;

import com.projeto.tracker.model.User;
import com.projeto.tracker.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:4200")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/salvar")
    public User save(@RequestBody User user) {return this.userRepository.save(user);};

    @GetMapping("/listar")
    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    @GetMapping("/getById/{id}")
    public Optional<User> getById(@PathVariable("id") Long id) {
        return this.userRepository.findById(id);
    }
}
