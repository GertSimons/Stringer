package com.example.android.stringer.database;

import android.arch.persistence.room.TypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class StringConverter {

    @TypeConverter
    public static ArrayList<Strings> StringToList(String bespanningen ) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Strings> myObjects = null;
        try {
            myObjects = mapper.readValue(bespanningen, new TypeReference<List<Strings>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myObjects;
    }

    @TypeConverter
    public static String ListToString(ArrayList<Strings> strings) {
        String resultstring = "";
        ObjectMapper mapper = new ObjectMapper();
        for(Strings string: strings ){
            try{
               resultstring += mapper.writeValueAsString(string);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return resultstring;
    }
}
