package ca.phlyingwaylstudios.kaolin;

import android.content.Context;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by il on 06/12/2015.
 */

@ParseClassName("Assignment")
public class Assignment extends KaolinObject{

    private static final String LOG_TAG = "Assignment";
//    public static final String ID = "objectId";
    public static final String BUDGETED_HOURS = "budgetedHours";
    public static final String HOURS_REMAINING = "hoursRemaining";
//    public static final String TASK = "task";
//    public static final String ROLE = "role";
//    public static final String PERSON = "person";
//    public static final String PROJECT = "project";
//    public static final String PHASE = "phase";
    public int tempHoldingIndex = 0;

    public boolean setUpAssignment(Context context, Person person, Role role, double hours,
                                   double rate, Task task, Project project, Phase phase){
        setPerson(person);
        setRole(role);
        setBudgetedHours(hours);
        setHoursRemaining(hours);
        setRate(rate);
        setTask(task);
        setProject(project);
        setPhase(phase);
        return true;
    }

    public double getBudgetedHours(){
        return getDouble(BUDGETED_HOURS);
    }

    public void setBudgetedHours(double hours){
        put(BUDGETED_HOURS, hours);
    }

    public double getHoursRemaining(){
        return getDouble(HOURS_REMAINING);
    }

    public void setHoursRemaining(double hours){
        put(HOURS_REMAINING, hours);
    }

    public double getRate(){
        return getDouble(RATE);
    }

    public void setRate(double rate){
        put(RATE, rate);
    }

    public Task getTask(){
        return (Task)getParseObject(TASK);
    }

    public void setTask(Task task){
        put(TASK, ParseObject.createWithoutData(Task.class, task.getObjectId()));
    }

    public Role getRole(){
        return (Role)getParseObject(ROLE);
    }

    public void setRole(Role role){
        put(ROLE, ParseObject.createWithoutData(Role.class, role.getObjectId()));
    }

    public Person getPerson(){
        return (Person)getParseObject(PERSON);
    }

    public void setPerson(Person person){
        put(PERSON, ParseObject.createWithoutData(Person.class, person.getObjectId()));
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
