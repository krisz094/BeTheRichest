package hu.uniobuda.nik.betherichest.Effects;

import hu.uniobuda.nik.betherichest.Interfaces.IEffectable;

/**
 * Created by krisz on 2017. 04. 27..
 */

public class AdderEffect implements IEffectable {
    Double percent;
    public AdderEffect(Double money) {
        this.percent = money;
    }
    @Override
    public double effect(double input) {
        return input + percent;
    }

    @Override
    public String getExtraInfo() {
        return "+" + String.valueOf(percent.intValue()) + "%";
    }
}
