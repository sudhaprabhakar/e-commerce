package allinonecyberteam.com.signup;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {


    private EditText regEmailField;
    private EditText regPassField;
    private EditText regConfirmPassField;

    private Button regBtn;
    private Button regLoginBtn;
    private ProgressBar regProgressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        regEmailField = findViewById(R.id.reg_email);
        regPassField = findViewById(R.id.reg_password);
        regConfirmPassField = findViewById(R.id.reg_confirm_password);

        regBtn = findViewById(R.id.reg_btn);
        regLoginBtn =findViewById(R.id.reg_login_btn);

        //This below code is when user click on Already have account
        /*regLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Intent signup = new Intent(SignUp.this,LogIn.class);
           startActivity(signup);
                finish();
            }
        });*/

        regProgressBar =findViewById(R.id.reg_progress_bar);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = regEmailField.getText().toString();
                String pass = regPassField.getText().toString();
                String confirmpass = regConfirmPassField.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirmpass)){

                    if(pass.equals(confirmpass)){

                        regProgressBar.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(SignUp.this,"Successfully Registered!",Toast.LENGTH_LONG).show();



                                }else {
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(SignUp.this,"Error:" + errorMessage,Toast.LENGTH_LONG).show();
                                }

                                regProgressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }else{
                        Toast.makeText(SignUp.this,"Your Password and Confirm Password doesn't match",Toast.LENGTH_LONG).show();
                    }


                }
                else {
                    Toast.makeText(SignUp.this,"Fields required to be filled",Toast.LENGTH_LONG).show();
                }


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){

           // sendToHomePage();
        }



    }

    /*private void sendToHomePage() {

        Intent mainIntent = new Intent(SignUp.this, HomePage.class);
        startActivity(mainIntent);
        finish();


    }*/
}



