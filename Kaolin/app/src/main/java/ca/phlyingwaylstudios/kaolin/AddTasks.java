package ca.phlyingwaylstudios.kaolin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private List<Person> personList = new ArrayList<Person>();
    private List<Role> roleList = new ArrayList<Role>();
    private List<Assignment> allAssignmentsList = new ArrayList<Assignment>();
    private List<Material> allMaterialsList = new ArrayList<Material>();
    private ArrayList<String> phaseNames = new ArrayList<>();
    private ArrayList<String> personNames = new ArrayList<>();
    private ArrayList<String> roleNames = new ArrayList<>();
    private Boolean isNew;
    private ArrayAdapter<Task> taskAdapter;
    private ArrayAdapter<Assignment> assignmentAdapter;
    private ArrayAdapter<Material> materialAdapter;
    private ArrayAdapter<String> phaseAdapter;
    private ArrayAdapter<String> roleAdapter;
    private ArrayAdapter<String> personAdapter;
    private int taskNum = 0;
    private int assignmentNum = 0;
    private int materialNum = 0;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormat;
    private Date startDate;
    private Date endDate;

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
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        dateFormat = new SimpleDateFormat("dd-MM-yyy");
        Calendar cal = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                startDate = newDate.getTime();
                fromDateEtxt.setText(dateFormat.format(newDate.getTime()));
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                endDate = newDate.getTime();
                toDateEtxt.setText(dateFormat.format(newDate.getTime()));
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        startDate = new Date();
        endDate = new Date();

        Intent i = getIntent();
        isNew = i.getBooleanExtra(Util.IS_NEW, false);
        taskAdapter = new ArrayAdapter<Task>(this,
                android.R.layout.simple_list_item_1, taskList);
        assignmentAdapter = new ArrayAdapter<Assignment>(this,
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
        materialAdapter = new ArrayAdapter<Material>(this,
                android.R.layout.simple_list_item_1, materialList);
        taskListView.setAdapter(taskAdapter);
        assignmentListView.setAdapter(assignmentAdapter);
        materialListView.setAdapter(materialAdapter);

        phaseSpinner.setOnItemSelectedListener(this);

        ParseQuery<Project> query = ParseQuery.getQuery(Project.class);
        query.whereEqualTo(Project.ID, i.getExtras().getString(Project.ID));
        query.getFirstInBackground(new GetCallback<Project>() {
            @Override
            public void done(Project object, ParseException e) {
                if (null == e) {
                    currentProject = object;
                    getPhases();
                } else {
                    Util.showFindError(getApplicationContext());
                }
            }
        });
    }

    private void getPhases(){
        ParseQuery<Phase> query = ParseQuery.getQuery(Phase.class);
        query.whereEqualTo(Phase.PROJECT, currentProject);
        query.orderByAscending(Phase.START_DATE);
        query.findInBackground(new FindCallback<Phase>() {
            @Override
            public void done(List<Phase> objects, ParseException e) {
                if (null == e) {
                    phaseList = objects;
                    for (Phase p : phaseList){
                        phaseNames.add(p.getName());
                    }
                    getPersons();
                } else {
                    Util.showFindError(getApplicationContext());
                }
            }
        });
    }

    private void getPersons(){
        ParseQuery<Person> query = ParseQuery.getQuery(Person.class);
        query.findInBackground(new FindCallback<Person>() {
            @Override
            public void done(List<Person> objects, ParseException e) {
                if (null == e){
                    personList = objects;
                    for (Person p : personList){
                        personNames.add(p.getName());
                    }
                    getRoles();
                } else {
                    Util.showFindError(getApplicationContext());
                }
            }
        });
    }

    private void getRoles(){
        ParseQuery<Role> query = ParseQuery.getQuery(Role.class);
        query.findInBackground(new FindCallback<Role>() {
            @Override
            public void done(List<Role> objects, ParseException e) {
                if (null == e){
                    roleList = objects;
                    for (Role r : roleList){
                        roleNames.add(r.getName());
                    }
                    if (isNew){
                        finishSetup();
                    } else {
                        getAssignments();
                    }
                } else {
                    Util.showFindError(getApplicationContext());
                }
            }
        });
    }

    private void getAssignments(){
        ParseQuery<Assignment> query = ParseQuery.getQuery(Assignment.class);
        query.whereEqualTo(Assignment.PROJECT, currentProject);
        query.findInBackground(new FindCallback<Assignment>() {
            @Override
            public void done(List<Assignment> objects, ParseException e) {
                if (null == e){
                    allAssignmentsList = objects;
                    getMaterials();
                } else {
                    Util.showFindError(getApplicationContext());
                }
            }
        });
    }

    private void getMaterials(){
        ParseQuery<Material> query = ParseQuery.getQuery(Material.class);
        query.whereEqualTo(Material.PROJECT, currentProject);
        query.findInBackground(new FindCallback<Material>() {
            @Override
            public void done(List<Material> objects, ParseException e) {
                if (null == e) {
                    allMaterialsList = objects;
                    finishSetup();
                } else {
                    Util.showFindError(getApplicationContext());
                }
            }
        });
    }

    private void finishSetup(){
        phaseAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, phaseNames);
        phaseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phaseSpinner.setAdapter(phaseAdapter);
        personAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, personNames);
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        personSpinner.setAdapter(personAdapter);
        roleAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, roleNames);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(roleAdapter);
        createNewTask();
    }

    public void createNewTask(){
        taskNum = taskList.size();
        currentTask = new Task();
        currentTask.setName("Task " + (taskNum + 1));
        currentTask.setProject(currentProject);
        currentTask.setPhase(phaseList.get(0));
        currentTask.setSkipWeekends(true);
        currentTask.setUnscheduled(true);
        currentTask.setStartDates(new ArrayList<Date>());
        currentTask.setEndDates(new ArrayList<Date>());
        currentTask.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (null == e) {
                    populateTaskFields();
                    taskList.add(currentTask);
                    refreshTaskList();
                } else {
                    Util.showSaveError(getApplication());
                }
            }
        });

    }

    private void populateTaskFields(){
        taskNameEditTxt.setText(currentTask.getName());
        phaseSpinner.setSelection(phaseList.indexOf(currentTask.getPhase()), true);
        skipWeekendsCheckbox.setChecked(currentTask.getSkipWeekends());
        unscheduledCheckbox.setChecked(currentTask.getUnscheduled());
        if (currentTask.getUnscheduled()){
            toDateEtxt.setText("");
            fromDateEtxt.setText("");
        } else {
            fromDateEtxt.setText(dateFormat.format(currentTask.getNewestStartDate()));
            toDateEtxt.setText(dateFormat.format(currentTask.getNewestEndDate()));
        }
        if (allAssignmentsList.size() > 0) {
            assignmentList = currentTask.collectAssignments(allAssignmentsList);
        }
        if (allMaterialsList.size() > 0){
            materialList = currentTask.collectMaterials(allMaterialsList);
        }
        clearAssignmentFields();
        clearMaterialFields();
    }

    private void clearAssignmentFields(){
        assignmentNum = assignmentList.size();
        personSpinner.setSelection(0, true);
        roleSpinner.setSelection(0, true);
        hoursEditTxt.setText("");
        rateEditTxt.setText("");
    }

    private void clearMaterialFields(){
        materialNum = materialList.size();
        matCostEditTxt.setText("");
        matNameEditTxt.setText("");
    }

    public void refreshTaskList(){
        taskListView.setAdapter(taskAdapter);
    }

    public void refreshAssignmentList(){
        assignmentListView.setAdapter(assignmentAdapter);
    }

    public void refreshMaterialList(){
        materialListView.setAdapter(materialAdapter);
    }

    private void saveTask(){
        String name = taskNameEditTxt.getText().toString();
        currentPhase = phaseList.get(phaseSpinner.getSelectedItemPosition());
        Boolean skip = skipWeekendsCheckbox.isChecked();
        Boolean unsched = unscheduledCheckbox.isChecked();
        if (currentTask.setUpTask(getApplicationContext(), name, currentPhase, skip, unsched,
                startDate, endDate, taskList)) {
            currentTask.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (null == e) {
                        if (taskNum >= taskList.size()) {
                            taskList.add(currentTask);
                        } else {
                            taskList.remove(taskNum);
                            taskList.add(taskNum, currentTask);
                        }
                        updateChildPhaseSelection();
                        refreshTaskList();
                    } else {
                        Util.showSaveError(getApplicationContext());
                    }
                }
            });
        }
    }

    private void updateChildPhaseSelection(){
        for (Assignment a : assignmentList){
            if (!a.getPhase().hasSameId(currentPhase)){
                a.setPhase(currentPhase);
            }
        }
    }

    private void saveAssignment(){
        Person person = personList.get(personSpinner.getSelectedItemPosition());
        Role role = roleList.get(roleSpinner.getSelectedItemPosition());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.newTaskBtn):
                createNewTask();
                break;
            case (R.id.saveTaskBtn):
                saveTask();
                break;
            case (R.id.fromDateEtxt):
                fromDatePickerDialog.show();
                Log.d(LOG_TAG, "from clicked");
                break;
            case (R.id.toDateEtxt):
                Log.d(LOG_TAG, "to clicked");
                toDatePickerDialog.show();
                break;
            case (R.id.newAssignBtn):
                clearAssignmentFields();
                break;
            case (R.id.saveAssignBtn):
                saveAssignment();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case (R.id.taskListView):
                taskNum = position;
                currentTask = taskList.get(position);
                populateTaskFields();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
