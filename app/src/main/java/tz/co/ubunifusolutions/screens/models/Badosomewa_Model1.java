package tz.co.ubunifusolutions.screens.models;

public class Badosomewa_Model1 {

    private String meter_Number;
    private String connection_Number;
    private String customer_Name;
    private boolean expandable;
    private String area_code, seq,  route;

    public Badosomewa_Model1(String meter_Number, String connection_Number, String customer_Name, boolean expandable, String area_code, String seq, String route) {
        this.meter_Number = meter_Number;
        this.connection_Number = connection_Number;
        this.customer_Name = customer_Name;
        this.expandable = expandable;
        this.area_code = area_code;
        this.seq = seq;
        this.route = route;
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

    public String getCustomer_Name() {
        return customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        this.customer_Name = customer_Name;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

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

    @Override
    public String toString() {
        return "Badosomewa_Model1{" +
                "meter_Number='" + meter_Number + '\'' +
                ", connection_Number='" + connection_Number + '\'' +
                ", customer_Name='" + customer_Name + '\'' +
                ", expandable=" + expandable +
                ", area_code='" + area_code + '\'' +
                ", seq='" + seq + '\'' +
                ", route='" + route + '\'' +
                '}';
    }
}
