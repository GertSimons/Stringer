package com.example.android.stringer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.android.stringer.database.Client;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/*

 */
public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder>{
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    final private ItemClickListener mItemCLickListener;

    private List<Client> clientList;
    private Context mcontext;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());


    public ClientAdapter(Context context, ItemClickListener listener){
        mcontext = context;
        mItemCLickListener = listener;
    }

    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(mcontext)
                .inflate(R.layout.activity_main, parent, false);

        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClientViewHolder holder, int position){
        Client client = clientList.get(position);
        String name = client.getName() + " " + client.getFirstName();
        String createdAt = dateFormat.format(client.getDateCreated());

        holder.nameClient.setText(name);
        holder.dateCreated.setText(createdAt);
    }

    @Override
    public int getItemCount(){
        if(clientList == null){
            return 0;
        }
        return clientList.size();
    }

    public List<Client> getClientList() {return clientList;}

    public void setClientList(List<Client> mClientList){
        clientList = mClientList;
        notifyDataSetChanged();
    }



    public interface ItemClickListener{
        void onItemClickListener(int itemId);
    }
    //inner class to create ViewHolders
    class ClientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nameClient;
        TextView dateCreated;


        public ClientViewHolder(View itemView){
            super(itemView);
            nameClient = itemView.findViewById(R.id.clientNameView);
            dateCreated = itemView.findViewById(R.id.createdView);

            itemView.setOnClickListener(this);
        }




        @Override
        public void onClick(View view){
            int elementId = clientList.get(getAdapterPosition()).getId();
            mItemCLickListener.onItemClickListener(elementId);
        }
    }
}
