package vaibhav.iiitd.com.todolist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.ArrayList;

public class TaskDetail extends AppCompatActivity {

    private int NUM_PAGES = 0;
    private int CURR_PAGE = 0;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    ArrayList<String[]> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_for_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle values_from_main = getIntent().getExtras();
        tasks = (ArrayList<String[]>)values_from_main.getSerializable("all_tasks");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        NUM_PAGES = tasks.size();
        CURR_PAGE = getIntent().getIntExtra("current_element", 1);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.tasks_view_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(CURR_PAGE);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
                invalidateOptionsMenu();
            }
        });
        mPagerAdapter.notifyDataSetChanged();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Log.i("Info"+tasks.get(position).);
            return ScreenSlidePageFragment.create(position, tasks.get(position));
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    /*public void DeleteTask(View v){

        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        TextView tv_details = (TextView)findViewById(R.id.tv_details);

        DBHelper db = new DBHelper(getApplicationContext());
        db.remove_task(tv_title.getText().toString(), tv_details.getText().toString());
        finish();
    }*/

    public void AddTask(View v){
        //Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(TaskDetail.this, AddTask.class);
        //intent.putExtra("count_elements", tasks.size());
        startActivity(intent);
    }
}
