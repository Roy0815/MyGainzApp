package p.d064905.mygainzapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends Activity {

    private ArrayList<String> data = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ListView lv = findViewById(R.id.ListView);
        generateListContent();
        lv.setAdapter(new MyListAdapter(this, R.layout.layout_template, data));
    }

    private void generateListContent(){
        for (int i = 0; i < 50; i++) {
            data.add("This is line number " + i);
        }
    }

    private class MyListAdapter extends ArrayAdapter<String>{
        private int layout;
        public MyListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder mainViewHolder = null;
            if(convertView ==null){
                LayoutInflater li = LayoutInflater.from(getContext());
                convertView = li.inflate(layout, parent, false);
                ViewHolder holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.listItemTextView);
                holder.btn = (Button) convertView.findViewById(R.id.listItemButton1);
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                convertView.setTag(holder);
            }else{
                mainViewHolder = (ViewHolder) convertView.getTag();
                mainViewHolder.name.setText(getItem(position));
            }
            return convertView;
        }
    }

    public class ViewHolder{
        TextView name;
        Button btn;
    }
}
