package com.example.jeremy.smartcalculator.views.activities;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeremy.smartcalculator.constants.AnimationType;
import com.example.jeremy.smartcalculator.constants.ProcessStates;
import com.example.jeremy.smartcalculator.presenters.CalculatorPresenter;

import com.example.jeremy.smartcalculator.R;
import com.example.jeremy.smartcalculator.IContract;

public class SimpleCalculatorActivity extends AppCompatActivity implements View.OnClickListener , IContract.IView , Handler.Callback {

    private CalculatorPresenter calculatorPresenter;
    private TextView textView;
    private Button dotButton , nextButton , backButton , equalButton , clearButton;

    private int[] numberId = {
            R.id.btnZero , R.id.btnOne , R.id.btnTwo , R.id.btnThree , R.id.btnFour ,
            R.id.btnFive , R.id.btnSix , R.id.btnSeven , R.id.btnEight , R.id.btnNine
    };
    private int[] operationId = {
            R.id.btnAdd , R.id.btnSubtract , R.id.btnMultiply , R.id.btnDivide ,
            R.id.btnSin , R.id.btnCos , R.id.btnTan , R.id.btnCtan ,
            R.id.btnPower , R.id.btnSqrt , R.id.btnLn , R.id.btnLog
    };
    private Button[] numbers = new Button[numberId.length];
    private Button[] operations = new Button[operationId.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_calculator);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initVars();
        initListeners();
    }

    private void initViews() {
        textView = findViewById(R.id.txtScreen);

        dotButton = findViewById(R.id.btnDot);
        nextButton = findViewById(R.id.btnNext);
        backButton = findViewById(R.id.btnBack);
        equalButton = findViewById(R.id.btnEqual);
        clearButton = findViewById(R.id.btnClear);
        for (int i = 0 ; i < numberId.length ; i++) numbers[i] = findViewById(numberId[i]);
        for (int i = 0 ; i < operationId.length ; i++) operations[i] = findViewById(operationId[i]);
    }

    private void initVars() {
        calculatorPresenter = new CalculatorPresenter(this , new Handler(this));
    }

    private void initListeners() {
        dotButton.setOnClickListener(this);
        disableBtn(dotButton);
        nextButton.setOnClickListener(this);
        disableBtn(nextButton);
        backButton.setOnClickListener(this);
        disableBtn(backButton);
        equalButton.setOnClickListener(this);
        disableBtn(equalButton);
        clearButton.setOnClickListener(this);
        disableBtn(clearButton);
        for (Button button : numbers) button.setOnClickListener(this);
        for (Button button : operations) {
            button.setOnClickListener(this);
            disableBtn(button);
        }
    }
    @Override
    public void animate(AnimationType animationType , String text) {
        switch (animationType) {
            case RENDER_NUMBER:
                textView.append(text);
                for (Button button : operations) enableBtn(button);
                enableBtn(backButton);
                enableBtn(clearButton);
                enableBtn(equalButton);
                enableBtn(dotButton);
                enableBtn(nextButton);
                break;
            case RENDER_OPERATION:
                textView.setText(text);
                for (Button button : operations) disableBtn(button);
                disableBtn(dotButton);
                disableBtn(equalButton);
                enableBtn(clearButton);
                enableBtn(backButton);
                enableBtn(nextButton);
                break;
            case RENDER_ERASE:
                textView.setText(text);
                enableBtn(dotButton);
                for (Button button : operations) enableBtn(button);
                enableBtn(clearButton);
                enableBtn(equalButton);
                enableBtn(backButton);
                enableBtn(nextButton);
                break;
            case CLEAR_FIELD:
                disableBtn(dotButton);
                for (Button button : operations) disableBtn(button);
                disableBtn(clearButton);
                disableBtn(equalButton);
                disableBtn(backButton);
                disableBtn(nextButton);
                textView.setText("");
                break;
            case RENDER_EMPTY_FIELD:
                textView.setText("");
                break;
            case RENDER_RESULT:
                textView.setText(text);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("input" , textView.getText().toString());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textView.setText(savedInstanceState.getString("input"));
    }
    @Override
    public void onClick(View v) {
        for (int i = 0 ; i < numberId.length ; i++) if (v.getId() == numberId[i]) calculatorPresenter.onTouchNumberExpressionBtn(numbers[i].getText().toString());
        for (int i = 0 ; i < operationId.length ; i++) if (v.getId() == operationId[i]) calculatorPresenter.onTouchOperationExpressionBtn(operations[i].getText().toString());
        if (v.getId() == R.id.btnDot) animate(AnimationType.RENDER_NUMBER, dotButton.getText().toString());
        switch (v.getId()) {
            case R.id.btnNext:
                calculatorPresenter.onTouchNextExpressionBtn(textView.getText().toString());
                break;
            case R.id.btnBack:
                calculatorPresenter.onTouchEraseBtn(textView.getText().toString());
                break;
            case R.id.btnClear:
                calculatorPresenter.onClear();
                break;
            case R.id.btnEqual:
                calculatorPresenter.onTouchEqualsBtn(textView.getText().toString());
                break;
        }
    }
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case ProcessStates.Failed.STATUS_INCORRECT_INPUT:
                Toast.makeText(this, R.string.incorrect_input, Toast.LENGTH_SHORT).show();
                break;
            case ProcessStates.Failed.STATUS_NOT_ENOUGH_EXPRESSION:
                Toast.makeText(this, R.string.nothing_calculate, Toast.LENGTH_SHORT).show();
                break;
            case ProcessStates.Successfull.STATUS_CALCULATED:
                Toast.makeText(this, R.string.calculated, Toast.LENGTH_SHORT).show();
                break;
            case ProcessStates.Successfull.STATUS_EXPRESSION_ADDED:
                Toast.makeText(this, "Next!", Toast.LENGTH_SHORT).show();
                break;
            case ProcessStates.Successfull.STATUS_CLEARED_ALL:
                Toast.makeText(this, R.string.cleared, Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    private void disableBtn(Button button) {
        button.setEnabled(false);
        button.setAlpha(0.5f);
    }

    private void enableBtn(Button button) {
        button.setEnabled(true);
        button.setAlpha(1.0f);
    }
}
