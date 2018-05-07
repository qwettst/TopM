package com.example.zz.zz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zz.zz.model.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 08.04.2018.
 */

public class DatabaseReviewHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_NAME = "review_db";

    public DatabaseReviewHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Review.CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Review.TABLE_NAME);
        onCreate(db);
    }

    public long insertReview(Review review) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Review.COLUMN_AUTHOR,review.getAuthor());
        values.put(Review.COLUMN_NAMEMASTER,review.getNameMaster());
        values.put(Review.COLUMN_SPEC,review.getSpec());
        values.put(Review.COLUMN_CITY,review.getCity());
        values.put(Review.COLUMN_STREET,review.getStreet());
        values.put(Review.COLUMN_DATEREVIEW,review.getDateReview());
        values.put(Review.COLUMN_TREVIEW, review.gettReview());
        values.put(Review.COLUMN_REVRATE,review.getRevRate());

        // insert row
        long id = db.insert(Review.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Review getReview(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Review.TABLE_NAME,
                new String[]{Review.COLUMN_ID, Review.COLUMN_AUTHOR, Review.COLUMN_NAMEMASTER, Review.COLUMN_SPEC, Review.COLUMN_CITY, Review.COLUMN_STREET, Review.COLUMN_DATEREVIEW, Review.COLUMN_TREVIEW, Review.COLUMN_REVRATE},
                Review.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Review note = new Review(
                cursor.getInt(cursor.getColumnIndex(Review.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Review.COLUMN_AUTHOR)),
                cursor.getString(cursor.getColumnIndex(Review.COLUMN_NAMEMASTER)),
                cursor.getString(cursor.getColumnIndex(Review.COLUMN_SPEC)),
                cursor.getString(cursor.getColumnIndex(Review.COLUMN_CITY)),
                cursor.getString(cursor.getColumnIndex(Review.COLUMN_STREET)),
                cursor.getString(cursor.getColumnIndex(Review.COLUMN_DATEREVIEW)),
                cursor.getString(cursor.getColumnIndex(Review.COLUMN_TREVIEW)),
                cursor.getFloat(cursor.getColumnIndex(Review.COLUMN_REVRATE)));
        // close the db connection
        cursor.close();

        return note;
    }

    public List<Review> getAllReview() {
        List<Review> reviews = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Review.TABLE_NAME ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Review review = new Review();
                review.setIdR(cursor.getInt(cursor.getColumnIndex(Review.COLUMN_ID)));
                review.setAuthor(cursor.getString(cursor.getColumnIndex(Review.COLUMN_AUTHOR)));
                review.setNameMaster(cursor.getString(cursor.getColumnIndex(Review.COLUMN_NAMEMASTER)));
                review.setSpec(cursor.getString(cursor.getColumnIndex(Review.COLUMN_SPEC)));
                review.setCity( cursor.getString(cursor.getColumnIndex(Review.COLUMN_CITY)));
                review.setStreet(cursor.getString(cursor.getColumnIndex(Review.COLUMN_STREET)));
                review.setDateReview(cursor.getString(cursor.getColumnIndex(Review.COLUMN_DATEREVIEW)));
                review.setTReview(cursor.getString(cursor.getColumnIndex(Review.COLUMN_TREVIEW)));
                review.setRevRate(cursor.getFloat(cursor.getColumnIndex(Review.COLUMN_REVRATE)));

                reviews.add(review);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        return reviews;
    }

    public List<Review> getReviewByName(String name) {
        List<Review> reviews = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String selectionArgs[] = new String[] {"%" + name + "%" };

        Cursor cursor = db.rawQuery("SELECT  * FROM " + Review.TABLE_NAME + " WHERE "+Review.COLUMN_NAMEMASTER+" LIKE ?",selectionArgs);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Review review = new Review();
                review.setIdR(cursor.getInt(cursor.getColumnIndex(Review.COLUMN_ID)));
                review.setAuthor(cursor.getString(cursor.getColumnIndex(Review.COLUMN_AUTHOR)));
                review.setNameMaster(cursor.getString(cursor.getColumnIndex(Review.COLUMN_NAMEMASTER)));
                review.setSpec(cursor.getString(cursor.getColumnIndex(Review.COLUMN_SPEC)));
                review.setCity( cursor.getString(cursor.getColumnIndex(Review.COLUMN_CITY)));
                review.setStreet(cursor.getString(cursor.getColumnIndex(Review.COLUMN_STREET)));
                review.setDateReview(cursor.getString(cursor.getColumnIndex(Review.COLUMN_DATEREVIEW)));
                review.setTReview(cursor.getString(cursor.getColumnIndex(Review.COLUMN_TREVIEW)));
                review.setRevRate(cursor.getFloat(cursor.getColumnIndex(Review.COLUMN_REVRATE)));

                reviews.add(review);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        return reviews;
    }

    public List<Review> getReviewByCity(String name) {
        List<Review> reviews = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String selectionArgs[] = new String[] {"%" + name + "%" };

        Cursor cursor = db.rawQuery("SELECT  * FROM " + Review.TABLE_NAME + " WHERE "+Review.COLUMN_CITY+" LIKE ?",selectionArgs);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Review review = new Review();
                review.setIdR(cursor.getInt(cursor.getColumnIndex(Review.COLUMN_ID)));
                review.setAuthor(cursor.getString(cursor.getColumnIndex(Review.COLUMN_AUTHOR)));
                review.setNameMaster(cursor.getString(cursor.getColumnIndex(Review.COLUMN_NAMEMASTER)));
                review.setSpec(cursor.getString(cursor.getColumnIndex(Review.COLUMN_SPEC)));
                review.setCity( cursor.getString(cursor.getColumnIndex(Review.COLUMN_CITY)));
                review.setStreet(cursor.getString(cursor.getColumnIndex(Review.COLUMN_STREET)));
                review.setDateReview(cursor.getString(cursor.getColumnIndex(Review.COLUMN_DATEREVIEW)));
                review.setTReview(cursor.getString(cursor.getColumnIndex(Review.COLUMN_TREVIEW)));
                review.setRevRate(cursor.getFloat(cursor.getColumnIndex(Review.COLUMN_REVRATE)));

                reviews.add(review);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        return reviews;
    }

    public void removeTABLE()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Review.TABLE_NAME,null,null);
        db.close();
    }

    public int getReviewsCount() {
        String countQuery = "SELECT  * FROM " + Review.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return count;
    }

    public void deleteReview(int pos) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Review.TABLE_NAME, Review.COLUMN_ID + " = ?",
                new String[]{String.valueOf(pos)});
        db.close();
    }

    public int updateReview(Review review, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Review.COLUMN_AUTHOR,review.getAuthor());
        values.put(Review.COLUMN_NAMEMASTER,review.getNameMaster());
        values.put(Review.COLUMN_SPEC,review.getSpec());
        values.put(Review.COLUMN_CITY,review.getCity());
        values.put(Review.COLUMN_STREET,review.getStreet());
        values.put(Review.COLUMN_DATEREVIEW,review.getDateReview());
        values.put(Review.COLUMN_TREVIEW, review.gettReview());
        values.put(Review.COLUMN_REVRATE,review.getRevRate());

        // updating row
        int i=db.update(Review.TABLE_NAME, values, Review.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return i;
    }

}
