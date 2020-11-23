package com.deviget.minesweeper.model.document;

import com.deviget.minesweeper.model.Board;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("games")
public class Game {

    @Id
    private String id;
    private String userId;
    private Instant createdDate;
    private Board board;

    public Game(String userId, Board board) {
        this.userId = userId;
        this.board = board;
        this.createdDate = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Board getBoard() {
        return board;
    }

}
