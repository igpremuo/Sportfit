package com.sdm.sportfit.app.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.logic.Diets;
import com.sdm.sportfit.app.logic.Foods;
import com.sdm.sportfit.app.logic.Points;
import com.sdm.sportfit.app.logic.Statistics;
import com.sdm.sportfit.app.logic.Trainings;
import com.sdm.sportfit.app.logic.Users;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jess on 22/03/14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    //Path Database
    private static String DATABASE_PATH = "/data/data/com.sdm.sportfit.app/";

    // Database Name
    private static final String DATABASE_NAME = "sportfit.db";

    // Contacts table name
    private static final String TABLE_FOODS = "Foods";
    private static final String TABLE_DIETS = "Diets";
    private static final String TABLE_POINTS = "Points";
    private static final String TABLE_TRAINING = "Training";
    private static final String TABLE_STATISTICS = "Statistics";
    private static final String TABLE_USERS = "users";

    private final Context myContext;
    private SQLiteDatabase myDataBase;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
        db.execSQL("CREATE TABLE " + TABLE_DIETS + " (nameDiet VARCHAR( 200 ) NOT NULL , idFood INT( 11 ) NOT NULL , typeMeal ENUM(  'Breakfast',  'Brunch',  'Lunch',  'Afternoon snack',  'Dinner' ) NOT NULL , timeMeal TIME NOT NULL ,dateMeal DATE NOT NULL , earnedCalories DOUBLE( 4, 2 ) NOT NULL ,PRIMARY KEY (nameDiet , idFood, typeMeal, dateMeal))");
        db.execSQL("CREATE TABLE " + TABLE_FOODS + " (id int(4) NOT NULL, nameES varchar(200) NOT NULL, nameEN varchar(200) NOT NULL, calories double NOT NULL, proteins double NOT NULL, carbohydrates double NOT NULL, fats double NOT NULL, water double NOT NULL, PRIMARY KEY (id) )");
        db.execSQL("CREATE TABLE " + TABLE_STATISTICS + " `idStatistic` INT( 11 ) NOT NULL AUTO_INCREMENT, idUser INT( 11 ) NOT NULL ,dateStatistics DATE NOT NULL ,weight DOUBLE( 3, 2 ) NOT NULL ,age INT( 3 ) NOT NULL ,sex ENUM('Women',  'Men') NOT NULL ,height DOUBLE( 4, 2 ) NOT NULL ,imc DOUBLE( 2, 2 ) NOT NULL ,water DOUBLE( 2, 2 ) NOT NULL , PRIMARY KEY (  idStatistics ,  idUser ,  dateStatistics )FOREIGN KEY (idUser) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE);");
        db.execSQL("CREATE TABLE " + TABLE_POINTS + " (id int(11) NOT NULL AUTO_INCREMENT, longitude decimal(18,14) NOT NULL, latitude decimal(18,14) NOT NULL, speed double NOT NULL, idTraining int(11) NOT NULL, PRIMARY KEY (id), FOREIGN KEY (idTraining) REFERENCES Training (idTraining) ON DELETE CASCADE ON UPDATE CASCADE);");
        db.execSQL("CREATE TABLE " + TABLE_TRAINING + "  (idTraining int(11) NOT NULL AUTO_INCREMENT, idUser int(11) NOT NULL, typeTrainig enum('Run','Cycling','Walk') NOT NULL, caloriesBurned double NOT NULL, duration double NOT NULL, averageSpeed double NOT NULL, averageRate double NOT NULL, distance double NOT NULL, PRIMARY KEY (idTraining), FOREIGN KEY (idUser) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE);");
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (id int(11) NOT NULL AUTO_INCREMENT, name varchar(250) DEFAULT NULL, email varchar(255) NOT NULL, password_hash text NOT NULL, api_key varchar(32) NOT NULL, created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, locationx decimal(18,14) DEFAULT NULL,  locationy decimal(18,14) DEFAULT NULL, picture varchar(200) DEFAULT NULL, PRIMARY KEY (id), UNIQUE KEY email (email));");
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.createError, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIETS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTICS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_POINTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
            // Create tables again
            onCreate(db);
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.dropError, Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    // Add new User
    public void addUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("id", user.getId());
            values.put("name", user.getName());
            values.put("email", user.getEmail());
            values.put("password_hash", user.getPassword_hash());
            values.put("api_key", user.getApi_key());
            values.put("created_at", String.valueOf(user.getCreated_at()));
            values.put("locationx", user.getLocationx());
            values.put("locationy", user.getLocationy());
            values.put("picture", user.getPicture());

            // Inserting Row
            db.insert(TABLE_USERS, null, values);
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.dropError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }

    }


    // Add new Food
    public void addFood(Foods food) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("id", food.getId());
            values.put("nameES", food.getNameES());
            values.put("nameEN", food.getNameEN());
            values.put("calories", food.getCalories());
            values.put("proteins", food.getProteins());
            values.put("carbohydrates", food.getCarbohydrates());
            values.put("fats", food.getFats());
            values.put("water", food.getWater());
            values.put("category", food.getCategory());

            // Inserting Row
            db.insert(TABLE_FOODS, null, values);
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.dropError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }

    }

    // Add new Training
    public void addTraining(Trainings training) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("idTraining", training.getIdTraining());
            values.put("typeTrainig", training.getTypeTrainig());
            values.put("caloriesBurned", training.getCaloriesBurned());
            values.put("duration", training.getDuration());
            values.put("averageSpeed", training.getAverageSpeed());
            values.put("averageRate", training.getAverageRate());
            values.put("distance", training.getDistance());

            // Inserting Row
            db.insert(TABLE_TRAINING, null, values);
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.dropError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }

    }

    // Add new Points
    public void addPoint(Points point) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("id", point.getId());
            values.put("longitude", point.getLocation().getLongitude());
            values.put("latitude", point.getLocation().getLatitude());
            values.put("speed", point.getSpeed());
            values.put("idTraining", point.getIdTraining());

            // Inserting Row
            db.insert(TABLE_POINTS, null, values);
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.dropError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }
    }

    // Add new Diets comprobar que guarde la fecha como toca
    public void addDiet(Diets diet) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("nameDiet", diet.getNameDiet());
            values.put("idFood", diet.getIdFood());
            values.put("typeMeal", diet.getTypeMeal());
            values.put("timeMeal", diet.getTimeMeal().toString());
            values.put("dateMeal", diet.getDateMeal().toString());
            values.put("earnedCalories", diet.getEarnedCalories());

            // Inserting Row
            db.insert(TABLE_DIETS, null, values);
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.dropError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }
    }

    // Add new Statistics
    public void addStatistics(Statistics statistic) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("idStatistics", statistic.getIdStatistics());
            values.put("idUser", statistic.getIdUser());
            values.put("dateStatistics", statistic.getDateStatistics().toString());
            values.put("weight", statistic.getWeight());
            values.put("age", statistic.getAge());
            values.put("gender", statistic.getGender());
            values.put("height", statistic.getHeight());
            values.put("imc", statistic.getImc());
            values.put("water", statistic.getWater());

            // Inserting Row
            db.insert(TABLE_STATISTICS, null, values);
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.dropError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }
    }

    public boolean checkLogin(String email, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        String password = "";
        try{
            String[] columns = new String[] {"password_hash"};
            String[] args = new String[] {email};
            Cursor c = db.query("users", columns, "email = ? ", args, null, null, null);

            if (c.moveToFirst()) {
                do {
                     password = c.getString(0);
                } while(c.moveToNext());
            }
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.selectError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }

            if (pass.equals(password)) {
                // User password is correct
                return true;
            } else {
                // user password is incorrect
                return false;
            }

    }

   /** public Users getUserId(String appiKey){

    }*/

    // Getting All Scores
    public List<Foods> getAllFoodsByCategory(String category, String language) {
        List<Foods> foodsList = new ArrayList<Foods>();
        Cursor cursor = null;
        Log.v("VERBOSE", "getAllFoods");
        SQLiteDatabase db = this.getWritableDatabase();

        try{

            if ("ES".equals(language)){
                cursor = db.rawQuery("SELECT * FROM " + TABLE_FOODS + " WHERE categoryES = '" + category + "' ORDER BY nameES;", null);
            } else {
                cursor = db.rawQuery("SELECT * FROM " + TABLE_FOODS + " WHERE categoryEN = '" + category + "' ORDER BY nameEN;", null);
            }

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Foods food = new Foods();
                    food.setId(Integer.parseInt(cursor.getString(0)));
                    food.setNameES(cursor.getString(1));
                    food.setNameEN(cursor.getString(2));
                    food.setCalories(cursor.getDouble(3));
                    food.setProteins(cursor.getDouble(4));
                    food.setCarbohydrates(cursor.getDouble(5));
                    food.setFats(cursor.getDouble(6));
                    food.setWater(cursor.getDouble(7));
                    food.setCategory(cursor.getString(8));

                    // Adding contact to list
                    foodsList.add(food);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.selectError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }

        // return contact list
        return foodsList;
    }

    // Delete Meal
    public void deleteMeal(String nameDiet, String typeMeal, int idFood, String dateMeal) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat formatedText = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = (Date) formatedText.parse(dateMeal);
            db.execSQL("DELETE FROM " + TABLE_DIETS  + " WHERE nameDiet = '" + nameDiet + "' AND typeMeal = '" + typeMeal + "' AND idFood = " + idFood + " AND dateMeal = " + date + ";");
        } catch (ParseException ex) {
            Toast toast = Toast.makeText(this.myContext, R.string.dateError, Toast.LENGTH_SHORT);
            toast.show();
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.deleteError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }
    }

    // Delete all STATISTICS
    public void deleteAllStatistics() {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLE_STATISTICS, null, null);
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.deleteError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }
    }

    // Delete all DIETS
    public void deleteAllDiets() {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLE_DIETS, null, null);
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.deleteError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }
    }

    // Delete all Foods
    public void deleteAllFoods() {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLE_FOODS, null, null);
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.deleteError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }
    }

    // Delete all Points
    public void deleteAllPoints() {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLE_POINTS, null, null);
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.deleteError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }
    }

    // Delete all TRAINING
    public void deleteAllTraining() {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLE_TRAINING, null, null);
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.deleteError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }
    }

    // Delete all Users
    public void deleteAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLE_USERS, null, null);
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.deleteError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }
    }
}
