package com.example.android.stringer.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "clients")
public class Client {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String firstName;
    private Date dateCreated;
    private ArrayList<Strings> strings;

    @Ignore
public Client(String name, String firstName, Date dateCreated, ArrayList<Strings> strings) {
        this.name = name;
        this.firstName = firstName;
        this.dateCreated = dateCreated;
  //      this.strings = strings;
    }

    public Client(int id,String name, String firstName, Date dateCreated, ArrayList<Strings> strings) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.dateCreated = dateCreated;
  //      this.strings = strings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

   public List<Strings> getStrings() {
       return strings;
   }

   public void setStrings(ArrayList<Strings> strings) {
        this.strings = strings;
    }
}
