package net.sornas.firefly.android;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import net.sornas.firefly.R;

public class CreateTransactionActivity extends AppCompatActivity {

    public static final String TYPE = "type";
    public static final String TYPE_INCOME = "income";
    public static final String TYPE_EXPENSE = "expense";
    public static final String TYPE_TRANSFER = "transfer";

    private MaterialButton incomeButton, expenseButton, transferButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);

        incomeButton = findViewById(R.id.transaction_button_income);
        expenseButton = findViewById(R.id.transaction_button_expense);
        transferButton = findViewById(R.id.transaction_button_transfer);

        try {
            handleButtonChange(getIntent().getStringExtra(TYPE));
        }
        catch (Exception e) {
            Log.e("Transaction", e.toString());
        }

        incomeButton.setOnClickListener(v -> handleButtonChange(TYPE_INCOME));
        expenseButton.setOnClickListener(v -> handleButtonChange(TYPE_EXPENSE));
        transferButton.setOnClickListener(v -> handleButtonChange(TYPE_TRANSFER));
    }

    private void handleButtonChange(String s) {
        switch (s) {
            case TYPE_INCOME:
                enableButton(incomeButton);
                disableButton(expenseButton);
                disableButton(transferButton);
                break;
            case TYPE_EXPENSE:
                disableButton(incomeButton);
                enableButton(expenseButton);
                disableButton(transferButton);
                break;
            case TYPE_TRANSFER:
                disableButton(incomeButton);
                disableButton(expenseButton);
                enableButton(transferButton);
                break;
        }
    }

    private void enableButton(MaterialButton button) {
        button.setBackgroundTintList(getColorStateList(R.color.colorAccent));
        button.setTextColor(getColor(R.color.white));
    }

    private void disableButton(MaterialButton button) {
        button.setBackgroundTintList(getColorStateList(R.color.white));
        button.setTextColor(getColor(R.color.colorAccent));
    }
}
