package com.example.android.stringer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.android.stringer.database.Client;
import com.example.android.stringer.database.FirebaseUtil;

import java.util.ArrayList;

public class ClientListFragment extends Fragment{
    ArrayList<Client> clients;
    View view;

    FloatingActionButton floatingActionButton;
    RecyclerView rvClients;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_client_list, container, false);
        FirebaseUtil.openFbReference("stringer");
        initRecyclerView();
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.floatingActionButton);
        rvClients = (RecyclerView) view.findViewById(R.id.rvClients);

        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getActivity(), AddClientActivity.class));
            }
        });

        return view;
    }

    private void initRecyclerView() {
        rvClients = view.findViewById(R.id.rvClients);

        LinearLayoutManager clientsLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        final ClientAdapter adapter = new ClientAdapter();

        rvClients.setAdapter(adapter);
        rvClients.setLayoutManager(clientsLayoutManager);
    }

    @Override
    public void onPause(){
        super.onPause();
      //  FirebaseUtil.removeListener();
    }
    @Override
    public void onResume(){
        super.onResume();
        FirebaseUtil.openFbReference("stringer");
       // FirebaseUtil.attachListener();
    }
}
