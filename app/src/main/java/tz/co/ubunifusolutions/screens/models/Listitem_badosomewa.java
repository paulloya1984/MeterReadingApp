package tz.co.ubunifusolutions.screens.models;

public class Listitem_badosomewa {
   private   String meternumber_waliosomewa;

    private String connectionnumber_waliosomewa;
    private String previuosreading_waliosomewa;

    public Listitem_badosomewa() {
    }

    public Listitem_badosomewa(String meternumber_waliosomewa, String connectionnumber_waliosomewa, String previuosreading_waliosomewa) {
        this.meternumber_waliosomewa = meternumber_waliosomewa;
        this.connectionnumber_waliosomewa = connectionnumber_waliosomewa;
        this.previuosreading_waliosomewa = previuosreading_waliosomewa;
    }

    public String getMeternumber_waliosomewa() { return meternumber_waliosomewa; }

    public String getConnectionnumber_waliosomewa() { return connectionnumber_waliosomewa; }

    public String getPreviuosreading_waliosomewa() { return previuosreading_waliosomewa; }
}
