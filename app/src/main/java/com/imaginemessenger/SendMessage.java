package com.imaginemessenger;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.imaginemessenger.R;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Raffy on 17/10/2017.
 */

public class SendMessage  extends AppCompatActivity {
    Button button;
    EditText messageText;
    private Spinner spinner_game,receiver;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (ContextCompat.checkSelfPermission(SendMessage.this,
                Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(SendMessage.this,
                    Manifest.permission.SEND_SMS)){
                ActivityCompat.requestPermissions(SendMessage.this,
                        new String[]{Manifest.permission.SEND_SMS},1);

            }else{ActivityCompat.requestPermissions(SendMessage.this,
                    new String[]{Manifest.permission.SEND_SMS},1);
            }

        }else {
            // do nothing
        }
        addItemsOnReceiverList();
        button=(Button)findViewById(R.id.send_btn);
        messageText= (EditText) findViewById(R.id.text_mess);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String mess= messageText.getText().toString();

                /*addListenerOnSpinnerGame();
                try {
                    SmsManager smsManager= SmsManager.getDefault();
                    smsManager.sendTextMessage(null,null,mess,null,null);
                    Toast.makeText(SendMessage.this,"Sent!",Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(SendMessage.this,"Failed!",Toast.LENGTH_SHORT).show();
                }*/
                auth.signOut();
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case 1:{
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(SendMessage.this,
                            Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(this,"Permission granted",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(this,"No permission granted",Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }

    }

    // add items into spinner dynamically
    public void addItemsOnReceiverList() {

        receiver = (Spinner) findViewById(R.id.receiver);
        List<String> list = new ArrayList<String>();
        //aggiungere in maniera dinamica gli utenti
        list.add("Alessio");
        list.add("Quentin");
        list.add("Raffaella");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        receiver.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerGame() {
        spinner_game = (Spinner) findViewById(R.id.games);
        // spinner_game.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

}
