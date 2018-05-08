package pl.krakow.nordic.szarki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Random;

public class UnitActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private SharedPreferences.Editor de;
    private String unit;
    private int i, lastWord, count;
    private TextView pWordTV, aWordTV;
    private CheckBox showWord, change, random;
    private Random generator = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        change = (CheckBox) findViewById(R.id.change);
        showWord = (CheckBox) findViewById(R.id.show);
        random = (CheckBox) findViewById(R.id.random);

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
        pWordTV = (TextView) findViewById(R.id.p_word);
        aWordTV = (TextView) findViewById(R.id.a_word);
        newWord();



        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch(NullPointerException e){
            Log.i("error", e.getMessage());
        }

    }

    private void newWord() {
        if(lastWord==count)
            lastWord=0;
        showWord.setChecked(false);
        if(random.isChecked()) {
            do {
                i = generator.nextInt(count) + 1;
            }
            while (i == lastWord);
        }else if(!random.isChecked()){

            i = lastWord+1;
        }

        if(!change.isChecked()){
            sp = getSharedPreferences("aWords" + unit, MODE_PRIVATE);
            pWordTV.setText(sp.getString(Integer.toString(i), "error"));
            if(showWord.isChecked()) {
                sp = getSharedPreferences("pWords" + unit, MODE_PRIVATE);
                aWordTV.setText(sp.getString(Integer.toString(i), "error"));
                return;
            }
            aWordTV.setText("");

        }else {
            sp = getSharedPreferences("pWords" + unit, MODE_PRIVATE);
            pWordTV.setText(sp.getString(Integer.toString(i), "error"));
            if(showWord.isChecked()) {
                sp = getSharedPreferences("aWords" + unit, MODE_PRIVATE);
                aWordTV.setText(sp.getString(Integer.toString(i), "error"));
                return;
            }
            aWordTV.setText("");
        }

        lastWord = i;
    }

    public void newWord(View view){
        newWord();
    }

    public void showWord(View view) {
        if(!change.isChecked()){
            if(showWord.isChecked()) {
                sp = getSharedPreferences("pWords" + unit, MODE_PRIVATE);
                aWordTV.setText(sp.getString(Integer.toString(i), "error"));
            }else {
                aWordTV.setText("");
            }
        }else {
            if(showWord.isChecked()) {
                sp = getSharedPreferences("aWords" + unit, MODE_PRIVATE);
                aWordTV.setText(sp.getString(Integer.toString(i), "error"));
            }else {
                aWordTV.setText("");
            }
        }
    }

    public void showList(View view) {
        Intent intent = new Intent(UnitActivity.this, WordListActivity.class);
        intent.putExtra("unit", unit);
        intent.putExtra("count", count);
        startActivity(intent);
    }

    public void dontknow(View view) {
        sp = getSharedPreferences("WordsScore" + unit, MODE_PRIVATE);
        de = sp.edit();
        de.putInt(Integer.toString(lastWord), -1);
        de.apply();

        newWord();

    }

    public void nextWord(View view) {
        sp = getSharedPreferences("WordsScore" + unit, MODE_PRIVATE);
        de = sp.edit();
        de.putInt(Integer.toString(lastWord), 1);
        de.apply();

        newWord();
    }

    @Override
    public void onBackPressed() {
        /*Intent intent = new Intent(UnitActivity.this, MainActivity.class);
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
