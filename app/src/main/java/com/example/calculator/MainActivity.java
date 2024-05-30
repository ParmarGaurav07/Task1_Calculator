package com.example.calculator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText e1;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, badd, bsub, bmul, bdiv, beql, bclr, bdel;

    StringBuilder expressionBuilder = new StringBuilder();
    boolean ifadd, ifdiv, ifmul, ifsub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View mainView = findViewById(R.id.main_layout);
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        e1 = findViewById(R.id.e1);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        b0 = findViewById(R.id.b0);
        badd = findViewById(R.id.badd);
        bsub = findViewById(R.id.bsub);
        bmul = findViewById(R.id.bmul);
        bdiv = findViewById(R.id.bdiv);
        beql = findViewById(R.id.beql);
        bclr = findViewById(R.id.bclr);
        bdel = findViewById(R.id.bdel);


        b1.setOnClickListener(v -> updateExpression("1"));
        b2.setOnClickListener(v -> updateExpression("2"));
        b3.setOnClickListener(v -> updateExpression("3"));
        b4.setOnClickListener(v -> updateExpression("4"));
        b5.setOnClickListener(v -> updateExpression("5"));
        b6.setOnClickListener(v -> updateExpression("6"));
        b7.setOnClickListener(v -> updateExpression("7"));
        b8.setOnClickListener(v -> updateExpression("8"));
        b9.setOnClickListener(v -> updateExpression("9"));
        b0.setOnClickListener(v -> updateExpression("0"));


        badd.setOnClickListener(v -> updateExpression("+"));
        bsub.setOnClickListener(v -> updateExpression("-"));
        bmul.setOnClickListener(v -> updateExpression("*"));
        bdiv.setOnClickListener(v -> updateExpression("/"));


        beql.setOnClickListener(v -> {
            calculateAndDisplay();
        });


        bclr.setOnClickListener(v -> {

            expressionBuilder.setLength(0);
            e1.setText("");
        });


        bdel.setOnClickListener(v -> {

            if (expressionBuilder.length() > 0) {
                expressionBuilder.deleteCharAt(expressionBuilder.length() - 1);
                e1.setText(expressionBuilder.toString());
            }
        });
    }


    private void updateExpression(String character) {

        expressionBuilder.append(character);

        e1.setText(expressionBuilder.toString());
    }



    private void calculateAndDisplay() {

        String expression = expressionBuilder.toString();
        try {
            double result = evaluateExpression(expression);
            String resultText;

            if (result == (int) result) {
                resultText = String.valueOf((int) result);
            } else {
                resultText = String.valueOf(result);
            }
            e1.setText(resultText);
            e1.setTextColor(Color.BLACK);
        } catch (ArithmeticException e) {
            e1.setText("Error: Division by zero");
            e1.setTextColor(Color.RED);
        } catch (NumberFormatException e) {
            e1.setText("Error: Invalid expression");
            e1.setTextColor(Color.RED);
        }
    }




    private double evaluateExpression(String expression) {

        String[] parts = expression.split("(?<=[-+*/])|(?=[-+*/])");
        if (parts.length != 3) {
            throw new NumberFormatException();
        }


        double operand1 = Double.parseDouble(parts[0].trim());
        double operand2 = Double.parseDouble(parts[2].trim());


        switch (parts[1].trim()) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException();
                }
                return operand1 / operand2;
            default:
                throw new NumberFormatException();
        }
    }

}
