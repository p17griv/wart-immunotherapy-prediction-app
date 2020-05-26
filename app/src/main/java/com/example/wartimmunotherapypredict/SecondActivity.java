package com.example.wartimmunotherapypredict;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {

    DatabaseHelper HistoryDb;

    int currentQuery;
    int age, numberOfWarts, surfaceAreaOfWart, indurationDiameterOfWart;
    String gender, typeOfWart, rsult, dateAndTime;
    ImageView potImageView;

    ImageButton nextBtn;
    ImageButton preBtn;
    ImageButton preBtn2;
    ImageButton submitBtn;
    ImageButton newBtn;
    ImageButton saveBtn;
    ImageButton historyBtn;
    ImageButton clearHistoryBtn;
    ImageButton infoBtn;

    TableLayout query1Table;
    TableLayout query2Table;
    TableLayout query3Table;
    TableLayout query4Table;
    TableLayout query5Table;
    TableLayout query6Table;
    TableLayout resultTable;
    ScrollView historyScrollView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        HistoryDb = new DatabaseHelper(this);

        //Buttons
        nextBtn = (ImageButton) findViewById(R.id.nextImageButton);
        preBtn = (ImageButton) findViewById(R.id.preImageButton);
        preBtn2 = (ImageButton) findViewById(R.id.preImageButton2);
        submitBtn = (ImageButton) findViewById(R.id.submitImageButton);
        newBtn = (ImageButton) findViewById(R.id.newImageButton);
        saveBtn = (ImageButton) findViewById(R.id.saveImageButton);
        historyBtn = (ImageButton) findViewById(R.id.historyImageButton);
        clearHistoryBtn = (ImageButton) findViewById(R.id.clearHistoryImageButton);
        infoBtn = (ImageButton) findViewById(R.id.infoImageButton);

        //Layouts
        query1Table = (TableLayout) findViewById(R.id.query1Table);
        query2Table = (TableLayout) findViewById(R.id.query2Table);
        query3Table = (TableLayout) findViewById(R.id.query3Table);
        query4Table = (TableLayout) findViewById(R.id.query4Table);
        query5Table = (TableLayout) findViewById(R.id.query5Table);
        query6Table = (TableLayout) findViewById(R.id.query6Table);
        resultTable = (TableLayout) findViewById(R.id.resultTable);
        historyScrollView = (ScrollView) findViewById(R.id.historyScrollView);

        potImageView = (ImageView)findViewById(R.id.potImageView2);
        currentQuery = 1;
        UIHandler();
        controlsHandler();
        showInfo();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder aBldr = new AlertDialog.Builder(SecondActivity.this);
        aBldr.setMessage("Are you sure you want to close the app? All unsaved progress will be lost!").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        aBldr.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = aBldr.create();
        alert.setTitle("Warning!!");
        alert.show();
    }

    public void showInfo()
    {
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
                    builder.setTitle("About the app");
                    builder.setView(R.layout.info_layout);
                    builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
            }
        });
    }

    public void controlsHandler()
    {
        if(currentQuery == 1)
            preBtn.setVisibility(View.GONE);
        else
            preBtn.setVisibility(View.VISIBLE);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(currentQuery != 7)
                    currentQuery += 1;
                UIHandler();
                controlsHandler();
            }
        });

        preBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(currentQuery != 1) {
                    currentQuery -= 1;
                    UIHandler();
                    controlsHandler();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void UIHandler()
    {
        TextView queryCountTextView = (TextView) findViewById(R.id.queryCountTextView);
        ProgressBar queryProgressBar = (ProgressBar) findViewById(R.id.queryProgressBar);
        queryProgressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#982c2c")));
        queryCountTextView.setVisibility(View.VISIBLE);
        queryProgressBar.setVisibility(View.VISIBLE);
        potImageView.setVisibility(View.GONE);

        if(currentQuery == 1) //Get gender
        {
            nextBtn.setVisibility(View.VISIBLE);
            submitBtn.setVisibility(View.GONE);
            queryCountTextView.setText("Query 1/6");
            queryProgressBar.setProgress(20);
            query1Table.setVisibility(View.VISIBLE);
            query2Table.setVisibility(View.GONE);

            RadioButton male = (RadioButton) findViewById(R.id.maleRadioButton);
            if(male.isChecked())
                gender = "male";
            else
                gender = "female";
        }
        else if(currentQuery == 2) //Get age
        {
            nextBtn.setVisibility(View.VISIBLE);
            preBtn.setVisibility(View.VISIBLE);
            submitBtn.setVisibility(View.GONE);
            queryCountTextView.setText("Query 2/6");
            queryProgressBar.setProgress(35);
            query1Table.setVisibility(View.GONE);
            query2Table.setVisibility(View.VISIBLE);
            query3Table.setVisibility(View.GONE);

            SeekBar query2SeekBar = (SeekBar) findViewById(R.id.query2SeekBar);
            query2SeekBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#982c2c")));
            query2SeekBar.getThumb().setColorFilter(Color.parseColor("#982c2c"), PorterDuff.Mode.SRC_IN);

            final TextView seekBarTextView = (TextView) findViewById(R.id.seekBartextView);
            age = 35;
            query2SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(progress >= 12)
                    {
                        seekBarTextView.setText("" + progress);
                        age = progress;
                    }
                    else
                    {
                        seekBarTextView.setText("12");
                        age = 12;
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        }
        else if(currentQuery == 3) //Get number of warts
        {
            nextBtn.setVisibility(View.GONE);
            submitBtn.setVisibility(View.VISIBLE);
            queryCountTextView.setText("Query 3/6");
            queryProgressBar.setProgress(45);
            query3Table.setVisibility(View.VISIBLE);
            query2Table.setVisibility(View.GONE);
            query4Table.setVisibility(View.GONE);

            final EditText numberInput = (EditText) findViewById(R.id.numberInput);
            numberInput.setBackgroundResource(android.R.drawable.edit_text);
            numberInput.setEnabled(true);
            final TextView error = (TextView) findViewById(R.id.errorTextView);
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nbr = 0;
                    if(!numberInput.getText().toString().equals(""))
                    {
                        nbr = Integer.parseInt(numberInput.getText().toString());
                    }
                    if(nbr <= 0 || nbr >= 500)
                    {
                        error.setVisibility(View.VISIBLE);
                        numberInput.setBackgroundColor(Color.parseColor("#6A7C0707"));
                    }
                    else
                    {
                        numberOfWarts = nbr;
                        numberInput.setEnabled(false);
                        error.setVisibility(View.GONE);
                        numberInput.setBackgroundResource(android.R.drawable.edit_text);
                        nextBtn.setVisibility(View.VISIBLE);
                        submitBtn.setVisibility(View.GONE);
                    }
                }
            });
        }
        else if(currentQuery == 4) //Get type of warts
        {
            nextBtn.setVisibility(View.VISIBLE);
            submitBtn.setVisibility(View.GONE);
            queryCountTextView.setText("Query 4/6");
            queryProgressBar.setProgress(60);
            query3Table.setVisibility(View.GONE);
            query4Table.setVisibility(View.VISIBLE);
            query5Table.setVisibility(View.GONE);

            RadioButton common = (RadioButton) findViewById(R.id.commonRadioButton);
            RadioButton plantar = (RadioButton) findViewById(R.id.plantarRadioButton);
            if(common.isChecked())
                typeOfWart = "common";

            else if(plantar.isChecked())
                typeOfWart = "plantar";
            else
                typeOfWart = "both";
        }
        else if(currentQuery == 5) //Get surface area of wart
        {
            nextBtn.setVisibility(View.GONE);
            submitBtn.setVisibility(View.VISIBLE);
            queryCountTextView.setText("Query 5/6");
            queryProgressBar.setProgress(75);
            query4Table.setVisibility(View.GONE);
            query5Table.setVisibility(View.VISIBLE);
            query6Table.setVisibility(View.GONE);

            final EditText number2Input = (EditText) findViewById(R.id.number2Input);
            number2Input.setBackgroundResource(android.R.drawable.edit_text);
            number2Input.setEnabled(true);
            final TextView error2 = (TextView) findViewById(R.id.error2TextView);
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nbr = 0;
                    if(!number2Input.getText().toString().equals(""))
                    {
                        nbr = Integer.parseInt(number2Input.getText().toString());
                    }
                    if(nbr <= 0 || nbr >= 2500)
                    {
                        error2.setVisibility(View.VISIBLE);
                        number2Input.setBackgroundColor(Color.parseColor("#6A7C0707"));
                    }
                    else
                    {
                        surfaceAreaOfWart = nbr;
                        number2Input.setEnabled(false);
                        error2.setVisibility(View.GONE);
                        number2Input.setBackgroundResource(android.R.drawable.edit_text);
                        nextBtn.setVisibility(View.VISIBLE);
                        submitBtn.setVisibility(View.GONE);
                    }
                }
            });
        }
        else if(currentQuery == 6) //Get induration diameter
        {
            nextBtn.setVisibility(View.GONE);
            submitBtn.setVisibility(View.VISIBLE);
            queryCountTextView.setText("Query 6/6");
            queryProgressBar.setProgress(90);
            query5Table.setVisibility(View.GONE);
            query6Table.setVisibility(View.VISIBLE);
            resultTable.setVisibility(View.GONE);

            final EditText number3Input = (EditText) findViewById(R.id.number3Input);
            number3Input.setBackgroundResource(android.R.drawable.edit_text);
            number3Input.setEnabled(true);
            final TextView error3 = (TextView) findViewById(R.id.error3TextView);
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nbr = 0;
                    if(!number3Input.getText().toString().equals(""))
                    {
                        nbr = Integer.parseInt(number3Input.getText().toString());
                    }
                    if(nbr <= 0 || nbr >= 250)
                    {
                        error3.setVisibility(View.VISIBLE);
                        number3Input.setBackgroundColor(Color.parseColor("#6A7C0707"));
                    }
                    else
                    {
                        indurationDiameterOfWart = nbr;
                        number3Input.setEnabled(false);
                        error3.setVisibility(View.GONE);
                        submitBtn.setVisibility(View.GONE);
                        number3Input.setBackgroundResource(android.R.drawable.edit_text);
                        loadPrediction();
                    }
                }
            });
        }
    }

    public void loadPrediction()
    {
        potImageView.setVisibility(View.VISIBLE);
        AnimationDrawable loadAnim = (AnimationDrawable)potImageView.getDrawable();
        loadAnim.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                potImageView.setVisibility(View.GONE);
                submit();
            }
        }, 2000);
    }

    public void submit()
    {
        final TextView queryCountTextView = (TextView) findViewById(R.id.queryCountTextView);
        ProgressBar queryProgressBar = (ProgressBar) findViewById(R.id.queryProgressBar);
        resultTable.setVisibility(View.VISIBLE);
        query6Table.setVisibility(View.GONE);

        preBtn.setVisibility(View.GONE);
        nextBtn.setVisibility(View.GONE);
        saveBtn.setVisibility(View.VISIBLE);
        newBtn.setVisibility(View.VISIBLE);
        historyBtn.setVisibility(View.VISIBLE);
        queryProgressBar.setVisibility(View.GONE);
        queryCountTextView.setVisibility(View.GONE);

        dateAndTime = getTime();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HistoryDb.insertData(age, gender, numberOfWarts, typeOfWart, surfaceAreaOfWart, indurationDiameterOfWart, dateAndTime))
                    Toast.makeText(SecondActivity.this, "Results saved successfully!", Toast.LENGTH_LONG).show();
                saveBtn.setEnabled(false);
            }
        });

        newBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                currentQuery = 1;
                UIHandler();
                controlsHandler();
                nextBtn.setVisibility(View.VISIBLE);

                newBtn.setVisibility(View.GONE);
                saveBtn.setVisibility(View.GONE);
                saveBtn.setEnabled(true);
                historyBtn.setVisibility(View.GONE);
                resultTable.setVisibility(View.GONE);
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView historyTextView = findViewById(R.id.historyTextView);

                historyScrollView.setVisibility(View.VISIBLE);
                queryCountTextView.setVisibility(View.VISIBLE);
                queryCountTextView.setText("History");
                preBtn2.setVisibility(View.VISIBLE);
                clearHistoryBtn.setVisibility(View.VISIBLE);

                newBtn.setVisibility(View.GONE);
                saveBtn.setVisibility(View.GONE);
                historyBtn.setVisibility(View.GONE);
                resultTable.setVisibility(View.GONE);

                Cursor res;
                StringBuilder sb = new StringBuilder("\n");
                res = HistoryDb.getAllResults();
                if (!res.moveToFirst())
                    res.moveToFirst();
                while (!res.isAfterLast()) {
                    sb.append("Prediction on: \n" + res.getString(res.getColumnIndex("dateAndTime"))
                    + "\nAge: " + res.getInt(res.getColumnIndex("age"))
                    + "\nGender: " + res.getString(res.getColumnIndex("gender"))
                    + "\nNumber of Warts: " + res.getInt(res.getColumnIndex("numberOfWarts"))
                    + "\nType of Warts: " + res.getString(res.getColumnIndex("typeOfWarts"))
                    + "\nSurface Area: " + res.getInt(res.getColumnIndex("surfaceArea"))
                    + "\nInduration diameter: " + res.getInt(res.getColumnIndex("indurationDiameter"))
                    + "\n\n______________________________________\n\n");
                    res.moveToNext();
                }
                historyTextView.setText(sb);

                preBtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        historyScrollView.setVisibility(View.GONE);
                        queryCountTextView.setVisibility(View.GONE);
                        preBtn2.setVisibility(View.GONE);
                        clearHistoryBtn.setVisibility(View.GONE);

                        newBtn.setVisibility(View.VISIBLE);
                        saveBtn.setVisibility(View.VISIBLE);
                        historyBtn.setVisibility(View.VISIBLE);
                        resultTable.setVisibility(View.VISIBLE);
                    }
                });

                clearHistoryBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        historyTextView.setText(" ");
                        HistoryDb.recreate();
                        Toast.makeText(SecondActivity.this, "History cleared successfully!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        rsult = makePrediction();
        TextView result = (TextView) findViewById(R.id.resultOutputTextView);
        result.setText(
                "Age: " + age + "\n"
                        + "Gender: " + gender + "\n"
                        + "# of warts: " + numberOfWarts + "\n"
                        + "Type: " + typeOfWart + "\n"
                        + "Surface area: " + surfaceAreaOfWart + "\n"
                        + "Induration diameter: " + indurationDiameterOfWart
                        + "\n_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _"
                        + "\nResult:\n" + rsult
                        + "\n\n---DISCLAIMER---\n\n"
                        + "The final decision should not be 100%\n based on the app's prediction!\n"
                        + "The app's accuracy is 63.1% and is\n correctly predicting \nA* 73.7% and B** 52.6% of time."
                        + "\n\n * Immunotherapy is the right method cases.\n"
                        + "** Immunotherapy is not the right method cases.");
    }

    public String getTime()
    {
        Date currentTime = Calendar.getInstance().getTime();
        return currentTime.toString();
    }

    public String makePrediction()
    {
        if(indurationDiameterOfWart <= 3)
            return "Immunotherapy will cure warts!";
        else
        {
            if(typeOfWart.equals("common"))
            {
                if(gender.equals("male"))
                {
                    if(indurationDiameterOfWart <= 6)
                        return "Immunotherapy\n is not the right method to cure warts!";
                    else
                    {
                        if(surfaceAreaOfWart <= 50)
                            return "Immunotherapy\n is not the right method to cure warts!";
                        else
                            return "Immunotherapy will cure warts!";
                    }
                }
                else
                if(numberOfWarts <= 5)
                    return "Immunotherapy will cure warts!";
                else
                {
                    if(indurationDiameterOfWart <= 8)
                        return "Immunotherapy will cure warts!";
                    else
                        return "Immunotherapy\n is not the right method to cure warts!";
                }
            }
            else if(typeOfWart.equals("plantar"))
            {
                if(indurationDiameterOfWart <= 9)
                    return "Immunotherapy\n is not the right method to cure warts!";
                else
                    return "Immunotherapy will cure warts!";
            }
            else
                return "Immunotherapy\n is not the right method to cure warts!";
        }
    }
}
