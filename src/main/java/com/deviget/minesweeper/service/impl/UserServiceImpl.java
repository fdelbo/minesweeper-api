package com.deviget.minesweeper.service.impl;

import com.deviget.minesweeper.model.document.User;
import com.deviget.minesweeper.repository.UserRepository;
import com.deviget.minesweeper.service.UserService;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }
}
