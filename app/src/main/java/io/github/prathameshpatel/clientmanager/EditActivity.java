package io.github.prathameshpatel.clientmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import io.github.prathameshpatel.clientmanager.db.AppDatabase;
import io.github.prathameshpatel.clientmanager.entity.Client;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "EditActivity.class";
    private AppDatabase mdb;

    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mAddressEditText;
    private EditText mPhoneEditText;

    public Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mFirstNameEditText = findViewById(R.id.edit_firstnameEditText);
        mLastNameEditText = findViewById(R.id.edit_lastnameEditText);
        mAddressEditText = findViewById(R.id.edit_addressEditText);
        mPhoneEditText = findViewById(R.id.edit_phoneEditText);

        Intent intent = getIntent();
        int id = intent.getIntExtra("clientid", 0);
        mdb = AppDatabase.getAppDatabase(this.getApplicationContext());

        new FillDetailsAsync().execute(id);
    }

    private class FillDetailsAsync extends AsyncTask<Integer, Void, Client> {
        @Override
        protected Client doInBackground(Integer... params) {
            mdb.beginTransaction();
            try {
                EditActivity.this.client = mdb.clientDao().loadClientbyID(params[0]);
                mdb.setTransactionSuccessful();
            } finally {
                mdb.endTransaction();
            }
            return client;
        }
        @Override
        protected void onPostExecute(Client c) {
            super.onPostExecute(c);
            client = c;
            if(client == null) {
                Log.e(TAG,"FillDetailsAsync onPostExecute - client=null");
            }
            else {
                mFirstNameEditText.setText(client.getFirstName());
                mLastNameEditText.setText(client.getLastName());
                mAddressEditText.setText(client.getAddress());
                mPhoneEditText.setText(client.getPhone());
            }
        }
    }

    private String[] getUserInput() {
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
                new UpdateDetailsAsync().execute(values[0],values[1],values[2],values[3]);
                return true;
            case R.id.add_cancel:
//                Toast.makeText(this, "Go back to main activity", Toast.LENGTH_SHORT).show();
                //Go back to DetailsActivity
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class UpdateDetailsAsync extends AsyncTask<String, Void, Client> {
        @Override
        protected Client doInBackground(String... params) {
            client.setFirstName(params[0]);
            client.setLastName(params[1]);
            client.setAddress(params[2]);
            client.setPhone(params[3]);
            mdb.beginTransaction();

            try {
                int rowsChanged = mdb.clientDao().updateClient(client);
                if(rowsChanged != 0) {
                    mdb.setTransactionSuccessful();
                    Log.e(TAG,"UpdateDetailsAsync doInBackground - client updated successfully");
                    mdb.endTransaction();
                    Intent intent = new Intent(EditActivity.this, DetailsActivity.class);
                    intent.putExtra("client_id", client.getClientId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    Log.e(TAG,"UpdateDetailsAsync doInBackground - client update problem");
                }

            } finally {
//                mdb.endTransaction();
            }
            return client;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }
}
