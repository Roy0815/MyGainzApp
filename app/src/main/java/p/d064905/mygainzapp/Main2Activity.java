package p.d064905.mygainzapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ScrollView sv = findViewById(R.id.scrollView2);

        LayoutInflater vi = getLayoutInflater();
        View v = vi.inflate(R.layout.activity_main2, null);

        // fill in any details dynamically here
        TextView textView = (TextView) v.findViewById(R.id.a_text_view);
        textView.setText("your text");

        // insert into main view
        ViewGroup insertPoint = (ViewGroup) ScrollView;
        insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
    }
}