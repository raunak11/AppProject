package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.User;
import Util.Constants;

public class DataBaseHandler extends SQLiteOpenHelper {

    Context ctx;
    public DataBaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx= context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create= "CREATE TABLE "+ Constants.Table_Name +"(" +
                Constants.KEY_ID+" INTEGER PRIMARY KEY,"+
                Constants.KEY_Name+" TEXT,"+
                Constants.KEY_Email+" TEXT,"+
                Constants.KEY_Pwd+" LONG);";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.Table_Name);
        onCreate(db);
    }
    // addGrocery
    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.KEY_Name,user.getName());
        contentValues.put(Constants.KEY_Email,user.getEmail());
        contentValues.put(Constants.KEY_Pwd,user.getPassword());

        db.insert(Constants.Table_Name,null,contentValues);
        db.close();
    }

    public User getUser(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constants.Table_Name,new String[]{Constants.KEY_ID,Constants.KEY_Name, Constants.KEY_Email,
                Constants.KEY_Pwd},Constants.KEY_ID +"=?",new String[]{String.valueOf(id)},null,null,null,null);

        User user = new User();
        user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
        user.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_Name)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(Constants.KEY_Email)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(Constants.KEY_Pwd)));

        return user;
    }
    // get a list of grocery
    public List<User> getAllUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<User> users = new ArrayList<>();

        try{
            Cursor cursor = db.query(Constants.Table_Name, new String[]{Constants.KEY_ID,Constants.KEY_Name, Constants.KEY_Email,
                    Constants.KEY_Pwd},null,null,null,null,null);
            if(cursor.moveToFirst()){
                do{
                    User user = new User();
                    user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                    user.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_Name)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(Constants.KEY_Email)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(Constants.KEY_Pwd)));

                    users.add(user);
                }
                while(cursor.moveToNext());
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return users;
    }
/*    public int update(Grocery grocery){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.KEY_GROCERY_ITEM,grocery.getGroceryName());
        contentValues.put(Constants.KEY_QUANTITY,grocery.getGroceryQty());
        contentValues.put(Constants.KEY_DATE_ADDED,grocery.getDateOfAdd());

        return db.update(Constants.Table_Name,contentValues,Constants.KEY_ID + "=?",new String[]{String.valueOf(grocery.getId())});
    }*/

    public void deleteUser(int id){
        SQLiteDatabase db =this.getWritableDatabase();
        db.delete(Constants.Table_Name,Constants.KEY_ID+ "=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public boolean check(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            Cursor cursor = db.query(Constants.Table_Name, new String[]{Constants.KEY_ID,Constants.KEY_Name, Constants.KEY_Email,
                    Constants.KEY_Pwd},null,null,null,null,null);
            if(cursor.moveToFirst()){
                do{
                    User user1 = new User();
                    String name = cursor.getString(cursor.getColumnIndex(Constants.KEY_Name));
                    String email = cursor.getString(cursor.getColumnIndex(Constants.KEY_Email));
                    String pwd = cursor.getString(cursor.getColumnIndex(Constants.KEY_Pwd));

                    if( (name.equals(user.getName()) || email.equals(user.getEmail())) && (pwd.equals(user.getPassword())) ){
                        int id = cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID));
                        user.setId(id);
                        user.setName(name);
                        user.setEmail(email);
                        user.setPassword(pwd);
                        return true;
                    }
                }
                while(cursor.moveToNext());
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public int getCount(){
        String count = "SELECT * FROM "+Constants.Table_Name;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(count,null);

        return cursor.getCount();
    }
}

