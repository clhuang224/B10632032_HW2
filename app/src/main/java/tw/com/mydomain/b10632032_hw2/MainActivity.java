package tw.com.mydomain.b10632032_hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_add:
                Intent intentToStartInputActivity = new Intent(MainActivity.this, InputActivity.class);
                startActivityForResult(intentToStartInputActivity, 1);
                return true;
            case R.id.mi_setting:
                Intent intentToStartSettingActivity = new Intent(MainActivity.this, SettingActivity.class);
                startActivityForResult(intentToStartSettingActivity, 2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if(data.getBooleanExtra("add", false))
                {
                    // TODO 新增資料到資料庫
                }
                break;
            case 2:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListAdapter(this);
        recyclerView.setAdapter(adapter);
    }
}
