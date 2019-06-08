package net.sornas.firefly;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import net.sornas.firefly.api.account.Account;
import net.sornas.firefly.api.http.AccountResponse;
import net.sornas.firefly.volley.FireflyRequester;
import net.sornas.firefly.volley.VolleyRequest;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "FireflySörnäs";
    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImRlOGZkMTE0ZmM5ZGExMjc1Njg5Mzk5ZGU3NDgxZTk4NTYyZGY0ODFiY2IyYjBlMDZjOWEzN2VjYTVlOGY3NjMyZWRjY2NiOGJiZjA4NGUyIn0.eyJhdWQiOiIxIiwianRpIjoiZGU4ZmQxMTRmYzlkYTEyNzU2ODkzOTlkZTc0ODFlOTg1NjJkZjQ4MWJjYjJiMGUwNmM5YTM3ZWNhNWU4Zjc2MzJlZGNjY2I4YmJmMDg0ZTIiLCJpYXQiOjE1NTg0NzYyNTUsIm5iZiI6MTU1ODQ3NjI1NSwiZXhwIjoxNTkwMDk4NjU1LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.AMd23IRigU_LZPLGm50OAWkjFp9_IRKELkBetdW6cDkauq0_zzDdCU2e6-uHnztBXXfij68MigriePDYX-hfP5yAVbhm8bBdEMlviYIOs4b1tfcQdspTUQuBRis1DOP_VDxdrSDG75yPLpoXYeslcGquWcTI49RE39U1bVmMMSPrYgJzFqzn9hPJDf9hvssvc2mo-P4GLRbhf6VJwK9xbp1HDX3ObEABNd5g32iVJuT94Sz_UPxKljJ6btvNlWVRdAHD0yHIapf7Mssz8FNRSnj4DjpD_dMv4-Mri9vqpTtAkbIyLyB7GEKYRH7R--AkB9aL53fm227Ro6jWaMPdToMKZwjJ-pIi1qCmRAh1uFkFjzMp0iYedZN-ltuYI6qkLSBwWoMBUhKEUuUazC097PyGZd6azfcXJ3z5DCaFcgzKs9LpNrrB4CnZshFLKLmI9KehPXYcSc0imXiH_ZH4vLhxQGwZ0Q14jyCA5p6YIo8m0P3_XFhpAt5lfhfm3y6XSLxEO1dCZLrwAKQlqLC6TKpZ3XBvvuNlO2xsSXFtwhsr3Pd6H1KqBwrUonqCdzhyZC6LexUaeB4F2sPYBuccOVQA9y81vdgbK6kLVjKfQC2sL-rHOirfjGH5yW18i32a-yMYKPVUcilZSuPdvcGViMr_LhbmnD5brYc27dSdqSc";
    private List<Account> accounts = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textViewTest = findViewById(R.id.textview_test);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //        .setAction("Action", null).show();
        });

        FireflyRequester requester = new FireflyRequester(this);

        requester.makeRequest(VolleyRequest.listAccounts, token, s -> {
            AccountResponse response = AccountResponse.readJson(s);
            accounts.addAll(response.getAccounts());
            Log.d(TAG, "Added " + response.getAccounts().size() + " accounts");
        });
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
