package com.placeholder.study_space_booking_android_app.Features.Register.Data.Sources;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Model.RegisterListener;
import com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources.RemoteSourceImplementation;

import java.util.ArrayList;
import java.util.List;

public class RegisterRemoteSource implements RegisterSource {
    private static volatile RegisterRemoteSource instance;
    private final DatabaseReference databaseReference;
    //private List<User> users;

    RegisterRemoteSource(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
        //this.users = new ArrayList<>();
    }

    public static RegisterRemoteSource getInstance() {
        if(instance == null) {
            instance = new RegisterRemoteSource(FirebaseDatabase.getInstance().getReference().child("user"));
        }
        return instance;
    }

    @Override
    public boolean hasExistingUser(String userName, final RegisterListener registerListener) {
        try {
            /*
            Log.d("in register", "waiting for normal user task to be created");
            Task<List<User>> normalUserTask = checkNormalUser(userName).then(Tasks.<Void>forResult(null));
            Log.d("in register", "waiting for normal user task to start");
            Tasks.await(normalUserTask);
            Log.d("in register", "waiting for normal user task to complete");
            Task<List<User>> administratorTask = checkAdministrator(userName).then(normalUserTask);
            List<User> userList = Tasks.await(administratorTask);

            if (userList.size() == 0) {
                registerListener.onNewUser();
            } else {
                registerListener.onExistingUser();
            }
            */

            Tasks.<Void>forResult(null)
                    .continueWithTask(checkNormalUser(userName))
                    .continueWithTask(checkAdministrator(userName))
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            }
                    )
                    .addOnSuccessListener(
                            new OnSuccessListener<List<User>>() {
                                @Override
                                public void onSuccess(List<User> users) {
                                    Log.d("in register source", "onSuccess: user size " + users.size());
                                    if (users.size() == 0) {
                                        registerListener.onNewUser();
                                    } else {
                                        registerListener.onExistingUser();
                                    }
                                }
                            }
                    );

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public Result<NormalUser> register(String userName, String password, RegisterListener registerListener) {
        try {
            String key = databaseReference.child("normal").push().getKey();
            databaseReference.child("normal").child(key).setValue(
                    new NormalUser(
                            key.hashCode(),
                            10,
                            userName,
                            password,
                            0
                    )
            );
            registerListener.onRegistered();
            return new Result.Accepted<>(null);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    Continuation<Void, Task<List<User>>> checkNormalUser(final String userName) {
        return new Continuation<Void, Task<List<User>>>() {
            @Override
            public Task<List<User>> then(@NonNull Task<Void> task) throws Exception {
                final TaskCompletionSource<List<User>> currentTask = new TaskCompletionSource<>();
                final List<User> users = new ArrayList<>();
                Query query = databaseReference.child("normal").orderByChild("userName").equalTo(userName);
                query.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d("in register normal user", "onDataChange: " + dataSnapshot.getKey());
                                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    Log.d("in register normal user", "onDataChange Snapshot: " + snapshot.getValue());
                                    NormalUser normalUser = snapshot.getValue(NormalUser.class);
                                    users.add(normalUser);
                                }
                                currentTask.setResult(users);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                currentTask.setException(new Exception(databaseError.getMessage()));
                            }
                        }
                );

                return currentTask.getTask();
            }
        };
    }

    Continuation<List<User>, Task<List<User>>> checkAdministrator(final String userName) {
        return new Continuation<List<User>, Task<List<User>>>() {
            @Override
            public Task<List<User>> then(@NonNull Task<List<User>> task) throws Exception {
                final TaskCompletionSource<List<User>> currentTask = new TaskCompletionSource<>();
                final List<User> userList = task.getResult();
                Query query = databaseReference.child("administrator").orderByChild("userName").equalTo(userName);
                query.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    userList.add(snapshot.getValue(NormalUser.class));
                                }
                                currentTask.setResult(userList);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                currentTask.setException(new Exception(databaseError.getMessage()));
                            }
                        }
                );
                return currentTask.getTask();
            }
        };
    }
}
