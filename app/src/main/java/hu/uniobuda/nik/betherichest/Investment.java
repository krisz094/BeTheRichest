package hu.uniobuda.nik.betherichest;

import android.graphics.Picture;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Szabi on 2017.03.31..
 */


public class Investment implements Parcelable {
    String name;
    int basePrice;
    double baseDpS;
    String description;
    int rank;
    final double coeff = 1.05;

    public Investment(String name, int basePrice, double baseDpS, String description) {
        this.name = name;
        this.basePrice = basePrice;
        this.baseDpS = baseDpS;
        this.description = description;
        rank = 0;
    }

    public void Buy() {
        rank++;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {

        return (int)Math.round(basePrice * Math.pow(coeff,rank));
    }
    public double getDps() {
        return baseDpS * rank;
    }

    public Investment(Parcel in) {
        name = in.readString();
        basePrice = Integer.parseInt(in.readString());
        baseDpS = Double.parseDouble(in.readString());
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(String.valueOf(basePrice));
        dest.writeString(String.valueOf(baseDpS));
        dest.writeString(description);
    }

    public static final Creator<Investment> CREATOR = new Creator<Investment>() {
        @Override
        public Investment createFromParcel(Parcel in) {
            return new Investment(in);
        }

        @Override
        public Investment[] newArray(int size) {
            return new Investment[size];
        }
    };
}
