package tz.co.ubunifusolutions.screens.models;

public class Waliosomewa_Model1 {

    private String meter_Number;
    private String connection_Number;
    private String current_Reading;
    private String customer_Name;
    private String image_Uri;
    private String area_code, seq,  route;

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    private boolean expandable;


    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    @Override
    public String toString() {
        return "Waliosomewa_Model1{" +
                "meter_Number='" + meter_Number + '\'' +
                ", connection_Number='" + connection_Number + '\'' +
                ", current_Reading='" + current_Reading + '\'' +
                ", customer_Name='" + customer_Name + '\'' +
                ", image_Uri='" + image_Uri + '\'' +
                ", area_code='" + area_code + '\'' +
                ", seq='" + seq + '\'' +
                ", route='" + route + '\'' +
                ", expandable=" + expandable +
                '}';
    }

    public Waliosomewa_Model1(String meter_Number, String connection_Number, String current_Reading, String customer_Name, String image_Uri, String area_code, String seq, String route) {
        this.meter_Number = meter_Number;
        this.connection_Number = connection_Number;
        this.current_Reading = current_Reading;
        this.customer_Name = customer_Name;
        this.area_code= area_code;
        this.seq = seq;
        this.route = route;
        this.image_Uri = image_Uri;
        this.expandable = false;
    }

    public String getMeter_Number() {
        return meter_Number;
    }

    public void setMeter_Number(String meter_Number) {
        this.meter_Number = meter_Number;
    }

    public String getConnection_Number() {
        return connection_Number;
    }

    public void setConnection_Number(String connection_Number) {
        this.connection_Number = connection_Number;
    }

    public String getCurrent_Reading() {
        return current_Reading;
    }

    public void setCurrent_Reading(String current_Reading) {
        this.current_Reading = current_Reading;
    }

    public String getCustomer_Name() {
        return customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        this.customer_Name = customer_Name;
    }

    public String getImage_Uri() {
        return image_Uri;
    }

    public void setImage_Uri(String image_Uri) {
        this.image_Uri = image_Uri;
    }

}
