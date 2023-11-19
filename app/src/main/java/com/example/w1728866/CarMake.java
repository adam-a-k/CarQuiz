package com.example.w1728866;

import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import org.w3c.dom.Text;


public class CarMake extends AppCompatActivity
{
    private Spinner spinnerCarMake;
    private ImageView carImage;
    private Button identifyButton;
    private int pickedCar, lastCar = 0;

    //Store images in an array holding their R.drawable integer values
    final private Integer [] carImages =
            {
                    R.drawable.aston_martin_db5, R.drawable.audi_r8, R.drawable.audi_a3, R.drawable.bmw_m5, R.drawable.bugatti_chiron, R.drawable.bugatti_veyron,
                    R.drawable.citroen_2cv, R.drawable.citroen_c4_cactus,  R.drawable.ferrari_enzo, R.drawable.ferrari_f40, R.drawable.fiat_500, R.drawable.fiat_multipla,
                    R.drawable.ford_anglia, R.drawable.ford_focus_rs, R.drawable.ford_mustang, R.drawable.honda_jazz, R.drawable.lamborghini, R.drawable.mazda_mx5,
                    R.drawable.mercedes_c_class, R.drawable.mercedes_s_class, R.drawable.mercedes_190e, R.drawable.mercedes_300sl,
                    R.drawable.nissan_gtr, R.drawable.porsche_911_gt3rs, R.drawable.reliant_robin, R.drawable.suzuki_ignis, R.drawable.vauxhall_adam, R.drawable.vauxhall_astra,
                    R.drawable.volkswagen_campervan, R.drawable.volkswagen_golf, R.drawable.volvo_v60
            };

    //Store car make names in an array that matches the carImages array
    final private String[] cars =
            {
                    "Aston Martin", "Audi", "Audi", "BMW", "Bugatti", "Bugatti", "Citroen", "Citroen", "Ferrari", "Ferrari",
                    "Fiat", "Fiat", "Ford", "Ford", "Ford", "Honda", "Lamborghini", "Mazda", "Mercedes", "Mercedes", "Mercedes", "Mercedes",
                    "Nissan", "Porsche", "Reliant", "Suzuki", "Vauxhall", "Vauxhall", "Volkswagen", "Volkswagen", "Volvo"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_make);

        spinnerCarMake = findViewById(R.id.spinnerCarMake);
        //spinnerCarMake.setOnItemSelectedListener(this);

        identifyButton = findViewById(R.id.identifyButton);
        carImage = findViewById(R.id.carImage);

        String[] carMakes = getResources().getStringArray(R.array.carMakes);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, carMakes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarMake.setAdapter(adapter);
        randomCar();
    }

    private void randomCar() {
        /*
        derived from https://www.youtube.com/watch?v=Pfee0wFD5M0&ab_channel=TihomirRAdeff
         */

        Random r = new Random();

         //remove duplicates
        do{
            pickedCar = r.nextInt(carImages.length);
        }while(pickedCar == lastCar);

        lastCar = pickedCar;
        carImage.setImageResource(carImages[pickedCar]);
        carImage.setTag(cars[pickedCar]);
    }

    public void identifyOnClick(View v)
    {
        String carMakeSelected = spinnerCarMake.getSelectedItem().toString();
        String carMake = carImage.getTag().toString();

        if(carMake.contains(carMakeSelected)){
            showToast(true, carMake);
        }else{
            showToast(false, carMake);
        }

        identifyButton.setText("Next");
        identifyButton.setOnClickListener(this::nextOnClick);
    }


    private void showToast(Boolean correct, String carMake)
    {
        /*
        Derived from https://codinginflow.com/tutorials/android/custom-toast
         */
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));
        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        if(correct){

            toastText.setText("Correct!");
            toastImage.setImageResource(R.drawable.ic_correct);
            layout.setBackgroundColor(Color.parseColor("#00E676"));
            toast.show();
        }else{

            toastText.setText("Wrong!");
            toastImage.setImageResource(R.drawable.ic_wrong);
            layout.setBackgroundColor(Color.parseColor("#DD2C00"));
            toast.show();
            showCarToast(carMake);
        }
    }

    private void showCarToast(String carMake)
    {
        /*
        Derived from https://codinginflow.com/tutorials/android/custom-toast
         */
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));
        TextView toastText = layout.findViewById(R.id.toast_text);

        toastText.setText(carMake);
        layout.setBackgroundColor(Color.parseColor("#FFEA00"));

        Toast carMakeToast = new Toast(getApplicationContext());
        carMakeToast.setGravity(Gravity.CENTER, 0, 5);
        carMakeToast.setDuration(Toast.LENGTH_SHORT);
        carMakeToast.setView(layout);
        carMakeToast.show();
    }

    //When 'next' is clicked, change screen and revert button
    public void nextOnClick(View v)
    {
        spinnerCarMake.setSelection(0);
        randomCar();
        identifyButton.setText("Identify");
        identifyButton.setOnClickListener(this::identifyOnClick);
    }
}