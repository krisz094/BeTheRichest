package hu.uniobuda.nik.betherichest.GameObjects;

/**
 * Created by Kristof on 2017. 04. 17..
 */

public class Leaders {
    private String name;
    private long money;

    public Leaders(String name, long money){
        this.name = name;
        this.money=money;
    }

    public String getName(){return  name;}

    public long getMoney(){return money;}


}
