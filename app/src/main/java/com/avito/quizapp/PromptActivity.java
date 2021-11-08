package com.avito.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {
    public static final String KEY_EXTRA_ANSWER_SHOWN = "answerShown";
    private boolean correctAnswer;
    private int question;
    private Button showAnswerButton;
    private TextView answerTextView;
    private TextView questionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        showAnswerButton = findViewById(R.id.show_answer_button);
        answerTextView = findViewById(R.id.answer_text_view);
        questionTextView = findViewById(R.id.hint_question);

        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);
        question = getIntent().getIntExtra(MainActivity.KEY_EXTRA_QUESTION, 0);

        //questionTextView.setText(question != 0 ? question : );

        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int answer = correctAnswer ? R.string.button_true : R.string.button_false;
                answerTextView.setText(answer);
                setAnswerShownResult(true);
            }
        });
    }

    private void setAnswerShownResult(boolean answerWasShown)
    {
        Intent result = new Intent();
        result.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerWasShown);
        setResult(RESULT_OK, result);
    }
}