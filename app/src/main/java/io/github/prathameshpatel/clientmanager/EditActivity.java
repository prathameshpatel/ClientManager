package io.github.prathameshpatel.clientmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
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
}
