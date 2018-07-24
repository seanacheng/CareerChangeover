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


    public ItemAdapter(Context c, List<Value> list) {
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        valuesList = list;
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.value_list_layout,null);
        TextView idTextView = v.findViewById(R.id.idTextView);
        TextView valueTextView = v.findViewById(R.id.valueTextView);
        RadioGroup radioGroup = v.findViewById(R.id.rankRadioGroup);
        int radioChecked = checkRadioGroupClicked(position);

        if (radioChecked > -1) {
            switch (radioChecked) {
                case 0:
                    radioGroup.check(R.id.radio0);
                    break;
                case 1:
                    radioGroup.check(R.id.radio1);
                    break;
                case 2:
                    radioGroup.check(R.id.radio2);
                    break;
                case 3:
                    radioGroup.check(R.id.radio3);
                    break;
                case 4:
                    radioGroup.check(R.id.radio4);
                    break;
                case 5:
                    radioGroup.check(R.id.radio5);
                    break;
                case 6:
                    radioGroup.check(R.id.radio6);
                    break;
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int id) {
                switch (id) {
                    case R.id.radio0:
                        valuesList.get(position).setSelfRank(0);
                        break;
                    case R.id.radio1:
                        valuesList.get(position).setSelfRank(1);
                        break;
                    case R.id.radio2:
                        valuesList.get(position).setSelfRank(2);
                        break;
                    case R.id.radio3:
                        valuesList.get(position).setSelfRank(3);
                        break;
                    case R.id.radio4:
                        valuesList.get(position).setSelfRank(4);
                        break;
                    case R.id.radio5:
                        valuesList.get(position).setSelfRank(5);
                        break;
                    case R.id.radio6:
                        valuesList.get(position).setSelfRank(6);
                        break;
                }
            }
        });
        int id = (int) getItemId(position);
        String value = valuesList.get(position).getValue();
        idTextView.setText(" "+id+". ");
        valueTextView.setText(value);

        return v;
    }

    private int checkRadioGroupClicked (int position) {
        return valuesList.get(position).getSelfRank();
    }

    public List<Value> getValuesList() {
        return valuesList;
    }
}
