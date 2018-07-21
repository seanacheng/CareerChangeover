package seanacheng.com.schwartzsurvey;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Value> valuesList;
    private RadioGroup radioGroup;


    public ItemAdapter(Context c, List<Value> list) {
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        valuesList = list;
        Log.d("tag", "ItemAdapter: constructor called");
    }

    @Override
    public int getCount() {
        return valuesList.size();
    }

    @Override
    public Object getItem(int position) {
        return valuesList.get(position).getValue();
    }

    @Override
    public long getItemId(int position) {
        return valuesList.get(position).getID();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Log.d("tag", "getView: begins");
        View v = mInflater.inflate(R.layout.value_list_layout,null);
        TextView idTextView = v.findViewById(R.id.idTextView);
        TextView valueTextView = v.findViewById(R.id.valueTextView);
//        radioGroup = v.findViewById(R.id.rankRadioGroup);
        int id = (int) getItemId(position);
        String value = valuesList.get(position).getValue();
        idTextView.setText(" "+id+". ");
        valueTextView.setText(value);
        Log.d("tag", "getView: return v");

        return v;
    }


}
