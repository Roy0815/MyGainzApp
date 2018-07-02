package p.d064905.mygainzapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class WorkoutActivity extends Activity {

    Button cancelworkoutbutton;
    Button endworkoutbutton;
    ListView lv;
    WorkoutSync workoutsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        getActionBar().setTitle("Workout");

        cancelworkoutbutton = findViewById(R.id.workoutabbrechenButton);
        endworkoutbutton = findViewById(R.id.workoutbeendenButton);
        lv = findViewById(R.id.ListExercise);
        aktualisieren();
    }
    public void aktualisieren() {
        workoutsync = new WorkoutSync(this);
        workoutsync.execute("https://mygainzapp.appspot.com/gainzapp/exercises?workout=5630742793027584");
    }

    public void fillList(ArrayList<Uebung> ALu){
        lv.setAdapter(new MyListAdapter(this, R.layout.layout_template, ALu));
    }

    private class MyListAdapter extends ArrayAdapter<Uebung> {
        private int layout;
        public MyListAdapter(@NonNull Context context, int resource, @NonNull List<Uebung> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            WorkoutActivity.ViewHolder mainViewHolder = null;
            if(convertView ==null){
                LayoutInflater li = LayoutInflater.from(getContext());
                convertView = li.inflate(layout, parent, false);
                WorkoutActivity.ViewHolder holder = new WorkoutActivity.ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.listItemTextView);
                holder.weight = convertView.findViewById(R.id.listItemTextViewWeight);
                holder.btn1 = (Button) convertView.findViewById(R.id.listItemButton1);
                holder.btn2 = (Button) convertView.findViewById(R.id.listItemButton2);
                holder.btn3 = (Button) convertView.findViewById(R.id.listItemButton3);
                holder.btn4 = (Button) convertView.findViewById(R.id.listItemButton4);
                holder.btn5 = (Button) convertView.findViewById(R.id.listItemButton5);

                holder.name.setText(getItem(position).name);
                holder.weight.setText(Integer.toString(getItem(position).gewicht) + " kg");
                holder.btn1.setText(Integer.toString(getItem(position).reps));
                holder.btn2.setText(Integer.toString(getItem(position).reps));
                holder.btn3.setText(Integer.toString(getItem(position).reps));
                holder.btn4.setText(Integer.toString(getItem(position).reps));
                holder.btn5.setText(Integer.toString(getItem(position).reps));

                 View.OnClickListener mClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view = (Button) view;
                        int i = Integer.parseInt((String)((Button) view).getText());
                        if (i > 0) {
                            if (!areDrawablesIdentical(view.getBackground(),ContextCompat.getDrawable(getContext(), R.drawable.roundedbuttonred))){
                                i--;
                            }
                            view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.roundedbuttongreen));
                        }else{
                            i = getItem(position).reps;
                            view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.roundedbuttonred));
                        }
                        ((Button) view).setText(Integer.toString(i));
                    }
                };
                holder.btn1.setOnClickListener(mClickListener);
                holder.btn2.setOnClickListener(mClickListener);
                holder.btn3.setOnClickListener(mClickListener);
                holder.btn4.setOnClickListener(mClickListener);
                holder.btn5.setOnClickListener(mClickListener);

                switch (getItem(position).saetze) {
                    case 1:
                        holder.btn2.setEnabled(false);
                        holder.btn2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.roundedbuttongreen));
                    case 2:
                        holder.btn3.setEnabled(false);
                        holder.btn3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.roundedbuttongreen));
                    case 3:
                        holder.btn4.setEnabled(false);
                        holder.btn4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.roundedbuttongreen));
                    case 4:
                        holder.btn5.setEnabled(false);
                        holder.btn5.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.roundedbuttongreen));
                        break;
                }
                convertView.setTag(holder);
            }else{
                mainViewHolder = (WorkoutActivity.ViewHolder) convertView.getTag();
                mainViewHolder.name.setText(getItem(position).name);
            }
            return convertView;
        }
    }

    public class ViewHolder{
        TextView name;
        TextView weight;
        Button btn1;
        Button btn2;
        Button btn3;
        Button btn4;
        Button btn5;
    }

    //PopUp Menü in Action Bar reinladen als 3 Menüpunkte oben rechts
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popupmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Wir prüfen, ob Menü-Element mit der ID "action_daten_aktualisieren"
        // ausgewählt wurde und geben eine Meldung aus
        int id = item.getItemId();
        if (id == R.id.overview) {
            changescreen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changescreen() {
        Intent intent0 = new Intent(this, MainActivity.class);
        startActivity(intent0);
    }
    public static boolean areDrawablesIdentical(Drawable drawableA, Drawable drawableB) {
        Drawable.ConstantState stateA = drawableA.getConstantState();
        Drawable.ConstantState stateB = drawableB.getConstantState();
        // If the constant state is identical, they are using the same drawable resource.
        // However, the opposite is not necessarily true.
        return (stateA != null && stateB != null && stateA.equals(stateB))
                || getBitmap(drawableA).sameAs(getBitmap(drawableB));
    }
    public static Bitmap getBitmap(Drawable drawable) {
        Bitmap result;
        if (drawable instanceof BitmapDrawable) {
            result = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            // Some drawables have no intrinsic width - e.g. solid colours.
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }

            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return result;
    }
}
