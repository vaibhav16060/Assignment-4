package vaibhav.iiitd.com.todolist;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Vaibhav on 04-11-2016.
 */
public class ScreenSlidePageFragment extends Fragment {

    public static final String ARG_PAGE = "page_number";
    public static final String ARG_TASK = "task_name";
    public static final String ARG_TIME = "task_time";
    private String mTaskName, mTaskTime;
    private int mPageNumber;

    public static ScreenSlidePageFragment create(int pageNumber, String[] task) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putString(ARG_TASK, task[0]);
        args.putString(ARG_TIME, task[1]);
        Log.i("Info",task[0]+" "+task[1]);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        mTaskName = getArguments().getString(ARG_TASK);
        mTaskTime = getArguments().getString(ARG_TIME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        final ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);
        final DBHelper db = new DBHelper(getActivity().getApplicationContext());

        // Set the title view to show the page number.
        Log.i("Hello", ("Task : " + mTaskName + " " + mTaskTime));
        ((TextView) rootView.findViewById(R.id.tv_title)).setText(mTaskName );
        ((TextView) rootView.findViewById(R.id.tv_details)).setText(db.get_details(mTaskName, mTaskTime));
        Button btn_remove_task = (Button)rootView.findViewById(R.id.btn_remove_task);
        btn_remove_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = ((TextView) rootView.findViewById(R.id.tv_title)).getText().toString();
                String details = ((TextView) rootView.findViewById(R.id.tv_details)).getText().toString();
                db.remove_task(title, details);
                getActivity().finish();
            }
        });

        return rootView;
    }

    public int getPageNumber() {
        return mPageNumber;
    }
}
