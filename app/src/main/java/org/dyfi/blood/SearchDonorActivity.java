package org.dyfi.blood;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.dyfi.blood.model.Donor;
import org.dyfi.blood.model.DonorListAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchDonorActivity extends AppCompatActivity {
    private String blood;
    private RecyclerView mRecycler;
    private FloatingActionButton fab_add;
    private List<Donor> DonorList;
    private DonorListAdapter adapter;
    DatabaseReference mDbRef;
    public static int REQUEST_PHONE_CALL =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_donor);
        mRecycler = findViewById(R.id.recycler_result);
        if (ContextCompat.checkSelfPermission(SearchDonorActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SearchDonorActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }
        fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchDonorActivity.this,AddDonorActivity.class));
            }
        });
        mRecycler.setHasFixedSize(true);
        final Spinner blood_grp = findViewById(R.id.spblood);
        blood_grp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                blood = blood_grp.getSelectedItem().toString();
                if(blood.equals("Blood Group")){
                    mDbRef = FirebaseDatabase.getInstance().getReference("users");
                }else {
                    mDbRef = FirebaseDatabase.getInstance().getReference("user").child(blood);
                }
                mDbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        DonorList = new ArrayList<>();
                        while (iterator.hasNext()) {
                            DataSnapshot child = iterator.next();
                            Donor item = child.getValue(Donor.class);
                            Log.d("OnDataCHange","Entered");
                            DonorList.add(item);

                        }
                        adapter = new DonorListAdapter(SearchDonorActivity.this,DonorList);
                        mRecycler.setAdapter(adapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

}
