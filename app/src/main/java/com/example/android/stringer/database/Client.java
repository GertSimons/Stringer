package com.example.android.stringer.database;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Date;


public class Client implements Serializable {

    private String id;
    private String name;
    private String firstName;
    private Date dateCreated;
    private Array bespanningen;
    private String typeRacket;
    private String imageUrl;
    private String imageName;



    public Client(){}

    public Client(String name, String firstName, String typeRacket) {
        this.name = name;
        this.firstName = firstName;
        this.typeRacket = typeRacket;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public Date getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    public Array getBespanningen() {
        return bespanningen;
    }
    public void setBespanningen(Array bespanningen) {
        this.bespanningen = bespanningen;
 
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImageName() {
        return imageName;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    public String getTypeRacket() {
        return typeRacket;
    }
    public void setTypeRacket(String typeRacket) {
        this.typeRacket = typeRacket;
    }
}

