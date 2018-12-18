package com.example.android.stringer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.example.android.stringer.database.Client;
import com.example.android.stringer.database.StringerDatabase;

import java.util.List;

import static android.widget.LinearLayout.VERTICAL;

public class MainActivity extends AppCompatActivity implements ClientAdapter.ItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ClientAdapter mAdapter;
    private StringerDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerViewClients);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this ));

        mAdapter = new ClientAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);

       // DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(),VERTICAL);
      //  mRecyclerView.addItemDecoration(decoration);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Client> clientList = mAdapter.getClientList();
                        mDb.stringerDAO().deleteClient(clientList.get(position));
                    }
                });


            }
        }).attachToRecyclerView(mRecyclerView);

        FloatingActionButton fabButton = findViewById(R.id.addClientFAB);
        fabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent addClientIntent = new Intent(MainActivity.this, AddClientActivity.class);
                startActivity(addClientIntent);
            }
        });
        mDb = StringerDatabase.getInstance(getApplicationContext());
        setupViewModel();
    }
    private void setupViewModel(){
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getClients().observe(this, new Observer<List<Client>>() {
            @Override
            public void onChanged(@Nullable List<Client> clients) {
                Log.d(TAG, "update list of clients with livedata");
                mAdapter.setClientList(clients);
            }
        });
    }
    @Override
    public void onItemClickListener(int clientId) {
        Intent intent = new Intent(MainActivity.this, AddClientActivity.class);
        intent.putExtra(AddClientActivity.EXTRA_CLIENT_ID, clientId);
        startActivity(intent);
    }
}
