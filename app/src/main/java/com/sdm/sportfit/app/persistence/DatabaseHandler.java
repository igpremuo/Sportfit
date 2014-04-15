package com.sdm.sportfit.app.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.logic.Diets;
import com.sdm.sportfit.app.logic.Foods;
import com.sdm.sportfit.app.logic.Points;
import com.sdm.sportfit.app.logic.Statistics;
import com.sdm.sportfit.app.logic.Trainings;
import com.sdm.sportfit.app.logic.Users;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private static DatabaseHandler sInstance;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;

    }

    public static DatabaseHandler getInstance(Context context){
        if(sInstance == null){
            sInstance = new DatabaseHandler(context.getApplicationContext());
            }
        return sInstance;
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.v("VERBOSE", "Borro las tablas");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIETS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTICS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_POINTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            Log.v("VERBOSE", " Creo las tablas");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_DIETS + " (nameDiet VARCHAR( 200 ) NOT NULL , idFood INT( 11 ) NOT NULL , typeMeal VARCHAR( 50 ) NOT NULL , timeMeal DATE NOT NULL ,dateMeal DATE NOT NULL , earnedCalories DOUBLE( 4,2 ) NOT NULL, quantity DOUBLE( 6,2 ), PRIMARY KEY (nameDiet , idFood, typeMeal, dateMeal))");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_FOODS + " (id int(4)  PRIMARY KEY, nameES varchar(200) NOT NULL, nameEN varchar(200) NOT NULL, categoryES varchar(200) NOT NULL, categoryEN varchar(200) NOT NULL,calories double NOT NULL, proteins double NOT NULL, carbohydrates double NOT NULL, fats double NOT NULL, water double NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_STATISTICS + " (idStatistic int(5), idUser INT( 11 ) NOT NULL ,dateStatistics DATE NOT NULL ,weight DOUBLE( 3, 2 ) NOT NULL ,age INT( 3 ) NOT NULL ,sex VARCHAR(50) NOT NULL ,height DOUBLE( 4, 2 ) NOT NULL ,imc DOUBLE( 2, 2 ) NOT NULL ,water DOUBLE( 2, 2 ) NOT NULL , PRIMARY KEY (  idStatistic ,  idUser ,  dateStatistics ), FOREIGN KEY (idUser) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_POINTS + " (id INTEGER  PRIMARY KEY AUTOINCREMENT, longitude decimal(18,14) NOT NULL, latitude decimal(18,14) NOT NULL, speed double NOT NULL, idTraining INTEGER NOT NULL, FOREIGN KEY (idTraining) REFERENCES Training (idTraining) ON DELETE CASCADE ON UPDATE CASCADE);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TRAINING + "  (idTraining INTEGER PRIMARY KEY, idUser INTEGER, typeTraining VARCHAR (50) NOT NULL, caloriesBurned double NOT NULL, duration long NOT NULL, averageSpeed double NOT NULL, averageRate double NOT NULL, distance double NOT NULL, dateTraining DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (idUser) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (id INTEGER  PRIMARY KEY, name varchar(250) DEFAULT NULL, email varchar(255) UNIQUE NOT NULL, password text NOT NULL, api_key varchar(32) NOT NULL, created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, locationx decimal(18,14) DEFAULT NULL,  locationy decimal(18,14) DEFAULT NULL, picture varchar(200) DEFAULT NULL);");
            db.execSQL("INSERT INTO Diets (nameDiet, idFood, typeMeal, timeMeal, dateMeal, earnedCalories, quantity) VALUES ('Dieta genérica 1', 681,  'Desayuno',  '09:00',  '2014-04-14',  75.46, 200.00);");
            db.execSQL("INSERT INTO Diets (nameDiet, idFood, typeMeal, timeMeal, dateMeal, earnedCalories,  quantity) VALUES ('Dieta genérica 1', 774, 'Desayuno', '09:00', '2014-04-14', 240.19, 100.00);");
            db.execSQL("INSERT INTO Diets (nameDiet, idFood, typeMeal, timeMeal, dateMeal, earnedCalories,  quantity) VALUES ('Dieta genérica 1', 917,  'Almuerzo',  '11:00',  '2014-04-14',  134.51,  100.00);");
            db.execSQL("INSERT INTO Diets (nameDiet, idFood, typeMeal, timeMeal, dateMeal, earnedCalories,  quantity) VALUES ('Dieta genérica 1', 994,  'Comida',  '14:00',  '2014-04-14',  104.32,  100.00);");
            db.execSQL("INSERT INTO Diets (nameDiet, idFood, typeMeal, timeMeal, dateMeal, earnedCalories,  quantity) VALUES ('Dieta genérica 1', 2662,  'Merienda',  '19:00',  '2014-04-13',  394.51,  100.00);");
            db.execSQL("INSERT INTO Diets (nameDiet, idFood, typeMeal, timeMeal, dateMeal, earnedCalories,  quantity) VALUES ('Dieta genérica 1', 2387,  'Merienda',  '19:00',  '2014-04-14',  27.16,  100.00);");
            db.execSQL("INSERT INTO Diets (nameDiet, idFood, typeMeal, timeMeal, dateMeal, earnedCalories,  quantity) VALUES ('Dieta genérica 1', 2387,  'Cena',  '23:30',  '2014-04-14',  27.16,  100.00);");
          //  db.execSQL("INSERT INTO users (name, email, password, api_key) VALUES ('paco','paco@gmail.com',  'paco', '2244a06077f75b20c04defe1e6be34e1');");
            Log.v("VERBOSE", "Tablas creadas");
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.createError, Toast.LENGTH_SHORT);
            toast.show();
            Log.v("VERBOSE", "Fallo al crear las tablas");
            sqlError.getStackTrace();
        }
        Log.v("VERBOSE", "Voy a entrar a crear la tablas de diet");
       // insertAllGenericDiets();
        Log.v("VERBOSE", "Salgo de crear las tablas diet");
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
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.dropError, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public boolean existsDataInTable(String nameTable){
        SQLiteDatabase db = this.getReadableDatabase();
        boolean exists = false;
        Cursor c = db.rawQuery("SELECT * FROM " + nameTable + ";", null);
        if (c.getCount() > 0){
            exists = true;
        }
        db.close();
        return exists;
    }

    // Add new User
    public void addUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("id", user.getId());
            values.put("name", user.getName());
            values.put("email", user.getEmail());
            values.put("password", user.getPassword());
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

    private void insertAllGenericDiets(){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Log.v("VERBOSE", "Creando dietas");
        db.execSQL("INSERT INTO Diets (nameDiet, idFood, typeMeal, timeMeal, dateMeal, earnedCalories, quantity) VALUES ('Dieta genérica 1', 681,  'Desayuno',  '09:00',  '2014-04-14',  75.46, 200.00);");
        db.execSQL("INSERT INTO Diets (nameDiet, idFood, typeMeal, timeMeal, dateMeal, earnedCalories,  quantity) VALUES ('Dieta genérica 1', 774, 'Desayuno', '09:00', '2014-04-14', 240.19, 100.00);");
        db.execSQL("INSERT INTO Diets (nameDiet, idFood, typeMeal, timeMeal, dateMeal, earnedCalories,  quantity) VALUES ('Dieta genérica 1', 917,  'Almuerzo',  '11:00',  '2014-04-14',  134.51,  100.00);");
        db.execSQL("INSERT INTO Diets (nameDiet, idFood, typeMeal, timeMeal, dateMeal, earnedCalories,  quantity) VALUES ('Dieta genérica 1', 994,  'Comida',  '14:00',  '2014-04-14',  104.32,  100.00);");
        db.execSQL("INSERT INTO Diets (nameDiet, idFood, typeMeal, timeMeal, dateMeal, earnedCalories,  quantity) VALUES ('Dieta genérica 1', 2662,  'Merienda',  '14:00',  '2014-04-14',  394.51,  100.00);");
        db.execSQL("INSERT INTO Diets (nameDiet, idFood, typeMeal, timeMeal, dateMeal, earnedCalories,  quantity) VALUES ('Dieta genérica 1', 2387,  'Cena',  '23:30',  '2014-04-14',  27.16,  100.00);");
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.insertError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }
    }


    // Add new Food
    public void addFood(Foods food) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Log.v("VERBOSE", "isentando alimentos");
            ContentValues values = new ContentValues();
            values.put("id", food.getId());
            values.put("nameES", food.getNameES());
            values.put("nameEN", food.getNameEN());
            values.put("categoryES", food.getCategoryES());
            values.put("categoryEN", food.getCategoryEN());
            values.put("calories", food.getCalories());
            values.put("proteins", food.getProteins());
            values.put("carbohydrates", food.getCarbohydrates());
            values.put("fats", food.getFats());
            values.put("water", food.getWater());


            // Inserting Row
            db.insert(TABLE_FOODS, null, values);
        } catch (SQLiteException sqlError){
            sqlError.getStackTrace();
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
            values.put("idUser", training.getIdUser());
            values.put("typeTraining", training.getTypeTraining());
            values.put("caloriesBurned", training.getCaloriesBurned());
            values.put("duration", training.getDuration());
            values.put("averageSpeed", training.getAverageSpeed());
            values.put("averageRate", training.getAverageRate());
            values.put("distance", training.getDistance());
            values.put("dateTraining", training.getDate());

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
            //aqui insertar lo otros dos datos


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

    public int checkLogin(String email, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor c;
            c = db.rawQuery("SELECT id FROM " + TABLE_USERS + " WHERE email = '" + email + "' AND password = '"+ pass +"';", null);

            if (c.moveToFirst()) {
                return c.getInt(0);
            }
            else {
                return 0;
            }

        } catch (SQLiteException sqlError){
            sqlError.getStackTrace();
            Toast toast = Toast.makeText(this.myContext, R.string.selectError, Toast.LENGTH_SHORT);
            toast.show();
        }
        return 0;
    }

    /**
     * Recoge cada comida
     * @param dateDiet
     * @param nameDiet
     * @return
     */
    public String getDietByDate(String dateDiet, String nameDiet){
        Cursor c = null;
        Diets diet = null;
        DateFormat formatedDate = new SimpleDateFormat("HH:mm");
        Date limit = null;
        try {
            limit = formatedDate.parse("23:59");
        }catch (ParseException e) {
            e.printStackTrace();
        }
        Long actual = Calendar.getInstance().getTimeInMillis();
        String typeMeal = "";
        if ("es".equals(Locale.getDefault().getLanguage())) {
            typeMeal = "Almuerzo";
        }else {
            typeMeal = "Dinner";
        }
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            c = db.rawQuery("SELECT timeMeal, typeMeal FROM "+ TABLE_DIETS +" WHERE dateMeal = '" + dateDiet + "' AND nameDiet = '" + nameDiet + "' ORDER BY timeMeal DESC;", null);
            Log.v("VERBOSE", "Resultados de la consulta timeMeal: "+c.getCount());
            if (c.moveToFirst()) {
                do {
                    Date timeMeal = null;
                    timeMeal= formatedDate.parse(c.getString(0));
                    Log.v("VERBOSE", "Valor de tiempo actual :"+ System.currentTimeMillis()/30000);
                    Log.v("VERBOSE", "Valor de tiempo limit :"+ limit.getTime());
                    Log.v("VERBOSE", "Valor de tiempo recogido :"+ timeMeal.getTime());
                    if(timeMeal.getTime() >= System.currentTimeMillis()/30000 && timeMeal.getTime() <= limit.getTime() ){
                        Log.v("VERBOSE","entro al if del metodo");
                        limit = timeMeal;
                        typeMeal = c.getString(1);
                    }
                } while (c.moveToNext());
            }
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.selectError, Toast.LENGTH_SHORT);
            toast.show();
            sqlError.getStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return typeMeal;
    }

    /**
     * Recoge cada comida
     * @param dateDiet
     * @param nameDiet
     * @param typeMeal
     * @return
     */
    public Diets getDietByMeal(String dateDiet, String nameDiet, String typeMeal){
        Log.v("VERBOSE", " Dentro del handlerDb");
        Cursor c = null;
        Diets diet = null;
        Log.v("VERBOSE", "typeMeal recibido : "+typeMeal);
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            Log.v("VERBOSE", "Realizando la consulta " + "SELECT a.nameDiet, a.idFood, a.typeMeal, a.timeMeal, a.dateMeal, a.earnedCalories, a.quantity, b.nameES, b.calories, b.categoryES, b.proteins, b.carbohydrates, b.fats, b.water  FROM " + TABLE_FOODS + " b, "+ TABLE_DIETS +" a WHERE a.idFood = b.id AND dateMeal = '" + dateDiet + "' AND a.nameDiet = '" + nameDiet + "' AND a.typeMeal = '" + typeMeal + "';" );
            //R.string.language Locale.getDefault().getLanguage();
            if ("es".equals(Locale.getDefault().getLanguage())){
                c = db.rawQuery("SELECT a.nameDiet, a.idFood, a.typeMeal, a.timeMeal, a.dateMeal, a.earnedCalories, a.quantity, b.nameES, b.calories, b.categoryES, b.proteins, b.carbohydrates, b.fats, b.water  FROM " + TABLE_FOODS + " b, "+ TABLE_DIETS +" a WHERE a.idFood = b.id AND dateMeal = '" + dateDiet + "' AND a.nameDiet = '" + nameDiet + "' AND a.typeMeal = '" + typeMeal + "';", null);
            } else {
                c = db.rawQuery("SELECT a.nameDiet, a.idFood, a.typeMeal, a.timeMeal, a.dateMeal, a.earnedCalories, a.quantity, b.nameEN, b.calories, b.categoryEN, b.proteins, b.carbohydrates, b.fats, b.water FROM " + TABLE_FOODS + " b, "+ TABLE_DIETS +" a WHERE a.idFood = b.id AND dateMeal = '" + dateDiet + "' AND a.nameDiet = '" + nameDiet + "'AND a.typeMeal = '" + typeMeal + "';", null);
            }
            Log.v("VERBOSE", "Consulta realizada hay datos" + c.moveToFirst());
            if (c.moveToFirst()) {
                diet = new Diets();
                diet.setNameDiet(c.getString(0));
                diet.setIdFood(Integer.parseInt(c.getString(1)));
                diet.setTypeMeal(c.getString(2));
                Log.v("VERBOSE", "timeMeal de getDietByMeal" +c.getString(3));
                diet.setTimeMeal(c.getString(3));
                diet.setDateMeal(c.getString(4));
                // looping through all rows and adding to list
                do {
                    Foods food = new Foods();
                    food.setId(Integer.parseInt(c.getString(1)));
                    food.setEarnedCalories(c.getDouble(5));
                    food.setQuantity(c.getDouble(6));
                    if ("es".equals(Locale.getDefault().getLanguage())){
                        food.setNameES(c.getString(7));
                        food.setCategoryES(c.getString(9));
                    } else {
                        food.setNameEN(c.getString(7));
                        food.setCategoryEN(c.getString(9));
                    }
                    food.setCalories(c.getDouble(8));
                    food.setProteins(c.getDouble(10));
                    food.setCarbohydrates(c.getDouble(11));
                    food.setFats(c.getDouble(12));
                    food.setWater(c.getDouble(13));
                    // Adding contact to list
                    diet.getListFoods().add(food);
                } while (c.moveToNext());
            }
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.selectError, Toast.LENGTH_SHORT);
            toast.show();
            sqlError.getStackTrace();
        } finally {
            db.close();
        }

        // return contact list
        return diet;
    }


    // Getting All Foods By Category
    public List<Foods> getAllFoodsByCategory(String category) {
        List<Foods> foodsList = new ArrayList<Foods>();
        Cursor cursor = null;
        Log.v("VERBOSE", "getAllFoods");
        SQLiteDatabase db = this.getWritableDatabase();

        try{

            if ("es".equals(Locale.getDefault().getLanguage())){
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
                    food.setCategoryES(cursor.getString(3));
                    food.setCategoryEN(cursor.getString(4));
                    food.setCalories(cursor.getDouble(5));
                    food.setProteins(cursor.getDouble(6));
                    food.setCarbohydrates(cursor.getDouble(7));
                    food.setFats(cursor.getDouble(8));
                    food.setWater(cursor.getDouble(9));


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

    // Getting Training with your Points
    public List<Trainings> getTraining() {
        List<Trainings> trainingsList = new ArrayList<Trainings>();
        Cursor cursor = null;
        Log.v("VERBOSE", "getAllTraining");
        SQLiteDatabase db = this.getWritableDatabase();
        int positionPoint = 0;
        try{

            cursor = db.rawQuery("SELECT t.idTraining, t.idUser, t.typeTraining, t.caloriesBurned, t.duration, t.averageSpeed, t.averageRate, t.distance, t.dateTraining, p.id, p.longitude, p.latitude, p.speed FROM " + TABLE_TRAINING + " t, " + TABLE_POINTS + "p WHERE t.idTraining = p.idTraining;", null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Trainings training = new Trainings();
                    Points point = new Points();
                    Location location = null;
                    training.setIdTraining(Integer.parseInt(cursor.getString(0)));
                    training.setIdUser(Integer.parseInt(cursor.getString(1)));
                    training.setTypeTraining(cursor.getString(2));
                    training.setCaloriesBurned(cursor.getDouble(3));
                    training.setDuration(cursor.getLong(4));
                    training.setAverageSpeed(cursor.getDouble(5));
                    training.setAverageRate(cursor.getDouble(6));
                    training.setDistance(cursor.getDouble(7));
                    Log.v("VERBOSE", "valor dateTraining " + cursor.getString(8));
                    training.setDate(cursor.getString(8));
                    point.setId(Integer.parseInt(cursor.getString(9)));
                    location.setLongitude(cursor.getDouble(10));
                    location.setLatitude(cursor.getDouble(11));
                    point.setLocation(location);
                    point.setSpeed(cursor.getDouble(12));
                    training.add(positionPoint, point);

                    // Adding contact to list
                    trainingsList.add(training);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.selectError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }

        // return contact list
        return trainingsList;
    }


    // Getting All Training
    public List<Trainings> getAllTrainings() {
        List<Trainings> trainingsList = new ArrayList<Trainings>();
        Cursor cursor = null;
        Log.v("VERBOSE", "getAllTraining");
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            Log.v("VERBOSE", "valor de la consulta: " + "SELECT * FROM " + TABLE_TRAINING + "; " );
            cursor = db.rawQuery("SELECT * FROM " + TABLE_TRAINING + ";", null);
            Log.v("VERBOSE", "valor de la consulta: " +   cursor.getCount());
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Trainings training = new Trainings();
                    training.setIdTraining(Integer.parseInt(cursor.getString(0)));
                    training.setTypeTraining(cursor.getString(1));
                    training.setCaloriesBurned(cursor.getDouble(2));
                    training.setDuration(cursor.getLong(3));
                    training.setAverageSpeed(cursor.getDouble(4));
                    training.setAverageRate(cursor.getDouble(5));
                    training.setDistance(cursor.getDouble(6));
                    Log.v("VERBOSE", "valor dateTraining " +  cursor.getString(7));
                    training.setDate(cursor.getString(7));

                    // Adding contact to list
                    trainingsList.add(training);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.selectError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }

        // return contact list
        return trainingsList;
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
