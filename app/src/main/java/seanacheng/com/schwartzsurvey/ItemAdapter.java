package seanacheng.com.schwartzsurvey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<String> values;

    public ItemAdapter(Context c, List<String> list) {
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        values = list;
    }

    @Override
    public int getCount() {
        return values.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.value_list_layout,null);
        TextView valueTextView = (TextView) v.findViewById(R.id.valueTextView);
        valueTextView.setText(values.get(position));

        return v;

    }
}
