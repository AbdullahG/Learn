package mag.com.learn;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void startLearn(View view){
        animateButton(view);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void startAddWord(View view){
        animateButton(view);
        startActivity(new Intent(getApplicationContext(), AddWord.class));
    }

    public void startQuiz(View view){
        animateButton(view);
        startActivity(new Intent(getApplicationContext(), QuizActivity.class));
    }

    public void animateButton(View view){
        YoYo.with(Techniques.FadeInDown)
                .duration(700)
                .repeat(5)
                .playOn(view);
    }
}
