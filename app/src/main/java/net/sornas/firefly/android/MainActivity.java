package net.sornas.firefly.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import net.sornas.firefly.R;
import net.sornas.firefly.android.preferences.SettingsActivity;
import net.sornas.firefly.api.http.AccountResponse;
import net.sornas.firefly.api.http.SingleAccountTransactionListResponse;
import net.sornas.firefly.api.model.account.Account;
import net.sornas.firefly.api.model.transaction.Transaction;
import net.sornas.firefly.volley.FireflyRequester;
import net.sornas.firefly.volley.VolleyRequest;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "FireflySörnäs";

    private final int listAccounts = 0;
    private final int listTransactions = 1;
    private boolean[] requestsDone = {false, false};
    private int transactionPages = 0;
    private List<Account> accounts = new LinkedList<>();
    private List<Transaction> transactions = new LinkedList<>();

    private String token;
    private String rootURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("MainActivity", "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //        .setAction("Action", null).show();
        });

        FireflyRequester requester = new FireflyRequester(this);

        Log.d("MainActivity", "Requesting transactions on url " + rootURL + " with token " + token);
        requester.makeRequest(VolleyRequest.listTransactions, token, rootURL,
                (s, pagination) -> {
                    transactionPages++;
                    SingleAccountTransactionListResponse response = new SingleAccountTransactionListResponse(s);
                    transactions.addAll(response.getTransactions());
                    Log.i("makeRequest", "Received page " + transactionPages + "/" + pagination.total_pages +
                            " (" + response.getTransactions().size() + "), " +
                            transactions.size() + "/" + pagination.total + " transactions");
                    if (transactionPages == pagination.total_pages) requestsDone[listTransactions] = true;
                });

        Log.d("MainActivity", "Requesting accounts on url " + rootURL + " with token " + token);
        requester.makeRequest(VolleyRequest.listAccounts, token, rootURL,
                (s, pagination) -> {
                    AccountResponse response = AccountResponse.readJson(s);
                    accounts.addAll(response.getAccounts());
                    Log.i("makeRequest", "Received " + response.getAccounts().size() + "/" + pagination.total + " accounts");
                    if (accounts.size() == pagination.total) requestsDone[listAccounts] = true;
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        rootURL = getPreferences(Context.MODE_PRIVATE).getString("url", "https://firefly.xn--srns-noa9h.se/");
        token = getPreferences(Context.MODE_PRIVATE).getString("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImRlOGZkMTE0ZmM5ZGExMjc1Njg5Mzk5ZGU3NDgxZTk4NTYyZGY0ODFiY2IyYjBlMDZjOWEzN2VjYTVlOGY3NjMyZWRjY2NiOGJiZjA4NGUyIn0.eyJhdWQiOiIxIiwianRpIjoiZGU4ZmQxMTRmYzlkYTEyNzU2ODkzOTlkZTc0ODFlOTg1NjJkZjQ4MWJjYjJiMGUwNmM5YTM3ZWNhNWU4Zjc2MzJlZGNjY2I4YmJmMDg0ZTIiLCJpYXQiOjE1NTg0NzYyNTUsIm5iZiI6MTU1ODQ3NjI1NSwiZXhwIjoxNTkwMDk4NjU1LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.AMd23IRigU_LZPLGm50OAWkjFp9_IRKELkBetdW6cDkauq0_zzDdCU2e6-uHnztBXXfij68MigriePDYX-hfP5yAVbhm8bBdEMlviYIOs4b1tfcQdspTUQuBRis1DOP_VDxdrSDG75yPLpoXYeslcGquWcTI49RE39U1bVmMMSPrYgJzFqzn9hPJDf9hvssvc2mo-P4GLRbhf6VJwK9xbp1HDX3ObEABNd5g32iVJuT94Sz_UPxKljJ6btvNlWVRdAHD0yHIapf7Mssz8FNRSnj4DjpD_dMv4-Mri9vqpTtAkbIyLyB7GEKYRH7R--AkB9aL53fm227Ro6jWaMPdToMKZwjJ-pIi1qCmRAh1uFkFjzMp0iYedZN-ltuYI6qkLSBwWoMBUhKEUuUazC097PyGZd6azfcXJ3z5DCaFcgzKs9LpNrrB4CnZshFLKLmI9KehPXYcSc0imXiH_ZH4vLhxQGwZ0Q14jyCA5p6YIo8m0P3_XFhpAt5lfhfm3y6XSLxEO1dCZLrwAKQlqLC6TKpZ3XBvvuNlO2xsSXFtwhsr3Pd6H1KqBwrUonqCdzhyZC6LexUaeB4F2sPYBuccOVQA9y81vdgbK6kLVjKfQC2sL-rHOirfjGH5yW18i32a-yMYKPVUcilZSuPdvcGViMr_LhbmnD5brYc27dSdqSc");
        Log.i("onResume", "Found url " + rootURL);
        Log.i("onResume", "Found token " + token);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
