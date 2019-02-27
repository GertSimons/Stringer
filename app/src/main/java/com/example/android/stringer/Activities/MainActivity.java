package com.example.android.stringer.Activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.android.stringer.Fragments.ClientListFragment;
import com.example.android.stringer.Fragments.DetailFragment;
import com.example.android.stringer.R;
import com.example.android.stringer.database.Client;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ClientListFragment.OnItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "error in mainactivity";
    ArrayList<Client> clients;
    private static final String DETAIL_TAG = "detail";
    private static final String CLIENT_LIST_TAG = "list";
    ClientListFragment clientListFragment;
    static DetailFragment clientDetailFragment;
    static FragmentManager fragmentManager;


    private static View view;
    private static  int integerToChangeColor;

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

        view = this.getWindow().getDecorView();
        String color = PreferenceManager.getDefaultSharedPreferences(this).getString("backgroundColor", "white");
        changeBackgroundColor(color);
        view.setBackgroundResource(integerToChangeColor);
    }

    public static void changeBackgroundColor(String color){
        integerToChangeColor = 0;
        switch (color){
            case "red": integerToChangeColor = R.color.red;
                break;
            case "white": integerToChangeColor = R.color.white;
                break;
            case"blue": integerToChangeColor = R.color.blue;
                break;
        }
        view.setBackgroundResource(integerToChangeColor);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try{
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
            MenuItem preferences = menu.findItem(R.id.preferences);
            return true;
        } catch(Exception ex){
            Log.e(TAG, ex.getLocalizedMessage());
            return false;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.preferences){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemSelected(Client client) {
        clientDetailFragment.setClient(client);
    }
    public static DetailFragment getClientDetailFragment() {
        return clientDetailFragment;
    }
    public static void refreshDetailFragment(Fragment fragment){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.detach(fragment);
        ft.attach(fragment);
        ft.commit();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}
