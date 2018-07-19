package seanacheng.com.schwartzsurvey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemAdapter extends BaseAdapter {

    LayoutInflater mInflator;
    List<String> values;

    public ItemAdapter(Context c, Map m) {
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        values = new ArrayList<String>();

    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = mInflator.inflate(R.layout.value_list_layout,null);
        TextView valueTextView = (TextView) v.findViewById(R.id.valueTextView);
        valueTextView.setText(values.get(position));

        return v;

    }
}
