package com.deviget.minesweeper.repository;

import com.deviget.minesweeper.model.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
