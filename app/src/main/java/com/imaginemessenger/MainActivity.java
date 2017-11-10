package com.imaginemessenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.imaginemessenger.R;


public class MainActivity extends AppCompatActivity {

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
    }
}
