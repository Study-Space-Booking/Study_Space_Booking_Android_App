package com.placeholder.study_space_booking_android_app.Features.SignIn.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Seat;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.DBSeatManager;
import com.placeholder.study_space_booking_android_app.DBTimeSlotManager;
import com.placeholder.study_space_booking_android_app.Features.Welcome.Activity.WelcomeActivity;
import com.placeholder.study_space_booking_android_app.R;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.DBAdminManager;
import com.placeholder.study_space_booking_android_app.DBUserInformationManager;
import com.placeholder.study_space_booking_android_app.Features.Register.Activity.RegisterActivity;
import com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Repository.RepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources.LocalSourceImplementation;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Repository.SignInRepository;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;
import com.placeholder.study_space_booking_android_app.DBTimeSlotManager;

public class SignInActivity extends AppCompatActivity {
    EditText editUserName;
    EditText editPassword;
    Button signInButton;
    Button showButton;
    Button writeButton; //write in to database
    Button showDBButton;
    TextView textView;
    Toolbar toolbar;
    private static final String TAG = "SignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        toolbar = findViewById(R.id.toolbar_sign_in);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_sign_in);
        //Toolbar toolbar = findViewById(R.id.sign_in_toolbar);
        //setSupportActionBar(toolbar);
        final DBUserInformationManager dbUserInformationManager = DBUserInformationManager.getInstance();
        dbUserInformationManager.initialize(this);
        final DBAdminManager dbAdminManager = DBAdminManager.getInstance();
        dbAdminManager.initialize(this);
        //LocalSourceImplementation localSourceImplementation = new LocalSourceImplementation(dbUserInformationManager, dbAdminManager);
        //SignInRepository repository = new RepositoryImplementation(localSourceImplementation, null);
        final SignInUseCases signInUseCases = SignInUseCases.getInstance();


        editUserName = (EditText)findViewById(R.id.username_sign_in);
        editPassword = (EditText)findViewById(R.id.password_sign_in);
        textView = (TextView)findViewById(R.id.go_to_register);
        signInButton = (Button) findViewById(R.id.button_sign_in);
        showButton = (Button) findViewById(R.id.button_show);
        writeButton = (Button) findViewById(R.id.writedatabase);
        showDBButton = (Button) findViewById(R.id.showDBbutton);

        textView.setOnClickListener(
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

        writeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        writeIntoDataBase(v, DBTimeSlotManager.getInstance(), DBSeatManager.getInstance(), DBUserInformationManager.getInstance());
                    }
                }
        );

        showDBButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDB(v, DBTimeSlotManager.getInstance());
                    }
                }
        );
    }

    public void writeIntoDataBase(View v, DBTimeSlotManager d, DBSeatManager seatM, DBUserInformationManager userDB) {
        d.initialize(SignInActivity.this);
        userDB.initialize(SignInActivity.this);


        int minutes1 = 5;
        long millis1 = minutes1 * 60 * 1000;

        int minutes2 = -10;
        long millis2 = minutes2 * 60 * 1000;

        int minutes3 = 20;
        long millis3 = minutes3 * 60 * 1000;

        int minutes4 = 30;
        long millis4 = minutes4 * 60 * 1000;
        // define the state of the seat, 0--available, 1--not available, 2--under maintainence
        // 1, 1, 1, 2, System.currentTimeMillis(), System.currentTimeMillis()+millis, null, null, null, null, 0
        TimeSlot tmp1 = new TimeSlot(1, 1, 1, 2, (int) (System.currentTimeMillis()/1000), (int) ((System.currentTimeMillis()+millis1)/1000),
                1, 2, 3, 3, 0);

        TimeSlot tmp2 = new TimeSlot(2, 1, 2, 2, (int) (System.currentTimeMillis()/1000), (int) ((System.currentTimeMillis()+millis2)/1000),
                1, 2, 3, 3, 0);

        TimeSlot tmp3 = new TimeSlot(3, 1, 3, 2, (int) (System.currentTimeMillis()/1000), (int) ((System.currentTimeMillis()+millis3)/1000),
                1, 2, 3, 3, 0);

        TimeSlot tmp4 = new TimeSlot(4, 1, 4, 2, (int) (System.currentTimeMillis()/1000), (int) ((System.currentTimeMillis()+millis4)/1000),
                1, 2, 3, 3, 0);

        Seat s1 = new Seat(1, 1);
        Seat s2 = new Seat(2, 1);
        Seat s3 = new Seat(3, 1);
        Seat s4 = new Seat(4, 1);

        NormalUser demo = new NormalUser(1, 10, "demo", "123", 0);

        userDB.insertUserInformation(demo);


        d.setTimeSlot(tmp1);
        d.setTimeSlot(tmp2);
        d.setTimeSlot(tmp3);
        d.setTimeSlot(tmp4);


    }

    public void showDB(View v, DBTimeSlotManager d) {
        d.initialize(SignInActivity.this);

        Cursor cursor = d.getTimeSlot(1);
        if(cursor.getCount() == 0) {
            showInformation("Information", "No information found");
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            while(cursor.moveToNext()) {
                stringBuffer.append("id: " + cursor.getString(0) + "\n");
                stringBuffer.append("placeId: " + cursor.getString(1) + "\n");
                stringBuffer.append("seatId: " + cursor.getString(2) + "\n");
                stringBuffer.append("userId: " + cursor.getString(3) + "\n");
                stringBuffer.append("bookstartTime: " + cursor.getString(4) + "\n");
                stringBuffer.append("bookendTime: " + cursor.getString(5) + "\n");
                stringBuffer.append("InTime: " + cursor.getString(6) + "\n");
                stringBuffer.append("OutTime: " + cursor.getString(7) + "\n");
                stringBuffer.append("tmepLeaveTime: " + cursor.getString(8) + "\n");
                stringBuffer.append("tmepBackTime: " + cursor.getString(9) + "\n");
                stringBuffer.append("state: " + cursor.getString(10) + "\n");
            }

            stringBuffer.append("currentTime" + System.currentTimeMillis()/1000 +"\n");
            showDBInfo("Information", stringBuffer.toString());
        }

    }

    public void showDBInfo(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }



    public void signIn(View v, SignInUseCases signInUseCases) {
        String userName = editUserName.getText().toString();
        String password =  editPassword.getText().toString();
        Result<User> result = signInUseCases.signIn(userName, password);
        if(result instanceof Result.Handle) {
            Exception exception = ((Result.Handle) result).getException();
            Toast.makeText(SignInActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(SignInActivity.this, "sign in", Toast.LENGTH_LONG).show();

            if(((Result.Accepted<User>)result).getModel() instanceof NormalUser) {
                Intent intent = new Intent(SignInActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }

        }
    }

    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


    public void showInformation(View v, DBUserInformationManager dbUserInformationManager) {
        dbUserInformationManager.initialize(SignInActivity.this);
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
                stringBuffer.append("state: " + cursor.getString(4) + "\n");
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
