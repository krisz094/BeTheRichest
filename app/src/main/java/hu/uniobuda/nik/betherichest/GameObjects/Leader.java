package hu.uniobuda.nik.betherichest.GameObjects;

/**
 * Created by Kristof on 2017. 04. 17..
 */

public class Leader {
    private String name;
    private boolean isPlayer = false;

    private long money;

    public boolean isPlayer() {
        return isPlayer;
    }

    public Leader(String name, long money) {
        this.name = name;
        this.money = money;
    }

    public Leader(String name, long money, boolean isPlayer) {
        this.name = name;
        this.money = money;
        this.isPlayer = isPlayer;
    }

    public String getName() {
        return name;
    }

    public long getMoney() {
        return money;
    }


}
