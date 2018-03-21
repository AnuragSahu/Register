package com.example.anurag.register;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBAdapter extends SQLiteOpenHelper {
    @SuppressLint("SdCardPath")
    public static final String DATABASE_NAME = "QuerReg";
    //public static final String TAG = "SQLiteExample_DATA_SOURCE";
    private static DBAdapter mInstance = null;
    private final Context context;
    private SQLiteDatabase db;

    String seq_str;

    public DBAdapter(Context ctx) {
        super(ctx, DATABASE_NAME, null, 1);
        this.context = ctx;
    }

    public static DBAdapter getInstance(Context ctx) {
        /**
         * use the application context as suggested by CommonsWare. this will
         * ensure that you dont accidentally leak an Activitys context (see this
         * article for more information:
         * http://developer.android.com/resources/articles
         * /avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new DBAdapter(ctx.getApplicationContext());
            try {
                mInstance.createDataBase();
                mInstance.openDataBase();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return mInstance;
    }

    private boolean checkDataBase() {
        File dbFile = new File(context.getFilesDir() + "/" + DATABASE_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException {
        try {
            InputStream input;
            String outPutFileName;
            File sd = Environment.getExternalStorageDirectory();
            File locDB = new File(sd.getPath(), "/");
            File locDstDB = new File(context.getFilesDir().getPath(), "/");
            File currentDB = new File(locDstDB, DATABASE_NAME);
            File backUpDB = new File(locDB, "QuerReg_back");
            String backupDBPath = sd.getPath() + "/" + "QuerReg_back";
            if (backUpDB.exists()) {
                @SuppressWarnings("resource")
                FileChannel src = new FileInputStream(backUpDB).getChannel();
                @SuppressWarnings("resource")
                FileChannel dst = new FileOutputStream(currentDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            } else {
                input = context.getAssets().open(DATABASE_NAME);
                outPutFileName = context.getFilesDir().getPath() + "/"
                        + DATABASE_NAME;
                OutputStream output = new FileOutputStream(outPutFileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
                output.flush();
                output.close();
                input.close();
            }
        } catch (IOException e) {
            Log.v("error", e.toString());
        }
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            // do nothing - database already exist
        } else {
            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    public void openDataBase() throws SQLException, IOException {
        String fullDbPath = context.getFilesDir().getPath() + "/"
                + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(fullDbPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    public SQLiteDatabase getDB() {
        try {
            openDataBase();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return db;
    }

    /******************************
     * closes the database
     ******************************/
    public synchronized void close() {
        if (db != null)
            db.close();
        super.close();
        mInstance = null;
    }

    public synchronized void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    public synchronized void onUpgrade(SQLiteDatabase db, int oldVersion,
                                       int newVersion) {
        // TODO Auto-generated method stub

    }

    public long insertIntoRegistrationDetails(String name,String nickName, String MobileNo, String Collage)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Nme",name);
        contentValues.put("NikNme",nickName);
        contentValues.put("MobileNo",MobileNo);
        contentValues.put("CollageName",Collage);
        long rowInserted = db.insert("RegisterTable",null,contentValues);
        return rowInserted;
    }

    public boolean checkMobileNoExist(String moNo)
    {
        Cursor cursor = db.rawQuery("select MobileNo from RegisterTable where MobileNo = '"+moNo+"'",null);
        if (cursor.getCount()>0)
        {
            return true;
        }
        return false;
    }

    public int countRegDetails()
    {
        Cursor cursor = db.rawQuery("select MobileNo from RegisterTable",null);
        return cursor.getCount();
    }

    public List<RegisterTable> getAllRegistrationDetails(String moNo)
    {
        List<RegisterTable> listRegDetails = new ArrayList<>();
        String sqlQuery = "select * from RegisterTable";
        Cursor cursor = db.rawQuery(sqlQuery,null);
        if (cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                RegisterTable registerTable = new RegisterTable();
                registerTable.setName(cursor.getString(cursor.getColumnIndex("Nme")));
                registerTable.setMobileno(cursor.getString(cursor.getColumnIndex("MobileNo")));
                registerTable.setNickName(cursor.getString(cursor.getColumnIndex("NikName")));
                registerTable.setCollage(cursor.getString(cursor.getColumnIndex("CollageName")));
                listRegDetails.add(registerTable);
            }
        }
        return listRegDetails;
    }

    public List<String> getListOf(String LstOf)
    {
        List<String> list = new ArrayList<>();
        String qry = "select "+LstOf+" from RegisterTable";
        Cursor cursor = db.rawQuery(qry,null);
        if(cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                String item = cursor.getString(cursor.getColumnIndex(LstOf));
                list.add(item);
            }
        }
        return list;
    }

    public RegisterTable getDetOf(String nkName)
    {
        RegisterTable list = new RegisterTable();
        String qry = "select * from RegisterTable where NikNme = '"+nkName+"'";
        Cursor cursor = db.rawQuery(qry,null);
        if(cursor.moveToNext()) {
//            Toast.makeText(context, cursor.getString(cursor.getColumnIndex("Nme")), Toast.LENGTH_SHORT).show();
            list.setName(cursor.getString(cursor.getColumnIndex("Nme")));
            list.setMobileno(cursor.getString(cursor.getColumnIndex("MobileNo")));
            list.setNickName(nkName);
            list.setCollage(cursor.getString(cursor.getColumnIndex("CollageName")));
        }
        return list;
    }

    public String getMobileNumber(String nkName)
    {
        String qry = "select * from RegisterTable where NikName = '"+nkName+"'";
        Cursor cursor = db.rawQuery(qry,null);
        Toast.makeText(context, "Cool Baby...", Toast.LENGTH_SHORT).show();
//        if(cursor.moveToNext()) return cursor.getString(cursor.getColumnIndex("MobileNo"));
//        else
            return "";
    }


}
