package pl.krakow.nordic.szarki;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class AllActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private SearchView sv;
    private ListView list;
    private ArrayAdapter<String> adapter ;
    private ArrayList<String> wordsK = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        sv = (SearchView) findViewById(R.id.search_bar);
        list = (ListView) findViewById(R.id.listViewAll);


        createWordListAll(113, "U1");
        createWordListAll(82, "U2");
        createWordListAll(122, "U3");
        createWordListAll(89, "U4");
        createWordListAll(54, "U5");
        createWordListAll(91, "U6");
        createWordListAll(78, "U7");

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        });
    }

    private void createWordListAll(int count, String unit) {
        for (int i = 1; i < (count + 1); i++) {
            String word;
            sp = getSharedPreferences("aWords" + unit, MODE_PRIVATE);
            word = sp.getString(Integer.toString(i), "error");
            sp = getSharedPreferences("pWords" + unit, MODE_PRIVATE);
            word += " - " + sp.getString(Integer.toString(i), "error");
            wordsK.add(word);
            adapter = new ArrayAdapter<>(this, R.layout.textview, wordsK);
            list.setAdapter(adapter);
        }
    }


    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
