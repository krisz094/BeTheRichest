package hu.uniobuda.nik.betherichest.Effects;

import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Investment;
import hu.uniobuda.nik.betherichest.Interfaces.IEffectable;

/**
 * Created by krisz on 2017. 05. 06..
 */

public class GlobalIncrementEffect implements IEffectable {
    Game currentGame;
    double moneyForEach;

    public double getMoneyForEach() {
        return moneyForEach;
    }

    public GlobalIncrementEffect(Game currentGame, double moneyForEach) {
        this.currentGame = currentGame;
        this.moneyForEach = moneyForEach;
    }

    @Override
    public double effect(double input) {
        int ranks = 0;
        for (Investment i : currentGame.getInvestments()) {
            ranks += i.getRank();
        }
        return input + ranks * moneyForEach;
    }

    @Override
    public String getExtraInfo() {

        //return String.valueOf((this.moneyForEach >= 1) ? (int) this.moneyForEach : this.moneyForEach);
        if (this.moneyForEach >= 1) {
            return String.valueOf((int) this.moneyForEach);
        }
        return String.valueOf(this.moneyForEach);
    }
}
