package com.imaginemessenger;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.imaginemessenger.R;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Raffy on 17/10/2017.
 */

public class SendMessage  extends AppCompatActivity {
    Button button, go;
    EditText messageText, checkuser;
    private Spinner spinner_game,receiver;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message);

        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(getResources().getColor(R.color.activityc));

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        go = (Button) findViewById(R.id.search);
        checkuser = (EditText) findViewById(R.id.check_users);
        button=(Button)findViewById(R.id.send_btn);
        messageText= (EditText) findViewById(R.id.text_mess);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUsers();
            }
        });
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                insertNewMessage();

            }

        });
    }


    // add items into spinner dynamically
    public void checkUsers() {


        DatabaseReference root = FirebaseDatabase.getInstance().getReference();

        final String username= checkuser.getText().toString().trim();
        root.child("users").orderByChild("username").equalTo(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null && dataSnapshot.getChildren()!=null &&
                        dataSnapshot.getChildren().iterator().hasNext()){

                    Toast.makeText(getApplicationContext(), username + " exists!",
                            Toast.LENGTH_LONG).show();



                }else {

                    Toast.makeText(getApplicationContext(), "No values for " + username,
                            Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                //Error
            }
        });



    }

    public void insertNewMessage(){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("messages");

        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        NewMessage mess = new NewMessage(auth.getCurrentUser().getEmail(), checkuser.getText().toString().trim(),
                messageText.getText().toString());

        mDatabase.child(ts).setValue(mess);

    }

    public void addListenerOnSpinnerGame() {
        spinner_game = (Spinner) findViewById(R.id.games);
        // spinner_game.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.search:
                //your code here

                Intent intent = new Intent(SendMessage.this, Login.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                auth.signOut();
                startActivity(intent);


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
