package com.imaginemessenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.imaginemessenger.R;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView message= (TextView) findViewById(R.id.welcome_mess);
        final Button button= (Button) findViewById(R.id.send_mess);

        Intent intent= getIntent();
        String username= intent.getStringExtra("username");
        String password= intent.getStringExtra("password");
        String msg_welcome= "Hi " +username+"!";
        message.setText(msg_welcome);



        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registration_intent= new Intent(MainActivity.this, SendMessage.class);
                MainActivity.this.startActivity(registration_intent);

            }

        });

        ArrayList image_details = getListData();
        final ListView lv1 = (ListView) findViewById(R.id.custom_list);
        lv1.setAdapter(new CustomListAdapter(this, image_details));
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                NewMessage newsData = (NewMessage) o;
                Toast.makeText(MainActivity.this, "Selected :" + " " + newsData, Toast.LENGTH_LONG).show();
            }
        });

    }

    private ArrayList getListData(){
        ArrayList<NewMessage> results = new ArrayList<NewMessage>();
        NewMessage newsData = new NewMessage("Dance of Democracy", "Pankaj Gupta", "Pankaj Gupta");

        results.add(newsData);

        // Add some more dummy data for testing
        return results;
    }
}
