package hu.uniobuda.nik.betherichest;

/**
 * Created by krisz on 2017. 04. 01..
 */

public class Upgrade {

    String name;
    String description;
    int price;
    IEffectable effect;
    Game currentGame;

    //TODO: egyedi feltételek alapján legyen csak elérhető bizonyos upgrade, pl: 10 hamburgeres megvásárlása után, etc
    public Upgrade(int id, String name, String description, int price, IEffectable effect, Game currentGame) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.effect = effect;
        this.currentGame = currentGame;
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
        return currentGame.gameState.currentMoney >= price && !IsBought();
    }

    public boolean IsBought() {
        return currentGame.gameState.GetUpgradeBoughtById(id);
    }

    public double Execute(double input) {
        return effect.Effect(input);
    }
}

