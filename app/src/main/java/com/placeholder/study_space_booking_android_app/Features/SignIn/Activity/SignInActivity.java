package com.placeholder.study_space_booking_android_app.Features.SignIn.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.placeholder.bookingapplication.R;
import com.placeholder.study_space_booking_android_app.DBUserInformationManager;
import com.placeholder.study_space_booking_android_app.Features.Register.Activity.RegisterActivity;
import com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Repository.RepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources.LocalSourceImplementation;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Bean.Result;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Repository.SignInRepository;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;

public class SignInActivity extends Activity {
    EditText editUserName;
    EditText editPassword;
    Button registerButton;
    Button signInButton;
    Button showButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //Toolbar toolbar = findViewById(R.id.sign_in_toolbar);
        //setSupportActionBar(toolbar);
        final DBUserInformationManager dbUserInformationManager = DBUserInformationManager.getInstance();
        LocalSourceImplementation localSourceImplementation = new LocalSourceImplementation(dbUserInformationManager);
        SignInRepository repository = new RepositoryImplementation(localSourceImplementation, null);
        final SignInUseCases signInUseCases = SignInUseCases.getInstance(repository);


        editUserName = (EditText)findViewById(R.id.username_sign_in);
        editPassword = (EditText)findViewById(R.id.password_sign_in);

        signInButton = (Button) findViewById(R.id.button_sign_in);
        showButton = (Button) findViewById(R.id.button_show);
        registerButton = (Button) findViewById(R.id.button_register);

        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        register(v);
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

        signInButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signIn(v, signInUseCases);
                    }
                }
        );
    }




    public void signIn(View v, SignInUseCases signInUseCases) {
        String userName = editUserName.getText().toString();
        String password =  editPassword.getText().toString();
        Result result = signInUseCases.signIn(userName, password);
        if(result instanceof Result.Handle) {
            Exception exception = ((Result.Handle) result).getMessage();
            Toast.makeText(SignInActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(SignInActivity.this, "sign in", Toast.LENGTH_LONG).show();
        }
    }

    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
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
