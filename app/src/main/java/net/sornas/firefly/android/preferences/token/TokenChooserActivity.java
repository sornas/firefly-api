package net.sornas.firefly.android.preferences.token;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import net.sornas.firefly.R;
import net.sornas.firefly.android.SerializableManager;

import java.util.ArrayList;

public class TokenChooserActivity extends AppCompatActivity {  // TODO convert into fragment
                                                               // replace SettingsFragment in SettingsActivity

    public static String tokenListFile = "tokenList";

    private ArrayList<Token> dataset = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Token> savedDataset = new ArrayList<>();
        savedDataset.add(new Token("Default", "https://firefly.xn--srns-noa9h.se/", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImRlOGZkMTE0ZmM5ZGExMjc1Njg5Mzk5ZGU3NDgxZTk4NTYyZGY0ODFiY2IyYjBlMDZjOWEzN2VjYTVlOGY3NjMyZWRjY2NiOGJiZjA4NGUyIn0.eyJhdWQiOiIxIiwianRpIjoiZGU4ZmQxMTRmYzlkYTEyNzU2ODkzOTlkZTc0ODFlOTg1NjJkZjQ4MWJjYjJiMGUwNmM5YTM3ZWNhNWU4Zjc2MzJlZGNjY2I4YmJmMDg0ZTIiLCJpYXQiOjE1NTg0NzYyNTUsIm5iZiI6MTU1ODQ3NjI1NSwiZXhwIjoxNTkwMDk4NjU1LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.AMd23IRigU_LZPLGm50OAWkjFp9_IRKELkBetdW6cDkauq0_zzDdCU2e6-uHnztBXXfij68MigriePDYX-hfP5yAVbhm8bBdEMlviYIOs4b1tfcQdspTUQuBRis1DOP_VDxdrSDG75yPLpoXYeslcGquWcTI49RE39U1bVmMMSPrYgJzFqzn9hPJDf9hvssvc2mo-P4GLRbhf6VJwK9xbp1HDX3ObEABNd5g32iVJuT94Sz_UPxKljJ6btvNlWVRdAHD0yHIapf7Mssz8FNRSnj4DjpD_dMv4-Mri9vqpTtAkbIyLyB7GEKYRH7R--AkB9aL53fm227Ro6jWaMPdToMKZwjJ-pIi1qCmRAh1uFkFjzMp0iYedZN-ltuYI6qkLSBwWoMBUhKEUuUazC097PyGZd6azfcXJ3z5DCaFcgzKs9LpNrrB4CnZshFLKLmI9KehPXYcSc0imXiH_ZH4vLhxQGwZ0Q14jyCA5p6YIo8m0P3_XFhpAt5lfhfm3y6XSLxEO1dCZLrwAKQlqLC6TKpZ3XBvvuNlO2xsSXFtwhsr3Pd6H1KqBwrUonqCdzhyZC6LexUaeB4F2sPYBuccOVQA9y81vdgbK6kLVjKfQC2sL-rHOirfjGH5yW18i32a-yMYKPVUcilZSuPdvcGViMr_LhbmnD5brYc27dSdqSc"));

        SerializableManager.saveSerializable(this, savedDataset, tokenListFile);

        dataset = SerializableManager.readSerializable(this, tokenListFile);

        setContentView(R.layout.activity_token_chooser);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add_token);
        fab.setOnClickListener(
                view -> {
                    Snackbar.make(view, "Not implemented!", Snackbar.LENGTH_SHORT)
                            .show();
                });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TokenChooserAdapter(dataset);
        recyclerView.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
