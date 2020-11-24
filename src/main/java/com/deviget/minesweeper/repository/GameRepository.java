package com.deviget.minesweeper.repository;

import com.deviget.minesweeper.model.document.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends MongoRepository<Game, String> {

    Optional<Game> findByIdAndUserId(String id, String userId);

    List<Game> findByUserId(String userId);
}
