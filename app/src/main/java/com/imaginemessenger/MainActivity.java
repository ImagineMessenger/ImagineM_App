package com.imaginemessenger;

import android.app.AlertDialog;
import android.app.Application;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.imaginemessenger.R;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    private ProgressBar wait;

    ArrayList<NewMessage> results;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(getResources().getColor(R.color.activityc));


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        final TextView message= (TextView) findViewById(R.id.welcome_mess);
        final Button button= (Button) findViewById(R.id.send_mess);
        wait = (ProgressBar) findViewById(R.id.progressBar1);

        Intent intent= getIntent();
        String username= auth.getCurrentUser().getEmail();

        String msg_welcome= "Check your messages " + username + "!";
        message.setText(msg_welcome);

        results = new ArrayList<NewMessage>();
        wait.setVisibility(View.VISIBLE);
        checkList();

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registration_intent= new Intent(MainActivity.this, SendMessage.class);
                MainActivity.this.startActivity(registration_intent);

            }

        });



    }

    /*private ArrayList getListData(){


        results.add(newsData);

        // Add some more dummy data for testing
        return results;
    }*/




    // add items into spinner dynamically
    public void checkList() {

        final ListView lv1 = (ListView) findViewById(R.id.custom_list);
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();

        final String user = auth.getCurrentUser().getEmail().toString().trim();
        root.child("messages").orderByChild("receiver").equalTo(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NewMessage m = snapshot.getValue(NewMessage.class);
                    System.out.println(m.getMessage());

                    results.add(m);
                }

                lv1.setAdapter(new CustomListAdapter(getApplicationContext(), results));

                //Take out the progress bar!

                wait.setVisibility(View.INVISIBLE);

                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = lv1.getItemAtPosition(position);
                            final NewMessage newsData = (NewMessage) o;


                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("GO AND FIND THE MESSAGE!")
                                    .setMessage("Ready to play?")
                                    .setCancelable(true)
                                    .setPositiveButton("GO!",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //dialog.cancel();

                                            Intent intent = new Intent();
                                            /*
                                            Bundle bundle = new Bundle();
                                            bundle.putString("MESSAGE", newsData.getMessage().trim());
                                            intent.setComponent(new ComponentName("com.imagine.imaginem_app", "com.unity3d.player.UnityPlayerActivity"));
                                            intent.putExtras(bundle);*/

                                            intent = getPackageManager().getLaunchIntentForPackage("com.imagine.imaginem_app");
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK );
                                            intent.putExtra("arguments",newsData.getMessage().trim());


                                            startActivity(intent);
                                        }
                                    });


                            AlertDialog alert = builder.create();
                            alert.show();


                            //Toast.makeText(MainActivity.this, "Selected :" + " " + newsData.getMessage().trim(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Error
            }

        });

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

                Intent intent = new Intent(MainActivity.this, Login.class)
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
