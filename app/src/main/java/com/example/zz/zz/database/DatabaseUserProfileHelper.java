package com.example.zz.zz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zz.zz.model.UserProfile_DB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 02.05.2018.
 */

public class DatabaseUserProfileHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

    private static final String DATABASE_NAME = "userprofile_db";

    public DatabaseUserProfileHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserProfile_DB.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + UserProfile_DB.TABLE_NAME);
        onCreate(db);
    }

    public void insertUser(UserProfile_DB userProfile) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(UserProfile_DB.COLUMN_ID,userProfile.getIdU());
        values.put(UserProfile_DB.COLUMN_FIRSTNAME,userProfile.getFirstname());
        values.put(UserProfile_DB.COLUMN_LASTNAME,userProfile.getLastname());
        values.put(UserProfile_DB.COLUMN_EMAIL,userProfile.getEmail());
        values.put(UserProfile_DB.COLUMN_UPROFILE,userProfile.getUprofile());
        // insert row
        db.insert(UserProfile_DB.TABLE_NAME, null, values);

        // close db connection
        db.close();

    }



    public UserProfile_DB getUser(String id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(UserProfile_DB.TABLE_NAME,
                new String[]{UserProfile_DB.COLUMN_ID, UserProfile_DB.COLUMN_FIRSTNAME, UserProfile_DB.COLUMN_LASTNAME, UserProfile_DB.COLUMN_EMAIL, UserProfile_DB.COLUMN_UPROFILE},
                UserProfile_DB.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        UserProfile_DB user = new UserProfile_DB(
                cursor.getInt(cursor.getColumnIndex(UserProfile_DB.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(UserProfile_DB.COLUMN_FIRSTNAME)),
                cursor.getString(cursor.getColumnIndex(UserProfile_DB.COLUMN_LASTNAME)),
                cursor.getString(cursor.getColumnIndex(UserProfile_DB.COLUMN_EMAIL)),
                cursor.getInt(cursor.getColumnIndex(UserProfile_DB.COLUMN_UPROFILE)));
        // close the db connection
        cursor.close();
        return user;
    }


    public UserProfile_DB getUserById(String email) {
        UserProfile_DB user = new UserProfile_DB();

        SQLiteDatabase db = this.getWritableDatabase();

        String selectionArgs[] = new String[] {"%" + email + "%" };

        Cursor cursor = db.rawQuery("SELECT  * FROM " + UserProfile_DB.TABLE_NAME + " WHERE "+UserProfile_DB.COLUMN_EMAIL+" LIKE ?",selectionArgs);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                user.setIdU(cursor.getInt(cursor.getColumnIndex(UserProfile_DB.COLUMN_ID)));
                user.setFirstname(cursor.getString(cursor.getColumnIndex(UserProfile_DB.COLUMN_FIRSTNAME)));
                user.setLastname(cursor.getString(cursor.getColumnIndex(UserProfile_DB.COLUMN_LASTNAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(UserProfile_DB.COLUMN_EMAIL)));
                user.setUprofile(cursor.getInt(cursor.getColumnIndex(UserProfile_DB.COLUMN_UPROFILE)));

            } while (cursor.moveToNext());

            db.close();

            return  user;
        }

        db.close();
        // close db connection
        return null;
    }

    public UserProfile_DB getUserById(int id) {
        UserProfile_DB user = new UserProfile_DB();

        SQLiteDatabase db = this.getWritableDatabase();

        String selectionArgs[] = new String[] {"%" + id + "%" };

        Cursor cursor = db.rawQuery("SELECT  * FROM " + UserProfile_DB.TABLE_NAME + " WHERE "+UserProfile_DB.COLUMN_ID+" LIKE ?",selectionArgs);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                user.setIdU(cursor.getInt(cursor.getColumnIndex(UserProfile_DB.COLUMN_ID)));
                user.setFirstname(cursor.getString(cursor.getColumnIndex(UserProfile_DB.COLUMN_FIRSTNAME)));
                user.setLastname(cursor.getString(cursor.getColumnIndex(UserProfile_DB.COLUMN_LASTNAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(UserProfile_DB.COLUMN_EMAIL)));
                user.setUprofile(cursor.getInt(cursor.getColumnIndex(UserProfile_DB.COLUMN_UPROFILE)));

            } while (cursor.moveToNext());

            db.close();

            return  user;
        }

        db.close();
        // close db connection
        return null;
    }

    public List<UserProfile_DB> getAllUserrs() {
        List<UserProfile_DB> users = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + UserProfile_DB.TABLE_NAME ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserProfile_DB user = new UserProfile_DB();
                user.setIdU(cursor.getInt(cursor.getColumnIndex(UserProfile_DB.COLUMN_ID)));
                user.setFirstname(cursor.getString(cursor.getColumnIndex(UserProfile_DB.COLUMN_FIRSTNAME)));
                user.setLastname(cursor.getString(cursor.getColumnIndex(UserProfile_DB.COLUMN_LASTNAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(UserProfile_DB.COLUMN_EMAIL)));
                user.setUprofile(cursor.getInt(cursor.getColumnIndex(UserProfile_DB.COLUMN_UPROFILE)));
                users.add(user);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        return users;
    }

    public void removeTABLE()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UserProfile_DB.TABLE_NAME,null,null);
        db.close();
    }


    public void updateUser(UserProfile_DB userProfile, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UserProfile_DB.COLUMN_ID,userProfile.getIdU());
        values.put(UserProfile_DB.COLUMN_FIRSTNAME,userProfile.getFirstname());
        values.put(UserProfile_DB.COLUMN_LASTNAME,userProfile.getLastname());
        values.put(UserProfile_DB.COLUMN_EMAIL,userProfile.getEmail());
        values.put(UserProfile_DB.COLUMN_UPROFILE,userProfile.getUprofile());

        // updating row
        db.update(UserProfile_DB.TABLE_NAME, values, UserProfile_DB.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }


}
