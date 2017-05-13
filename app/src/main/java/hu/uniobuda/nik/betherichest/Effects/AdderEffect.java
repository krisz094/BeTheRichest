package hu.uniobuda.nik.betherichest.Effects;

import hu.uniobuda.nik.betherichest.Interfaces.IEffectable;

/**
 * Created by krisz on 2017. 04. 27..
 */

public class AdderEffect implements IEffectable {
    Double money;
    public AdderEffect(Double money) {
        this.money = money;
    }
    @Override
    public double effect(double input) {
        return input + money;
    }

    @Override
    public String getExtraInfo() {
        return "+" + money.toString() + "$";
    }
}
