package org.dyfi.blood;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class AddDonorActivity extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor);
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        final TextInputEditText name = findViewById(R.id.et_name);
        final TextInputEditText adress = findViewById(R.id.et_adress);
        final TextInputEditText mobile = findViewById(R.id.et_phone);
        final Spinner blood = findViewById(R.id.sp_blood);
        final TextInputEditText last_date = findViewById(R.id.et_date);
        final Calendar myCalendar = Calendar.getInstance();
        Button add_bt = findViewById(R.id.bt_add_donor);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(last_date,myCalendar);
            }

        };

        last_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddDonorActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        add_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name1 = name.getText().toString();
                String adress1 = adress.getText().toString();
                String mobile1 = mobile.getText().toString();
                String date = last_date.getText().toString();
                String blood1 = blood.getSelectedItem().toString();

                HashMap<String, String> user = new HashMap<>();
                user.put("name", name1);
                user.put("adress",adress1);
                user.put("mobile", mobile1);
                user.put("blood", blood1);
                user.put("date", date);

                if (name1.equals("") || blood1.equals("") || mobile1.equals("") || blood1.equals("Blood Group")) {
                    Toast.makeText(AddDonorActivity.this, "Please fill all the details.", Toast.LENGTH_SHORT).show();
                } else {
                    mFirebaseDatabase.child("user").child(blood1).child(mobile1).setValue(user);
                    mFirebaseDatabase.child("users").child(mobile1).setValue(user);
                    Toast.makeText(AddDonorActivity.this,"Donor Added", Toast.LENGTH_SHORT).show();
                    Intent home=new Intent(AddDonorActivity.this,SearchDonorActivity.class);
                    startActivity(home);
                    finish();
                }
            }
        });


    }
    private void updateLabel(TextInputEditText last_date, Calendar myCalendar) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        last_date.setText(sdf.format(myCalendar.getTime()));
    }
}
