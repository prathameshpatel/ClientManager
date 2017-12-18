package io.github.prathameshpatel.clientmanager;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.List;

import io.github.prathameshpatel.clientmanager.db.AppDatabase;
import io.github.prathameshpatel.clientmanager.db.DataGenerator;
import io.github.prathameshpatel.clientmanager.entity.Client;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        populateFragment();

        db = AppDatabase.getAppDatabase(getApplicationContext());
        new DatabaseAsync().execute();
    }

    private class DatabaseAsync extends AsyncTask<Void,Void,Void> {
        List<Client> fullNames;
        @Override
        protected Void doInBackground(Void... voids) {
            db.beginTransaction();
            try {
                db.clientDao().deleteAllClients();
                db.clientDao().insertClientList(DataGenerator.generateClients());
                fullNames = db.clientDao().loadFullNames();
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            for(Client client : fullNames) {
                System.out.println(client.getFirstName()+" "+client.getLastName());
            }
        }
    }

    public void populateFragment() {

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, AllFragment.newInstance(null,null));
        transaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.bottom_nav_favorites:
                                selectedFragment = FavoritesFragment.newInstance(null, null);
                                break;
                            case R.id.bottom_nav_all:
                                selectedFragment = AllFragment.newInstance(null, null);
                                break;
                            case R.id.bottom_nav_recents:
                                selectedFragment = RecentsFragment.newInstance(null, null);
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout,selectedFragment);
                        transaction.commit();
                        return true;
                    }
                }
        );
    }


}
