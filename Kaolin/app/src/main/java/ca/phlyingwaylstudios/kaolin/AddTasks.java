package ca.phlyingwaylstudios.kaolin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class AddTasks extends AppCompatActivity implements View.OnClickListener{

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
    }

    @Override
    public void onClick(View v) {

    }
}
