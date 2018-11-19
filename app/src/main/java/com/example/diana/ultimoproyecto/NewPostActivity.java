package com.example.diana.ultimoproyecto;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class NewPostActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    Button shareSocialNetwork, savePost;
    EditText txtDescription;
    TextView txtLocation;

    Button fotoCamara;
    Button fotoGalary;
    ImageView foto;

    private Uri uri;

    final static int CONS = 0;

    DatabaseReference databaseReference;

    Geocoder geocoder;
    List<Address> addresses;
    private LocationManager locationManager;
    double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        init();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        onLocationChanged(location);

        geocoder = new Geocoder(NewPostActivity.this);
        try {

            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String adress = addresses.get(0).getAddressLine(0);
            String area = addresses.get(0).getLocality();
            String city = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String post = addresses.get(0).getPostalCode();

            txtLocation.setText(adress + area + city + country + post);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.btn_logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(NewPostActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            uri = data.getData();
            foto.setImageURI(uri);
        }
    }

    private void init() {
        savePost = findViewById(R.id.save_post);
        shareSocialNetwork = findViewById(R.id.share_social_network);
        txtDescription = findViewById(R.id.txt_description);
        txtLocation = findViewById(R.id.txt_location);

        fotoCamara = findViewById(R.id.add_foto_camera);
        fotoGalary = findViewById(R.id.add_foto_gallery);
        foto = findViewById(R.id.foto);
        fotoCamara.setOnClickListener(this);
        fotoGalary.setOnClickListener(this);

        shareSocialNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String desc = txtDescription.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.putExtra(Intent.EXTRA_TEXT, desc);
                startActivity(Intent.createChooser(intent, "Share using?"));
            }

        });

        savePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postUrlFoto = uri.toString();
                String postDescription = txtDescription.getText().toString();
                String postLocation = txtLocation.getText().toString();

                String ID = databaseReference.push().getKey();
                Post post = new Post(postUrlFoto, postDescription,postLocation);

                databaseReference.child("post").child(ID).setValue(post);
                Toast.makeText(getApplicationContext(),"Post was published.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int btn;
        btn = view.getId();
        switch (btn) {

            case R.id.add_foto_camera:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CONS);
                break;

            case R.id.add_foto_gallery:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent.createChooser(intent, "Seleccione la app"), 10);
                break;
        }

    }


}

