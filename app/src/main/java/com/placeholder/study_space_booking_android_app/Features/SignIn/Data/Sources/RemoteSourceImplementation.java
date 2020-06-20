package com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

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
import com.google.firebase.database.core.view.QuerySpec;
import com.placeholder.study_space_booking_android_app.Core.Beans.Admin;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Model.SignInListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RemoteSourceImplementation implements Source {
    private static volatile RemoteSourceImplementation instance;
    private final DatabaseReference databaseReference;
    //private List<User> users;

    RemoteSourceImplementation(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
        //this.users = new ArrayList<>();
    }

    public static RemoteSourceImplementation getInstance() {
        if(instance == null) {
            instance = new RemoteSourceImplementation(FirebaseDatabase.getInstance().getReference().child("user"));
        }
        return instance;
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Result<User> getUserInformation(String password, final String userName, final SignInListener signInListener) {
        try {
            /*
            Query noramlQuery = databaseReference.child("normal").orderByChild("name").equalTo(userName);
            noramlQuery.addListenerForSingleValueEvent(normalUserValueEventListener(password, userName, signInListener));
            */
            Log.d("in remote source", "getUserInformation: wait for task to be created");
            Tasks.<Void>forResult(null)
                    .continueWithTask(normalUserValueEventListener(password, userName, signInListener))
                    .continueWithTask(administratorValueEventListener(password, userName, signInListener))
                    .addOnSuccessListener(
                            new OnSuccessListener<List<User>>() {
                                @Override
                                public void onSuccess(List<User> users) {
                                    Log.d("in remote source", "onSuccess: user size" + users.size());
                                    if(users.size() == 0) {
                                        signInListener.onNoUserFound();
                                    } else if(users.size() >= 2) {
                                        signInListener.onDuplicateUserFound();
                                    } else {
                                        signInListener.onUserFound(users.get(0));
                                    }
                                }
                            }
                    )
            .addOnFailureListener(
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("in remote source", "onFailure: " + e);
                            signInListener.onGettingUserInformationException(e);
                        }
                    }
            );
            return new Result.Accepted<>(null);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    Continuation<Void, Task<List<User>>> normalUserValueEventListener(final String password, final String userName, final SignInListener signInListener) {
        return new Continuation<Void, Task<List<User>>>() {
            @Override
            public Task<List<User>> then(@NonNull final Task<Void> task) throws Exception {
                final TaskCompletionSource<List<User>> currentTask = new TaskCompletionSource<>();
                final List<User> users = new ArrayList<>();
                Query query = databaseReference.child("normal").orderByChild("userName").equalTo(userName);
                query.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d("in remote source", "check normal user data change: user size" + users.size());
                                if (!dataSnapshot.exists()) {
                                    //signInListener.onNoUserFound();
                                } else {
                                    //List<NormalUser> users = new ArrayList<>();
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        users.add(snapshot.getValue(NormalUser.class));
                                    }
                                    Log.d("in remote source", "check normal user data change after loading: user size" + users.size());
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

    Continuation<List<User>, Task<List<User>>> administratorValueEventListener(final String password, final String userName, final SignInListener signInListener) {
        return new Continuation<List<User>, Task<List<User>>>() {
            @Override
            public Task<List<User>> then(final @NonNull Task<List<User>> task) throws Exception {
                final TaskCompletionSource<List<User>> currentTask = new TaskCompletionSource<>();
                final List<User> userList = task.getResult();
                Query query = databaseReference.child("administrator").orderByChild("userName").equalTo(userName);
                query.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d("in remote source", "check administrator data change: user size" + userList.size());
                                if (!dataSnapshot.exists()) {
                                    //signInListener.onNoUserFound();
                                } else {

                                    //List<NormalUser> users = new ArrayList<>();
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        userList.add(snapshot.getValue(Admin.class));
                                    }
                                    Log.d("in remote source", "check administrator data change after loading: user size" + userList.size());
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
