package model;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by paulodichone on 2/16/15.
 */
public class MyWish {

    public String title;
    public String content;
    public String recordDate;
    public int itemId;
    public String ImageUri;
    public ByteArrayOutputStream stream;


    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecordDate() {

        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    //public byte[] setImage(Bitmap bitmap) {
    //stream = new ByteArrayOutputStream();
    //bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
    //Log.v("Image from setImage :", bitmap.toString());
    //Log.v("Stream from setImage :", stream.toByteArray().toString());
    //return stream.toByteArray();

    //}

    //public Bitmap getImage(byte[] image) {

    //Log.v("Image from getImage :", BitmapFactory.decodeByteArray(image, 0, image.length).toString());
    //return BitmapFactory.decodeByteArray(image, 0, image.length);
    //}



}
