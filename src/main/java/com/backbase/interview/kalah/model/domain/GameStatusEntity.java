package com.backbase.interview.kalah.model.domain;

import javax.persistence.*;

@Entity
@Table
public class GameStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer index;
    private Integer value;
    @ManyToOne
    @JoinColumn(name="gameId")
    private GameEntity game;

    public GameStatusEntity(){}

    public GameStatusEntity(Integer index, Integer value, GameEntity game) {
        this.index = index;
        this.value = value;
        this.game = game;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }
}
