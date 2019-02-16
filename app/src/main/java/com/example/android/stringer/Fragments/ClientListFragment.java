package com.example.android.stringer.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.android.stringer.Activities.AddClientActivity;
import com.example.android.stringer.Adapter.ClientAdapter;
import com.example.android.stringer.R;
import com.example.android.stringer.database.Client;
import com.example.android.stringer.database.FirebaseUtil;

public class ClientListFragment extends Fragment{
    View view;
    FloatingActionButton floatingActionButton;
    RecyclerView rvClients;
    ClientAdapter adapter;
    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener {
        void onItemSelected(Client client);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ItemsListFragment.OnItemSelectedListener");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_client_list, container, false);
        FirebaseUtil.openFbReference("stringer");
        initRecyclerView();

        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        rvClients = view.findViewById(R.id.rvClients);

        floatingActionButton.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), AddClientActivity.class)));
        return view;
    }

    private void initRecyclerView() {
        rvClients = view.findViewById(R.id.rvClients);

        LinearLayoutManager clientsLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        adapter = new ClientAdapter();
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
