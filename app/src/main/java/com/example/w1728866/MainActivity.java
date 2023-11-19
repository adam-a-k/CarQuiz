package com.example.w1728866;

import androidx.appcompat.app.AppCompatActivity;
import android.content.*;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void carMake(View v)
    {
        Intent carMake = new Intent(this, CarMake.class);
        startActivity(carMake);
    }

    public void hints(View v)
    {
        Intent hints = new Intent(this, Hints.class);
        startActivity(hints);
    }

    public void carImage(View v)
    {
        Intent carImage = new Intent(this, CarImage.class);
        startActivity(carImage);
    }

    public void advancedLevel(View v)
    {
        Intent advancedLevel = new Intent(this, AdvancedLevel.class);
        startActivity(advancedLevel);
    }
}