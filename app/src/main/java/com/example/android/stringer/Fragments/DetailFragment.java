package com.example.android.stringer.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.stringer.R;
import com.example.android.stringer.database.Client;



public class DetailFragment extends Fragment {
    Client client;
    TextView tvName;
    TextView tvFirstName;

    public DetailFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.client = new Client();
    }
    public void setClient(Client client){
        this.client = client;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_detail, container, false);

        tvName = view.findViewById(R.id.txtVwName);
        tvName.setText(client.getName());

        tvFirstName = view.findViewById(R.id.txtVwFirstName);
        tvFirstName.setText(client.getFirstName());
        return view;
    }
}
