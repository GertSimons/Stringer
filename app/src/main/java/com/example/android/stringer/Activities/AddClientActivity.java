package com.example.android.stringer.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.android.stringer.R;
import com.example.android.stringer.database.Client;
import com.example.android.stringer.database.FirebaseUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;

public class AddClientActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;
    EditText txtName;
    EditText txtFirstName;
    EditText txtDate;
    EditText txtTypeRacket;
    Client client;
    ImageView imageView;
    Bitmap photo;
    private static final int CAMERA_REQUEST = 1888;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        FirebaseDatabase mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;

        txtName = findViewById(R.id.txtName);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtDate = findViewById(R.id.txtDateCreated);
        txtTypeRacket = findViewById(R.id.txtTypeRacket);
        imageView = findViewById(R.id.image);

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
        btnImage.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ActivityCompat.requestPermissions(AddClientActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_REQUEST);
            startActivityForResult(cameraIntent,CAMERA_REQUEST);
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
    public void onResume() {
        super.onResume();
        showImage(client.getImageUrl());
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

        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            photo = (Bitmap) data.getExtras().get("data");

            int time = (int) (System.currentTimeMillis());
            Timestamp tsTemp = new Timestamp(time);
            String timeStamp = tsTemp.toString();

            FirebaseStorage mStorage = FirebaseStorage.getInstance();
            StorageReference mStorageRef = mStorage.getReference().child(timeStamp);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 20 , baos);
            byte[] imageData = baos.toByteArray();

            UploadTask uploadTask = mStorageRef.putBytes(imageData);
            uploadTask.addOnSuccessListener(this, taskSnapshot -> {
                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                result.addOnSuccessListener(uri -> {
                    Uri downloadUrl = uri;
                    String imageLink = uri.toString();
                    client.setImageUrl(imageLink);
                    Log.d("URL: ", imageLink);

                });
                String pictureName = taskSnapshot.getStorage().getPath();
                client.setImageName(pictureName);
                Log.d("Name " , pictureName);
                showImage(client.getImageUrl());
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
