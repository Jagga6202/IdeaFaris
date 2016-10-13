package com.brstdev.ideafaris.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brstdev.ideafaris.LoginActivity;
import com.brstdev.ideafaris.R;
import com.brstdev.ideafaris.model.FirebaseUserData;
import com.brstdev.ideafaris.utils.MarshMallowPermission;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.twitter.sdk.android.Twitter;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by brst-pc16 on 9/16/16.
 */
public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imagebutton;
    CircleImageView profilepic;
    EditText profilename, profileemail, profilephone, profile_address;
    Button save;
    Toolbar mToolbar;
    Uri picUri;
    MarshMallowPermission marshPermission;
    public static final String PREFS_NAME = "login";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    Bitmap bitmap;
    Handler handler;
    String name, email, gender, id, location, idfire, phone;
    TextView toolbar_title;
    public String logged;
    public String loggedtwitter;
    String cameraImage, gallaryImage;
    Bitmap finalBitmap, lastBitmap;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseUser mFireBaseUser;
    private DatabaseReference myRef;
    ImageView profilegenderpic;
    Spinner profilegender;
    String genderdp = "Male";
    String twUsename = "", twphonnum = "", twloc = "";
    String pic = "";

    private FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
        profilepic = (CircleImageView) findViewById(R.id.title);
        imagebutton = (ImageView) findViewById(R.id.imagebutton);
        profilename = (EditText) findViewById(R.id.profilename);
        profileemail = (EditText) findViewById(R.id.profileemail);
        profilephone = (EditText) findViewById(R.id.profilephone);
        profilegender = (Spinner) findViewById(R.id.profilegender);
        profile_address = (EditText) findViewById(R.id.profile_address);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        profileemail.setKeyListener(null);
        List<String> categories = new ArrayList<String>();
        categories.add("Male");
        categories.add("Female");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, categories);
        profilegender.setAdapter(dataAdapter);
        addgender();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("StoryLine");
        settings = getSharedPreferences(PREFS_NAME, 0);
        logged = settings.getString("logged", "");
        loggedtwitter = settings.getString("loggedtwitter", "");
        if (logged.equalsIgnoreCase("logged")) {
            name = settings.getString("name", "");
            email = settings.getString("email", "");
            gender = settings.getString("gender", "");
            phone = settings.getString("phone", "");
            Log.e("HomeTown", settings.getString("hometown1", ""));
            location = settings.getString("hometown1", "");
            Log.e("location", location);
            getprofilepic();
            profilename.setText(name);
            profileemail.setText(email);
            if (phone.equalsIgnoreCase("Nil")) {
                profilephone.setText("");
            } else {
                profilephone.setText(phone);
            }
            if (location.equalsIgnoreCase("Nil")) {
                profile_address.setText("");
            } else {
                profile_address.setText(location);
            }
        } else if (loggedtwitter.equalsIgnoreCase("loggedtwitter")) {
            name = settings.getString("name", "");
            email = settings.getString("email", "");
            location = settings.getString("hometown1", "");
            gender = settings.getString("gender", "");
            phone = settings.getString("phone", "");
            getprofilepic();
            profilename.setText(name);
            profileemail.setText(email);
            if (phone.equalsIgnoreCase("Nil")) {
                profilephone.setText("");
            } else {
                profilephone.setText(phone);
            }
            if (location.equalsIgnoreCase("Nil")) {
                profile_address.setText("");
            } else {
                profile_address.setText(location);
            }
        }
        save = (Button) findViewById(R.id.save);
        initToolbar();
        save.setOnClickListener(this);
        imagebutton.setOnClickListener(this);
        profilepic.setOnClickListener(this);
        marshPermission = new MarshMallowPermission(EditProfileActivity.this);
    }

    /*Intiallize Toolbar*/
    void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText("Edit Profile");
        mToolbar.setNavigationIcon(R.drawable.arrow);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                savedetails();

                EditProfileActivity.this.finish();
                Intent intMain = new Intent(EditProfileActivity.this, TabIconText.class);
                intMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intMain);
                settings.edit().putString("editProfile", "true").commit();
                break;

            case R.id.imagebutton:
                if (!marshPermission.checkPermissionCamera()) {
                    if (!marshPermission.checkPermissionForExternalStorage() || !marshPermission.checkPermissionForREADExternalStorage()) {
                        marshPermission.requestpermissionforcamerareadwrite();
                    }
                } else {
                    selectImage();
                }
                break;

            case R.id.title:
                if (!marshPermission.checkPermissionCamera()) {
                    if (!marshPermission.checkPermissionForExternalStorage() || !marshPermission.checkPermissionForREADExternalStorage()) {
                        marshPermission.requestpermissionforcamerareadwrite();
                    }
                } else {
                    selectImage();
                }
                break;

            case R.id.logout:
                if (logged.equalsIgnoreCase("logged")) {
                    LoginManager.getInstance().logOut();
                } else if (loggedtwitter.equalsIgnoreCase("loggedtwitter")) {
                    CookieSyncManager.createInstance(this);
                    CookieManager cookieManager = CookieManager.getInstance();
                    cookieManager.removeSessionCookie();
                    Twitter.getSessionManager().clearActiveSession();
                    Twitter.logOut();
                }
                FirebaseAuth.getInstance().signOut();
                settings = getSharedPreferences(PREFS_NAME, 0);
                editor = settings.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }

    /*Choose Image from camera or galleryvvvvvv*/
    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();//dfsfffdfsfwer
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                try {
                    File f = new File(Environment.getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    Uri selectedimage = Uri.fromFile(f);
                    cropimage(selectedimage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                cropimage(selectedImage);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri resultUri = result.getUri();
                try {
                    finalBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    lastBitmap = getResizedBitmap(finalBitmap, 100);
                    if (logged.equalsIgnoreCase("logged")) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        lastBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] b = baos.toByteArray();
                        pic = Base64.encodeToString(b, Base64.DEFAULT);
                        settings.edit().putString("idtest", pic).apply();
                    } else if (loggedtwitter.equalsIgnoreCase("loggedtwitter")) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        lastBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] b = baos.toByteArray();
                        pic = Base64.encodeToString(b, Base64.DEFAULT);
                        settings.edit().putString("idtest", pic).apply();
                    }
                    profilepic.setImageBitmap(finalBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*Crop Image*/
    public void cropimage(Uri imageUri) {
        CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).start(this);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MarshMallowPermission.CAMERA_PERMISSION:
                selectImage();
                break;

            default:
                break;
        }
    }

    /*Get Facebook Profilepic*/
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
    /*Get Twitter profilepic*/
    /*public void getprofilepictwitter()
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    //Your code goes here
                    id=settings.getString("twitterprofileimg","");
                    Log.e("dddd",id);
                    if(id.contains("http://"))
                    {
                        try {
                            URL url=new URL(id);
                            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            handler.sendEmptyMessage(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        byte [] encodeByte= Base64.decode(id, Base64.DEFAULT);
                        bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                        handler.sendEmptyMessage(0);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                profilepic.setImageBitmap(bitmap);
                super.handleMessage(msg);
            }
        };
    }*/


    /* Reduces the size of the image*/
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    /*For Gender select*/
    public void addgender() {
        profilegender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderdp = parent.getItemAtPosition(position).toString();
                Log.e("gender", genderdp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void savedetails() {
        twloc = profile_address.getText().toString();
        twphonnum = profilephone.getText().toString();
        settings.edit().putString("gender", genderdp).apply();
        idfire = settings.getString("idfire", "");
        Log.e("profilefb", id);
        if (profilename.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Name field is mandatory.", Toast.LENGTH_LONG).show();
            return;
        } else {
            twUsename = profilename.getText().toString();
            settings.edit().putString("name", twUsename).apply();
        }
       /* if(twloc.equalsIgnoreCase(""))
        {
            settings.edit().putString("hometown1","Nil").apply();
        }
        else
        {
            settings.edit().putString("hometown1",twloc).apply();
        }
        if(twphonnum.equalsIgnoreCase(""))
        {
            settings.edit().putString("phone","Nil").apply();
        }
        else
        {
            settings.edit().putString("phone",twphonnum).apply();
        }


*/
        settings.edit().putString("hometown1", twloc).apply();
        settings.edit().putString("phone", twphonnum).apply();
        if (!pic.equalsIgnoreCase("")) {
            FirebaseUserData userData = new FirebaseUserData(twUsename, email, pic, "7:00", twphonnum, twloc);
            myRef.child("Users").child(idfire).setValue(userData);
        } else {
            FirebaseUserData userData = new FirebaseUserData(twUsename, email, id, "7:00", twphonnum, twloc);
            myRef.child("Users").child(idfire).setValue(userData);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onresume", "onresume");
    }
}