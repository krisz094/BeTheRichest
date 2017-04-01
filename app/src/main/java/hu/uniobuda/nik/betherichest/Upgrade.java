package hu.uniobuda.nik.betherichest;

import java.lang.reflect.Array;

/**
 * Created by krisz on 2017. 04. 01..
 */

public class Upgrade {

    String name;
    String description;
    int price;
    IEffectable effect;
    Game currentGame;
    ConditionalProvider[] conditions;

    //TODO: egyedi feltételek alapján legyen csak elérhető bizonyos upgrade, pl: 10 hamburgeres megvásárlása után, etc
    public Upgrade(int id, String name, String description, int price, IEffectable effect, ConditionalProvider[] conditions, Game currentGame) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.effect = effect;
        this.currentGame = currentGame;
        this.conditions = conditions;
    }

    int id;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public boolean IsBuyable() {
        return !IsBought() && currentGame.gameState.currentMoney >= price && AreConditionsTrue();
    }

    private boolean AreConditionsTrue() {

        for (ConditionalProvider condition : conditions) {
            if (!condition.Evaluate(currentGame)) {
                return false;
            }
        }
        return true;
    }

    public boolean IsBought() {
        return currentGame.gameState.GetUpgradeBoughtById(id);
    }

    public double Execute(double input) {
        return effect.Effect(input);
    }
}

