package sg.edu.rp.c346.id20014518.demodatabase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnInsert, btnGetTasks;
    TextView tvResults;
    ListView lv;
    ArrayList<Task> al;
    ArrayAdapter<String> aa;
    EditText etTask, etDate;
    boolean asc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTask = findViewById(R.id.editTextTask);
        etDate = findViewById(R.id.editTextDate);
        tvResults = findViewById(R.id.tvResults);
        lv = findViewById(R.id.lv);


        btnInsert = findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                db.insertTask(etTask.getText().toString(), etDate.getText().toString());
            }
        });


        btnGetTasks = findViewById(R.id.btnGetTasks);
        btnGetTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper db = new DBHelper(MainActivity.this);
                ArrayList<String> dbh = db.getTaskContent();
                db.close();
                String txt = "";
                for (int i = 0; i < dbh.size(); i++) {
                    txt += i + ". " + dbh.get(i) + "\n";
                }

                tvResults.setText(txt);


                DBHelper dbh2 = new DBHelper(MainActivity.this);
                al = dbh2.getTasks(asc);
                dbh2.close();
                asc = !asc;

                aa = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, al);
                lv.setAdapter(aa);


                al.clear();
                al.addAll(db.getTasks(asc));
                aa.notifyDataSetChanged();

            }
        });

    }
}