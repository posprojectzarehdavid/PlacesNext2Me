package at.zeecode.monkeyman.einrichtungenindernaehe;

import java.io.Serializable;

/**
 * Created by MonkeyMan on 09.06.2016.
 */
public class MyPlace implements Serializable, Comparable<MyPlace> {
    double latitude;
    double longitude;
    int value; // 1 --> in Filter; 0 --> nicht in Filter
    String name;
    String icon;
    String address;
    String [] types;

    public MyPlace(double latitude, double longitude, String name, String icon,
                   String address, String[] types) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.icon = icon;
        this.address = address;
        this.types = types;
        this.value = 0;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getAddress() {
        return address;
    }

    public String[] getTypes() {
        return types;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(MyPlace myPlace) {
        return this.name.compareTo(myPlace.name);
    }
}
