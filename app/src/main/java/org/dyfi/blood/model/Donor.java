package org.dyfi.blood.model;

public class Donor {

    private String name;
    private String mobile;
    private String blood;
    private String date;
    private String adress;


    public Donor() {
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Donor(String name, String mobile, String blood, String date, String adress) {
        this.name = name;
        this.mobile = mobile;
        this.blood = blood;
        this.date = date;
        this.adress = adress;
    }
}
