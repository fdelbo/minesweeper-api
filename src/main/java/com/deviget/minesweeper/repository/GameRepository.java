package com.deviget.minesweeper.repository;

import com.deviget.minesweeper.model.document.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {
}
