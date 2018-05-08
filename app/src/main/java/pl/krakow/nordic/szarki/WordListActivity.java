package pl.krakow.nordic.szarki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WordListActivity extends AppCompatActivity {


    private SharedPreferences sp;
    private int count, score;
    private String unit;
    private TextView wordList;
    private LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                unit = null;
                count = 0;
            } else {
                unit= extras.getString("unit");
                count = extras.getInt("count");
            }
        } else {
            unit= (String) savedInstanceState.getSerializable("unit");
            count= (int) savedInstanceState.getSerializable("count");
        }

        linear = (LinearLayout) findViewById(R.id.linearLayout);

        createWordList();

    }

    private void createWordList() {
        for (int i = 1; i < (count + 1); i++) {
            String word;
            sp = getSharedPreferences("aWords" + unit, MODE_PRIVATE);
            word = sp.getString(Integer.toString(i), "error");
            sp = getSharedPreferences("pWords" + unit, MODE_PRIVATE);
            word += " - " + sp.getString(Integer.toString(i), "error");
            sp = getSharedPreferences("WordsScore" + unit, MODE_PRIVATE);
            score = sp.getInt(Integer.toString(i), 0);
            wordList = new TextView(this);
            LinearLayout.LayoutParams parameter = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            parameter.gravity = Gravity.START;
            parameter.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            wordList.setLayoutParams(parameter);
            if(score==-1){
                wordList.setTextColor(Color.RED);
            }
            else if(score==1) {
                wordList.setTextColor(Color.parseColor("#00994C"));
            }
            else wordList.setTextColor(Color.BLACK);
            wordList.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            wordList.setText(word);
            linear.addView(wordList);
        }
    }

    @Override
    public void onBackPressed() {
        /*Intent intent = new Intent(WordListActivity.this, UnitActivity.class);
        intent.putExtra("unit", unit);
        intent.putExtra("count", count);
        startActivity(intent);*/
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
