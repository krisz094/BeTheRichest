package hu.uniobuda.nik.betherichest;

/**
 * Created by krisz on 2017. 04. 01..
 */

public class Upgrade {


    public Upgrade(String name, String description, int price, int affectedId, IEffectable effect) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.affectedId = affectedId;
        this.effect = effect;
        bought = false;
    }

    public void Buy() {
        bought = true;
    }

    public boolean isBought() {
        return bought;
    }

    String name;
    String description;
    int price;
    int affectedId;
    IEffectable effect;
    boolean bought;
}

