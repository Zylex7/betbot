package com.zylex.betbot.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "game_rule_bet")
public class GameRuleBet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "rule_id")
    private Rule rule;

    @Column(name = "bet_made")
    private boolean betMade;

    public GameRuleBet() {
    }

    public GameRuleBet(Game game, Rule rule, boolean betMade) {
        this.game = game;
        this.rule = rule;
        this.betMade = betMade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public boolean isBetMade() {
        return betMade;
    }

    public void setBetMade(boolean betMade) {
        this.betMade = betMade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRuleBet betInfo = (GameRuleBet) o;
        return id == betInfo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return rule.getName();
    }
}