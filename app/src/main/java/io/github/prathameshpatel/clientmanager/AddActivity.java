package io.github.prathameshpatel.clientmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import io.github.prathameshpatel.clientmanager.db.AppDatabase;
import io.github.prathameshpatel.clientmanager.entity.Client;

public class AddActivity extends AppCompatActivity {

    private ImageView mImageview;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mAddressEditText;
    private EditText mPhoneEditText;
    private Button mCameraButton;

    private AppDatabase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mdb = AppDatabase.getAppDatabase(this.getApplicationContext());

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

        String[] insertValues = {firstName,lastName,address,phone};
        return insertValues;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_save:
                //Save to database
                String[] values = getUserInput();
                new InsertDetailsAsync().execute(values[0],values[1],values[2],values[3]);
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
                if(rowid != -1) {
                    mdb.setTransactionSuccessful();
                    Log.e("AddActivity","InsertDetailsAsync doInBackground - client inserted successfully");
                    mdb.endTransaction();
//                    finish();
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    Log.e("AddActivity","InsertDetailsAsync doInBackground - client insert problem");
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
