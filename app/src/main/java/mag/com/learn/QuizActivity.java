package mag.com.learn;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class QuizActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private final int MULTIPLE_CHOICE_COUNT =5;

    ArrayList<String> array;
    private  ListView list;
    private ArrayAdapter<String> adapter;

    private HashMap<String, String> dict;

    private ArrayList<String> definitions;

    private String currentWord ="";

    public static float score = 0;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        dict = new HashMap<>();

        try {
            readAll();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        adapter= new ArrayAdapter<>(this, R.layout.list_layout, R.id.content,array);
        list = (ListView) findViewById(R.id.list_view);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        generateRandom();
        //SensorClass ss = new SensorClass();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        tv = new TextView(this);
        tv.setText("Score: 0");
        tv.setTextColor(Color.WHITE);
        tv.setPadding(10, 0,10,10);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextSize(19);
        menu.add(R.string.app_name).setActionView(tv).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
        //Toast.makeText(this, list.getItemAtPosition(index).toString(),Toast.LENGTH_SHORT).show();
        //array.remove(index);
        //adapter.notifyDataSetChanged();

        if(dict.get(currentWord).equals(list.getItemAtPosition(index).toString())){
            Toast.makeText(this, "You got it!!",Toast.LENGTH_SHORT).show();
            score += 1f;
            generateRandom();
        }else{
            Toast.makeText(this, "Study more!!",Toast.LENGTH_SHORT).show();
            score -= 0.5f;
        }

        if(score <= 0){
            startActivity(new Intent(QuizActivity.this, MainActivity.class));
            score = 0;
        }
        tv.setText("Score: " + score);
    }

    private void readAll() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(Environment.getExternalStorageDirectory() + "/dictionary.txt"));
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] words = line.split("\\:");
            dict.put(words[0],words[1]);
        }
        scanner.close();
        definitions = new ArrayList<>(dict.values());
        array = new ArrayList<>(dict.keySet());
    }

    private void generateRandom(){
        //shuffle array pick one
        Collections.shuffle(array);
        String word = array.get(0);


        //ask question
        TextView the_word = (TextView) findViewById(R.id.textView);
        the_word.setText(word);
        currentWord = word;

        definitions.clear();
        for(int i=0; i< MULTIPLE_CHOICE_COUNT; i++){
            definitions.add(dict.get(array.get(i)));
        }
        Collections.shuffle(definitions);
        adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_layout,
                R.id.content,
                definitions
        );

        list.setAdapter(adapter);
    }
}
