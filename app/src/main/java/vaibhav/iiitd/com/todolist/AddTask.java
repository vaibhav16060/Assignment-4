package vaibhav.iiitd.com.todolist;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {
    int a11=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button btn_add_detail = (Button)findViewById(R.id.btn_add_task);
        btn_add_detail.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                EditText tb_title = (EditText)findViewById(R.id.tb_title);
                EditText tb_detail = (EditText)findViewById(R.id.tb_detail);
                if(tb_detail.getText().toString().equals("") || tb_title.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "No field can be left blank !", Toast.LENGTH_SHORT).show();
                }
                else {
                    DBHelper db = new DBHelper(getApplicationContext());
                    db.insert_into_task(tb_title.getText().toString(), tb_detail.getText().toString());
                    Toast.makeText(getApplicationContext(), "Value inserted !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
