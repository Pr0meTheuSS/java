/*
 * Project: TowerDefence
 * Created Date: Monday, April 3rd 2023, 5:10:49 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */
package Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.*;

import Models.Enemies.AbstractEnemy;
import Models.Enemies.StupidEnemy;
import Models.Mosque.Mosque;
import Models.Towers.AbstractTower;

public class Level {

    public Level(int levelId) {
        LevelJSONParser levelParser = new LevelJSONParser();
        JSONObject configs = levelParser.parseLevelConfigurations(levelId);

        try {
            towerLimit = configs.getInt("towerLimit");

            JSONArray enemiesJSON = configs.getJSONArray("enemies");
            for (int i = 0; i < enemiesJSON.length(); i++) {
                double x = enemiesJSON.getJSONObject(i).getDouble("x");
                double y = enemiesJSON.getJSONObject(i).getDouble("y");
                enemies.add(new StupidEnemy(x, y));
            }

            double mosqueX = configs.getJSONObject("mosque").getDouble("x");
            double mosqueY = configs.getJSONObject("mosque").getDouble("y");
            double mosqueWidth = configs.getJSONObject("mosque").getDouble("width");    
            ИванЕвгеньевич.setMosque(mosqueX, mosqueY, mosqueWidth);

        } catch (JSONException ex) {
            System.err.println(ex.getMessage());
            throw new RuntimeException(ex.getCause());
        }
    }

    public boolean buildTower(AbstractTower newTower) {
        if (towerLimit > 0) {
            towers.add(newTower);
            towerLimit--;
            return true;
        }
        
        return false;
    }

    private double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public boolean checkAbleToBuildTower(double x, double y) {
        var buildingTower = towers.get(towers.size() - 1);
        if (ИванЕвгеньевич.isCollision(buildingTower.getX(), buildingTower.getY())) {
            return false;
        }

		for (int i = 0; i < towers.size() - 1; i++) {
            var tower = towers.get(i);
            if (dist(tower.getX(), tower.getY(), x, y) <= 
                tower.getRadius() + buildingTower.getRadius()) {
                return false;
            }
        }

        return true;
	}

    public void update() {
        if (!gameStarted) {
            return;
        }

        if (isGameOver()) {
			return;
		}

        for (var e : enemies) {
            towers.stream()
                .filter(t-> t.ableToAttack(e.getX(), e.getY()))
                .forEach(t -> Attack(t, e));
        }

        var targetX = ИванЕвгеньевич.getX();
        var targetY = ИванЕвгеньевич.getY();

        enemies.removeIf(e -> !e.isAlive());
        enemies.forEach(e -> e.moveTo(targetX, targetY));
        enemies
            .stream()
            .filter(e -> e.isAlive() && e.ableToAttack(targetX, targetY))
            .forEach(e -> ИванЕвгеньевич.causeDamage(e.getAttackPower()));
    }

    public void start() {
        gameStarted = true;
    }

    public int getCurrentTowerLimit() {
        return towerLimit;
    }

    private void Attack(Attacker attacker, Attackable attackable) {
        attacker.setCurrentTarget(attackable);
		attackable.causeDamage(attacker.getAttackPower());
	}

    public boolean isShadyWon() {
        return ИванЕвгеньевич.isAlive() && enemies.size() == 0;
    }

    public List<AbstractTower> getTowers() {
        return towers; 
    } 

    public List<AbstractEnemy> getEnemies() {
        return enemies;
    }

    public Mosque getMosque() {
        return ИванЕвгеньевич;
    }

    public void implaceBuildingTower(double x, double y) {
        towers.get(towers.size() - 1).moveTo(x, y);
    }

    public void setTargetAreaForBuildingTower(double x, double y) {
        towers.get(towers.size() - 1).setTargetArea(x, y);
    }

    public boolean isGameOver() {
        return !ИванЕвгеньевич.isAlive() || enemies.size() == 0;
    }

    public boolean isGameStart() {
        return gameStarted;
    }

    Mosque ИванЕвгеньевич = new Mosque();
    private List<AbstractTower> towers = new ArrayList<>();
    private List<AbstractEnemy> enemies = new ArrayList<>();
	private boolean gameStarted = false;
    private int towerLimit = 0;

}

class LevelJSONParser {

    public JSONObject parseLevelConfigurations(int levelId) {
        try (InputStream inputStream = LevelJSONParser.class.getResourceAsStream("/level" + levelId + ".json")) {
            String content = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                  .lines()
                  .collect(Collectors.joining("\n"));
            return new JSONObject(content);
        } catch (IOException e) {
            System.err.println("Cannot load confing file");
            throw new RuntimeException(e);
        } catch (JSONException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
