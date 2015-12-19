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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddWork extends AppCompatActivity
        implements Spinner.OnItemSelectedListener,
        ListView.OnItemClickListener,
        View.OnClickListener{

    private static final String LOG_TAG = "AddWork";
    private int state;
    private static final int EDITING_ASSIGNMENT = 1;
    private static final int EDITING_MATERIAL = 2;
    private static final int NEW_ASSIGNMENT = 3;
    private static final int NEW_MATERIAL = 4;

    private TextView titleTxt;
    private Spinner projectSpinner;
    private Spinner viewSpinner;
    private ListView taskListView;
    private EditText dateEditTxt;
    private Button newAssignBtn;
    private Button newMatBtn;
    private Button saveBtn;
    private LinearLayout addWorkCostView;
    private TextView addNameTxt;
    private EditText addWorkCostEditTxt;
    private LinearLayout newAssignmentView;
    private Spinner personSpinner;
    private Spinner roleSpinner;
    private EditText rateEditTxt;
    private EditText newAssignWorkEditTxt;
    private LinearLayout newMaterialView;
    private EditText materialNameEditTxt;
    private EditText newMatCostEditTxt;
    private ListView assignmentListView;
    private ListView materialListView;

//    private Project currentProject;
//    private Task currentTask;
//    private Assignment currentAssignment;
//    private Material currentMaterial;
//    private WorkHours currentWorkHours;
//    private MaterialCost currentMaterialCost;
    private List<Project> projectList;
    private List<Task> taskList;
    private List<Assignment> assignmentList;
    private List<Material> materialList;
    private List<WorkHours> workHoursList;
    private List<MaterialCost> materialCostList;
    private List<Person> personList;
    private List<Role> roleList;
    private ArrayAdapter<Task> taskAdapter;
    private ArrayAdapter<Assignment> assignmentAdapter;
    private ArrayAdapter<Material> materialAdapter;
    private ArrayAdapter<Role> roleAdapter;
    private ArrayAdapter<Person> personAdapter;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat;
    private Date currentDate;
   // private int startingProjectNum;
    private int projectNum = -1;
    private int taskNum;
    private int assignmentNum;
    private int materialNum;
    private int workHoursNum;
    private int materialCostNum;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);

        getViews();
        setListeners();
        Log.d(LOG_TAG, "set listeners");
        setUpDatePicker();
        Log.d(LOG_TAG, "set date picker");
        taskListView.setAdapter(taskAdapter);
        assignmentListView.setAdapter(assignmentAdapter);
        materialListView.setAdapter(materialAdapter);
        state = EDITING_ASSIGNMENT;
        changeMode();

        Intent i = getIntent();
        projectNum = i.getExtras().getInt(KaolinObject.ID);
        ParseQuery<Project> query = ParseQuery.getQuery(Project.class);
        query.whereNotEqualTo(Project.IS_TEMPLATE, true);
        query.orderByAscending(Project.END_DATE);
        query.findInBackground(new FindCallback<Project>() {
            @Override
            public void done(List<Project> objects, ParseException e) {
                if (null == e) {
                    projectList = objects;
                    setUpSpinners();
                    Log.d(LOG_TAG, "got projects");
                    populateView();
                } else {
                    Util.showFindError(getApplicationContext());
                }
            }
        });
    }

    private void getViews(){
        titleTxt = (TextView) findViewById(R.id.titleTxt);
        projectSpinner = (Spinner) findViewById(R.id.projectSpinner);
        viewSpinner = (Spinner) findViewById(R.id.viewSpinner);
        taskListView = (ListView) findViewById(R.id.taskListView);
        dateEditTxt = (EditText) findViewById(R.id.dateEditTxt);
        newAssignBtn = (Button) findViewById(R.id.newAssignBtn);
        newMatBtn = (Button) findViewById(R.id.newMatBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        addWorkCostView = (LinearLayout) findViewById(R.id.addWorkCostView);
        addNameTxt = (TextView) findViewById(R.id.addNameTxt);
        addWorkCostEditTxt = (EditText) findViewById(R.id.addWorkCostEditTxt);
        newAssignmentView = (LinearLayout) findViewById(R.id.newAssignmentView);
        personSpinner = (Spinner) findViewById(R.id.personSpinner);
        roleSpinner = (Spinner) findViewById(R.id.roleSpinner);
        rateEditTxt = (EditText) findViewById(R.id.rateEditTxt);
        newAssignWorkEditTxt = (EditText) findViewById(R.id.newAssignWorkEditTxt);
        newMaterialView = (LinearLayout) findViewById(R.id.newMaterialView);
        materialNameEditTxt = (EditText) findViewById(R.id.materialNameEditTxt);
        newMatCostEditTxt = (EditText) findViewById(R.id.newMatCostEditTxt);
        assignmentListView = (ListView) findViewById(R.id.assignmentListView);
        materialListView = (ListView) findViewById(R.id.materialListView);
    }

    private void setListeners(){
        projectSpinner.setOnItemSelectedListener(this);
        viewSpinner.setOnItemSelectedListener(this);
        taskListView.setOnItemClickListener(this);
        dateEditTxt.setOnClickListener(this);
        newAssignBtn.setOnClickListener(this);
        newMatBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        assignmentListView.setOnItemClickListener(this);
        materialListView.setOnItemClickListener(this);
    }

    private void setUpDatePicker(){
        dateFormat = new SimpleDateFormat("MMM dd yyyy");
        Calendar cal = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                currentDate = newDate.getTime();
                dateEditTxt.setText(dateFormat.format(newDate.getTime()));
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        currentDate = new Date();
    }

    private void populateView(){
//        if (projectNum != this.projectNum) {
//            currentProject = projectList.get(projectNum);
//            this.projectNum = projectNum;
//        }
        titleTxt.setText(projectList.get(projectNum).getName());
        if (!projectList.get(projectNum).isCollected()){
            collectData();
        } else {
            populateTaskList();
        }
    }

    private void setUpSpinners(){
        ParseQuery<Person> personQuery = ParseQuery.getQuery(Person.class);
        personQuery.findInBackground(new FindCallback<Person>() {
            @Override
            public void done(List<Person> objects, ParseException e) {
                if (null == e) {
                    personList = objects;
                    ParseQuery<Role> roleQuery = ParseQuery.getQuery(Role.class);
                    roleQuery.findInBackground(new FindCallback<Role>() {
                        @Override
                        public void done(List<Role> objects, ParseException e) {
                            if (null == e) {
                                roleList = objects;
                                setSpinnerAdapters();
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

    private void setSpinnerAdapters(){
        personAdapter = new ArrayAdapter<Person>(this,
                android.R.layout.simple_spinner_item, personList);
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        personSpinner.setAdapter(personAdapter);
        roleAdapter = new ArrayAdapter<Role>(this,
                android.R.layout.simple_spinner_item, roleList);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(roleAdapter);
    }

    private void collectData(){
        ParseQuery<Task> taskQuery = new ParseQuery<Task>(Task.class);
        taskQuery.whereEqualTo(KaolinObject.PROJECT, projectList.get(projectNum));
        taskQuery.findInBackground(new FindCallback<Task>() {
            @Override
            public void done(List<Task> objects, ParseException e) {
                if (null == e) {
                    taskList = objects;
                    ParseQuery<Assignment> assignQuery = new ParseQuery<Assignment>(Assignment.class);
                    assignQuery.whereEqualTo(KaolinObject.PROJECT, projectList.get(projectNum));
                    assignQuery.orderByAscending(KaolinObject.CREATED_AT);
                    assignQuery.findInBackground(new FindCallback<Assignment>() {
                        @Override
                        public void done(List<Assignment> objects, ParseException e) {
                            if (null == e) {
                                assignmentList = objects;
                                ParseQuery<Material> matQuery = new ParseQuery<Material>(Material.class);
                                matQuery.whereEqualTo(KaolinObject.PROJECT, projectList.get(projectNum));
                                matQuery.orderByAscending(KaolinObject.CREATED_AT);
                                matQuery.findInBackground(new FindCallback<Material>() {
                                    @Override
                                    public void done(List<Material> objects, ParseException e) {
                                        if (null == e) {
                                            materialList = objects;
                                            ParseQuery<WorkHours> workQuery = new ParseQuery<WorkHours>(WorkHours.class);
                                            workQuery.whereEqualTo(KaolinObject.PROJECT, projectList.get(projectNum));
                                            workQuery.orderByAscending(KaolinObject.DATE);
                                            workQuery.findInBackground(new FindCallback<WorkHours>() {
                                                @Override
                                                public void done(List<WorkHours> objects, ParseException e) {
                                                    if (null == e) {
                                                        workHoursList = objects;
                                                        ParseQuery<MaterialCost> costQuery = new ParseQuery<MaterialCost>(MaterialCost.class);
                                                        costQuery.whereEqualTo(KaolinObject.PROJECT, projectList.get(projectNum));
                                                        costQuery.orderByAscending(KaolinObject.DATE);
                                                        costQuery.findInBackground(new FindCallback<MaterialCost>() {
                                                            @Override
                                                            public void done(List<MaterialCost> objects, ParseException e) {
                                                                if (null == e) {
                                                                    materialCostList = objects;
                                                                    prepareData();
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
                } else {
                    Util.showFindError(getApplicationContext());
                }
            }
        });
    }

    private void prepareData(){
        projectList.get(projectNum).setTasks(taskList);
        for (WorkHours w : workHoursList){
            for (Assignment a : assignmentList){
                if (w.getAssignment() == a){
                    a.addWorkHours(w);
                    break;
                }
            }
        }
        for (MaterialCost mc : materialCostList){
            for (Material m : materialList){
                if (mc.getMaterial() == m){
                    m.addMaterialCost(mc);
                    break;
                }
            }
        }
        for (Assignment a : assignmentList){
            for (Task t : projectList.get(projectNum).getTasks()){
                if (a.getTask() == t){
                    t.addAssignment(a);
                    break;
                }
            }
        }
        for (Material m : materialList){
            for (Task t : projectList.get(projectNum).getTasks()){
                if (m.getTask() == t){
                    t.addMaterial(m);
                    break;
                }
            }
        }
        projectList.get(projectNum).setCollected(true);
        populateView();
    }

    private void populateTaskList(){
        buildTaskAdapter();
        taskNum = 0;
        buildAssignmentAdapter();
        buildMaterialAdapter();
        assignmentNum = 0;
        materialNum = 0;
        populateAddFields();
    }

    private void populateAddFields(){
        switch (state){
            case (EDITING_ASSIGNMENT):
                addNameTxt.setText(projectList.get(projectNum).getTask(taskNum)
                        .getAssignment(assignmentNum).getPerson().getName() + " - "
                        + projectList.get(projectNum).getTask(taskNum).getAssignment(assignmentNum)
                        .getRole().getName());
                //WORKING HERE figure out if work hours exist for assignment on day
                break;
            case (EDITING_MATERIAL):
                addNameTxt.setText(projectList.get(projectNum).getTask(taskNum)
                        .getMaterial(materialNum).getName());
                break;
        }
    }

    private void buildTaskAdapter() {
        taskAdapter = new ArrayAdapter<Task>(this,
                android.R.layout.simple_list_item_1, projectList.get(projectNum).getTasks());
        refreshTaskList();
    }

    public void refreshTaskList() {
        taskListView.setAdapter(taskAdapter);
    }

    private void buildAssignmentAdapter() {
        assignmentAdapter = new ArrayAdapter<Assignment>(this,
                android.R.layout.simple_list_item_2, android.R.id.text1,
                projectList.get(projectNum).getTasks().get(taskNum).getAssignments()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(projectList.get(projectNum).getTasks().get(taskNum).getAssignments()
                        .get(position).getRole().getName());
                text2.setText(projectList.get(projectNum).getTasks().get(taskNum).getAssignments()
                        .get(position).getPerson().getName());
                return view;
            }
        };
        refreshAssignmentList();
    }

    private void refreshAssignmentList() {
        assignmentListView.setAdapter(assignmentAdapter);
    }

    private void buildMaterialAdapter() {
        materialAdapter = new ArrayAdapter<Material>(this,
                android.R.layout.simple_list_item_1,
                projectList.get(projectNum).getTasks().get(taskNum).getMaterials());
        refreshMaterialList();
    }

    private void refreshMaterialList() {
        materialListView.setAdapter(materialAdapter);
    }

    private void changeMode(){
        switch (state){
            case (NEW_ASSIGNMENT):
                addWorkCostView.setVisibility(View.INVISIBLE);
                newMaterialView.setVisibility(View.INVISIBLE);
                newAssignmentView.setVisibility(View.VISIBLE);
                break;
            case (NEW_MATERIAL):
                addWorkCostView.setVisibility(View.INVISIBLE);
                newAssignmentView.setVisibility(View.INVISIBLE);
                newMaterialView.setVisibility(View.VISIBLE);
                break;
            default:
                addWorkCostView.setVisibility(View.VISIBLE);
                newAssignmentView.setVisibility(View.INVISIBLE);
                newMaterialView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {

    }
}
