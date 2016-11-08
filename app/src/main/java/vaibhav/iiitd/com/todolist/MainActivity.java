package vaibhav.iiitd.com.todolist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String[]> tasks = new ArrayList<String[]>();
    RecyclerView rv_items;
    ToDoAdapter adapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(getApplicationContext());
        tasks = dbHelper.get_data_for_display();
        //create recycler view
        rv_items = (RecyclerView) findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager rlm = new LinearLayoutManager(getApplicationContext());
        rv_items.setLayoutManager(rlm);
        rv_items.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv_items.setItemAnimator(new DefaultItemAnimator());

        if(tasks == null){
            //show that no items are available for display
        }
        else {

            adapter = new ToDoAdapter(tasks);

            //rv_items.addItemDecoration(new DividerItemDecoration(getApplicationContext(), null));
            //rv_items.addItemDecoration(this, LinearLayoutManager.VERTICAL);
            rv_items.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
    }

    public void AddTask(View v){
        //Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, AddTask.class);
        //intent.putExtra("count_elements", tasks.size());
        startActivity(intent);
    }

    public void ViewTask(View v){
        //Toast.makeText(getApplicationContext(), "Item " + v.getId() , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, TaskDetail.class);
        intent.putExtra("current_element", (v.getId()));
        Bundle values = new Bundle();
        values.putSerializable("all_tasks", tasks);
        intent.putExtras(values);
        startActivity(intent);
    }

    @Override
    public void onResume(){

        super.onResume();
        ArrayList<String[]> refreshed_tasks = dbHelper.get_data_for_display();
        if(refreshed_tasks != null) {
            adapter = new ToDoAdapter(refreshed_tasks);
            rv_items.setAdapter(adapter);
            tasks = refreshed_tasks;
            adapter.notifyDataSetChanged();
        }
    }
}
