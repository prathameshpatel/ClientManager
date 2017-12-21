package io.github.prathameshpatel.clientmanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.prathameshpatel.clientmanager.db.AppDatabase;
import io.github.prathameshpatel.clientmanager.entity.Client;

public class AddActivity extends AppCompatActivity {

    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private String mImageFileName;

    private ImageView mImageview;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mAddressEditText;
    private EditText mPhoneEditText;
    private Button mCameraButton;
    private View mLayout;

    private AppDatabase mdb;

    private static final int REQUEST_IMAGE_CAPTURE = 2392;
    private static final String TAG = "AddActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mLayout = findViewById(R.id.layout_main);
        mdb = AppDatabase.getAppDatabase(this.getApplicationContext());
        mImageview = findViewById(R.id.imageView);
        mCameraButton = findViewById(R.id.camera_button);
//        mCameraButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                takePictureIntent();
//                checkPermissions();
//            }
//        });
    }

    private String[] getUserInput() {

        mImageview = findViewById(R.id.imageView);
        mFirstNameEditText = findViewById(R.id.firstnameEditText);
        mLastNameEditText = findViewById(R.id.lastnameEditText);
        mAddressEditText = findViewById(R.id.addressEditText);
        mPhoneEditText = findViewById(R.id.phoneEditText);
        mCameraButton = findViewById(R.id.camera_button);

        String firstName = mFirstNameEditText.getText().toString();
        String lastName = mLastNameEditText.getText().toString();
        String address = mAddressEditText.getText().toString();
        String phone = mPhoneEditText.getText().toString();

        String[] insertValues = {firstName, lastName, address, phone};
        return insertValues;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_save:
                //Save to database
                String[] values = getUserInput();
                new InsertDetailsAsync().execute(values[0], values[1], values[2], values[3]);
                return true;
            case R.id.add_cancel:
//                Toast.makeText(this, "Go back to main activity", Toast.LENGTH_SHORT).show();
                //Go back to MainActivity
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class InsertDetailsAsync extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            mdb.beginTransaction();
            try {
                Client client = new Client();
                client.setFirstName(params[0]);
                client.setLastName(params[1]);
                client.setAddress(params[2]);
                client.setPhone(params[3]);

                long rowid = mdb.clientDao().insertClient(client);
                if (rowid != -1) {
                    mdb.setTransactionSuccessful();
                    Log.e("AddActivity", "InsertDetailsAsync doInBackground - client inserted successfully");
                    mdb.endTransaction();
//                    finish();
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Log.e("AddActivity", "InsertDetailsAsync doInBackground - client insert problem");
                }
            } finally {
//                mdb.endTransaction();
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                mImageview.setImageBitmap(mImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkPermissions() {
        // Check if the Camera permission has been granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is already available, start camera preview
            takePictureIntent();
        } else {
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            Snackbar.make(mLayout, "Camera access is required to display the camera preview.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    //the system shows a standard dialog box to user on requestPermissions
                    ActivityCompat.requestPermissions(AddActivity.this,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_IMAGE_CAPTURE);
                }
            }).show();

        } else {
            Snackbar.make(mLayout,
                    "Permission is not available. Requesting camera permission.",
                    Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, call the camera intent.
                takePictureIntent();
            } else {
                // Permission request was denied.
                Snackbar.make(mLayout, "Camera permission request was denied.", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void takePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //make sure that the start activity has an app that can handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create the file where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                Log.e(TAG, "takePictureIntent - IOException");
            }
            //Continue if the file was successfully created
            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        Log.e(TAG, "createImageFile - after timeStamp");
        mImageFileName = "JPEG_" + timeStamp + "_";
        Log.e(TAG, "createImageFile - after setting mImageFileName");
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        Log.e(TAG, "createImageFile - after setting storageDir");
        File image = File.createTempFile(
                mImageFileName,  // prefix
                ".jpg",   // suffix
                storageDir      // directory
        );
        Log.e(TAG, "createImageFile - after setting file");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        Log.e(TAG, "createImageFile - after setting mCurrentPhotoPath");
        return image;
    }*/
    //    @Override
//    public void finish() {
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("passed_item", "get back to main activity");
//
//        //setResult(RESULT_OK);
//        setResult(RESULT_OK, returnIntent);
//        super.finish();
//    }
}
