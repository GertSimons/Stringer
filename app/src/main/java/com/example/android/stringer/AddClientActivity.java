package com.example.android.stringer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.stringer.database.Client;
import com.example.android.stringer.database.StringerDatabase;
import com.example.android.stringer.database.Strings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddClientActivity extends AppCompatActivity {
    public static final String EXTRA_CLIENT_ID = "extraClientId";
    public static final String INSTANCE_CLIENT_ID = "instanceTaskId";

    private static final int DEFAULT_CLIENT_ID = 1;

    private int mClientId = DEFAULT_CLIENT_ID;
    private StringerDatabase sDb;
    EditText mName;
    EditText mFirstName;
    Button mButton;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        initViews();

        sDb = StringerDatabase.getInstance(getApplicationContext());

        if(savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_CLIENT_ID)){
            mClientId = savedInstanceState.getInt(INSTANCE_CLIENT_ID,DEFAULT_CLIENT_ID);
        }

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_CLIENT_ID)){
            if(mClientId == DEFAULT_CLIENT_ID){
                mClientId = intent.getIntExtra(EXTRA_CLIENT_ID,DEFAULT_CLIENT_ID);
                AddClientViewModelFactory factory =new AddClientViewModelFactory(sDb, mClientId);

                final AddClientViewModel viewModel = ViewModelProviders.of(this,factory).get(AddClientViewModel.class);

                viewModel.getClient().observe(this, new Observer<Client>(){
                    @Override
                    public void onChanged(@Nullable Client client){
                        viewModel.getClient().removeObserver(this);
                        populateUI(client);
                    }

                });
            }
        }
    }
    protected void onSaveInstanceState(Bundle outstate){
        outstate.putInt(INSTANCE_CLIENT_ID,mClientId);
        super.onSaveInstanceState(outstate);
    }
    private void initViews(){
        mName = findViewById(R.id.editTextName);
        mFirstName = findViewById(R.id.editTextFirstName);

        mButton = findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onSaveButtonClicked();
            }
        });
    }

    private void populateUI(Client client){
        if(client == null){
            return;
        }
        mFirstName.setText(client.getFirstName());
        mName.setText(client.getName());
    }
    public void onSaveButtonClicked(){
        String name = mName.getText().toString();
        String firstName = mFirstName.getText().toString();
        Date date = new Date();
        ArrayList<Strings> stringsList = new ArrayList<Strings>();

        final Client client = new Client(name,firstName,date,stringsList);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if(mClientId == DEFAULT_CLIENT_ID) {
                    sDb.stringerDAO().insertClient(client);
                } else {
                    client.setId(mClientId);
                    sDb.stringerDAO().updateClient(client);
                }
                finish();
            }
        });
    }
}