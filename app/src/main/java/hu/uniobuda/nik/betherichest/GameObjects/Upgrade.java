package hu.uniobuda.nik.betherichest.GameObjects;

import hu.uniobuda.nik.betherichest.Factories.ConditionalProvider;
import hu.uniobuda.nik.betherichest.Interfaces.IEffectable;

public class Upgrade {
    private String description;
    private long price;
    private IEffectable effect;
    private Game currentGame;
    private ConditionalProvider[] conditions;
    private int id;
    private int imageResource;
    private int color;
    private boolean displayedOnce;

    public Upgrade(int id, String description, long price, IEffectable effect, ConditionalProvider[] conditions, Game currentGame, int imageResource, int color) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.effect = effect;
        this.currentGame = currentGame;
        this.conditions = conditions;
        this.imageResource = imageResource;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public long getPrice() {
        return price;
    }

    public boolean isBuyable() {
        return !isBought() && currentGame.getCurrentMoney() >= price && areConditionsTrue();
    }

    public boolean isDisplayable() {
        if (!displayedOnce) {
            displayedOnce = currentGame.getCurrentMoney() >= price * 0.70 && areConditionsTrue();
        }
        return !isBought() && displayedOnce;
    }

    private boolean areConditionsTrue() {

        for (ConditionalProvider condition : conditions) {
            if (!condition.Evaluate(currentGame)) {
                return false;
            }
        }
        return true;
    }

    public String getEffect() {
        return this.effect.getClass().getSimpleName();
    }

    public boolean buy() {
        if (isBuyable()) {
            currentGame.buyUpgrade(id);
            return true;
        }
        return false;
    }

    public boolean isBought() {
        return currentGame.gameState.getUpgradeBoughtById(id);
    }

    public double execute(double input) {
        return effect.effect(input);
    }

    public int getImageResource() {
        return imageResource;
    }
}

