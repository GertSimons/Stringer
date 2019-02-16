package com.example.android.stringer.Activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.stringer.Fragments.ClientListFragment;
import com.example.android.stringer.Fragments.DetailFragment;
import com.example.android.stringer.R;
import com.example.android.stringer.database.Client;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ClientListFragment.OnItemSelectedListener {
    ArrayList<Client> clients;
    private static final String DETAIL_TAG = "detail";
    private static final String CLIENT_LIST_TAG = "list";
    ClientListFragment clientListFragment;
    static DetailFragment clientDetailFragment;
    static FragmentManager fragmentManager;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        clientListFragment = (ClientListFragment) fragmentManager.findFragmentByTag(CLIENT_LIST_TAG);
        if(clientListFragment == null){
            clientListFragment = new ClientListFragment();
            fragmentManager.beginTransaction().add(R.id.ClientListContainer, clientListFragment, CLIENT_LIST_TAG).commit();
        }

        clientDetailFragment = (DetailFragment) fragmentManager.findFragmentByTag(DETAIL_TAG);
        if(clientDetailFragment == null){
            clientDetailFragment = new DetailFragment();
            fragmentManager.beginTransaction().add(R.id.ClientDetailContainer, clientDetailFragment, DETAIL_TAG).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.log_out_menu, menu);
        MenuItem logoutMenu = menu.findItem(R.id.logout_menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        /*if(item.getItemId() == R.id.logout_menu){
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener((task)->{Log.d("Logout","User Logged Out");
                        FirebaseUtil.attachListener();
                    });
            FirebaseUtil.removeListener();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Client client) {
        clientDetailFragment.setClient(client);
    }

    public static DetailFragment getClientDetailFragment(){
        return clientDetailFragment;
    }
    public static void refreshDetailFragment(Fragment fragment){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.detach(fragment);
        ft.attach(fragment);
        ft.commit();
    }
}
