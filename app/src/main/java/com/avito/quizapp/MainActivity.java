package com.avito.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    private int currentIndex = 0;

    private Question[] questions = new Question[]
            {
                    new Question(R.string.q_hardware, false),
                    new Question(R.string.q_arpanet, true),
                    new Question(R.string.q_interpreter, true),
                    new Question(R.string.q_clang, true),
                    new Question(R.string.q_dotnet, true),
                    new Question(R.string.q_cdbash, true)
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.button_true);
        falseButton = findViewById(R.id.button_false);
        nextButton = findViewById(R.id.button_next);
        questionTextView = findViewById(R.id.question_text_view);
        setNewQuextion(); //init first question

        trueButton.setOnClickListener(v -> checkAnswer(true));
        falseButton.setOnClickListener(v -> checkAnswer(false));
        nextButton.setOnClickListener(v -> setNewQuextion());
    }

    private void checkAnswer(boolean userAnswer) {
        int resultMessageId;
        if (userAnswer == questions[currentIndex].isTrueAnswer())
        {
            resultMessageId = R.string.correct_answer;
            setNewQuextion();
        }
        else {
            resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNewQuextion()
    {
        currentIndex = (currentIndex + 1) % questions.length;
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }
}