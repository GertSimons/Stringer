package com.example.android.stringer.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "strings")
public class Strings {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String typeOfString;
    private long tension;
    private Date dateStrung;
    private Client client;
    private int clientID;

    @Ignore
    public Strings(String typeOfString, long tension, Date dateStrung, Client client){
        this.typeOfString = typeOfString;
        this.tension = tension;
        this.dateStrung = dateStrung;
        this.client = client;
        this.clientID = client.getId();
    }

    public Strings(int id, String typeOfString, long tension, Date dateStrung, Client client){
        this.id = id;
        this.typeOfString = typeOfString;
        this.tension = tension;
        this.dateStrung = dateStrung;
        this.client = client;
        this.clientID = client.getId();
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}
