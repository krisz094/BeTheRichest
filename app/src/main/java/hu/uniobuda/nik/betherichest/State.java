package hu.uniobuda.nik.betherichest;

/**
 * Created by Szabi on 2017.04.01..
 */

public class State {
    static State instance;

    double currentMoney;
    int[] ranks;

    public static State Get() {
        if (instance == null) {
            instance = new State();
        }
        return instance;
    }

    private State() {
        currentMoney = 0;
        ranks = new int[]{0, 0, 0, 0};
    }
}
