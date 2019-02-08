package com.example.android.stringer.database;


import java.util.Date;


public class Bespanning {

    private int id;
    private String typeOfString;
    private long tension;
    private Date dateStrung;
    private int clientID;

    public Bespanning(String typeOfString, long tension, Date dateStrung, int clientID) {
        this.typeOfString = typeOfString;
        this.tension = tension;
        this.dateStrung = dateStrung;
        this.clientID = clientID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeOfString() {
        return typeOfString;
    }

    public void setTypeOfString(String typeOfString) {
        this.typeOfString = typeOfString;
    }

    public long getTension() {
        return tension;
    }

    public void setTension(long tension) {
        this.tension = tension;
    }

    public Date getDateStrung() {
        return dateStrung;
    }

    public void setDateStrung(Date dateStrung) {
        this.dateStrung = dateStrung;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}
