package org.dyfi.blood.model;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.dyfi.blood.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DonorViewHolder extends RecyclerView.ViewHolder {
    private View mView;
    public CardView cardMain;

    public DonorViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        cardMain=itemView.findViewById(R.id.cardRowMain);
    }
    public void setDetails(final Context ctx, final Donor model){

        final Calendar myCalendar = Calendar.getInstance();
        TextView mName = mView.findViewById(R.id.tv_name);
        TextView mAdress = mView.findViewById(R.id.tv_adress);
        TextView mPhone = mView.findViewById(R.id.tv_phone);
        final TextView mDate = mView.findViewById(R.id.tv_date);
        ImageView mBlood = mView.findViewById(R.id.img_blood);
        ImageButton mCall = mView.findViewById(R.id.bt_call);

        mName.setText(model.getName());
        mAdress.setText(model.getAdress());
        mPhone.setText(model.getMobile());
        mDate.setText(model.getDate());

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(mDate,myCalendar,model);
            }

        };

        switch (model.getBlood()){
            case "A+":{
                Picasso.get().load(R.drawable.type1).into(mBlood);
                break;
            }
            case "A-":{
                Picasso.get().load(R.drawable.type2).into(mBlood);
                break;
            }
            case "B+":{
                Picasso.get().load(R.drawable.type3).into(mBlood);
                break;
            }
            case "B-":{
                Picasso.get().load(R.drawable.type4).into(mBlood);
                break;
            }
            case "AB+":{
                Picasso.get().load(R.drawable.type5).into(mBlood);
                break;
            }
            case "AB-":{
                Picasso.get().load(R.drawable.type6).into(mBlood);
                break;
            }
            case "O+":{
                Picasso.get().load(R.drawable.type7).into(mBlood);
                break;
            }
            case "O-":{
                Picasso.get().load(R.drawable.type8).into(mBlood);
                break;
            }

        }

        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "Blood"+model.getBlood(), Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+model.getMobile()));
                ctx.startActivity(callIntent);
            }
        });
        cardMain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(ctx, "long click", Toast.LENGTH_SHORT).show();
                new DatePickerDialog(ctx, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                return false;
            }
        });

    }
    private void updateLabel(TextView last_date, Calendar myCalendar, Donor model) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String date_selected = sdf.format(myCalendar.getTime());
        last_date.setText(date_selected);
        DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabase.child("user").child(model.getBlood()).child(model.getMobile()).child("date").setValue(date_selected);
        mFirebaseDatabase.child("users").child(model.getMobile()).child("date").setValue(date_selected);
    }
}
