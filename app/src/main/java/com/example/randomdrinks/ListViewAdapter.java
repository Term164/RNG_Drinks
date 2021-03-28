package com.example.randomdrinks;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Toast;


import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    LayoutInflater mInflater;

    public ListViewAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return  list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.size();
    }

    // Setting the custom list element here
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        // If the view is null we set up the custom list Item otherwise we set the holder to the
        // previous item that is stored. We then set the text of the element and the tag and id so
        // we can get the information later.
        convertView = null;

        if (convertView == null){
            holder = new ViewHolder();
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item, parent, false);

            holder.caption = convertView.findViewById(R.id.player);
            holder.caption.setTag(position);
            holder.caption.setText(list.get(position));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int tag_position = (Integer) holder.caption.getTag();
        holder.caption.setId(tag_position);

        // We add a textChanged listener so we can edit the text and save the new input.
        holder.caption.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final int position2 = holder.caption.getId();
                final EditText Caption = (EditText) holder.caption;
                if (Caption.getText().toString().length() > 0){
                    list.set(position2, Caption.getText().toString());
                }else {
                    Toast.makeText(context, "Please enter player name", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return convertView;
    }

    class ViewHolder {
        EditText caption;
    }
}
