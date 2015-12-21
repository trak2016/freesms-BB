package com.cieslak.jacek.freesms.model;


public class SMS {

    String numberTo;
    String numberFrom;
    String message;
    int Date;

    public SMS(String numberTo, String numberFrom, String message, int Date){
        this.numberTo = numberTo;
        this.numberFrom = numberFrom;
        this.message = message;
        this.Date = Date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDate() {
        return Date;
    }

    public void setDate(int date) {
        Date = date;
    }

    public String getNumberFrom() {
        return numberFrom;
    }

    public void setNumberFrom(String numberFrom) {
        this.numberFrom = numberFrom;
    }

    public String getNumberTo() {
        return numberTo;
    }

    public void setNumberTo(String numberTo) {
        this.numberTo = numberTo;
    }
}
