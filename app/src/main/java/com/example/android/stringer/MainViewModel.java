package com.example.android.stringer;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.android.stringer.database.Client;
import com.example.android.stringer.database.StringerDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<Client>> clients;

    public MainViewModel(Application application) {
        super(application);
        StringerDatabase database = StringerDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        clients = database.stringerDao().loadAllClients();
    }

    public LiveData<List<Client>> getClients() {
        return clients;
    }
}