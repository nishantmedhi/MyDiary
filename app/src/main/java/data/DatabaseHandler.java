package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.MyWish;

/**
 * Created by paulodichone on 2/16/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper  {

    private final ArrayList<MyWish> wishList = new ArrayList<>();


    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create our table

        String CREATE_WISHES_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, " + Constants.TITLE_NAME +
                " TEXT, " + Constants.CONTENT_NAME + " TEXT, " + Constants.DATE_NAME + " TEXT, " + Constants.IMAGE_NAME + " TEXT);";

        db.execSQL(CREATE_WISHES_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        Log.v("ONUPGRADE", "DROPING THE TABLE AND CREATING A NEW ONE!");

        //create a new one
        onCreate(db);


    }




    //delete a wish
    public void deleteWish(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ? ",
                new String[]{ String.valueOf(id)});

        db.close();

    }

    public Boolean UpdateWish(int id,MyWish wish)
    {


        try {
            ContentValues values = new ContentValues();
            values.put(Constants.TITLE_NAME, wish.getTitle());
            values.put(Constants.CONTENT_NAME, wish.getContent());
            values.put(Constants.DATE_NAME, wish.getRecordDate());
            values.put(Constants.IMAGE_NAME, wish.getImageUri());

            SQLiteDatabase db = this.getWritableDatabase();
            db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + "=" + id, null);
            db.close();
            return true;
        }
        catch (Exception e)
        {
            Log.v("MainaDB",e.getLocalizedMessage()+"...");
        }
        return  false;

    }

    //add content to table
    public void addWishes(MyWish wish) {

        Log.v("MainaWish",wish.getImageUri()+"...");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.TITLE_NAME, wish.getTitle());
        values.put(Constants.CONTENT_NAME, wish.getContent());
        values.put(Constants.DATE_NAME, wish.getRecordDate());
        values.put(Constants.IMAGE_NAME, wish.getImageUri());
        //Log.v("Kutta :", String.valueOf(wish.getImage()));
        //byte[] image = wish.setImage()
        //values.put(Constants.IMAGE_NAME, wish.getImage(image).toString());




        db.insert(Constants.TABLE_NAME, null, values);
        //db.insert(Constants.TABLE_NAME, null, values);

        // Log.v("Wish successfully!", "yeah!!");

        db.close();


    }

    //Get all wishes
    public ArrayList<MyWish> getWishes() {

        wishList.clear();

        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID, Constants.TITLE_NAME, Constants.CONTENT_NAME, Constants.DATE_NAME, Constants.IMAGE_NAME},null,null, null,null, Constants.DATE_NAME + " DESC");

        //loop through cursor
        if (cursor.moveToFirst()) {

            do {

                MyWish wish = new MyWish();
                wish.setTitle(cursor.getString(cursor.getColumnIndex(Constants.TITLE_NAME)));
                wish.setContent(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_NAME)));
                wish.setItemId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
                wish.setRecordDate(cursor.getString(cursor.getColumnIndex(Constants.DATE_NAME)));
                wish.setImageUri(cursor.getString(cursor.getColumnIndex(Constants.IMAGE_NAME)));
                //byte[] image = cursor.getBlob(4);
                //Bitmap b = wish.getImage();// returns Bitmap
                //wish.setImage(b);
                //Log.v("Image from Blob :", image.toString());


                //Log.v("Date from handler :",wish.getRecordDate());



                wishList.add(wish);

            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return wishList;

    }


}
