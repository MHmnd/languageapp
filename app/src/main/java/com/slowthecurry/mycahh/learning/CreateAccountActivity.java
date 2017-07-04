package com.slowthecurry.mycahh.learning;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends BaseActivity {
    private final String LOG_TAG = "CREATE ACCOUNT ACTIVITY";
    private Context context;

    //UI components
    EditText userNameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    ProgressDialog createAccountProgressDialog;
    Button createAccountButton;

    //Firebase stuff
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fbAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        firebaseAuth = FirebaseAuth.getInstance();
        fbAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                if (currentUser != null) {
                    //currentUser is signed in
                    Log.d(LOG_TAG, currentUser.getProviderId());
                } else {
                    //Nobody home y'all!
                    Log.d(LOG_TAG, "NOT SIGNED IN YO!!!!");
                }
            }//end @Override onAuthStateChanged()
        };


        initializeUI();

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateAccountPressed();
            }
        });

        context = getApplicationContext();
    }//end onCreate

    private void initializeUI() {
        userNameEditText = (EditText) findViewById(R.id.edit_text_username_create);
        emailEditText = (EditText) findViewById(R.id.edit_text_email_create);
        passwordEditText = (EditText) findViewById(R.id.edit_text_password_create);
        createAccountButton = (Button) findViewById(R.id.btn_create_account_final);

        createAccountProgressDialog = new ProgressDialog(this);
        createAccountProgressDialog.setTitle(getResources().getString(R.string.progress_dialog_loading));
        createAccountProgressDialog.setMessage(getResources().getString(R.string.progress_dialog_authenticating_with_firebase));
    }//end intitializeUI()

    private void onCreateAccountPressed() {
        final String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(LOG_TAG, "USER CREATED");
                            firebaseAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                Intent launchMainActivity = new Intent(context, MainActivity.class);
                                                startActivity(launchMainActivity);

                                            } else {
                                                Log.d("SIGN IN", "ERROR");
                                            }

                                        }
                                    });
                        } else {
                            Log.d(LOG_TAG, "CREATE USER FAILED");
                            Toast.makeText(context, "It didn't work! Try again :)", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }//end onCreateAccountPressed

    public void onSignInPressed(View view) {
        finish();
    }

    //Overridden Methods

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(fbAuthStateListener);
    }//end onStart()

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(fbAuthStateListener);
    }//end onStop()
}
