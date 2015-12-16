package ca.phlyingwaylstudios.kaolin;

import android.content.Context;

import com.parse.ParseClassName;

import java.util.List;

/**
 * Created by il on 06/12/2015.
 */

@ParseClassName("Material")
public class Material extends KaolinObject{

    private static final String LOG_TAG = "Material";
//    public static final String ID = "objectId";
//    public static final String NAME = "name";
    public static final String BUDGETED_COST = "budgetedCost";
//    public static final String TASK = "task";
//    public static final String PROJECT = "project";
//    public static final String PHASE = "phase";
    public int tempHoldingIndex = 0;

    public boolean setUpMaterial(Context context, String name, double cost, Task task,
                                 Project project, Phase phase, List<Material> materialList){
        if (checkString(context, name, NAME, materialList)){
            setName(name);
            setBudgetedCost(cost);
            setTask(task);
            setProject(project);
            setPhase(phase);
            return true;
        } else {
            return false;
        }
    }

    public String getName(){
        return getString(NAME);
    }

    public void setName(String value) {
        put(NAME, value);
    }

    public double getBudgetedCost(){
        return getDouble(BUDGETED_COST);
    }

    public void setBudgetedCost(double cost){
        put(BUDGETED_COST, cost);
    }

    public Task getTask(){
        return (Task)getParseObject(TASK);
    }

    public void setTask(Task task){
        put(TASK, task);
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

    public String toString(){
        return getName();
    }
}
