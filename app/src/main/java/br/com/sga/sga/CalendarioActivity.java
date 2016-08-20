package br.com.sga.sga;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CalendarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            String[]  myStringArray={"JAN","FEV","MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"};
            ArrayAdapter<String> myAdapter=new
                    ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    myStringArray);
            ListView myList=(ListView)
                    findViewById(R.id.listView);
            myList.setAdapter(myAdapter);

    }
}
