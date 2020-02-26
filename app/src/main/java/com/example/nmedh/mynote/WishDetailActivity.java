package com.example.nmedh.mynote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;
import android.os.Handler;
import java.util.logging.LogRecord;
import data.DatabaseHandler;



public class WishDetailActivity extends ActionBarActivity {
    private TextView title, content;
    private TextView date;
    private ImageView imageview;

    private String ImageUri;
    public Calendar myCalender;
    private int id;
    private int Clicked_First;
    private Button play;
    private int i;
    int result;
    private TextToSpeech tts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_detail2);

        i=0;
        title = (TextView) findViewById(R.id.detailsTitle);
        date = (TextView) findViewById(R.id.detailsDateText);
        content = (TextView) findViewById(R.id.detailsTextView);
        imageview = (ImageView) findViewById(R.id.imageView2);
        play = (Button) findViewById(R.id.playbutton);

        myCalender = Calendar.getInstance();


        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            id = extras.getInt("id");
            title.setText(extras.getString("title"));
            date.setText(extras.getString("date"));
            Log.v("Date 4 :", date.getText().toString());

            ImageUri = extras.getString("image", "NO Image");

            Log.v("Maina", ImageUri + "...");

            if (!ImageUri.equals("NO Image")) {

                //imageview.requestLayout();
                //imageview.getLayoutParams().height = 900;
                //imageview.getLayoutParams().width = 800;
                imageview.setImageURI(Uri.parse(ImageUri));
            }
            content.setText("" + extras.getString("content") + "");

        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++i;
                if(i%2==1)
                {
                    play.setText("Stop");
                    tts=new TextToSpeech(WishDetailActivity.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int i) {
                            if(i==TextToSpeech.SUCCESS){
                                result=tts.setLanguage(Locale.US);
                                tts.speak(content.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Feature not supported",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    if(tts==null)
                    {
                        ++i;
                        play.setText("Play");
                    }
                }
                else if(i%2==0)
                {
                    play.setText("Play");
                    tts.shutdown();
                }
            }
        });

    }





    //TextToSpeech tts = new TextToSpeech(getApplicationContext(), (TextToSpeech.OnInitListener) getApplicationContext());
    //tts.setLanguage(Locale.US);
    //tts.speak(content.getText().toString(),TextToSpeech.QUEUE_ADD,null);






    //public void SetEnableStatus(Boolean Status)
    //{
       /* Update_Wish_Button.setEnabled(Status);
        if(Status)
            Update_Wish_Button.setVisibility(View.VISIBLE);
        else
            Update_Wish_Button.setVisibility(View.INVISIBLE); */
    //title.setEnabled(Status);
    //date.setEnabled(Status);
    //content.setEnabled(Status);
    //imageview.setEnabled(Status);

    //if(Status)
    //{
    //imageview.setOnClickListener(new View.OnClickListener() {
    //@Override
    //public void onClick(View v) {

    //AlertDialog alertbox = new AlertDialog.Builder(WishDetailActivity.this)
    //.setMessage("Choose Image From :")
    //.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
    //@Override
    //public void onClick(DialogInterface dialogInterface, int i) {


    //Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    //pickPhoto.setType("image/*");


    //startActivityForResult(pickPhoto, 0);
    //}
    //})
    //.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
    //@Override
    //public void onClick(DialogInterface dialogInterface, int i) {


    //Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    //startActivityForResult(takePicture, 1);
    //}
    //})
    //.show();
    //}
    //});
    //final DatePickerDialog.OnDateSetListener mydate = new DatePickerDialog.OnDateSetListener() {
    //@Override
    //public void onDateSet(DatePicker datePicker, int year, int monthofyear, int dayofmonth) {
    //myCalender.set(Calendar.YEAR, year);
    //myCalender.set(Calendar.MONTH, monthofyear);
    //myCalender.set(Calendar.DAY_OF_MONTH, dayofmonth);
    //updateLabel();
    //}
    //};

    //date.setOnClickListener(new View.OnClickListener() {
    //@Override
    //public void onClick(View view) {

    //new DatePickerDialog(WishDetailActivity.this, mydate, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
    //}
    //});
    //}
    //else
    //{
    //imageview.setOnClickListener(new View.OnClickListener() {
    //@Override
    //public void onClick(View v) {

    //}
    //});
    //date.setEnabled(Status);
    //}
    //}
    //private void updateLabel() {
    // String myFormat = "yyyy-MM-dd hh:mm:ss E";
    //SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
    //date.setText(sdf.format(myCalender.getTime()));
    //Log.v("Date 1 :", datetextview.getText().toString());
    // }
    //@Override
    // protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
    // super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
    // switch(requestCode) {
    //   case 0:
    //if(resultCode == RESULT_OK) {

    //Pick

    //InputStream inputStream = null;
    //try {
    //  inputStream = getApplicationContext().getContentResolver().openInputStream(imageReturnedIntent.getData());
    //} catch (FileNotFoundException e) {
    //  e.printStackTrace();
    //}

