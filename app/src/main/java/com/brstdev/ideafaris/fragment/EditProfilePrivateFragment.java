package com.brstdev.ideafaris.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.brstdev.ideafaris.LoginActivity;
import com.brstdev.ideafaris.MainAcivity;
import com.brstdev.ideafaris.R;
import com.brstdev.ideafaris.activity.EditProfileActivity;
import com.brstdev.ideafaris.activity.EditProfilePrivateActivity;
import com.brstdev.ideafaris.activity.FriendListActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.twitter.sdk.android.Twitter;

import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by brst-pc16 on 9/14/16.
 */
public class EditProfilePrivateFragment extends Fragment {
    CircleImageView profilepic;
    TextView profilename, logout;
    EditText profileemail, profilephone, profilegender, profileaddress;
    Button friendlist;
    Toolbar mToolbar;
    FloatingActionButton fab;
    View rootView;
    TextView toolbar_title;
    public static final String PREFS_NAME = "login";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    Bitmap bitmap;
    Handler handler;
    String name, email, gender, id, location, phone;
    public String logged;
    public String loggedtwitter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.edit_profile_private, container, false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        profilepic = (CircleImageView) rootView.findViewById(R.id.profilepic);
        profilename = (TextView) rootView.findViewById(R.id.profilename);
        profileemail = (EditText) rootView.findViewById(R.id.profileemail);
        profilephone = (EditText) rootView.findViewById(R.id.profilephone);
        profilegender = (EditText) rootView.findViewById(R.id.profilegender);
        profileaddress = (EditText) rootView.findViewById(R.id.profileaddress);
        friendlist = (Button) rootView.findViewById(R.id.friendlist);
        toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
        logout = (TextView) rootView.findViewById(R.id.logout);
        settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        logged = settings.getString("logged", "");
        loggedtwitter = settings.getString("loggedtwitter", "");
        if (logged.equalsIgnoreCase("logged")) {
            name = settings.getString("name", "");
            email = settings.getString("email", "");
            gender = settings.getString("gender", "");
            location = settings.getString("hometown1", "");
            getprofilepic();
            profilename.setText(name);
            profileemail.setText(email);
            profilegender.setText(gender);
            if (location.equalsIgnoreCase("")) {
                profileaddress.setText("Nil");
            } else {
                profileaddress.setText(location);
            }
        } else if (loggedtwitter.equalsIgnoreCase("loggedtwitter")) {
            name = settings.getString("name", "");
            email = settings.getString("email", "");
            location = settings.getString("hometown1", "");
            getprofilepic();
            profilename.setText(name);
            profileemail.setText(email);
            if (location.equalsIgnoreCase("")) {
                profileaddress.setText("Nil");
            } else {
                profileaddress.setText(location);
            }
        }
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
                //getActivity().finish();

            }
        });
        initToolbar();
        friendlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendListActivity.class);
                startActivity(intent);
                //getActivity().finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutfun();
            }
        });

        return rootView;
    }

    void initToolbar() {

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayUseLogoEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText("Profile");
        mToolbar.setNavigationIcon(R.drawable.arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainAcivity.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().finish();
            }
        });
    }

    public void getprofilepic() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Your code goes here
                    id = settings.getString("idtest", "");
                    if (id.contains("https://") || id.contains("http://")) {
                        try {
                            URL url = new URL(id);
                            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            handler.sendEmptyMessage(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        byte[] encodeByte = Base64.decode(id, Base64.DEFAULT);
                        bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                        handler.sendEmptyMessage(0);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                profilepic.setImageBitmap(bitmap);
                super.handleMessage(msg);
            }

        };
    }

    @Override
    public void onResume() {
        super.onResume();
        settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        if (logged.equalsIgnoreCase("logged")) {
            name = settings.getString("name", "");
            email = settings.getString("email", "");
            gender = settings.getString("gender", "");
            phone = settings.getString("phone", "");
            Log.e("HomeTown", settings.getString("hometown1", ""));
            location = settings.getString("hometown1", "");
            getprofilepic();
            profilename.setText(name);
            profileemail.setText(email);
            profilegender.setText(gender);
            if (phone.equalsIgnoreCase("")) {
                profilephone.setText("Nil");
            } else {
                profilephone.setText(phone);
            }
            if (location.equalsIgnoreCase("")) {
                profileaddress.setText("Nil");
            } else {
                profileaddress.setText(location);
            }


        } else if (loggedtwitter.equalsIgnoreCase("loggedtwitter")) {
            name = settings.getString("name", "");
            email = settings.getString("email", "");
            location = settings.getString("hometown1", "");
            phone = settings.getString("phone", "");
            gender = settings.getString("gender", "");
            getprofilepic();
            profilegender.setText(gender);
            profilename.setText(name);
            profileemail.setText(email);
            profileaddress.setText(location);
            profilephone.setText(phone);
        }

    }

    public void logoutfun() {
        if (logged.equalsIgnoreCase("logged")) {

            LoginManager.getInstance().logOut();

        } else if (loggedtwitter.equalsIgnoreCase("loggedtwitter")) {
            CookieSyncManager.createInstance(getActivity());
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeSessionCookie();
            Twitter.getSessionManager().clearActiveSession();
            Twitter.logOut();
        }
        FirebaseAuth.getInstance().signOut();
        settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();

    }
}