package org.dyfi.blood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private String username, passwd;
    private TextInputEditText email, pswd;
    private Button signin;
    private FirebaseAuth mAuth;
    private View mLayout;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, SearchDonorActivity.class));
            finish();
        }
        mLayout = findViewById(R.id.login_layout);
        progressBar = findViewById(R.id.loginProgress);
        progressBar.setVisibility(View.GONE);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);


        email = findViewById(R.id.et_email);
        pswd = findViewById(R.id.et_password);
        signin = findViewById(R.id.bt_sign_in);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
//                    final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
//                    pd.setMessage("Signing In... \nPlease Wait....");
//                    pd.show();
                    progressBar.setVisibility(View.VISIBLE);
                    signin.setEnabled(false);
                    username = email.getText().toString();
                    passwd = pswd.getText().toString();
                    mAuth.signInWithEmailAndPassword(username, passwd)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Log.d("SIGNUP", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    pd.dismiss();
                                    progressBar.setVisibility(View.GONE);
                                    updateUI(user);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("SIGNUP", "signInWithEmail:failure", e);
                                    Snackbar.make(mLayout, "Authentication Failed.. \nPlease try again...", Snackbar.LENGTH_LONG).show();
//                                    pd.dismiss();
                                    progressBar.setVisibility(View.GONE);
                                    signin.setEnabled(true);
                                }
                            });
                }
            }
        });
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            startActivity(new Intent(this, SearchDonorActivity.class));
            finish();
        }
    }

    private boolean validate() {
        boolean result = true;
        if (TextUtils.isEmpty(pswd.getText())) {
            pswd.setError("Required");
            result = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            email.setError("Invalid Email");
            result = false;
        }
        return result;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
