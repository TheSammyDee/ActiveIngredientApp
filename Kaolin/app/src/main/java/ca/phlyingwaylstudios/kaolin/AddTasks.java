package ca.phlyingwaylstudios.kaolin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class AddTasks extends AppCompatActivity
        implements View.OnClickListener,
        ListView.OnItemClickListener,
        AdapterView.OnItemSelectedListener{

    private static final String LOG_TAG = "AddTasks";
    private ListView taskListView;
    private EditText taskNameEditTxt;
    private Spinner phaseSpinner;
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private CheckBox skipWeekendsCheckbox;
    private CheckBox unscheduledCheckbox;
    private Button newTaskBtn;
    private Button saveTaskBtn;
    private Button finishButton;
    private Button newAssignBtn;
    private Button saveAssignBtn;
    private Spinner roleSpinner;
    private Spinner personSpinner;
    private EditText hoursEditTxt;
    private EditText rateEditTxt;
    private ListView assignmentListView;
    private Button newMatBtn;
    private Button saveMatBtn;
    private EditText matNameEditTxt;
    private EditText matCostEditTxt;
    private ListView materialListView;

    private Project currentProject;
    private Phase currentPhase;
    private Task currentTask;
    private Assignment currentAssignment;
    private Material currentMaterial;
    private List<Phase> phaseList = new ArrayList<Phase>();
    private List<Task> taskList = new ArrayList<Task>();
    private List<Assignment> assignmentList = new ArrayList<Assignment>();
    private List<Material> materialList = new ArrayList<Material>();
    private Boolean isNew;
    private ArrayAdapter<Task> taskAdapter;
    private ArrayAdapter<Assignment> assignmentAdapter;
    private ArrayAdapter<Material> materialAdapter;
    private int taskNum = 0;
    private int assignmentNum = 0;
    private int materialNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);
        taskListView = (ListView)findViewById(R.id.taskListView);
        taskNameEditTxt = (EditText)findViewById(R.id.nameEditTxt);
        phaseSpinner = (Spinner)findViewById(R.id.phaseSpinner);
        fromDateEtxt = (EditText)findViewById(R.id.fromDateEtxt);
        toDateEtxt = (EditText)findViewById(R.id.toDateEtxt);
        skipWeekendsCheckbox = (CheckBox)findViewById(R.id.skipWeekendsCheckbox);
        unscheduledCheckbox = (CheckBox)findViewById(R.id.unscheduledCheckbox);
        newTaskBtn = (Button)findViewById(R.id.newTaskBtn);
        saveTaskBtn = (Button)findViewById(R.id.saveTaskBtn);
        finishButton = (Button)findViewById(R.id.finishBtn);
        newAssignBtn = (Button)findViewById(R.id.newAssignBtn);
        saveAssignBtn = (Button)findViewById(R.id.saveAssignBtn);
        roleSpinner = (Spinner)findViewById(R.id.roleSpinner);
        personSpinner = (Spinner)findViewById(R.id.personSpinner);
        hoursEditTxt = (EditText)findViewById(R.id.hoursEditTxt);
        rateEditTxt = (EditText) findViewById(R.id.rateEditTxt);
        assignmentListView = (ListView) findViewById(R.id.assignmentsListView);
        newMatBtn = (Button) findViewById(R.id.newMatBtn);
        saveMatBtn = (Button) findViewById(R.id.saveMatBtn);
        matNameEditTxt = (EditText) findViewById(R.id.matNameEditTxt);
        matCostEditTxt = (EditText)findViewById(R.id.matCostEditTxt);
        materialListView = (ListView)findViewById(R.id.materialsListView);

        newTaskBtn.setOnClickListener(this);
        saveTaskBtn.setOnClickListener(this);
        finishButton.setOnClickListener(this);
        newAssignBtn.setOnClickListener(this);
        saveAssignBtn.setOnClickListener(this);
        newMatBtn.setOnClickListener(this);
        saveMatBtn.setOnClickListener(this);
        taskListView.setOnItemClickListener(this);
        assignmentListView.setOnItemClickListener(this);
        materialListView.setOnItemClickListener(this);

        Intent i = getIntent();
        isNew = i.getBooleanExtra(Util.IS_NEW, false);
        taskAdapter = new ArrayAdapter<Task>(getApplicationContext(),
                android.R.layout.simple_list_item_1, taskList);
        assignmentAdapter = new ArrayAdapter<Assignment>(getApplicationContext(),
                android.R.layout.simple_list_item_2, android.R.id.text1, assignmentList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(assignmentList.get(position).getRole().getName());
                text2.setText(assignmentList.get(position).getPerson().getName());
                return view;
            }
        };
        materialAdapter = new ArrayAdapter<Material>(getApplicationContext(),
                android.R.layout.simple_list_item_1, materialList);
        taskListView.setAdapter(taskAdapter);
        assignmentListView.setAdapter(assignmentAdapter);
        materialListView.setAdapter(materialAdapter);
        ArrayAdapter<Phase> phaseAdapter = new ArrayAdapter<Phase>(getApplicationContext(), android.R.layout.simple_spinner_item, phaseList);
        phaseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phaseSpinner.setAdapter(phaseAdapter);
        phaseSpinner.setOnItemSelectedListener(this);

        ParseQuery<Project> query = ParseQuery.getQuery(Project.class);
        query.whereEqualTo(Project.ID, i.getExtras().getString(Project.ID));
        query.getFirstInBackground(new GetCallback<Project>() {
            @Override
            public void done(Project object, ParseException e) {
                if (null == e) {
                    currentProject = object;
                    ParseQuery<Phase> query = ParseQuery.getQuery(Phase.class);
                    query.whereEqualTo(Phase.PROJECT, currentProject);
                    query.orderByAscending(Phase.START_DATE);
                    query.findInBackground(new FindCallback<Phase>() {
                        @Override
                        public void done(List<Phase> objects, ParseException e) {
                            if (null == e) {
                                phaseList = objects;
                            } else {
                                Util.showFindError(getApplicationContext());
                            }
                        }
                    });
                } else {
                    Util.showFindError(getApplicationContext());
                }
            }
        });
    }

    public void createNewTask(){
        taskNum = taskList.size();
        currentTask = new Task();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.newTaskBtn):
                createNewTask();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
