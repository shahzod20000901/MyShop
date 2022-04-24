package com.example.myshop.ui.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myshop.R;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {

    private Button logoutBtn;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logoutBtn=(Button) findViewById(R.id.button);
        loadingBar=new ProgressDialog(this);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                loadingBar.dismiss();
                loadingBar.setCanceledOnTouchOutside(false);
                Intent logoutIntent=new Intent(HomeActivity.this, MainActivity.class);
                startActivity(logoutIntent);


            }
        });

    }
}