package io.github.prathameshpatel.clientmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    EditText mNameEditText;
    EditText mAddressEditText;
    EditText mPhoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mNameEditText = findViewById(R.id.nameEditText);
        mAddressEditText = findViewById(R.id.addressEditText);
        mPhoneEditText = findViewById(R.id.phoneEditText);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_save:
                Toast.makeText(this, "will add to database", Toast.LENGTH_SHORT).show();
                //TODO: save to database
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }
}
