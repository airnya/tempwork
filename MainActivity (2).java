package com.example.airny.lesson9;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
private Snackbar mSnackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show();
                //Snackbar snackbar = Snackbar.make(view, "Пора кормить ебучего кота!!!", Snackbar.LENGTH_LONG)
                mSnackbar = Snackbar.make(view, "Пора кормить ебучего кота!", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null);
                mSnackbar.setDuration(5000);
                View snackbarView = mSnackbar.getView();
                snackbarView.setBackgroundColor(Color.BLUE);
                mSnackbar.show();

            }
        });
   //     Button dismisButton = (Button) findViewById(R.id.buttonDismis);
 //       dismisButton.setOnClickListener(new View.OnClickListener() {
     //       @Override

         //   public void onClick(View view) {
          //      mSnackbar.dismiss(); }});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




//FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//fab.setOnClickListener(new View.onClickListener()){
    //@Override

   // }

   // public void onClick(View view) {
   // }
}
