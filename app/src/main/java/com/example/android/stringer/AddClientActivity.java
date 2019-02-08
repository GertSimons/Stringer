package com.example.android.stringer;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.stringer.database.Client;
import com.example.android.stringer.database.FirebaseUtil;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class AddClientActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private static final int PICTURE_RESULT = 42;
    EditText txtName;
    EditText txtFirstName;
    EditText txtDate;
    EditText txtTypeRacket;
    Client client;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        //FirebaseUtil.openFbReference("stringer");
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;

        txtName = (EditText)findViewById(R.id.txtName);
        txtFirstName = (EditText)findViewById(R.id.txtFirstName);
        txtDate = (EditText)findViewById(R.id.txtDateCreated);
        txtTypeRacket =(EditText)findViewById(R.id.txtTypeRacket);
        imageView = (ImageView) findViewById(R.id.image);

        Intent intent = getIntent();
        Client client = (Client) intent.getSerializableExtra("Client");
        if(client==null){
            client = new Client();
        }
        this.client = client;
        txtName.setText(client.getName());
        txtFirstName.setText((client.getFirstName()));
        txtTypeRacket.setText(client.getTypeRacket());
        showImage(client.getImageUrl());
        Button btnImage = findViewById(R.id.btnImage);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(intent.createChooser(intent, "insert picture"), PICTURE_RESULT);

            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.save_menu:
                saveClient();
                Toast.makeText(this,"Client saved",Toast.LENGTH_LONG).show();
                clean();
                backToMainActivity();
                return true;
            case R.id.delete_menu:
                deleteClient();
                Toast.makeText(this,"Client deleted",Toast.LENGTH_LONG).show();
                backToMainActivity();

        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICTURE_RESULT && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            StorageReference ref = FirebaseUtil.mStorageRef.child(imageUri.getLastPathSegment());
            ref.putFile(imageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                   result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {
                           String imageLink = uri.toString();
                           client.setImageUrl(imageLink);
                           showImage(imageLink);
                           Log.d("URL: ", imageLink);
                       }
                   });
                    String pictureName = taskSnapshot.getStorage().getPath();
                    client.setImageName(pictureName);
                    Log.d("Name", pictureName);

                }
            });
        }
    }
    private void clean(){
        txtDate.setText("");
        txtTypeRacket.setText("");
        txtName.setText("");
        txtFirstName.setText("");
        txtName.requestFocus();
    }
    private void saveClient() {
        client.setName(txtName.getText().toString());
        client.setFirstName(txtFirstName.getText().toString());
        client.setTypeRacket(txtTypeRacket.getText().toString());
        if(client.getId()==null){
            mDatabaseReference.push().setValue(client);
        } else {
            mDatabaseReference.child(client.getId()).setValue(client);
        }
    }
    private void deleteClient(){
        if ( client == null){
            Toast.makeText(this, "please save the deal before deleting", Toast.LENGTH_LONG).show();
            return;
        }
        mDatabaseReference.child(client.getId()).removeValue();
    }
    private void backToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    private void showImage(String url) {
        if (url != null) {
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            Picasso.get()
                    .load(url)
                    .resize(width, width*2/3)
                    .centerCrop()
                    .into(imageView);
        }
    }

}
