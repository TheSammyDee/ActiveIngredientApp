package ca.phlyingwaylstudios.kaolin;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by il on 25/11/2015.
 */
public class EditPhaseArrayAdapter extends ArrayAdapter<Phase> {

    private final Context context;
    private final List<Phase> phases;

    public EditPhaseArrayAdapter(Context context, List<Phase> phases){
        super(context, -1, phases);
        this.context = context;
        this.phases = phases;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.edit_phases_list_item, parent, false);
        TextView nameTxt = (TextView) rowView.findViewById(R.id.nameTxt);
        FrameLayout colorSwatch = (FrameLayout) rowView.findViewById(R.id.colorSwatch);
        nameTxt.setText(phases.get(position).getName());
        colorSwatch.setBackgroundColor(Color.parseColor(phases.get(position).getColor()));

        return rowView;
    }
}
