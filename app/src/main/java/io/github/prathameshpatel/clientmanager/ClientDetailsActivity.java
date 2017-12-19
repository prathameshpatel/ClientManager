package io.github.prathameshpatel.clientmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.github.prathameshpatel.clientmanager.db.AppDatabase;
import io.github.prathameshpatel.clientmanager.entity.Client;

public class ClientDetailsActivity extends AppCompatActivity {

    private AppDatabase mdb;
    private TextView clientId;
    private TextView firstName;
    private TextView lastName;
    private TextView address;
    private TextView phone;
    private TextView isFavorite;

    private static final String TAG = "ClientDetailsActivity";
    public Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);

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
//        Client c = ClientDetailsActivity.this.client;

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
}
