package seanacheng.com.schwartzsurvey;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Value[] valuesArray;
    private String columnName;


    public ItemAdapter(Context c, Value[] array, String column) {
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        valuesArray = array;
        columnName = column;
    }

    @Override
    public int getCount() {
        return valuesArray.length;
    }

    @Override
    public Object getItem(int position) {
        return valuesArray[position].getValue();
    }

    @Override
    public long getItemId(int position) {
        return valuesArray[position].getID();
    }


    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.value_list_layout,null);
        TextView idTextView = v.findViewById(R.id.idTextView);
        TextView valueTextView = v.findViewById(R.id.valueTextView);
        RadioGroup radioGroup = v.findViewById(R.id.rankRadioGroup);
        int radioChecked = valuesArray[position].getRank(columnName);

        switch (radioChecked) {
            case -1:
                break;
            case 0: radioGroup.check(R.id.radio0);
                break;
            case 1: radioGroup.check(R.id.radio1);
                break;
            case 2: radioGroup.check(R.id.radio2);
                break;
            case 3: radioGroup.check(R.id.radio3);
                break;
            case 4: radioGroup.check(R.id.radio4);
                break;
            case 5: radioGroup.check(R.id.radio5);
                break;
            case 6: radioGroup.check(R.id.radio6);
                break;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int id) {
                switch (id) {
                    case R.id.radio0:
                        valuesArray[position].setRank(0,columnName);
                        break;
                    case R.id.radio1:
                        valuesArray[position].setRank(1,columnName);
                        break;
                    case R.id.radio2:
                        valuesArray[position].setRank(2,columnName);
                        break;
                    case R.id.radio3:
                        valuesArray[position].setRank(3,columnName);
                        break;
                    case R.id.radio4:
                        valuesArray[position].setRank(4,columnName);
                        break;
                    case R.id.radio5:
                        valuesArray[position].setRank(5,columnName);
                        break;
                    case R.id.radio6:
                        valuesArray[position].setRank(6,columnName);
                        break;
                }
            }
        });
        int id = (int) getItemId(position);
        String value = valuesArray[position].getValue();
        idTextView.setText(" "+id+". ");
        valueTextView.setText(value);

        return v;
    }

    public Value[] getValuesArray() {
        return valuesArray;
    }
}
