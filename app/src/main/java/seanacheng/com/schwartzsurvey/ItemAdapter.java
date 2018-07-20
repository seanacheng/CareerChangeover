package seanacheng.com.schwartzsurvey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class ItemAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Map<Integer, String> valueMap = new HashMap<>();

    public ItemAdapter(Context c, Map<Integer, String> map) {
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        valueMap = map;
    }

    @Override
    public int getCount() {
        return valueMap.size();
    }

    @Override
    public Object getItem(int position) {
        return valueMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.value_list_layout,null);
        TextView idTextView = (TextView) v.findViewById(R.id.idTextView);
        TextView valueTextView = (TextView) v.findViewById(R.id.valueTextView);
        String id = Integer.toString(position+1);
        String value = valueMap.get(position);
        idTextView.setText(id+". ");
        valueTextView.setText(value);

        return v;

    }
}
