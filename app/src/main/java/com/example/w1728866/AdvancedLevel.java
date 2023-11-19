package com.example.w1728866;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.Random;

public class AdvancedLevel extends AppCompatActivity
{
    private TextView points;
    private Integer pointsNo;
    private Button submitButton;
    private ImageView carImage1, carImage2, carImage3;
    private ImageView[] carImageViews;
    private EditText input1, input2, input3;
    private EditText[] inputs;
    private Integer correctCount = 0;
    private Integer submitCount = 3;
    private Boolean[] correctArray;

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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_level);

        points = findViewById(R.id.points);
        pointsNo = 0;
        submitButton = findViewById(R.id.submitButton);

        carImage1 = findViewById(R.id.advanced_level_car1);
        carImage2 = findViewById(R.id.advanced_level_car2);
        carImage3 = findViewById(R.id.advanced_level_car3);
        carImageViews = new ImageView[]{carImage1, carImage2, carImage3};

        input1 = findViewById(R.id.advanced_level_input1);
        input2 = findViewById(R.id.advanced_level_input2);
        input3 = findViewById(R.id.advanced_level_input3);
        inputs = new EditText[]{input1, input2, input3};

        correctArray = new Boolean[]{false, false, false};

        randomCar();
    }

    public void submitOnClick(View v)
    {
        //Loop through each input
        for(int i=0; i<inputs.length; i++)
        {   //Check if input is correct
            if(carImageViews[i].getTag().toString().toLowerCase().equals(inputs[i].getText().toString().toLowerCase()))
            {
                //Mark as correct for that array index
                correctArray[i] = true;
            }
        }

        //Loop through correct array
        for(Boolean correct: correctArray)
        {   //Count those correct out of each input
            if(correct)
            {
                correctCount ++;
            }
        }

        //Check if fully correct
        if(correctCount == 3)
        {
            updateScreen(true, correctCount);
        }else
        {
            //If not, mark as incorrect attempt made
            submitCount --;
            //Check if the maximum number of incorrect submissions have been reached
            if(submitCount == 0) {
                updateScreen(false, correctCount);
            }
            correctCount = 0;
        }
    }

    private void updateScreen(Boolean correct, Integer correctCount)
    {
        showToast(correct);
        pointsNo = pointsNo + correctCount;

        //reset variables
        for(EditText input: inputs)
        {
            input.setText("");
        }
        correctCount = 0;
        submitCount = 3;
        correctArray = new Boolean[]{false, false, false};
        points.setText(pointsNo.toString());
        submitButton.setText("Next");
        submitButton.setOnClickListener(this::nextOnClick);
    }


    private void showToast(Boolean correct)
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
        }
    }

    private void randomCar()
    {
        Random r = new Random();
        Integer rNo = 0;

        //Loop through images array and set each image
        for (ImageView image : carImageViews)
        {
            rNo = r.nextInt(cars.length);
            image.setImageResource(carImages[rNo]);
            //Set the image tag as the car make name, using the same index number
            image.setTag(cars[rNo]);
        }

        checkDuplicateMakes();
    }

    private void checkDuplicateMakes()
    {
        Random r = new Random();

        Integer rNo = 0;

        for (int i = 0; i < carImageViews.length; i++)
        {
            for (int j = 0; j < i; j++)
            {
                if(carImageViews[i].getTag().toString().equals(carImageViews[j].getTag().toString()))
                {
                    rNo = r.nextInt(cars.length);
                    carImageViews[i].setImageResource(carImages[rNo]);
                    carImageViews[i].setTag(cars[rNo]);
                    if(j != carImageViews.length)
                    {
                        checkDuplicateMakes();
                    }
                }
            }
        }
    }

    public void nextOnClick(View v)
    {
        randomCar();
        submitButton.setText("Submit");
        submitButton.setOnClickListener(this::submitOnClick);
    }

}