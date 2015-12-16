package ca.phlyingwaylstudios.kaolin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
    private Button deleteTaskBtn;
    private Button deleteAssignBtn;
    private Button deleteMatBtn;
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
    private AlertDialog taskDeleteAlert;
    private AlertDialog assignmentDeleteAlert;
    private AlertDialog materialDeleteAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);

        getViews();
        setListeners();
        setUpDatePickers();

        Intent i = getIntent();
        isNew = i.getBooleanExtra(Util.IS_NEW, false);
        buildTaskAdapter();
        buildAssignmentAdapter();
        buildMaterialAdapter();
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
                    Log.d(LOG_TAG, "project " + currentProject.getName() + " - " + currentProject.getObjectId());
                    getPhases();
                } else {
                    Util.showFindError(getApplicationContext());
                }
            }
        });
    }

    private void getViews(){
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
        deleteTaskBtn = (Button)findViewById(R.id.deleteTaskBtn);
        deleteAssignBtn = (Button)findViewById(R.id.deleteAssignBtn);
        deleteMatBtn = (Button)findViewById(R.id.deleteMatBtn);
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
    }

    private void setListeners(){
        newTaskBtn.setOnClickListener(this);
        saveTaskBtn.setOnClickListener(this);
        finishButton.setOnClickListener(this);
        newAssignBtn.setOnClickListener(this);
        saveAssignBtn.setOnClickListener(this);
        newMatBtn.setOnClickListener(this);
        saveMatBtn.setOnClickListener(this);
        deleteTaskBtn.setOnClickListener(this);
        deleteAssignBtn.setOnClickListener(this);
        deleteMatBtn.setOnClickListener(this);
        taskListView.setOnItemClickListener(this);
        assignmentListView.setOnItemClickListener(this);
        materialListView.setOnItemClickListener(this);
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);
    }

    private void setUpDatePickers(){
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
    }

    private void setUpTaskAlert(){
        AlertDialog.Builder taskDelBuilder = new AlertDialog.Builder(AddTasks.this);
        taskDelBuilder.setTitle(R.string.app_name);
        taskDelBuilder.setMessage("Delete task " + currentTask.getName() + "?");
        taskDelBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                deleteTask();
            }
        });
        taskDelBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        taskDeleteAlert = taskDelBuilder.create();
        taskDeleteAlert.show();
    }

    private void setUpAssignmentAlert(){
        AlertDialog.Builder assignDelBuilder = new AlertDialog.Builder(AddTasks.this);
        assignDelBuilder.setTitle(R.string.app_name);
        assignDelBuilder.setMessage("Delete assignment for " + currentAssignment.getPerson().getName() + "?");
        assignDelBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                deleteAssignment();
            }
        });
        assignDelBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        assignmentDeleteAlert = assignDelBuilder.create();
        assignmentDeleteAlert.show();
    }

    private void setUpMaterialAlert(){
        AlertDialog.Builder matDelBuilder = new AlertDialog.Builder(AddTasks.this);
        matDelBuilder.setTitle(R.string.app_name);
        matDelBuilder.setMessage("Delete material " + currentMaterial.getName() + "?");
        matDelBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                deleteMaterial();
            }
        });
        matDelBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        materialDeleteAlert = matDelBuilder.create();
        materialDeleteAlert.show();
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
                        Log.d(LOG_TAG, "phase " + p.getName());
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
                if (null == e) {
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
                    getTasks();
                } else {
                    Util.showFindError(getApplicationContext());
                }
            }
        });
    }

    private void getTasks(){
        ParseQuery<Task> query = ParseQuery.getQuery(Task.class);
        query.whereEqualTo(Task.PROJECT, currentProject);
        query.findInBackground(new FindCallback<Task>() {
            @Override
            public void done(List<Task> objects, ParseException e) {
                if (null == e) {
                    taskList = objects;
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
        if (isNew) {
            createNewTask();
        } else {
            taskNum = 0;
            currentTask = taskList.get(0);
            populateTaskFields();
            buildTaskAdapter();
            refreshTaskList();
            buildAssignmentAdapter();
            refreshAssignmentList();
            buildMaterialAdapter();
            refreshMaterialList();
        }
    }

    public void createNewTask(){
        taskNum = taskList.size();
        currentTask = new Task();
        currentTask.setName("Task " + (taskNum + 1));
        currentTask.setProject(currentProject);
        currentPhase = phaseList.get(0);
        currentTask.setPhase(currentPhase);
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
        assignmentList = new ArrayList<>();
        buildAssignmentAdapter();
        clearAssignmentFields();
        materialList = new ArrayList<>();
        buildMaterialAdapter();
        clearMaterialFields();
    }

    private void populateTaskFields(){
        taskNameEditTxt.setText(currentTask.getName());
        currentPhase = currentTask.getPhase();
        phaseSpinner.setSelection(phaseList.indexOf(currentPhase), true);
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
            buildAssignmentAdapter();
        }
        if (allMaterialsList.size() > 0){
            materialList = currentTask.collectMaterials(allMaterialsList);
            buildMaterialAdapter();
        }
        clearAssignmentFields();
        refreshAssignmentList();
        clearMaterialFields();
        refreshMaterialList();
    }

    private void populateAssignmentFields(){
        personSpinner.setSelection(personList.indexOf(currentAssignment.getPerson()));
        roleSpinner.setSelection(roleList.indexOf(currentAssignment.getRole()));
        hoursEditTxt.setText(Double.toString(currentAssignment.getBudgetedHours()));
        rateEditTxt.setText(Double.toString(currentAssignment.getRate()));
    }

    private void populateMaterialFields(){
        matNameEditTxt.setText(currentMaterial.getName());
        matCostEditTxt.setText(Double.toString(currentMaterial.getBudgetedCost()));
    }

    private void clearAssignmentFields(){
        assignmentNum = assignmentList.size();
        currentAssignment = new Assignment();
        personSpinner.setSelection(0, true);
        roleSpinner.setSelection(0, true);
        hoursEditTxt.setText("");
        rateEditTxt.setText("");
    }

    private void clearMaterialFields(){
        materialNum = materialList.size();
        currentMaterial = new Material();
        matCostEditTxt.setText("");
        matNameEditTxt.setText("");
    }

    private void buildTaskAdapter(){
        taskAdapter = new ArrayAdapter<Task>(this,
                android.R.layout.simple_list_item_1, taskList);
    }

    public void refreshTaskList(){
        taskListView.setAdapter(taskAdapter);
    }

    private void buildAssignmentAdapter(){
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
    }

    private void refreshAssignmentList(){
        assignmentListView.setAdapter(assignmentAdapter);
    }

    private void buildMaterialAdapter(){
        materialAdapter = new ArrayAdapter<Material>(this,
                android.R.layout.simple_list_item_1, materialList);
    }

    private void refreshMaterialList(){
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
                a.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (!(null == e)){
                            Util.showSaveError(getApplicationContext());
                        }
                    }
                });
            }
        }
        for (Material m : materialList){
            if (!m.getPhase().hasSameId(currentPhase)){
                m.setPhase(currentPhase);
                m.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (!(null == e)){
                            Util.showSaveError(getApplicationContext());
                        }
                    }
                });
            }
        }
    }

    private void saveAssignment(){
        Person person = personList.get(personSpinner.getSelectedItemPosition());
        Role role = roleList.get(roleSpinner.getSelectedItemPosition());
        String hoursString = hoursEditTxt.getText().toString();
        String rateString = rateEditTxt.getText().toString();
        double hours = 0;
        double rate = 0;
        if (!hoursString.isEmpty()){
            hours = Double.valueOf(hoursString);
        }
        if (!rateString.isEmpty()){
            rate = Double.valueOf(rateString);
        }
        if (currentAssignment.setUpAssignment(getApplicationContext(), person, role, hours, rate,
                currentTask, currentProject, currentPhase)) {
            currentAssignment.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (null == e) {
                        if (assignmentNum >= assignmentList.size()) {
                            assignmentList.add(currentAssignment);
                            currentAssignment.tempHoldingIndex = allAssignmentsList.size();
                            allAssignmentsList.add(currentAssignment);
                        } else {
                            assignmentList.remove(assignmentNum);
                            assignmentList.add(assignmentNum, currentAssignment);
                            allAssignmentsList.remove(currentAssignment.tempHoldingIndex);
                            allAssignmentsList.add(currentAssignment.tempHoldingIndex, currentAssignment);
                        }
                        refreshAssignmentList();
                    } else {
                        Util.showSaveError(getApplicationContext());
                    }
                }
            });
        }
    }

    private void saveMaterial(){
        String name = matNameEditTxt.getText().toString();
        String costString = matCostEditTxt.getText().toString();
        double cost = 0;
        if (!costString.isEmpty()){
            cost = Double.valueOf(costString);
        }
        if (currentMaterial.setUpMaterial(getApplicationContext(), name, cost, currentTask,
                currentProject, currentPhase, materialList)){
            currentMaterial.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (null == e){
                        if (materialNum >= materialList.size()){
                            materialList.add(currentMaterial);
                            currentMaterial.tempHoldingIndex = allMaterialsList.size();
                            allMaterialsList.add(currentMaterial);
                        } else {
                            materialList.remove(materialNum);
                            materialList.add(materialNum, currentMaterial);
                            allMaterialsList.remove(currentMaterial.tempHoldingIndex);
                            allMaterialsList.add(currentMaterial.tempHoldingIndex, currentMaterial);
                        }
                        refreshMaterialList();
                    } else {
                        Util.showSaveError(getApplicationContext());
                    }
                }
            });
        }
    }

    private void deleteTask(){
        for (int i = assignmentList.size()-1; i>-1; i--){
            Assignment a = assignmentList.get(i);
            allAssignmentsList.remove(a.tempHoldingIndex);
            assignmentList.remove(i);
            a.deleteInBackground();
        }
        for (int j = materialList.size()-1; j>-1; j--){
            Material m = materialList.get(j);
            allMaterialsList.remove(m.tempHoldingIndex);
            materialList.remove(j);
            m.deleteInBackground();
        }
        taskList.remove(taskNum);
        currentTask.deleteInBackground();
        refreshTaskList();
        taskNum = 0;
        currentTask = taskList.get(0);
        populateTaskFields();
    }

    private void deleteAssignment(){
        for (Assignment a : assignmentList){
            if (a.tempHoldingIndex > currentAssignment.tempHoldingIndex){
                a.tempHoldingIndex--;
            }
        }
        allAssignmentsList.remove(currentAssignment.tempHoldingIndex);
        assignmentList.remove(assignmentNum);
        currentAssignment.deleteInBackground();
        clearAssignmentFields();
        refreshAssignmentList();
    }

    private void deleteMaterial(){
        for (Material m : materialList){
            if (m.tempHoldingIndex > currentMaterial.tempHoldingIndex){
                m.tempHoldingIndex--;
            }
        }
        allMaterialsList.remove(currentMaterial.tempHoldingIndex);
        materialList.remove(materialNum);
        currentMaterial.deleteInBackground();
        clearMaterialFields();
        refreshMaterialList();
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
                break;
            case (R.id.toDateEtxt):
                toDatePickerDialog.show();
                break;
            case (R.id.newAssignBtn):
                clearAssignmentFields();
                break;
            case (R.id.saveAssignBtn):
                saveAssignment();
                break;
            case (R.id.newMatBtn):
                clearMaterialFields();
                break;
            case (R.id.saveMatBtn):
                saveMaterial();
                break;
            case (R.id.deleteTaskBtn):
                setUpTaskAlert();
                break;
            case (R.id.deleteAssignBtn):
                setUpAssignmentAlert();
                break;
            case (R.id.deleteMatBtn):
                setUpMaterialAlert();
                break;
            case (R.id.finishBtn):
                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case (R.id.taskListView):
                taskNum = position;
                currentTask = taskList.get(position);
                populateTaskFields();
                break;
            case (R.id.assignmentsListView):
                assignmentNum = position;
                currentAssignment = assignmentList.get(position);
                populateAssignmentFields();
                break;
            case (R.id.materialsListView):
                materialNum = position;
                currentMaterial = materialList.get(position);
                populateMaterialFields();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
