package com.example.airny.lesson8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Первый способ
        //Toast toast = Toast.makeText(getApplicationContext(),
          //      "Пора котика кормить!", Toast.LENGTH_SHORT);
        //toast.show();
    }
//Второй способ
    //public void showToast(View view) {
      //  Toast toast = Toast.makeText(getApplicationContext(),
        //        "Котика корми сука!",
          //      Toast.LENGTH_SHORT);
       //toast.setGravity(Gravity.CENTER, 0, 0);
        //toast.show();}
//третий
    //int duration = Toast.LENGTH_LONG;
    //Toast toast2 = Toast.makeText(getApplicationContext(),
      //      R.string.catfood,
        //    duration);
    //toast2.setGravity(Gravity.TOP, 0, 0);
    //toast2.show();}
public void showToast(View view) {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.catfood, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0,0);
    LinearLayout toastContainer = (LinearLayout) toast.getView();
    ImageView catImageView = new ImageView(getApplicationContext());
    catImageView.setImageResource(R.drawable.hungrycat);
    toastContainer.addView(catImageView, 0);
    toast.show();
}

}

