package ca.phlyingwaylstudios.kaolin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class AddTasks extends AppCompatActivity {

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
    private ListView tasksListView;
    private Button newMatBtn;
    private Button saveMatBtn;
    private EditText matNameEditTxt;
    private EditText matCostEditTxt;
    private ListView materialListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
