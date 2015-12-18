package ca.phlyingwaylstudios.kaolin;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by Samantha on 17/12/2015.
 */

@ParseClassName("MaterialCost")
public class MaterialCost extends KaolinObject{

    private static final String LOG_TAG = "MaterialCost";
    public static final String COST = "cost";

    public Date getDate(){
        return getDate(DATE);
    }

    public void setDate(Date date){
        put(DATE, Util.removeTime(date));
    }

    public double getCost(){
        return getDouble(COST);
    }

    public void setCost(double cost){
        put(COST, cost);
    }

    public Task getTask(){
        return (Task)getParseObject(TASK);
    }

    public void setTask(Task task){
        put(TASK, ParseObject.createWithoutData(Task.class, task.getObjectId()));
    }

    public Material getMaterial(){
        return (Material)getParseObject(MATERIAL);
    }

    public void setMaterial(Material material){
        put(MATERIAL, ParseObject.createWithoutData(Material.class, material.getObjectId()));
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
