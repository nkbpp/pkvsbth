package ru.pfr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pfr.model.pkv.User;
import ru.pfr.repo.pkv.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByLoginuser(String login) {
        return userRepository.findByLogin(login).get();
    }

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

}
