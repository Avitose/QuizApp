package com.avito.quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String QUIZ_TAG = "MainActivity";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_EXTRA_ANSWER = "com.avito.quizapp.correctAnswer";
    public static final String KEY_EXTRA_QUESTION = "com.avito.quizapp.question";
    public static final int REQUEST_CODE_PROMPT = 0;
    private Boolean answerWasShown;
    //public static final String KEY_EXTRA_QUESTION_INDEX = "com.avito.quizapp.currentQuestion";
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button hintButton;
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
    protected void onStart() {
        super.onStart();
        Log.d(QUIZ_TAG, "Wywołano life cycle method: onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(QUIZ_TAG, "Wywołano life cycle method: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(QUIZ_TAG, "Wywołano life cycle method: onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(QUIZ_TAG, "Wywołano life cycle method: onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(QUIZ_TAG, "Wywołano life cycle method: onResume");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG, "Wywołano onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG, "Wywołano life cycle method: onCreate");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
        {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        trueButton = findViewById(R.id.button_true);
        falseButton = findViewById(R.id.button_false);
        nextButton = findViewById(R.id.button_next);
        hintButton = findViewById(R.id.button_hint);
        questionTextView = findViewById(R.id.question_text_view);
        if (currentIndex==0) { setNewQuestion(currentIndex);}
        else {
            setNewQuestion();
        }

        trueButton.setOnClickListener(v -> checkAnswer(true));
        falseButton.setOnClickListener(v -> checkAnswer(false));
        nextButton.setOnClickListener(v -> setNewQuestion());
        hintButton.setOnClickListener( v-> {
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);
            boolean correctAnswer = questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            intent.putExtra(KEY_EXTRA_QUESTION, questions[currentIndex].getQuestionId());
            startActivityForResult(intent, REQUEST_CODE_PROMPT);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) { return; }
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) {
                return;
            }
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }

    private void checkAnswer(boolean userAnswer) {
        int resultMessageId;
        if (answerWasShown)
        {
            resultMessageId = R.string.answer_was_shown;
            Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
            return;
        }
        if (userAnswer == questions[currentIndex].isTrueAnswer())
        {
            resultMessageId = R.string.correct_answer;
            setNewQuestion();
        }
        else {
            resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNewQuestion()
    {
        currentIndex = (currentIndex + 1) % questions.length;
        answerWasShown = false;
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    private void setNewQuestion(int index)
    {
        currentIndex = index;
        answerWasShown = false;
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }
}