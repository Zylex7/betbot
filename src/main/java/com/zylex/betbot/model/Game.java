package com.zylex.betbot.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Instance of a football game.
 */
public class Game {

    private String league;

    private String leagueLink;

    private LocalDateTime dateTime;

    private String firstTeam;

    private String secondTeam;

    private String firstWin;

    private String tie;

    private String secondWin;

    private String firstWinOrTie;

    private String secondWinOrTie;

    public Game(String league, String leagueLink, LocalDateTime dateTime, String firstTeam, String secondTeam, String firstWin, String tie, String secondWin, String firstWinOrTie, String secondWinOrTie) {
        this.league = league;
        this.leagueLink = leagueLink;
        this.dateTime = dateTime;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.firstWin = firstWin;
        this.tie = tie;
        this.secondWin = secondWin;
        this.firstWinOrTie = firstWinOrTie;
        this.secondWinOrTie = secondWinOrTie;
    }

    public String getLeague() {
        return league;
    }

    public String getLeagueLink() {
        return leagueLink;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }

    public String getFirstWin() {
        return firstWin;
    }

    public String getTie() {
        return tie;
    }

    public String getSecondWin() {
        return secondWin;
    }

    public String getFirstWinOrTie() {
        return firstWinOrTie;
    }

    public String getSecondWinOrTie() {
        return secondWinOrTie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(league, game.league) &&
                Objects.equals(leagueLink, game.leagueLink) &&
                Objects.equals(dateTime, game.dateTime) &&
                Objects.equals(firstTeam, game.firstTeam) &&
                Objects.equals(secondTeam, game.secondTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(league, leagueLink, dateTime, firstTeam, secondTeam);
    }

    @Override
    public String toString() {
        return "Game{" +
                "league='" + league + '\'' +
                ", dateTime=" + dateTime +
                ", firstTeam='" + firstTeam + '\'' +
                ", secondTeam='" + secondTeam + '\'' +
                ", firstWin='" + firstWin + '\'' +
                ", tie='" + tie + '\'' +
                ", secondWin='" + secondWin + '\'' +
                ", firstWinOrTie='" + firstWinOrTie + '\'' +
                ", secondWinOrTie='" + secondWinOrTie + '\'' +
                '}';
    }
}
