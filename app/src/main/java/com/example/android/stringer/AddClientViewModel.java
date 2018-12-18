package com.example.android.stringer;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.stringer.database.Client;
import com.example.android.stringer.database.StringerDatabase;

public class AddClientViewModel extends ViewModel {
    private LiveData<Client> client;

    public AddClientViewModel(StringerDatabase database, int clientId){
        client = database.stringerDao().loadClientById(clientId);
    }

    public LiveData<Client> getClient() {
        return client;
    }
}
