package com.deviget.minesweeper.model.document;

import com.deviget.minesweeper.game.Board;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Objects;

@Document("games")
public class Game {

    @Id
    private String id;
    private String userId;
    private Instant createdDate;
    private Instant lastMoveDate;
    private Instant lastResumeDate;
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

    public Instant getLastMoveDate() {
        return lastMoveDate;
    }

    public void setLastMoveDate(Instant lastMoveDate) {
        this.lastMoveDate = lastMoveDate;
    }

    public Instant getLastResumeDate() {
        return lastResumeDate;
    }

    public void setLastResumeDate(Instant lastResumeDate) {
        this.lastResumeDate = lastResumeDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id) &&
                Objects.equals(userId, game.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId);
    }
}
