package com.example.android.stringer;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.android.stringer.database.Client;
import com.example.android.stringer.database.FirebaseUtil;
import com.firebase.ui.auth.AuthUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ClientListFragment.OnItemSelectedListener {
    ArrayList<Client> clients;


    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);

    }

    @Override
    public void onItemSelected(Client i) {

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
        if(item.getItemId() == R.id.logout_menu){
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener((task)->{Log.d("Logout","User Logged Out");
                        FirebaseUtil.attachListener();
                    });
            FirebaseUtil.removeListener();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }


}
