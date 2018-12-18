package com.example.android.stringer.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface StringerDao {

    //Dao-methods for Client-table

    @Query("SELECT * FROM clients ORDER BY name")
    LiveData<List<Client>> loadAllClients();

    @Query("SELECT * FROM clients WHERE id = :id")
    LiveData<Client> loadClientById(int id);

    @Insert
    void insertClient(Client client);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateClient(Client client);

    @Delete
    void deleteClient(Client client);


    //Dao-methods for Strins-table

    @Query("SELECT * FROM strings INNER JOIN clients ON strings.clientID = clients.id WHERE strings.clientID = :id")
    LiveData<List<Strings>> loadAllStrings(int id);

    @Insert
    void insertString(String string);

    @Update
    void updateString(String string);

    @Delete
    void deleteString(String string);
    }
