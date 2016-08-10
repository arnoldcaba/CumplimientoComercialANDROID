package com.cecentro.arklan.cumplimientocomercial;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    TextView user,password;
    Button ingresar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user=(TextView)findViewById(R.id.txt_user);
        password=(TextView)findViewById(R.id.txt_password);
        ingresar=(Button)findViewById(R.id.btn_ingresar);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    ingreso();

                } else {
                    // User is signed out
                   Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };



        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_ingresar:
                        ingresar.setBackgroundColor(Color.rgb(239,83,80));
                        mAuth.signInWithEmailAndPassword(user.getText().toString(), password.getText().toString())
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {


                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {
                                            Log.w(TAG, "signInWithEmail", task.getException());
                                            ingresar.setBackgroundColor(Color.rgb(183,28,28));
                                            Toast.makeText(LoginActivity.this, "Autenticacion fallo.",
                                                    Toast.LENGTH_SHORT).show();
                                        }else {

                                        }

                                    }
                                });

                }
            }
        });

    }

    private void ingreso(){
        Intent intent = new Intent(LoginActivity.this, Global.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
