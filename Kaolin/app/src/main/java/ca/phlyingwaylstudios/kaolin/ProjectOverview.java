package ca.phlyingwaylstudios.kaolin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class ProjectOverview extends AppCompatActivity {

    private static final String LOG_TAG = "ProjectOverview";
    private static final int SETUP_REQUEST = 1;
    private static final int NEW_PHASES_REQUEST = 2;
    private static final int NEW_TASKS_REQUEST = 3;

    private ListView projectListView;
    private List<Project> projectList = null;
    private ProjectOverviewArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        projectListView = (ListView) findViewById(R.id.projectListView);

        ParseQuery<Project> query = ParseQuery.getQuery(Project.class);
        query.orderByAscending(Project.END_DATE);
        query.findInBackground(new FindCallback<Project>() {
            public void done(List<Project> results, ParseException e) {
                if (null == e) {
                    projectList = results;
                    arrayAdapter = new ProjectOverviewArrayAdapter(ProjectOverview.this, projectList);
                    projectListView.setAdapter(arrayAdapter);
                } else {
                    Util.showFindError(getApplicationContext());
                }
            }
        });


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.menu_newProject) {
            Intent newProjectIntent = new Intent(this, NewProject.class);
            startActivityForResult(newProjectIntent, SETUP_REQUEST);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETUP_REQUEST) {
            if (resultCode == RESULT_OK) {
                String projectId = data.getExtras().getString(Project.ID);
                Intent phaseIntent = new Intent(this, EditPhases.class);
                phaseIntent.putExtra(Project.ID, projectId);
                phaseIntent.putExtra(Util.IS_NEW, true);
                startActivityForResult(phaseIntent, NEW_PHASES_REQUEST);
            }
        } else if (requestCode == NEW_PHASES_REQUEST){
            if (resultCode == RESULT_OK){
                String projectId = data.getExtras().getString(Project.ID);
                Intent tasksIntent = new Intent(this, AddTasks.class);
                tasksIntent.putExtra(Project.ID, projectId);
                tasksIntent.putExtra(Util.IS_NEW, true);
                startActivityForResult(tasksIntent, NEW_TASKS_REQUEST);
            }
        }
    }

    public void resetList() {
        ParseQuery<Project> query = ParseQuery.getQuery(Project.class);
        query.orderByAscending(Project.END_DATE);
        query.findInBackground(new FindCallback<Project>() {
            public void done(List<Project> results, ParseException e) {
                if (null == e) {
                    projectList = results;
                    projectListView.setAdapter(arrayAdapter);
                } else {
                    Util.showFindError(getApplicationContext());
                }
            }
        });
    }

}