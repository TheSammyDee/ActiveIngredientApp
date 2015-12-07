package ca.phlyingwaylstudios.kaolin;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by il on 06/12/2015.
 */

@ParseClassName("Assignment")
public class Assignment extends ParseObject{

    private static final String LOG_TAG = "Assignment";
    public static final String ID = "objectId";
    public static final String BUDGETED_HOURS = "budgetedHours";
    public static final String HOURS_REMAINING = "hoursRemaining";
    public static final String TASK = "task";
    public static final String ROLE = "role";
    public static final String PERSON = "person";
    public static final String PROJECT = "project";
    public static final String PHASE = "phase";

    public Float getBudgetedHours(){
        return (Float)getNumber(BUDGETED_HOURS);
    }

    public void setBudgetedHours(Number hours){
        put(BUDGETED_HOURS, hours);
    }

    public Float getHoursRemaining(){
        return (Float)getNumber(HOURS_REMAINING);
    }

    public void setHoursRemaining(Number hours){
        put(HOURS_REMAINING, hours);
    }

    public Task getTask(){
        return (Task)getParseObject(TASK);
    }

    public void setTask(Task task){
        put(TASK, task);
    }

    public Role getRole(){
        return (Role)getParseObject(ROLE);
    }

    public void setRole(Role role){
        put(ROLE, role);
    }

    public Person getPerson(){
        return (Person)getParseObject(PERSON);
    }

    public void setPerson(Person person){
        put(PERSON, person);
    }

    public Project getProject(){
        return (Project)getParseObject(PROJECT);
    }

    public void setProject(Project project){
        put(PROJECT, project);
    }

    public Phase getPhase(){
        return (Phase)getParseObject(PHASE);
    }

    public void setPhase(Phase phase){
        put(PHASE, phase);
    }
}
