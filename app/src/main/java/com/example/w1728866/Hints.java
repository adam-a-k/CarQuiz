package com.example.w1728866;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class Hints extends AppCompatActivity {
    private TextView dashes;
    private ImageView carImage;
    private Button submitButton;
    private EditText input;
    private char[] letters;
    private Integer submitNo = 3;
    private int pickedCar = 0, lastCar = 0;

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
        setContentView(R.layout.activity_hints);

        dashes = findViewById(R.id.dashes);
        carImage = findViewById(R.id.carImage);
        input = findViewById(R.id.input);
        submitButton = findViewById(R.id.submitButton);

        randomCar();
    }

    private void randomCar()
    {
        /*
        derived from https://www.youtube.com/watch?v=Pfee0wFD5M0&ab_channel=TihomirRAdeff
         */

        Random r = new Random();

        //remove duplicates
        do
        {
            pickedCar = r.nextInt(carImages.length);
        }while(pickedCar == lastCar);

        lastCar = pickedCar;
        carImage.setImageResource(carImages[pickedCar]);
        //Set the image tag as the car make name, using the same index number
        carImage.setTag(cars[pickedCar]);
        setDashes();
    }

    //Gets the length of the car make name and sets dashes to be displayed with the same length
    private void setDashes()
    {
        Integer nameLength = carImage.getTag().toString().length();
        char[] makeDashes = carImage.getTag().toString().toCharArray();

        for (int i=0; i<nameLength; i++)
        {
            makeDashes[i] = '-';
        }
        String makeDashesStr = String.valueOf(makeDashes);

        dashes.setText(makeDashesStr);
    }

    public void submitOnClick(View v)
    {
        //take user input and convert to upper case
        String guessString = input.getText().toString().toUpperCase();
        char guess = " ".charAt(0);
        Boolean correctGuess = false;
        if(guessString.length() > 0){
            guess = guessString.charAt(0);
        }

        //Store a char array of the displayed letters/dashes
        char[] currentDashes = dashes.getText().toString().toCharArray();

        letters = carImage.getTag().toString().toUpperCase().toCharArray();
        //Loop through the displayed letters/dashes
        for (int i=0; i<currentDashes.length; i++)
        {   //If the guess is correct, set dash(es) to letter(s)
            if(guess == letters[i])
            {
                currentDashes[i] = letters[i];
                correctGuess = true;
            }
        }
        //If the guess is wrong,
        if(!correctGuess)
        {
            submitNo --;
        }

        //Update the displayed letters/dashes, revealing any letters if correct
        String currentDashesString = String.valueOf(currentDashes);
        dashes.setText(currentDashesString);

        input.setText("");
        checkAnswer();
    }

    private void checkAnswer()
    {
        //Check if there have been 3 incorrect attempts made
        if(submitNo == 0)
        {
            showToast(false, carImage.getTag().toString());
            update();
        }
        //Check if the word is complete
        if(dashes.getText().toString().equals(carImage.getTag().toString().toUpperCase()))
        {
            showToast(true, "");
            update();
        }

    }

    private void update()
    {
        //Reset variables
        submitNo = 3;
        input.setText("");
        //Change button to 'next'
        submitButton.setText("Next");
        submitButton.setOnClickListener(this::nextOnClick);
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
        input.setText("");
        randomCar();
        submitButton.setText("Submit");
        submitButton.setOnClickListener(this::submitOnClick);
    }
}