package com.example.android.stringer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.stringer.database.Client;
import com.example.android.stringer.database.FirebaseUtil;


public class DetailFragment extends Fragment {
    Client client;
    TextView tvName;
    TextView tvFirstName;

    public DetailFragment(){}
    @SuppressLint("ValidFragment")
    public DetailFragment(Client client){
        this.client = client;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(client == null){
            client = new Client("Testnaam", "Testvoornaam", "testTypeRacket");
        } else {
            client = (Client) getArguments().getSerializable("client");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_client_detail, container, false);

        //FirebaseUtil.openFbReference("stringer");
        tvName = (TextView) view.findViewById(R.id.txtVwName);

        tvName.setText(client.getName());
        //tvName.setText("test");

        TextView tvFirstName = (TextView) view.findViewById(R.id.txtVwFirstName);
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
    public static DetailFragment newInstance(Client client){
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("client", client);
        fragment.setArguments(args);
        return fragment;

    }
}
