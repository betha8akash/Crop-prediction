package com.example.farmwise;

public class HelperClass {
    public HelperClass() {
    }

    String name,mob,adharno,place,pin,pass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getAdharno() {
        return adharno;
    }

    public void setAdharno(String adharno) {
        this.adharno = adharno;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public HelperClass(String name, String mob, String adharno, String place, String pin, String pass) {
        this.name = name;
        this.mob = mob;
        this.adharno = adharno;
        this.place = place;
        this.pin = pin;
        this.pass = pass;
    }
}
