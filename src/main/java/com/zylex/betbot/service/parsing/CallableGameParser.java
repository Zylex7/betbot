package com.zylex.betbot.service.parsing;

import com.zylex.betbot.controller.logger.ParsingConsoleLogger;
import com.zylex.betbot.model.Game;
import com.zylex.betbot.service.Day;
import com.zylex.betbot.service.DriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Thread for parsing one league link.
 */
@SuppressWarnings("WeakerAccess")
public class CallableGameParser implements Callable<List<Game>> {

    private ParsingConsoleLogger logger;

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private DriverManager driverManager;

    private String leagueLink;

    private Day day;

    public CallableGameParser(ParsingConsoleLogger logger, DriverManager driverManager, String leagueLink, Day day) {
        this.logger = logger;
        this.driverManager = driverManager;
        this.leagueLink = leagueLink;
        this.day = day;
    }

    /**
     * Processes parsing of one league.
     * @return - list of games.
     */
    @Override
    public List<Game> call() {
        WebDriver driver = driverManager.getDriver();
        try {
            return processGameParsing(driver);
        } finally {
            driverManager.addDriverToQueue(driver);
        }
    }

    private List<Game> processGameParsing(WebDriver driver) {
        driver.navigate().to(String.format("https://1xstavka.ru/%s", leagueLink));
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        Document document = Jsoup.parse(driver.getPageSource());
        return parseGames(document);
    }

    private List<Game> parseGames(Document document) {
        List<Game> games = new ArrayList<>();
        String leagueName = document.select("a.c-events__liga").text();
        Elements gameElements = document.select("div.c-events__item_game");
        int currentDay = LocalDate.now().plusDays(day.INDEX).getDayOfMonth();
        for (Element gameElement : gameElements) {
            LocalDateTime dateTime = processDate(gameElement);
            if (currentDay > dateTime.getDayOfMonth()) {
                continue;
            } else if (currentDay < dateTime.getDayOfMonth()) {
                break;
            }
            Elements teams = gameElement.select("span.c-events__team");
            String firstTeam = teams.get(0).text();
            String secondTeam = teams.get(1).text();
            if (firstTeam.contains("Хозяева (голы)")) {
                continue;
            }
            Elements coefficients = gameElement.select("div.c-bets > a.c-bets__bet");
            String firstWin = coefficients.get(0).text();
            String tie = coefficients.get(1).text();
            String secondWin = coefficients.get(2).text();
            String firstWinOrTie = coefficients.get(3).text();
            String secondWinOrTie = coefficients.get(5).text();
            Game game = new Game(leagueName,
                    String.format("https://1xstavka.ru/%s", leagueLink),
                    dateTime,
                    firstTeam,
                    secondTeam,
                    firstWin,
                    tie,
                    secondWin,
                    firstWinOrTie,
                    secondWinOrTie);
            games.add(game);
        }
        logger.logLeagueGame();
        return games;
    }

    private LocalDateTime processDate(Element gameElement) {
        String time = gameElement.select("div.c-events__time > span").text();
        String year = String.valueOf(LocalDate.now().getYear());
        time = time.replace(" ", String.format(".%s ", year)).substring(0, 16);
        return LocalDateTime.parse(time, FORMATTER);
    }
}
