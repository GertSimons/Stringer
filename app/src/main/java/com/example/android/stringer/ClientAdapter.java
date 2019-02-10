package com.example.android.stringer;

import android.content.Context;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.stringer.database.Client;
import com.example.android.stringer.database.FirebaseUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder>{
    ArrayList<Client> clients;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;
    private ImageView imageClient;

    public ClientAdapter(){

        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        this.clients = FirebaseUtil.mClients;
        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Client client = dataSnapshot.getValue(Client.class);
                Log.d("Client: ", client.getFirstName());
                client.setId(dataSnapshot.getKey());

                clients.add(client);
                notifyItemInserted(clients.size()-1);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildListener);
    }

    @Override
    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_row,parent,false);

        return new ClientViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ClientViewHolder holder, int position) {
        Client client = clients.get(position);
        holder.bind(client);
    }
    @Override
    public int getItemCount() {
        return clients.size();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{
        TextView tvFirstName;
        TextView tvName;


        public ClientViewHolder(View itemView) {
            super(itemView);
            tvFirstName = (TextView) itemView.findViewById(R.id.tvFirstName);
            tvName = (TextView) itemView.findViewById((R.id.tvName));
            imageClient = (ImageView) itemView.findViewById(R.id.imageClient);

            itemView.setOnClickListener(this);
        }

        public void bind(Client client){
            tvFirstName.setText(client.getFirstName());
            tvName.setText(client.getName());
            showImage(client.getImageUrl());
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d("click" , String.valueOf((position)));
            Client selectedClient = clients.get(position);

            Intent intent = new Intent(itemView.getContext(), AddClientActivity.class);
            intent.putExtra("Client", selectedClient);

            itemView.getContext().startActivity(intent);
        }

        private void showImage(String url){
            if (url != null) {
                Picasso.get()
                        .load(url)
                        .resize(160,160)
                        .centerCrop()
                        .into(imageClient);

            }

        }
    }
}
