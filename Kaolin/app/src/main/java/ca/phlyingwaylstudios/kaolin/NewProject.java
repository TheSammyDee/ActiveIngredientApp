package ca.phlyingwaylstudios.kaolin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewProject extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, AdapterView.OnItemSelectedListener, View.OnClickListener{

    private static final String LOG_TAG = "NewProject";
    private EditText nameEditTxt;
    private EditText codeEditTxt;
    private FrameLayout colorSwatch;
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    private DatePicker datePicker;
    private Spinner spinner;
    private Button cancelBtn;
    private Button createBtn;
    private int red;
    private int blue;
    private int green;
    private String color = "#FF0000";
    private ArrayList<String> templateNames = new ArrayList<String>();
    private int templateNumber = 0;
    List<Project> projectList = null;
    Project newProject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        ParseQuery<Project> query = ParseQuery.getQuery(Project.class);
        query.findInBackground(new FindCallback<Project>() {
            public void done(List<Project> results, ParseException e) {
                if (null == e) {
                    projectList = results;
                } else {
                    Util.showFindError(getApplicationContext());
                }
            }
        });

        nameEditTxt = (EditText) findViewById(R.id.nameEditTxt);
        codeEditTxt = (EditText) findViewById(R.id.codeEditTxt);
        colorSwatch = (FrameLayout) findViewById(R.id.colorSwatch);
        redSeekBar = (SeekBar) findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar) findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar) findViewById(R.id.blueSeekBar);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        spinner = (Spinner) findViewById(R.id.spinner);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        createBtn = (Button) findViewById(R.id.createBtn);

        redSeekBar.setOnSeekBarChangeListener(this);
        redSeekBar.setProgress(255);
        greenSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);
        cancelBtn.setOnClickListener(this);
        createBtn.setOnClickListener(this);
        templateNames.add("none");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, templateNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void createNewProject(){
        newProject = new Project();
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        if (newProject.setUpProject(getApplicationContext(), nameEditTxt.getText().toString(), codeEditTxt.getText().toString(), color, calendar.getTime(), projectList)){
            newProject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (null == e) {
                        finishNewProject();
                    } else {
                        Util.showSaveError(getApplicationContext());
                    }
                }
            });
        }

    }

    public void finishNewProject(){
        Intent data = new Intent();
        data.putExtra(Project.ID, newProject.getObjectId());
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.redSeekBar:
                red = progress;
                break;
            case R.id.greenSeekBar:
                green = progress;
                break;
            case R.id.blueSeekBar:
                blue = progress;
                break;
        }
        int colorInt = Color.rgb(red, green, blue);
        colorSwatch.setBackgroundColor(colorInt);
        color = String.format("#%06X", (0xFFFFFF & colorInt));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        templateNumber = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancelBtn:
                finish();
                break;
            case R.id.createBtn:
                createNewProject();
                break;
        }
    }
}
