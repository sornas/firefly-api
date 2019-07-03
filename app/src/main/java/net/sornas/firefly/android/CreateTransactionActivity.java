package net.sornas.firefly.android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ViewSwitcher;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import net.sornas.firefly.R;

public class CreateTransactionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TYPE = "type";
    public static final String TYPE_INCOME = "income";
    public static final String TYPE_EXPENSE = "expense";
    public static final String TYPE_TRANSFER = "transfer";

    private MaterialButton incomeButton, expenseButton, transferButton;
    private ViewSwitcher accountFromViewSwitcher, accountToViewSwitcher;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);

        Spinner accountFromSpinner = findViewById(R.id.transaction_input_account_from_spinner);
        ArrayAdapter<CharSequence> accountFromSpinnerAdapter= ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        accountFromSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountFromSpinner.setAdapter(accountFromSpinnerAdapter);

        Spinner accountToSpinner = findViewById(R.id.transaction_input_account_to_spinner);
        ArrayAdapter<CharSequence> accountToSpinnerAdapter= ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        accountToSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountToSpinner.setAdapter(accountToSpinnerAdapter);

        accountFromViewSwitcher = findViewById(R.id.transaction_switcher_account_from);
        accountToViewSwitcher = findViewById(R.id.transaction_switcher_account_to);

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

                showSpinner(accountFromViewSwitcher);
                showTextField(accountToViewSwitcher);
                break;
            case TYPE_EXPENSE:
                disableButton(incomeButton);
                enableButton(expenseButton);
                disableButton(transferButton);

                showTextField(accountFromViewSwitcher);
                showSpinner(accountToViewSwitcher);
                break;
            case TYPE_TRANSFER:
                disableButton(incomeButton);
                disableButton(expenseButton);
                enableButton(transferButton);

                showSpinner(accountFromViewSwitcher);
                showSpinner(accountToViewSwitcher);
                break;
        }
    }

    private void enableButton(MaterialButton button) {
        button.setBackgroundTintList(getColorStateList(R.color.colorSecondary));
        button.setTextColor(getColor(R.color.colorOnSecondary));
    }

    private void disableButton(MaterialButton button) {
        button.setBackgroundTintList(getColorStateList(R.color.colorSurface));
        button.setTextColor(getColor(R.color.colorSecondary));
    }

    private void showTextField(ViewSwitcher switcher) {
        switcher.setDisplayedChild(0);
    }

    private void showSpinner(ViewSwitcher switcher) {
        switcher.setDisplayedChild(1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("Transaction", "Item " + parent.getItemAtPosition(position) + " selected");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d("Transaction", "Nothing selected");
    }
}
