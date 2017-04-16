package hu.uniobuda.nik.betherichest.GameObjects;

import hu.uniobuda.nik.betherichest.Factories.ConditionalProvider;
import hu.uniobuda.nik.betherichest.Interfaces.IEffectable;

/**
 * Created by krisz on 2017. 04. 01..
 */

public class Upgrade {

    private String name;
    private String description;
    private int price;
    private IEffectable effect;
    private Game currentGame;
    private ConditionalProvider[] conditions;
    private int id;

    public Upgrade(int id, String name, String description, int price, IEffectable effect, ConditionalProvider[] conditions, Game currentGame) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.effect = effect;
        this.currentGame = currentGame;
        this.conditions = conditions;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public boolean isBuyable() {
        return !isBought() && currentGame.gameState.currentMoney >= price && areConditionsTrue();
    }

    private boolean areConditionsTrue() {

        for (ConditionalProvider condition : conditions) {
            if (!condition.Evaluate(currentGame)) {
                return false;
            }
        }
        return true;
    }

    public void buy() {
        if (isBuyable()) {
            currentGame.buyUpgrade(id);
        }
    }

    public boolean isBought() {
        return currentGame.gameState.getUpgradeBoughtById(id);
    }

    public double execute(double input) {
        return effect.effect(input);
    }
}

