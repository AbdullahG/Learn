package mag.com.learn;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private Button wordBtn;
    private TextView word;
    private TextView wordtr;
    private LinearLayout mainLayout;
    Random random = new Random();
    private static HashMap<String, String> dictionary = new HashMap<>();
    private static File file = new File(Environment.getExternalStorageDirectory() + "/dictionary.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        word = (TextView) findViewById(R.id.word);
        wordtr = (TextView) findViewById(R.id.wordtr);
        mainLayout = (LinearLayout) findViewById(R.id.wordLayout);
        try {
            this.loadDictionary();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateColors();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        QuizActivity.score = 0;
        super.onStart();
    }

    public static void loadDictionary() throws IOException {
        if (!file.exists()) {
            initializeDictionary();
        }
        Scanner scanner = new Scanner(file);
        String input = "";
        String[] words;
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            words = input.split("\\:");
            if(words.length <= 1)
                continue;
            dictionary.put(words[0].trim(), words[1].trim());
        }
        scanner.close();
    }

    public static void initializeDictionary() throws IOException {
            file.createNewFile();
            PrintWriter pw = new PrintWriter(file);
            pw.println("application:uygulama");
            pw.println("transaction:işlem");
            pw.println("developer:geliştirici");
            pw.println("spring:bahar");
            pw.println("execute:çalıştırmak");
            pw.println("accomplish:başarmak");
            pw.println("architect:mimar");
            pw.println("desire:arzu");
            pw.println("example:örnek");
            pw.close();
    }

    public String trim(String str) {
        return str.replaceAll("[^A-Za-z0-9 ]", "");
    }

    public void showWord(View view) {
        YoYo.with(Techniques.FlipInY)
                .duration(700)
                .repeat(1)
                .playOn(view);
        List<String> list = new ArrayList<>(dictionary.keySet());

        int index = random.nextInt(dictionary.keySet().size());
        word.setText(list.get(index));
        wordtr.setText(dictionary.get(list.get(index)));
//        Toast.makeText(MainActivity.this, "Reason can not be blank", Toast.LENGTH_SHORT).show();
        updateColors();
    }

    public int getRandomColor() {
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public void updateColors() {
        word.setTextColor(getRandomColor());
        wordtr.setTextColor(getRandomColor());
        mainLayout.setBackgroundColor(getRandomColor());
    }

    public static boolean isWordExists(String str){
        return dictionary.keySet().contains(str);
    }

    public static void addWordToFile(String en, String tr) throws IOException {
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(
                new FileWriter(file, true)));
        synchronized (dictionary){
            for (String key:
                    dictionary.keySet()) {
                printWriter.println(key + ":" + dictionary.get(key));
            }
            dictionary.put(en,tr);
        }
        printWriter.println(en + ":" + tr);
        printWriter.close();
    }
}
