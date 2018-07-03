package p.d064905.mygainzapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends Activity {

    Button cancelworkoutbutton;
    Button endworkoutbutton;
    ListView lv;
    ExerciseSync workoutsync;
    ProgressBar progress;
    int breaktime;
    Thread t;
    int z;
    TextView breakview;
    Intent intent1;
    String workoutid;
    String workoutname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        //Standardwerte, falls Intent Übergabe nicht funktioniert
        workoutname = "Workout";
        workoutid = "5636953047302144";

        cancelworkoutbutton = findViewById(R.id.workoutabbrechenButton);
        endworkoutbutton = findViewById(R.id.workoutbeendenButton);
        lv = findViewById(R.id.ListExercise);
        progress = findViewById(R.id.progressBar);
        breakview = findViewById(R.id.textView);
        breaktime =-5;

        //Vorbereitung für Toast
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        final CharSequence text = "Break is over.";

        intent1 = getIntent();
        workoutid = intent1.getStringExtra("id");
        workoutname = intent1.getStringExtra("name");

        aktualisieren();

        getActionBar().setTitle(workoutname);

        //Progressbar wird je nach Pausenzeit im Sekundentakt angepasst und schmeißt am Ende Toast
        t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                z++;
                                if (z < breaktime) {
                                    progress.setProgress(z);
                                    breakview.setText(""+z+"/"+breaktime);
                                }
                                else if (z == breaktime) {
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    progress.setProgress(z);
                                    breakview.setText(""+z+"/"+breaktime);
                                    //ProgressBar wird rot
                                    progress.getProgressDrawable().setColorFilter(Color.parseColor("#ffb300"), PorterDuff.Mode.SRC_IN);
                                }else if (breaktime != -5){
                                    breakview.setText(""+z+"/"+breaktime);
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }

    public void aktualisieren() {
        //if (workoutid.equals("5636953047302144")) {
            workoutsync = new ExerciseSync(this);
            try {
                workoutsync.execute("https://mygainzapp.appspot.com/gainzapp/exercises?workout=" + URLEncoder.encode(workoutid, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        /*}else{
            ArrayList<Uebung> empty = new ArrayList<Uebung>();
            Uebung uebungEmpty = new Uebung(123, "Für diesen Plan sind noch keine Workouts vorhanden", 0, 0, 1, 100,2);
            empty.add(uebungEmpty);
            fillList(empty);
        }*/
    }

    public void fillList(ArrayList<Uebung> ALu){
        if (ALu.isEmpty()){
            ALu.add(new Uebung(0, "Noch keine Übungen in diesem Workout!", 0, 0, 1, 5, 0));
        }
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
            ExerciseActivity.ViewHolder mainViewHolder = null;
            if(convertView ==null){
                LayoutInflater li = LayoutInflater.from(getContext());
                convertView = li.inflate(layout, parent, false);
                ExerciseActivity.ViewHolder holder = new ExerciseActivity.ViewHolder();
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
                            } else {
                                //Pausenzeit der aktuellen Übung auslesen, den Maximalwert der ProgressBar
                                // anpassen und Farbe auf Grün setzen
                                breaktime = getItem(position).getPausenzeit();
                                progress.setMax(breaktime);
                                z = 0;
                                progress.getProgressDrawable().setColorFilter(Color.parseColor("#304ffe"), PorterDuff.Mode.SRC_IN);
                            }
                            view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.roundedbuttongreen));
                            ((Button) view).setTextColor(Color.parseColor("#fafafa"));
                        }else{
                            i = getItem(position).reps;
                            view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.roundedbuttonred));
                            ((Button) view).setTextColor(Color.parseColor("#000000"));
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
                        holder.btn2.setVisibility(View.INVISIBLE);
                        holder.btn2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.roundedbuttongreen));
                    case 2:
                        holder.btn3.setVisibility(View.INVISIBLE);
                        holder.btn3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.roundedbuttongreen));
                    case 3:
                        holder.btn4.setVisibility(View.INVISIBLE);
                        holder.btn4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.roundedbuttongreen));
                    case 4:
                        holder.btn5.setVisibility(View.INVISIBLE);
                        holder.btn5.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.roundedbuttongreen));
                        break;
                }
                convertView.setTag(holder);
            }else{
                mainViewHolder = (ExerciseActivity.ViewHolder) convertView.getTag();
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
        // Wir prüfen, ob Menü-Element mit der ID "overview"
        // ausgewählt wurde und springen dann auf den "Übersicht"-Screen
        int id = item.getItemId();
        if (id == R.id.overview) {
            changescreen(cancelworkoutbutton);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changescreen(View aView) {
        //Intent intent0 = new Intent(this, MainActivity.class);
        //startActivity(intent0);
        finish();
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
