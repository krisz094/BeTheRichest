package hu.uniobuda.nik.betherichest.Effects;

import hu.uniobuda.nik.betherichest.Interfaces.IEffectable;

/**
 * Created by krisz on 2017. 05. 07..
 */

public class MultiplierEffect implements IEffectable {

    Double multiplier;

    public Double getMultiplier() {
        return multiplier;
    }

    public MultiplierEffect(Double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public double effect(double input) {
        return input * this.multiplier;
    }

    @Override
    public String getExtraInfo() {
        return "+" + String.valueOf((multiplier-1)*100) + "%";
    }
}
