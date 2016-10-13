package com.brstdev.ideafaris;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.brstdev.ideafaris.model.FirebaseUserData;
import com.brstdev.ideafaris.model.GlobalStory;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by brst-pc16 on 9/12/16.
 */
public class LoginActivity extends AppCompatActivity {
    Button loginFb, loginTwitter;
    CallbackManager callbackManager;
    public static final String PREFS_NAME = "login";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    ProgressDialog pd;
    private TwitterAuthClient client;
    TwitterSession session;
    String username, twitterImage, location, emailtwitter, currentDateTimeString;
    ArrayList<String> elist;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    public String idFire;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        loginFb = (Button) findViewById(R.id.loginfb);
        loginTwitter = (Button) findViewById(R.id.logintwitter);
        pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("loading");
        mAuth = FirebaseAuth.getInstance();
        client = new TwitterAuthClient();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("StoryLine");
        elist = new ArrayList<String>();
        readfire();
        currentDateTimeString = new SimpleDateFormat("HH:mm").format(new Date());

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("", "onAuthStateChanged:signed_in:" + user.getUid() + user.getEmail());
                    final FirebaseUserData firebaseUserData = new FirebaseUserData(user.getDisplayName(),
                            user.getEmail(), user.getPhotoUrl().toString(), currentDateTimeString, "Nil", "Nil");
                    idFire = user.getUid();
                    Log.e("idfire", idFire);
                    settings = getSharedPreferences(PREFS_NAME, 0);
                    editor = settings.edit();
                    editor.putString("idfire", idFire);
                    editor.commit();
                    if (elist.contains(user.getEmail())) {
                        Log.e("  sddsds ", "  sjsjs");
                        return;
                    } else {
                        Log.e("  loli ", "  sjiooioiosjs");
                        myRef.child("Users").child(user.getUid()).setValue(firebaseUserData);
                    }

                } else {
                    Log.d("", "onAuthStateChanged:signed_out");
                }

            }
        };

        loginFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginfb();
            }
        });
        loginTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logintwitter();
            }

        });

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        Log.e("result", loginResult.toString());
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        String accessToken = loginResult.getAccessToken().getToken();
                        Log.i("accessToken", accessToken);
                        pd.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(final JSONObject object, GraphResponse response) {
                                        Log.i("LoginActivity", response.toString());
                                        pd.dismiss();
                                        Bundle bFacebookData = getFacebookData(object);
                                        if (bFacebookData != null) {
                                            try {
                                                /*Checking if user email exist in firebasedatabase*/
                                                if (elist.contains(object.optString("email"))) {
                                                    settings = getSharedPreferences(PREFS_NAME, 0);
                                                    editor = settings.edit();
                                                    myRef.child("Users").child(idFire).addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            FirebaseUserData person = dataSnapshot.getValue(FirebaseUserData.class);
                                                            Log.e("pp", dataSnapshot.toString());
                                                            editor.putString("name", person.getUsername());
                                                            editor.putString("email", person.getEmail());
                                                            editor.putString("gender", "Male");
                                                            if (person.getPhoneNum().equalsIgnoreCase("")) {
                                                                editor.putString("phone", "Nil");
                                                            } else {
                                                                editor.putString("phone", person.getPhoneNum());
                                                            }
                                                            if (person.getLocation().equalsIgnoreCase("")) {
                                                                editor.putString("hometown1", "Nil");
                                                            } else {
                                                                editor.putString("hometown1", person.getLocation());
                                                            }

                                                            editor.putString("idtest", person.getProfileUrl());
                                                            editor.putString("logged", "logged");
                                                            editor.commit();
                                                            Intent intent = new Intent(LoginActivity.this, MainAcivity.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(intent);
                                                            finish();
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });
                                                } else {
                                                    settings = getSharedPreferences(PREFS_NAME, 0);
                                                    editor = settings.edit();
                                                    username = object.getString("name");
                                                    String hometown = object.getString("hometown");
                                                    JSONObject mainObject = new JSONObject(hometown);
                                                    String namehome = mainObject.getString("name");
                                                    Log.e("hometown", namehome);
                                                    editor.putString("name", object.optString("name"));
                                                    editor.putString("email", object.optString("email"));
                                                    editor.putString("gender", object.optString("gender"));
                                                    editor.putString("phone", "Nil");
                                                    editor.putString("hometown1", namehome);
                                                    String profileImg = "https://graph.facebook.com/" + loginResult.getAccessToken().getUserId() + "/picture?width=80&height=80";
                                                    editor.putString("idtest", profileImg);
                                                    editor.putString("logged", "logged");
                                                    editor.commit();
                                                    Intent intent = new Intent(LoginActivity.this, MainAcivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id,first_name,last_name,email,gender,name,hometown"); // facebook
                                request.setParameters(parameters);
                                request.executeAsync();
                            }
                        }, 5000);
                    }

                    @Override
                    public void onCancel() {
                        Log.e("result", "cancel");
                        pd.dismiss();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e("result", exception.toString());
                        pd.dismiss();
                    }
                });
    }


    /*FireBase Login For Facebook*/
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("", "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("", "signInWithCredential:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w("", "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /*Firebase Login For Twitter*/
    private void handleTwitterSession(TwitterSession session) {
        Log.d("", "handleTwitterSession:" + session);
        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("", "signInWithCredential:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w("", "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /*Login with Facebook*/
    public void loginfb() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email", "user_hometown"));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        client.onActivityResult(requestCode, resultCode, data);
    }

    /*getting user details from facebook*/
    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();
        try {
            String id = object.getString("id");
            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("hometown"))
                bundle.putString("hometown", object.getString("hometown"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bundle;
    }


    /*Login with twitter*/
    public void logintwitter() {
        client.authorize(LoginActivity.this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                session = Twitter.getSessionManager().getActiveSession();
                client.requestEmail(session, new Callback<String>() {
                    @Override
                    public void success(final Result<String> result) {
                        Log.e("session", session.toString());
                        handleTwitterSession(session);
                        pd.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                emailtwitter = result.data.toString();
                                Log.e("emailtwi", emailtwitter);
                                Twitter.getApiClient(session).getAccountService()
                                        .verifyCredentials(true, false, new Callback<User>() {
                                            @Override
                                            public void success(Result<User> userResult) {
                                                pd.dismiss();
                                                User user = userResult.data;
                                                twitterImage = user.profileImageUrl;
                                                username = user.name;
                                                location = user.location;
                                                /*Checking if user email exist in firebasedatabase*/
                                                if (elist.contains(emailtwitter)) {
                                                    settings = getSharedPreferences(PREFS_NAME, 0);
                                                    editor = settings.edit();
                                                    myRef.child("Users").child(idFire).addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            FirebaseUserData person = dataSnapshot.getValue(FirebaseUserData.class);
                                                            Log.e("pp", dataSnapshot.toString());
                                                            editor.putString("name", person.getUsername());
                                                            editor.putString("email", person.getEmail());
                                                            editor.putString("gender", "Male");
                                                            editor.putString("phone", person.getPhoneNum());
                                                            editor.putString("hometown1", person.getLocation());
                                                            editor.putString("idtest", person.getProfileUrl());
                                                            editor.putString("loggedtwitter", "loggedtwitter");
                                                            editor.commit();
                                                            Intent intent = new Intent(LoginActivity.this, MainAcivity.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(intent);
                                                            finish();
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });
                                                } else {
                                                    Log.d("Details", user.description + " " + twitterImage + " " + username + " " + location + " " + emailtwitter + " " + user.getId());
                                                    settings = getSharedPreferences(PREFS_NAME, 0);
                                                    editor = settings.edit();
                                                    editor.putString("idtest", twitterImage);
                                                    editor.putString("name", username);
                                                    editor.putString("hometown1", location);
                                                    editor.putString("email", emailtwitter);
                                                    editor.putString("gender", "Nil");
                                                    editor.putString("phone", "Nil");
                                                    editor.putString("loggedtwitter", "loggedtwitter");
                                                    editor.commit();
                                                    Intent intent = new Intent(LoginActivity.this, MainAcivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }

                                            @Override
                                            public void failure(TwitterException e) {
                                                pd.dismiss();
                                            }
                                        });
                            }
                        }, 5000);

                    }

                    @Override
                    public void failure(TwitterException e) {

                    }
                });

            }

            @Override
            public void failure(TwitterException e) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /*Reading user details from firedatabase*/
    public void readfire() {
        myRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    FirebaseUserData person = postSnapshot.getValue(FirebaseUserData.class);
                    String emaildata = person.getEmail();
                    elist.add(emaildata);
                    Log.e("detailss", "detailsss");
                    Log.e("emailfirebase", elist.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}







