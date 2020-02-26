package com.example.nmedh.mynote;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;




import data.DatabaseHandler;
import model.MyWish;




public class MainActivity extends ActionBarActivity{

    private EditText title;
    private EditText content;
    private DatabaseHandler dba;
    private TextView datetextview;
    public Calendar myCalender;
    public ImageView imageview;
    private Boolean HasImage;
    private String ImageUri;
    private int id;
    public int click;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private int c=0;

    private static final int PERMS_REQUEST_CODE = 123;



    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(c==1)
        {
            Log.v("C from PostResume", String.valueOf(c));
            Intent intent = new Intent(MainActivity.this,SplashScreen.class);
            startActivity(intent);
            c=0;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        datetextview.setText(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss E").format(new Date()));

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        HasImage=false;

        dba = new DatabaseHandler(MainActivity.this);

        title = (EditText) findViewById(R.id.titleEditText);
        content = (EditText) findViewById(R.id.wishEditText);
        datetextview = (TextView) findViewById(R.id.dateview);
        imageview = (ImageView) findViewById(R.id.imageView);
        //mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        //mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);


        //mDrawerLayout.setDrawerListener(mToggle);
        //mToggle.syncState();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        myCalender = Calendar.getInstance();

        datetextview.setText(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss E").format(new Date()));

        Log.v("Date", datetextview.getText().toString());

        final DatePickerDialog.OnDateSetListener mydate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthofyear, int dayofmonth) {
                myCalender.set(Calendar.YEAR, year);
                myCalender.set(Calendar.MONTH, monthofyear);
                myCalender.set(Calendar.DAY_OF_MONTH, dayofmonth);
                updateLabel();
            }
        };

        datetextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(MainActivity.this, mydate, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(HasImage==true) {
                    AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
                    alert.setTitle("Delete Image");
                    alert.setMessage("Do you want to delete the image?");
                    alert.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            //imageview.setImageBitmap(null);
                            imageview.setImageURI(null);
                            imageview.requestLayout();
                            //imageview.getLayoutParams().height = 0;
                            //imageview.getLayoutParams().width = 0;
                            HasImage=false;
                            ImageUri=null;
                        }
                    });
                    alert.show();
                }
            }
        });


        Bundle extras = getIntent().getExtras();
        if(extras!=null) {

            id = extras.getInt("id");
            title.setText(extras.getString("title"));
            content.setText(extras.getString("content"));
            datetextview.setText(extras.getString("date"));
            Log.v("Date 4 :", datetextview.getText().toString());
            click = extras.getInt("value");
            ImageUri=extras.getString("image","NO Image");

            Log.v("Maina",ImageUri+"...");

            if(!ImageUri.equals("NO Image")) {

                imageview.setImageURI(Uri.parse(ImageUri));
                //imageview.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                //imageview.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                HasImage=true;
            }

        }
    }

    private boolean hasPermissions(){
        int res = 0;
        //string array of permissions,
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        for (String perms : permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }

    private void requestPerms(){
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode){
            case PERMS_REQUEST_CODE:

                for (int res : grantResults){
                    // if user granted all permissions.
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }

                break;
            default:
                // if user not granted permissions.
                allowed = false;
                break;
        }

        if (allowed){
            //user granted all permissions we can perform our task.
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            //Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //pickPhoto.setType("image/*");

            //startActivityForResult(pickPhoto, 0);
        }
        else {
            // we will give warning to user that they haven't granted permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //MainActivity.this.finish();
            //System.exit(0);

            //
            // moveTaskToBack(false);
            Log.v("Hello","from onKeyDown");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        //if(mToggle.onOptionsItemSelected(item))
        //{
        //return true;
        //}
        switch (item.getItemId()){
            case R.id.action_delete:


                //AlertDialog alertbox = new AlertDialog.Builder(this)
                //.setMessage("Choose Image From :")
                //.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                // @Override
                //public void onClick(DialogInterface dialogInterface, int i) {
                if(hasPermissions())
                {

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.setType("image/*");

                    startActivityForResult(pickPhoto, 0);

                }
                else {
                    //our app doesn't have permissions, So i m requesting permissions.
                    requestPerms();
                }


                //}
                //})
                //.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                // @Override
                //public void onClick(DialogInterface dialogInterface, int i) {


                //Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //startActivityForResult(takePicture, 1);
                //}
                // })
                // .show();

                return true;

            case R.id.action_share:
                //Show my entries
                Intent i = new Intent(MainActivity.this, DisplayWishesActivity.class);
                startActivity(i);

                return true;

            case R.id.save:
                //Save an entry
                if(content.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Empty content!!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    saveToDB();
                }

                return true;

            case R.id.option1:
                //go to change password activity
                Intent password = new Intent(MainActivity.this,CreatePassword.class);
                startActivity(password);
                return true;

            case R.id.option2:

                Toast.makeText(getApplicationContext(),"Setings",Toast.LENGTH_SHORT).show();
                Intent sett = new Intent(MainActivity.this,Settings.class);
                startActivity(sett);

                return true;

            case R.id.option3:

                Toast.makeText(getApplicationContext(),"About",Toast.LENGTH_SHORT).show();

                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK) {

                    //Pick

                    InputStream inputStream = null;
                    try {
                        inputStream = getApplicationContext().getContentResolver().openInputStream(imageReturnedIntent.getData());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 4;
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, opts);


                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);


                    ImageUri = getPath(imageReturnedIntent.getData());
                    //ImageUri = getImageUri(getApplicationContext(), bitmap).toString();
                    Log.v("ImageLocation", ImageUri + "..");

                    //imageview.getLayoutParams().height = 900;
                    //imageview.getLayoutParams().width = 800;
                    imageview.setImageURI(Uri.parse(ImageUri));
                    //imageview.setImageBitmap(bitmap);
                    //Uri.parse(ImageUri);


                    HasImage = true;


                }
                break;
            //case 1:
            //Take Picture
            //if(resultCode == RESULT_OK){
            //InputStream inputStream = null;
            //Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
            // imageview.setImageBitmap(photo);

            //ByteArrayOutputStream stream = new ByteArrayOutputStream();
            //photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            //ImageUri = getImageUri(getApplicationContext(), photo).toString();

            //imageview.requestLayout();
            //imageview.getLayoutParams().height = 900;
            //imageview.getLayoutParams().width = 800;
            //imageview.setImageURI(Uri.parse(ImageUri));
            // HasImage=true;


            //Log.v("ImageLocation",ImageUri+"..");

            //}
            //break;
        }
    }
    public  String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null)
            return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private void updateLabel() {
        String myFormat = "yyyy-MM-dd kk:mm:ss E";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        datetextview.setText(sdf.format(myCalender.getTime()));
        Log.v("Date 1 :", datetextview.getText().toString());
    }

    public void saveToDB() {

        Log.v("Maina",ImageUri+"....");



        MyWish wish = new MyWish();
        wish.setTitle(title.getText().toString().trim());
        if(title.getText().toString().equals(""))
        {
            wish.setTitle("Untitled");
        }
        wish.setContent(content.getText().toString().trim());
        wish.setRecordDate(datetextview.getText().toString().trim());
        wish.setImageUri(ImageUri);

        //wish.setImage(((BitmapDrawable)imageview.getDrawable()).getBitmap());//bytetype

        //Log.v("Image 1", String.valueOf((((BitmapDrawable) imageview.getDrawable()).getBitmap())));
        Log.v("Date 2 :", datetextview.getText().toString());



        if(click==1)
        {
            if(dba.UpdateWish(id,wish))
            {
                //dba.close();
                Toast.makeText(getApplicationContext(),"Changes saved successfully",Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, DisplayWishesActivity.class));
                MainActivity.this.finish();
            }
        }
        else
        {
            dba.addWishes(wish);

        }

        dba.close();

        //wish.getImage(wish.setImage(((BitmapDrawable)imageview.getDrawable()).getBitmap()));//bytetype inserted in getImage.Displays Bitmap

        //clear
        title.setText("");
        content.setText("");
        datetextview.setText(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss E").format(new Date()));

        Intent i = new Intent(MainActivity.this, DisplayWishesActivity.class);
        startActivity(i);

    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        c=1;
                        Log.v("C from onClick:", String.valueOf(c));
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);

                        startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(startMain);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }


}

