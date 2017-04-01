package hu.uniobuda.nik.betherichest;

/**
 * Created by krisz on 2017. 04. 01..
 */

public class Upgrade {


    public Upgrade(int id,String name, String description, int price, IEffectable effect) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.effect = effect;
    }
    int id;
    String name;
    String description;
    int price;
    IEffectable effect;

    public double Execute(double input) {
        return effect.Effect(input);
    }
}

