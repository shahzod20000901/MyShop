package com.example.myshop.ui.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myshop.ui.LoginActivity;
import com.example.myshop.Model.Users;
import com.example.myshop.Prevalent.Prevalent;
import com.example.myshop.R;
import com.example.myshop.ui.RegisterActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    public Button joinButton, loginButton;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinButton=(Button) findViewById(R.id.main_join_btn);
        loginButton=(Button) findViewById(R.id.main_login_btn);
        loadingBar=new ProgressDialog(this);

        Paper.init(this);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent=new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        String UserPhoneKey=Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey=Paper.book().read(Prevalent.UserPasswordKey);

        if(UserPhoneKey != "" && UserPasswordKey != "")
        {
            if(TextUtils.isEmpty(UserPhoneKey) && TextUtils.isEmpty(UserPasswordKey))
            {
                //ValidateUser(UserPhoneKey, UserPasswordKey);
                loadingBar.setTitle("Вход в приложение ...");
                loadingBar.setMessage("Пожалуйста подаждите....");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }

    }



    private void ValidateUser(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("Users").child(phone).exists())
                    {
                        Users userData = snapshot.child("Users").child(phone).getValue(Users.class);

                        assert userData != null;
                        if (!userData.getPhone().equals(phone)) {
                            return;
                        }
                        if(userData.getPassword().equals(password))
                        {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Успешный вход!", Toast.LENGTH_SHORT).show();

                            Intent homeIntent=new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                        }
                        else
                        {
                            loadingBar.dismiss();
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Аккаунт с номером " + "phone" + " не существует!!!", Toast.LENGTH_SHORT).show();

                        Intent registerIntent=new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(registerIntent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




    }
}