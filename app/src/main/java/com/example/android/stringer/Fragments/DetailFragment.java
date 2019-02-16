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

        //FirebaseUtil.openFbReference("stringer");
        tvName = view.findViewById(R.id.txtVwName);

        tvName.setText(client.getName());
        //tvName.setText("test");

        TextView tvFirstName = view.findViewById(R.id.txtVwFirstName);
        tvFirstName.setText(client.getFirstName());
        //tvFirstName.setText("testvoornaam");

        /*TextView dateCreated = (TextView) view.findViewById(R.id.txtVwDateCreated);
        dateCreated.;*/

        /*TextView typeRacket = (TextView) view.findViewById(R.id.txtTypeRacket);
        typeRacket.setText(client.getTypeRacket());

        /*ImageView image = (ImageView) view.findViewById(R.id.imageClient);
        Uri uri = client.getImageName();
        image.setImageURI(client.getImageName());*/
        return view;
    }
}
