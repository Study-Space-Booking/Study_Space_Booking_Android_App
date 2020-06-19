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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

import java.util.ArrayList;
import java.util.List;
//import com.placeholder.study_space_booking_android_app.Services.TSService;

public class SignInActivity extends AppCompatActivity {
    public static EditText editUserName;
    public static EditText editPassword;
    Button signInButton;
    Button showButton;
    Button writeButton; //write in to database
    Button showDBButton;
    TextView textView;
    Toolbar toolbar;
    DatabaseReference seatdatabaseReference = FirebaseDatabase.getInstance().getReference("seat");
    DatabaseReference tsdatabaseReference = FirebaseDatabase.getInstance().getReference("timeslot");

    private static final String TAG = "SignInActivity";

    public static void setEditUserName(String newname) {
        editUserName.setText(newname);
    }

    public static void setEditPassword(String password) {
        editPassword.setText(password);
    }



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


        Seat s1 = new Seat( 1, 1);
        Seat s2 = new Seat(2, 1);
        Seat s3 = new Seat(3, 1);
        Seat s5 = new Seat(5, 1);
        Seat s6 = new Seat(6, 1);
        Seat s7 = new Seat(7, 1);
        Seat s8 = new Seat(8, 1);Seat s4 = new Seat(4, 1);
        Seat s9 = new Seat(9, 1);
        Seat s10 = new Seat(10, 1);

        Seat s11 = new Seat(11, 1);
        Seat s12 = new Seat(12, 1);
        Seat s13 = new Seat(13, 1);
        Seat s14 = new Seat(14, 1);
        Seat s15 = new Seat(15, 1);
        Seat s16 = new Seat(16, 1);
        Seat s17 = new Seat(17, 1);
        Seat s18 = new Seat(18, 1);
        Seat s19 = new Seat(19, 1);
        Seat s20 = new Seat(20, 1);
        Seat s21 = new Seat(21, 1);





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


        NormalUser demo = new NormalUser(1, 10, "demo", "123", 0);
        NormalUser demo2 = new NormalUser(2, 10, "demo2", "123", 0);
        Admin admin = new Admin(1, "admin", "admin");
        Admin admin2 = new Admin(2, "admin2", "admin");


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

        Seat s1p = new Seat(22, 2);
        Seat s2p = new Seat(23, 2);
        Seat s3p = new Seat(24, 2);
        Seat s5p = new Seat(25, 2);
        Seat s6p = new Seat(26, 2);
        Seat s7p = new Seat(27, 2);
        Seat s8p = new Seat(28, 2);
        Seat s9p = new Seat(29, 2);
        Seat s10p = new Seat(30, 2);
        Seat s11p = new Seat(31, 2);
        Seat s12p = new Seat(32, 2);
        Seat s13p = new Seat(33, 2);
        Seat s14p = new Seat(34, 2);
        Seat s15p = new Seat(35, 2);
        Seat s16p = new Seat(36, 2);
        Seat s17p = new Seat(37, 2);
        Seat s18p = new Seat(38, 2);
        Seat s19p = new Seat(39, 2);
        Seat s20p = new Seat(40, 2);
        Seat s21p = new Seat(41, 2);
        Seat s4p = new Seat(42, 2);

        dbLogHistoryManager.insertHistory(his1);
        dbLogHistoryManager.insertHistory(his2);
        dbLogHistoryManager.insertHistory(his3);
        dbLogHistoryManager.insertHistory(his4);

        dbLogHistoryManager.insertHistory(his1p);
        dbLogHistoryManager.insertHistory(his2p);
        dbLogHistoryManager.insertHistory(his3p);
        dbLogHistoryManager.insertHistory(his4p);

        List<Seat> seatList = new ArrayList<>();
        seatList.add(s1);
        seatList.add(s2);
        seatList.add(s3);
        seatList.add(s4);
        seatList.add(s5);
        seatList.add(s6);
        seatList.add(s7);
        seatList.add(s8);
        seatList.add(s9);
        seatList.add(s10);
        seatList.add(s11);
        seatList.add(s12);
        seatList.add(s13);
        seatList.add(s14);
        seatList.add(s15);
        seatList.add(s16);
        seatList.add(s17);
        seatList.add(s18);
        seatList.add(s19);
        seatList.add(s20);
        seatList.add(s21);

        seatList.add(s1p);
        seatList.add(s2p);
        seatList.add(s3p);
        seatList.add(s4p);
        seatList.add(s5p);
        seatList.add(s6p);
        seatList.add(s7p);
        seatList.add(s8p);
        seatList.add(s9p);
        seatList.add(s10p);
        seatList.add(s11p);
        seatList.add(s12p);
        seatList.add(s13p);
        seatList.add(s14p);
        seatList.add(s15p);
        seatList.add(s16p);
        seatList.add(s17p);
        seatList.add(s18p);
        seatList.add(s19p);
        seatList.add(s20p);
        seatList.add(s21p);

        /*
        String key = seatdatabaseReference.push().getKey();
        seatdatabaseReference.child(key).setValue(s4p);

         */
        /*
        for (Seat s: seatList) {
            String key = seatdatabaseReference.push().getKey();
            seatdatabaseReference.child(key).setValue(s);
        }

         */
        seatM.setSeat(s1);
        seatM.setSeat(s2);
        seatM.setSeat(s3);
        seatM.setSeat(s4);
        seatM.setSeat(s5);
        seatM.setSeat(s6);
        seatM.setSeat(s7);
        seatM.setSeat(s8);
        seatM.setSeat(s9);
        seatM.setSeat(s10);
        seatM.setSeat(s11);
        seatM.setSeat(s12);
        seatM.setSeat(s13);
        seatM.setSeat(s14);
        seatM.setSeat(s15);
        seatM.setSeat(s16);
        seatM.setSeat(s17);
        seatM.setSeat(s18);
        seatM.setSeat(s19);
        seatM.setSeat(s20);
        seatM.setSeat(s21);




        seatM.setSeat(s1p);
        seatM.setSeat(s2p);
        seatM.setSeat(s3p);
        seatM.setSeat(s4p);
        seatM.setSeat(s5p);
        seatM.setSeat(s6p);
        seatM.setSeat(s7p);
        seatM.setSeat(s8p);
        seatM.setSeat(s9p);
        seatM.setSeat(s10p);
        seatM.setSeat(s11p);
        seatM.setSeat(s12p);
        seatM.setSeat(s13p);
        seatM.setSeat(s14p);
        seatM.setSeat(s15p);
        seatM.setSeat(s16p);
        seatM.setSeat(s17p);
        seatM.setSeat(s18p);
        seatM.setSeat(s19p);
        seatM.setSeat(s20p);
        seatM.setSeat(s21p);


        d.setTimeSlot(tmp1);
        d.setTimeSlot(tmp2);
        d.setTimeSlot(tmp3);
        d.setTimeSlot(tmp4);

        d.setTimeSlot(tmp1p);
        d.setTimeSlot(tmp2p);
        d.setTimeSlot(tmp3p);
        d.setTimeSlot(tmp4p);

        adminDB.insertAdmin(admin);
        userDB.insertUserInformation(demo);
        adminDB.insertAdmin(admin2);
        userDB.insertUserInformation(demo2);

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
