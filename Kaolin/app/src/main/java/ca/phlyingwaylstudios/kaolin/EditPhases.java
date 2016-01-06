package ca.phlyingwaylstudios.kaolin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SeekBar;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class EditPhases extends AppCompatActivity
        implements SeekBar.OnSeekBarChangeListener,
        View.OnClickListener,
        ListView.OnItemClickListener{

    private static final String LOG_TAG = "EditPhases";
    private EditText nameEditTxt;
    private FrameLayout colorSwatch;
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    private Button addBtn;
    private Button saveBtn;
    private Button finishBtn;

    private ListView phasesListView;
    private List<Phase> phaseList = new ArrayList<Phase>();
    private EditPhaseArrayAdapter adapter;
    private Project currentProject;
    private Phase currentPhase;
    private int phaseNum = 0;
    private boolean isNew;
    int red;
    int green;
    int blue;
    int colorInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phases);
        Intent i = getIntent();
        isNew = i.getBooleanExtra(Util.IS_NEW, false);
        nameEditTxt = (EditText) findViewById(R.id.nameEditTxt);
        colorSwatch = (FrameLayout) findViewById(R.id.colorSwatch);
        redSeekBar = (SeekBar) findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar) findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar) findViewById(R.id.blueSeekBar);
        addBtn = (Button) findViewById(R.id.addBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        finishBtn = (Button) findViewById(R.id.finishBtn);
        redSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);
        addBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        finishBtn.setOnClickListener(this);
        phasesListView = (ListView) findViewById(R.id.phasesListView);
        phasesListView.setOnItemClickListener(this);

        adapter = new EditPhaseArrayAdapter(this, phaseList);
        ParseQuery<Project> query = ParseQuery.getQuery(Project.class);
        query.whereEqualTo(Project.ID, i.getExtras().getString(Project.ID));
        query.getFirstInBackground(new GetCallback<Project>() {
            @Override
            public void done(Project object, ParseException e) {
                if (null == e) {
                    currentProject = object;
                    Log.d(LOG_TAG, "found project");
                    checkIfNew();
                } else {
                    Util.showFindError(getApplicationContext());
                    Log.d(LOG_TAG, "finding the project");
                }
            }
        });
    }

    private void checkIfNew(){
        if (isNew){
            createNewPhase();
            currentPhase.saveInBackground();
            phaseList.add(currentPhase);
        } else {
            ParseQuery<Phase> query = ParseQuery.getQuery(Phase.class);
            query.whereEqualTo(Phase.PROJECT, currentProject);
            query.orderByAscending(Phase.START_DATE);
            query.findInBackground(new FindCallback<Phase>() {
                @Override
                public void done(List<Phase> objects, ParseException e) {
                    if (null == e) {
                        phaseList = objects;
                        phaseNum = 0;
                        currentPhase = phaseList.get(phaseNum);
                        populateFields();
                    } else {
                        Util.showFindError(getApplicationContext());
                    }
                }
            });
        }
        phasesListView.setAdapter(adapter);
    }

    private void createNewPhase(){
        phaseNum = phaseList.size();
        currentPhase = new Phase();
        currentPhase.setName("Phase " + (phaseNum + 1));
        currentPhase.setColor("#BFFF0000");
        currentPhase.setStartDate(currentProject.getStartDate());
        currentPhase.setEndDate(currentProject.getEndDate());
        currentPhase.setProject(ParseObject.createWithoutData(
                Project.class, currentProject.getObjectId()));
        populateFields();
    }

    private void populateFields(){
        nameEditTxt.setText(currentPhase.getName());
        int c = Color.parseColor(currentPhase.getColor());
        redSeekBar.setProgress(Color.red(c));
        greenSeekBar.setProgress(Color.green(c));
        blueSeekBar.setProgress(Color.blue(c));
    }

    private void refreshList(){
        phasesListView.setAdapter(adapter);
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
        colorInt = Color.argb(191, red, green, blue);
        colorSwatch.setBackgroundColor(colorInt);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBtn:
                createNewPhase();
                refreshList();
                break;
            case R.id.saveBtn:
                String name = nameEditTxt.getText().toString();
                String color = String.format("#%08X", (0xFFFFFFFF & colorInt));
                if (currentPhase.setUpPhase(getApplicationContext(), name, color, phaseList)) {
                    currentPhase.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (null == e) {
                                if (phaseNum >= phaseList.size()) {
                                    phaseList.add(currentPhase);
                                } else {
                                    phaseList.remove(phaseNum);
                                    phaseList.add(phaseNum, currentPhase);
                                }
                                refreshList();
                            } else {
                                Util.showSaveError(getApplicationContext());
                            }
                        }
                    });
                }
                break;
            case R.id.finishBtn:
                Intent data = new Intent();
                data.putExtra(Project.ID, currentProject.getObjectId());
                setResult(RESULT_OK, data);
                finish();

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        phaseNum = position;
        currentPhase = phaseList.get(position);
        populateFields();
    }
}
