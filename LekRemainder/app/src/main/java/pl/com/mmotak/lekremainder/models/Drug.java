package pl.com.mmotak.lekremainder.models;

/**
 * Created by mmotak on 28.11.2016.
 */

public class Drug {

    public String name;
    public String type;

    public Drug() {
        name = "";
        type = "";
    }

    public Drug(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
