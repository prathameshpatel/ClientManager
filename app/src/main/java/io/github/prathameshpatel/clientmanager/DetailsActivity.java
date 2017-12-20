package io.github.prathameshpatel.clientmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import io.github.prathameshpatel.clientmanager.db.AppDatabase;
import io.github.prathameshpatel.clientmanager.entity.Client;

public class DetailsActivity extends AppCompatActivity {

    private AppDatabase mdb;
    private TextView clientId;
    private TextView firstName;
    private TextView lastName;
    private TextView address;
    private TextView phone;
    private TextView isFavorite;

    private static final String TAG = "DetailsActivity";
    public Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        clientId = findViewById(R.id.id_textview);
        firstName = findViewById(R.id.firstname_textview);
        lastName = findViewById(R.id.lastname_textview);
        address = findViewById(R.id.address_textview);
        phone = findViewById(R.id.phone_textview);
        isFavorite = findViewById(R.id.isfavorite_textview);

        Intent intent = getIntent();
        int id = intent.getIntExtra("client_id", 0);
        mdb = AppDatabase.getAppDatabase(this.getApplicationContext());

//        Client[] params = {client};

        //Execute Asynctask
        new GetDetailsAsync().execute(id);
    }

    private class GetDetailsAsync extends AsyncTask<Integer, Void, Client> {
//        Client c = DetailsActivity.this.client;

        @Override
        protected Client doInBackground(Integer... params) {
            mdb.beginTransaction();
            try {
                client = mdb.clientDao().loadClientbyID(params[0]);
                mdb.setTransactionSuccessful();
            } finally {
                mdb.endTransaction();
            }
            return client;
        }

        @Override
        protected void onPostExecute(Client client) {
            super.onPostExecute(client);

            clientId.setText("Cient ID = " + client.getClientId());
            firstName.setText("First Name = " + client.getFirstName());
            lastName.setText("Last Name = " + client.getLastName());
            address.setText("Address = " + client.getAddress());
            phone.setText("Phone = " + client.getPhone());
            isFavorite.setText("isFavorite = " + client.getIsFavorite());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.details_delete:
                //Delete from database
                new DeleteDetailsAsync().execute();
                return true;
            case R.id.details_edit:
                //Go to EditActivity
                Intent intent = new Intent(DetailsActivity.this, EditActivity.class);
                intent.putExtra("clientid",client.getClientId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class DeleteDetailsAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            mdb.beginTransaction();
            try {
                Log.e("ClientDetlsActvty Delet",client.toString());
                int currentid = client.getClientId();
                int rowid = mdb.clientDao().deleteClient(currentid);
                if(rowid != 0) {
                    mdb.setTransactionSuccessful();
                    Log.e(TAG,"InsertDetailsAsync doInBackground - client inserted successfully");
                    mdb.endTransaction();
//                    finish();
                    Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    Log.e(TAG,"InsertDetailsAsync doInBackground - client insert problem");
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
        menuInflater.inflate(R.menu.details_menu, menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }
}
