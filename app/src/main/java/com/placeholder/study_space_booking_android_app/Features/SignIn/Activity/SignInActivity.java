package com.placeholder.study_space_booking_android_app.Features.SignIn.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.placeholder.study_space_booking_android_app.Core.Beans.Admin;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.Seat;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.Home.Activity.AdminHistoryActivity;
import com.placeholder.study_space_booking_android_app.Features.Register.Activity.RegisterActivity;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Activity.ViewReportActivity;
import com.placeholder.study_space_booking_android_app.Features.Welcome.Activity.WelcomeActivity;
import com.placeholder.study_space_booking_android_app.R;
import com.placeholder.study_space_booking_android_app.db.DBAdminManager;
import com.placeholder.study_space_booking_android_app.db.DBLogHistoryManager;
import com.placeholder.study_space_booking_android_app.db.DBSeatManager;
import com.placeholder.study_space_booking_android_app.db.DBTimeSlotManager;
import com.placeholder.study_space_booking_android_app.db.DBUserInformationManager;
import com.placeholder.study_space_booking_android_app.db.Injection;
//import com.placeholder.study_space_booking_android_app.Services.TSService;

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



        Injection injection = new Injection();
        injection.inject(this);

        //final DBUserInformationManager dbUserInformationManager = DBUserInformationManager.getInstance();
        //dbUserInformationManager.initialize(this);
        //final DBAdminManager dbAdminManager = DBAdminManager.getInstance();
        //dbAdminManager.initialize(this);
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
                        showInformation(v, DBUserInformationManager.getInstance());
                    }
                }
        );

        signInButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signIn(v, signInUseCases);
                        //startService(new Intent(SignInActivity.this, TSService.class));
                    }
                }
        );

        writeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        writeIntoDataBase(v, DBTimeSlotManager.getInstance(), DBSeatManager.getInstance(), DBUserInformationManager.getInstance(),
                                DBAdminManager.getInstance(), DBLogHistoryManager.getInstance());
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

    public void writeIntoDataBase(View v, DBTimeSlotManager d, DBSeatManager seatM, DBUserInformationManager userDB, DBAdminManager adminDB,
                                  DBLogHistoryManager dbLogHistoryManager) {
        d.initialize(SignInActivity.this);
        userDB.initialize(SignInActivity.this);
        adminDB.initialize(SignInActivity.this);
        seatM.initialize(SignInActivity.this);
        dbLogHistoryManager.initialize(SignInActivity.this);


        int minutes1 = 0;
        long millis1 = minutes1 * 60 * 1000;

        int minutes2 = 1;
        long millis2 = minutes2 * 60 * 1000;

        int minutes3 = 2;
        long millis3 = minutes3 * 60 * 1000;

        int minutes4 = 3;
        long millis4 = minutes4 * 60 * 1000;
        // define the state of the seat, 0--available, 1--not available, 2--under maintainence
        // 1, 1, 1, 2, System.currentTimeMillis(), System.currentTimeMillis()+millis, null, null, null, null, 0
        TimeSlot tmp1 = new TimeSlot(1, 1, 1, 1, (int) (System.currentTimeMillis()/1000), (int) ((System.currentTimeMillis()+millis1)/1000),
                1, 2, 3, 3, 1);

        TimeSlot tmp2 = new TimeSlot(2, 1, 2, 1, (int) (System.currentTimeMillis()/1000), (int) ((System.currentTimeMillis()+millis2)/1000),
                1, 2, 3, 3, 1);

        TimeSlot tmp3 = new TimeSlot(3, 1, 3, 1, (int) (System.currentTimeMillis()/1000), (int) ((System.currentTimeMillis()+millis3)/1000),
                1, 2, 3, 3, 1);

        TimeSlot tmp4 = new TimeSlot(4, 1, 4, 1, (int) (System.currentTimeMillis()/1000), (int) ((System.currentTimeMillis()+millis4)/1000),
                1, 2, 3, 3, 1);

        Seat s1 = new Seat(1, 1);
        Seat s2 = new Seat(2, 1);
        Seat s3 = new Seat(3, 1);
        Seat s4 = new Seat(4, 1);



        //  place 2
        int minutes1p = 0;
        long millis1p = minutes1p * 60 * 1000;

        int minutes2p = 1;
        long millis2p = minutes2p * 60 * 1000;

        int minutes3p = 2;
        long millis3p = minutes3p * 60 * 1000;

        int minutes4p = 3;
        long millis4p = minutes4p * 60 * 1000;
        // define the state of the seat, 0--available, 1--not available, 2--under maintainence
        // 1, 1, 1, 2, System.currentTimeMillis(), System.currentTimeMillis()+millis, null, null, null, null, 0
        TimeSlot tmp1p = new TimeSlot(5, 2, 1, 1, (int) (System.currentTimeMillis()/1000), (int) ((System.currentTimeMillis()+millis1p)/1000),
                1, 2, 3, 3, 1);

        TimeSlot tmp2p = new TimeSlot(6, 2, 2, 1, (int) (System.currentTimeMillis()/1000), (int) ((System.currentTimeMillis()+millis2p)/1000),
                1, 2, 3, 3, 1);

        TimeSlot tmp3p = new TimeSlot(7, 2, 3, 1, (int) (System.currentTimeMillis()/1000), (int) ((System.currentTimeMillis()+millis3p)/1000),
                1, 2, 3, 3, 1);

        TimeSlot tmp4p = new TimeSlot(8, 2, 4, 1, (int) (System.currentTimeMillis()/1000), (int) ((System.currentTimeMillis()+millis4p)/1000),
                1, 2, 3, 3, 1);

        Seat s1p = new Seat(5, 2);
        Seat s2p = new Seat(6, 2);
        Seat s3p = new Seat(7, 2);
        Seat s4p = new Seat(8, 2);

        NormalUser demo = new NormalUser(1, 10, "demo", "123", 0);
        NormalUser demo2 = new NormalUser(2, 10, "demo2", "123", 0);
        Admin admin = new Admin(1, "admin", "admin");
        Admin admin2 = new Admin(2, "admin2", "admin");
        adminDB.insertAdmin(admin);
        userDB.insertUserInformation(demo);
        adminDB.insertAdmin(admin2);
        userDB.insertUserInformation(demo2);

        TimeSlot his1 = new TimeSlot(1, 1, 1, 1, (int) (System.currentTimeMillis()/1000 - 9000), (int) ((System.currentTimeMillis()+millis1)/1000 - 9000),
                1, 2, 3, 3, 1);

        TimeSlot his2 = new TimeSlot(2, 1, 2, 1, (int) (System.currentTimeMillis()/1000- 9000), (int) ((System.currentTimeMillis()+millis2)/1000 - 9000),
                1, 2, 3, 3, 1);

        TimeSlot his3 = new TimeSlot(3, 1, 3, 1, (int) (System.currentTimeMillis()/1000- 9000), (int) ((System.currentTimeMillis()+millis3)/1000- 9000),
                1, 2, 3, 3, 1);

        TimeSlot his4 = new TimeSlot(4, 1, 4, 1, (int) (System.currentTimeMillis()/1000- 9000), (int) ((System.currentTimeMillis()+millis4)/1000- 9000),
                1, 2, 3, 3, 1);

        TimeSlot his1p = new TimeSlot(5, 2, 1, 2, (int) (System.currentTimeMillis()/1000- 9000), (int) ((System.currentTimeMillis()+millis1p)/1000- 9000),
                1, 2, 3, 3, 1);

        TimeSlot his2p = new TimeSlot(6, 2, 2, 2, (int) (System.currentTimeMillis()/1000- 9000), (int) ((System.currentTimeMillis()+millis2p)/1000- 9000),
                1, 2, 3, 3, 1);

        TimeSlot his3p = new TimeSlot(7, 2, 3, 2, (int) (System.currentTimeMillis()/1000- 9000), (int) ((System.currentTimeMillis()+millis3p)/1000- 9000),
                1, 2, 3, 3, 1);

        TimeSlot his4p = new TimeSlot(8, 2, 4, 2, (int) (System.currentTimeMillis()/1000- 9000), (int) ((System.currentTimeMillis()+millis4p)/1000- 9000),
                1, 2, 3, 3, 1);

        dbLogHistoryManager.insertHistory(his1);
        dbLogHistoryManager.insertHistory(his2);
        dbLogHistoryManager.insertHistory(his3);
        dbLogHistoryManager.insertHistory(his4);

        dbLogHistoryManager.insertHistory(his1p);
        dbLogHistoryManager.insertHistory(his2p);
        dbLogHistoryManager.insertHistory(his3p);
        dbLogHistoryManager.insertHistory(his4p);


        seatM.setSeat(s1p);
        seatM.setSeat(s2p);
        seatM.setSeat(s3p);
        seatM.setSeat(s4p);

        seatM.setSeat(s1);
        seatM.setSeat(s2);
        seatM.setSeat(s3);
        seatM.setSeat(s4);

        d.setTimeSlot(tmp1);
        d.setTimeSlot(tmp2);
        d.setTimeSlot(tmp3);
        d.setTimeSlot(tmp4);

        d.setTimeSlot(tmp1p);
        d.setTimeSlot(tmp2p);
        d.setTimeSlot(tmp3p);
        d.setTimeSlot(tmp4p);


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

        /*
        // showLoading()...
        ApiService.getInstance().login(userName, password, new ApiService.Callback<Result<User>>() {
            @Override
            public void callback(final Result<User> result) {
                // dismissLoading();
                if (result.isSuccess()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SignInActivity.this, WelcomeActivity.class);
                            // to trans some data to next activity
                            intent.putExtra("userName", result.getData().getUserName());
                            startActivity(intent);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignInActivity.this, result.getMsg(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
         */

        Result<User> result = signInUseCases.signIn(userName, password);
        if(result instanceof Result.Handle) {
            Exception exception = ((Result.Handle) result).getException();
            Toast.makeText(SignInActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(SignInActivity.this, "sign in", Toast.LENGTH_LONG).show();

            if(((Result.Accepted<User>)result).getModel() instanceof NormalUser) {
                Intent intent = new Intent(SignInActivity.this, WelcomeActivity.class);
                startActivity(intent);
            } else if (((Result.Accepted<User>)result).getModel() instanceof Admin) {
                Intent intent = new Intent(SignInActivity.this, ViewReportActivity.class);
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
