package com.backbase.interview.kalah.model.domain;

import com.backbase.interview.kalah.model.Turn;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Table
@Entity
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Turn turn;
    private Turn winner;
    @OneToMany(targetEntity=GameStatusEntity.class, mappedBy = "game", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private List<GameStatusEntity> statuses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public Turn getWinner() {
        return winner;
    }

    public void setWinner(Turn winner) {
        this.winner = winner;
    }

    public List<GameStatusEntity> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<GameStatusEntity> statuses) {
        this.statuses = statuses;
    }
}