//                    BitmapFactory.Options opts = new BitmapFactory.Options();
    //                  opts.inSampleSize = 4;
    //                Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,opts);
    //      ImageUri=getPath(imageReturnedIntent.getData());

//                    Log.v("ImageLocation",ImageUri+"..");
//
    //                  imageview.setImageURI(Uri.parse(ImageUri));
    //                //HasImage=true;






    // }

    //  break;
    //  case 1:
    //Take Picture
    //    if(resultCode == RESULT_OK){
    //      InputStream inputStream = null;
    //    Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
    // imageview.setImageBitmap(photo);

    //  ByteArrayOutputStream stream = new ByteArrayOutputStream();
    //photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);

//                    ImageUri = getImageUri(getApplicationContext(), photo).toString();

    //                  imageview.setImageURI(Uri.parse(ImageUri));
    // HasImage=true;


    //                Log.v("ImageLocation",ImageUri+"..");

    //          }
    //        break;
    //}
    //}
    //public  String getPath(Uri uri)
    //{
    //  String[] projection = { MediaStore.Images.Media.DATA };
    //Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
    // if (cursor == null) return null;
    //int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    //cursor.moveToFirst();
    //String s=cursor.getString(column_index);
    //cursor.close();
    //return s;
    //}
    //public static Uri getImageUri(Context inContext, Bitmap inImage) {
    //  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    //inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    //String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
    //return Uri.parse(path);
    //}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.secondmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:


                //Bundle extras = getIntent().getExtras();




                AlertDialog alertbox = new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to delete this Entry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                                dba.deleteWish(id);

                                Toast.makeText(getApplicationContext(), "Wish Deleted!", Toast.LENGTH_LONG).show();

                                startActivity(new Intent(WishDetailActivity.this, DisplayWishesActivity.class));
                                WishDetailActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                            }
                        })
                        .show();

                return true;

            case R.id.action_share:

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, content.getText().toString());
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title.getText().toString());
                startActivity(Intent.createChooser(sharingIntent, "Share using"));

                return true;

            case R.id.save:
                //if(!Clicked_First)
                //Clicked_First=true;
                alertbox = new AlertDialog.Builder(WishDetailActivity.this)
                        .setMessage("Enable Editing?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Clicked_First=1;
                                Intent intent = new Intent(WishDetailActivity.this,MainActivity.class);
                                intent.putExtra("content", content.getText());
                                intent.putExtra("date", date.getText());
                                intent.putExtra("title", title.getText());
                                intent.putExtra("id", id);
                                intent.putExtra("value", Clicked_First);
                                intent.putExtra("image", ImageUri);


                                startActivity(intent);
                                WishDetailActivity.this.finish();

                                //SetEnableStatus(true);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //SetEnableStatus(false);

                              /*  MyWish myWish=new MyWish();
                                myWish.setImageUri("dwdw");
                                myWish.setContent("dwdw");
                                myWish.setItemId(id);
                                myWish.setRecordDate("dwdw");
                                myWish.setTitle("dhwdhw");
                                DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                                if(dba.UpdateWish(id,myWish))
                                {
                                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(WishDetailActivity.this, DisplayWishesActivity.class));
                                    WishDetailActivity.this.finish();
                                } */
                            }
                        })
                        .show();
                //}
                //else
                //{
                //  alertbox = new AlertDialog.Builder(WishDetailActivity.this)
                //  .setMessage("Save Changes?")
                //    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                //@Override
                //public void onClick(DialogInterface dialogInterface, int i) {

                //  MyWish myWish=new MyWish();
                //  myWish.setImageUri(ImageUri);
                // myWish.setContent(content.getText().toString().trim().replace("\"", ""));
                // myWish.setItemId(id);
                // myWish.setRecordDate(date.getText().toString().replace("Created: ",""));
                // myWish.setTitle(title.getText().toString());
                // DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                // if(dba.UpdateWish(id,myWish))
                //{
                // Toast.makeText(getApplicationContext(),"Changes saved successfully",Toast.LENGTH_LONG).show();
                //startActivity(new Intent(WishDetailActivity.this, DisplayWishesActivity.class));
                //WishDetailActivity.this.finish();
                // }

                //    }
                //})
                //.setNegativeButton("No", new DialogInterface.OnClickListener() {
                //@Override
                //public void onClick(DialogInterface dialogInterface, int i) {
                // dialogInterface.dismiss();

                //}
                //})
                //.show();
                //}
                return true;
            default:

                return super.onOptionsItemSelected(item);

        }
    }



}


