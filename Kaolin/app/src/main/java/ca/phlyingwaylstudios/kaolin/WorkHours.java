package ca.phlyingwaylstudios.kaolin;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by Samantha on 17/12/2015.
 */

@ParseClassName("WorkHours")
public class WorkHours extends KaolinObject {

    private static final String LOG_TAG = "WorkHours";
    public static final String HOURS = "hours";

    public Date getDate(){
        return getDate(DATE);
    }

    public void setDate(Date date){
        put(DATE, Util.removeTime(date));
    }

    public double getHours(){
        return getDouble(HOURS);
    }

    public void setHours(double hours){
        put(HOURS, hours);
    }

    public Task getTask(){
        return (Task)getParseObject(TASK);
    }

    public void setTask(Task task){
        put(TASK, ParseObject.createWithoutData(Task.class, task.getObjectId()));
    }

    public Assignment getAssignment(){
        return (Assignment)getParseObject(ASSIGNMENT);
    }

    public void setAssignment(Assignment assignment){
        put(ASSIGNMENT, ParseObject.createWithoutData(Assignment.class, assignment.getObjectId()));
    }

    public Project getProject(){
        return (Project)getParseObject(PROJECT);
    }

    public void setProject(Project project){
        put(PROJECT, ParseObject.createWithoutData(Project.class, project.getObjectId()));
    }

    public Phase getPhase(){
        return (Phase)getParseObject(PHASE);
    }

    public void setPhase(Phase phase){
        put(PHASE, ParseObject.createWithoutData(Phase.class, phase.getObjectId()));
    }
}
