package tz.co.ubunifusolutions.screens.models;

public class waliosomewa {

    private String meternumber;
    private String connectionnumber;
    private String customername;
    private String currentreading;
    private int image;

    public waliosomewa(String meternumber, String connectionnumber, String customername, String currentreading, int image) {
        this.meternumber = meternumber;
        this.connectionnumber = connectionnumber;
        this.customername = customername;
        this.currentreading = currentreading;
        this.image = image;
    }

    public String getMeternumber() {
        return meternumber;
    }

    public String getConnectionnumber() {
        return connectionnumber;
    }

    public String getCustomername() {
        return customername;
    }

    public String getCurrentreading() {
        return currentreading;
    }

    public int getImage() {
        return image;
    }
}
