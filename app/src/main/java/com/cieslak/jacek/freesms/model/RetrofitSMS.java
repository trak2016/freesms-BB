package com.cieslak.jacek.freesms.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetrofitSMS {

    @SerializedName("numberFrom")
    @Expose
    private String numberFrom;
    @SerializedName("numberTo")
    @Expose
    private String numberTo;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("date")
    @Expose
    private Integer date;

    public RetrofitSMS(){}

    public RetrofitSMS(String numberFrom, Integer date, String message, String numberTo) {
        this.numberFrom = numberFrom;
        this.date = date;
        this.message = message;
        this.numberTo = numberTo;
    }

    /**
     *
     * @return
     * The numberFrom
     */
    public String getNumberFrom() {
        return numberFrom;
    }

    /**
     *
     * @param numberFrom
     * The numberFrom
     */
    public void setNumberFrom(String numberFrom) {
        this.numberFrom = numberFrom;
    }

    /**
     *
     * @return
     * The numberTo
     */
    public String getNumberTo() {
        return numberTo;
    }

    /**
     *
     * @param numberTo
     * The numberTo
     */
    public void setNumberTo(String numberTo) {
        this.numberTo = numberTo;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The date
     */
    public Integer getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(Integer date) {
        this.date = date;
    }
}