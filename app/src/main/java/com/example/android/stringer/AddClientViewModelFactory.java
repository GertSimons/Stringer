package com.example.android.stringer;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.stringer.database.StringerDatabase;

public class AddClientViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final StringerDatabase mDb;
    private final int mClientId;

    public AddClientViewModelFactory(StringerDatabase database, int clientId){
        mDb = database;
        mClientId = clientId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        return(T) new AddClientViewModel(mDb,mClientId);
    }
}
