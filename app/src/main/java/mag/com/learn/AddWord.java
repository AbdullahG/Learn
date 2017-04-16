package mag.com.learn;

import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AddWord extends AppCompatActivity {
    private Button addWordBtn;
    private EditText word;
    private EditText wordTr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        addWordBtn = (Button) findViewById(R.id.addWordBtn);
        word = (EditText) findViewById(R.id.wordInput);
        wordTr = (EditText) findViewById(R.id.wordTrInput);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void addWord(View view) throws IOException, JSONException {
        String wordStr = word.getText().toString();
        String wordTrStr = wordTr.getText().toString();
        if(wordStr.equals("")){
            Toast.makeText(AddWord.this, "Word cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(wordTrStr.equals("")){
            Toast.makeText(AddWord.this, "Word - Turkish cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(MainActivity.isWordExists(wordStr)){
            Toast.makeText(AddWord.this, "Word is already exists", Toast.LENGTH_SHORT).show();
            return;
        }
        Thread thread = new Thread(new WriteFileAsync(wordStr, wordTrStr));
        thread.start();
        word.setText("");
        wordTr.setText("");
        Toast.makeText(AddWord.this, "The word is added successfully", Toast.LENGTH_SHORT).show();
    }
    private class WriteFileAsync implements Runnable{
        String en, tr;
        WriteFileAsync(String en, String tr){
            this.en = en;
            this.tr = tr;
        }
        @Override
        public void run() {
            try {
                MainActivity.addWordToFile(en, tr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
