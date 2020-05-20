package com.placeholder.study_space_booking_android_app.Features.Register.Activity;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;
import com.placeholder.bookingapplication.R;
import com.placeholder.study_space_booking_android_app.DBUserInformationManager;
import com.placeholder.study_space_booking_android_app.Features.Register.Data.Repository.RegisterRepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.Register.Data.Sources.RegisterLocalSource;
import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Bean.Result;
import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Repository.RegisterRepository;
import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Usecases.RegisterUseCases;
import com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources.LocalSourceImplementation;

public class RegisterActivity extends Activity {
    EditText editUserName;
    EditText editPassword;
    EditText editConfirmPassword;
    Button registerButton;
    Button showButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Toolbar toolbar = findViewById(R.id.register_toolbar);
        //setSupportActionBar(toolbar);
        final DBUserInformationManager dbUserInformationManager = DBUserInformationManager.getInstance();
        RegisterLocalSource registerLocalSource = RegisterLocalSource.getInstance(dbUserInformationManager);
        RegisterRepository registerRepository = RegisterRepositoryImplementation.getInstance(registerLocalSource, null);
        final RegisterUseCases registerUseCases = RegisterUseCases.getInstance(registerRepository);

        editUserName = (EditText)findViewById(R.id.username_register);
        editPassword = (EditText)findViewById(R.id.password_register);
        editConfirmPassword = (EditText)findViewById(R.id.confirm_password_register);
        registerButton = (Button)findViewById(R.id.button_register);
        showButton = (Button)findViewById(R.id.button_register_show);

        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        register(v, registerUseCases);
                    }
                }
        );
        showButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showInformation(v, dbUserInformationManager);
                    }
                }
        );

    }


    public void register(View v, RegisterUseCases registerUseCases) {
        String userName = editUserName.getText().toString();
        String password =  editPassword.getText().toString();
        String confirmPassword = editConfirmPassword.getText().toString();
        Result result = registerUseCases.register(userName, password, confirmPassword);
        if(result instanceof Result.Handle) {
            Exception exception = ((Result.Handle) result).getMessage();
            Toast.makeText(RegisterActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(RegisterActivity.this, "register", Toast.LENGTH_LONG).show();
        }
    }



    public void showInformation(View v, DBUserInformationManager dbUserInformationManager) {
        Cursor cursor = dbUserInformationManager.getAllUser();
        if(cursor.getCount() == 0) {
            showInformation("Information", "No information found");
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            while(cursor.moveToNext()) {
                stringBuffer.append("id: " + cursor.getString(0) + "\n");
                stringBuffer.append("userName: " + cursor.getString(1) + "\n");
                stringBuffer.append("password: " + cursor.getString(2) + "\n");
                stringBuffer.append("credit: " + cursor.getString(3) + "\n");
                stringBuffer.append("role: " + cursor.getString(4) + "\n");
                stringBuffer.append("isBlocked: " + cursor.getString(5) + "\n\n");
            }
            showInformation("Information", stringBuffer.toString());
        }
    }

    public void showInformation(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    */

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
}
