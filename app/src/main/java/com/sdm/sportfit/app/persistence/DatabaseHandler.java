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
import com.sdm.sportfit.app.logic.Diet;
import com.sdm.sportfit.app.logic.Diets;
import com.sdm.sportfit.app.logic.Foods;
import com.sdm.sportfit.app.logic.Points;
import com.sdm.sportfit.app.logic.Statistics;
import com.sdm.sportfit.app.logic.Trainings;
import com.sdm.sportfit.app.logic.Users;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private static final String TABLE_DIET = "Diet";
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
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_DIET);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIETS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTICS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_POINTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            Log.v("VERBOSE", " Creo las tablas");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_DIET + " (idDiet INTEGER PRIMARY KEY AUTOINCREMENT, nameDiet VARCHAR(20) NOT NULL , description VARCHAR(200) NOT NULL, totalCalories DOUBLE(8,2) NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_DIETS + " (idDiet INT(9) NOT NULL , idFood INT(11) NOT NULL , typeMeal VARCHAR( 50 ) NOT NULL , timeMeal VARCHAR(30) NOT NULL , dateMeal INTEGER(2) NOT NULL, earnedCalories DOUBLE( 4,2 ) NOT NULL, quantity int( 4 ), PRIMARY KEY (idDiet , idFood, typeMeal, dateMeal), FOREIGN KEY (idDiet) REFERENCES Diet (idDiet) ON DELETE CASCADE ON UPDATE CASCADE);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_FOODS + " (id INT(4) PRIMARY KEY, nameES VARCHAR(200) NOT NULL, nameEN VARCHAR(200) NOT NULL, categoryES VARCHAR(200) NOT NULL, categoryEN VARCHAR(200) NOT NULL,calories DOUBLE NOT NULL, proteins DOUBLE NOT NULL, carbohydrates double NOT NULL, fats double NOT NULL, water double NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_STATISTICS + " (idStatistic INTEGER PRIMARY KEY AUTOINCREMENT, idUser INT( 11 ) NOT NULL ,dateStatistics timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,weight DOUBLE( 3, 2 ) NOT NULL ,age INT( 3 ) NOT NULL, sex VARCHAR(50) NOT NULL, height INT( 3 ) NOT NULL, imc DOUBLE( 2, 2 ), water DOUBLE( 2, 2 ), pgc DOUBLE( 2, 2 ), sizeNeck INT ( 3 ), sizeWaist INT ( 3 ), sizeHip INT ( 3 ), FOREIGN KEY (idUser) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_POINTS + " (id INTEGER  PRIMARY KEY AUTOINCREMENT, longitude decimal(18,14) NOT NULL, latitude decimal(18,14) NOT NULL, speed double NOT NULL, idTraining INTEGER NOT NULL, FOREIGN KEY (idTraining) REFERENCES Training (idTraining) ON DELETE CASCADE ON UPDATE CASCADE);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TRAINING + "  (idTraining INTEGER PRIMARY KEY AUTOINCREMENT, idUser INTEGER, typeTraining VARCHAR (50) NOT NULL, caloriesBurned double NOT NULL, duration long NOT NULL, averageSpeed double NOT NULL, averageRate double NOT NULL, distance double NOT NULL, dateTraining DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (idUser) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (id INTEGER  PRIMARY KEY, name varchar(250) DEFAULT NULL, email varchar(255) UNIQUE NOT NULL, password text NOT NULL, api_key varchar(32) NOT NULL, created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, locationx decimal(18,14) DEFAULT NULL,  locationy decimal(18,14) DEFAULT NULL, picture varchar(200) DEFAULT NULL);");
      //      db.execSQL("INSERT INTO `Statistics` VALUES (1, 2, '2014-04-21 19:45:00', 70.00, 25, 'Man', 175.00, 12.00, 19.00, 48.00, 30, 85, 'Sedentary');");
       //             db.execSQL("INSERT INTO Statistics VALUES (2, 2, '2014-04-21 19:47:13', 68.00, 25, 'Man', 175.00, 11.00, 17.00, 45.00, 30, 85, 'Sedentary');");
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
        Log.v("VERBOSE", "Tabla comprobacion " + nameTable );
        Cursor c = db.rawQuery("SELECT * FROM " + nameTable + ";", null);
        Log.v("VERBOSE", "Tabla comprobacion " + nameTable + "numero de filas "  + c.getCount() );
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
    public long addTraining(Trainings training) {
        SQLiteDatabase db = this.getWritableDatabase();
        long idTraining = -1;
        try{
            ContentValues values = new ContentValues();
            values.put("idUser", training.getIdUser());
            values.put("typeTraining", training.getTypeTraining());
            values.put("caloriesBurned", training.getCaloriesBurned());
            values.put("duration", training.getDuration());
            values.put("averageSpeed", training.getAverageSpeed());
            values.put("averageRate", training.getAverageRate());
            values.put("distance", training.getDistance());
            values.put("dateTraining", training.getDate());

            // Inserting Row
            idTraining = db.insert(TABLE_TRAINING, null, values);
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.dropError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }

        return idTraining;

    }

    // Add new Points
    public void addPoint(Points point, long idTraining) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("longitude", point.getLocation().getLongitude());
            values.put("latitude", point.getLocation().getLatitude());
            values.put("speed", point.getSpeed());
            values.put("idTraining", idTraining);

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
    public void addDiets(Diets diet) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v("Verbose", "Añadiendo dieta a la base de datos");
        try{
            ContentValues values = new ContentValues();
            Log.v("Verbose", "Valor de getiddiet" + diet.getIdDiet() + " " + diet.getIdFood());
            values.put("idDiet", diet.getIdDiet());
            values.put("idFood", diet.getIdFood());
            values.put("typeMeal", diet.getTypeMeal());
            values.put("timeMeal", diet.getTimeMeal());
            values.put("dateMeal", diet.getDateMeal());
            values.put("earnedCalories", diet.getEarnedCalories());
            values.put("quantity", diet.getQuantity());
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

    // Add new Diet
    public int addDiet(Diet diet) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowIdDiet = -1;
        try{
            ContentValues values = new ContentValues();
            //values.put("idDiet", diet.getIdDiet());
            values.put("nameDiet", diet.getNameDiet());
            values.put("description", diet.getDescription());
            values.put("totalCalories", diet.getTotalCalories());

            // Inserting Row
            rowIdDiet = Integer.parseInt(String.valueOf(db.insert(TABLE_DIET, null, values)));
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.dropError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }
        return rowIdDiet;
    }


    // Add new Statistics
    public void addStatistics(Statistics statistic) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v("VERBOSE", "Creando la estadistica");
        try{
            ContentValues values = new ContentValues();
            values.put("idUser", statistic.getIdUser());
            values.put("weight", statistic.getWeight());
            values.put("age", statistic.getAge());
            values.put("sex", statistic.getGender());
            values.put("height", statistic.getHeight());
            values.put("imc", statistic.getImc());
            values.put("water", statistic.getWater());
            values.put("pgc", statistic.getPgc());
            values.put("sizeNeck", statistic.getSizeNeck());
            values.put("sizeWaist", statistic.getSizeWaist());
            values.put("sizeHip",statistic.getSizeHip());

            Log.v("VERBOSE", "Añadiendo estadistica estadistica");
            // Inserting Row
            db.insert(TABLE_STATISTICS, null, values);
        } catch (SQLiteException sqlError){
            sqlError.getStackTrace();
            Toast toast = Toast.makeText(this.myContext, R.string.dropError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }
    }

    // Add new Diets comprobar que guarde la fecha como toca
    public void addFoodToDietDB(Foods food, String typeMeal, String timeMeal, int dateDiet, Diet diet, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v("Verbose", "Añadiendo alimento a la dieta de la base de datos");
        try{
            ContentValues values = new ContentValues();
            Log.v("VERBOSE", "Valor de datos");

            values.put("idDiet", diet.getIdDiet());
            values.put("idFood", food.getId());
            values.put("typeMeal", typeMeal);
            values.put("timeMeal", timeMeal);
            values.put("dateMeal", dateDiet);
            values.put("earnedCalories", (food.getCalories()/100) * quantity);
            values.put("quantity", quantity);
            //aqui insertar lo otros dos datos
            Log.v("Verbose", "Pasando la frontera");

            // Inserting Row
            db.insert(TABLE_DIETS, null, values);
            Log.v("Verbose", "Haciendo el update");
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.dropError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }
        updateDiet(diet, (food.getCalories()/100) * quantity);

    }


    /**
     * Comprueba si el usuario existe en la base de datos
     * @param email
     * @param pass
     * @return
     */
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
        } finally {
            db.close();
        }
        return 0;
    }

    /**
     * Metodo que te devuleve la hora de una comida en concreto
      * @param dateDiet
     * @param idDiet
     * @param typeMeal
     * @return
     */
    public String getDietByDateAndIdAndTypeMeal(int dateDiet, int idDiet, String typeMeal) {
        Log.v("VERBOSE", "Entrando ... ");
        Cursor c = null;
        String timeMeal = "";
        Log.v("VERBOSE", "Entrando en getDietByDateAndIdAndTypeMeal ");
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            c = db.rawQuery("SELECT timeMeal FROM "+ TABLE_DIETS +" WHERE dateMeal = '" + dateDiet + "' AND idDiet = '" + idDiet + "' AND typeMeal = '" + typeMeal + "'  ORDER BY timeMeal;", null);
            Log.v("VERBOSE", "Numero de filas de getDietByDateAndIdAndTypeMeal " + c.getCount());
            if (c.moveToFirst()) {
                do {
                   timeMeal = c.getString(0);
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
        return timeMeal;
    }

    /**
     * Recoger todos las comidas de un dia y dieta concreta
     * @param dateDiet
     * @param idDiet
     * @return
     */
    public List<Diets> getDietByDateAndId(int dateDiet, int idDiet){
        Log.v("VERBOSE", "Entrando ... ");
        Cursor c = null;
        List<Diets> listDiet = new ArrayList<Diets>();
        Log.v("VERBOSE", "Entrando en getDietByDateAndId ");
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            c = db.rawQuery("SELECT idDiet, idFood, typeMeal, timeMeal, dateMeal, earnedCalories, quantity FROM "+ TABLE_DIETS +" WHERE dateMeal = '" + dateDiet + "' AND idDiet = '" + idDiet + "' ORDER BY timeMeal;", null);
            Log.v("VERBOSE", "Numero de filas de getDietByDateAndId " + c.getCount());
            if (c.moveToFirst()) {
                do {
                Diets diet = new Diets();
                diet.setIdDiet(c.getInt(0));
                diet.setIdFood(c.getInt(1));
                diet.setTypeMeal(c.getString(2));
                diet.setTimeMeal(c.getString(3));
                diet.setDateMeal(c.getInt(4));
                diet.setEarnedCalories(c.getDouble(5));
                diet.setQuantity(c.getInt(6));
                listDiet.add(diet);
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
        return listDiet;
    }

    /**
     * Recoge cada comida
     * @param idDiet
     * @return
     */
    public Diet getDietById(int idDiet){
        Cursor c = null;
        Diet diet = null;
        Log.v("VERBOSE", "Entrando en getDietById ");
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            c = db.rawQuery("SELECT nameDiet, description, totalCalories FROM "+ TABLE_DIET +" WHERE idDiet = " + idDiet + ";", null);

            if (c.getCount() != 0) {
                    c.moveToFirst();
                    diet = new Diet();
                    diet.setIdDiet(idDiet);
                    diet.setNameDiet(c.getString(0));
                    diet.setDescription(c.getString(1));
                    diet.setTotalCalories(c.getDouble(2));
            } else {
                Toast toast = Toast.makeText(this.myContext, R.string.selectDietError, Toast.LENGTH_SHORT);
                toast.show();
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

    // Getting All Training
    public List<Diet> getAllDiets() {
        List<Diet> dietsList = new ArrayList<Diet>();
        Cursor cursor = null;
        Log.v("VERBOSE", "Entrando en getAllDiets");
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            cursor = db.rawQuery("SELECT * FROM " + TABLE_DIET + ";", null);
            Log.v("VERBOSE", "Numero de filas de getAllDiets " + cursor.getCount());
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Diet diet = new Diet();
                    diet.setIdDiet(cursor.getInt(0));
                    diet.setNameDiet(cursor.getString(1));
                    diet.setDescription(cursor.getString(2));
                    diet.setTotalCalories(cursor.getDouble(3));
                    dietsList.add(diet);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.selectError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }

        // return contact list
        return dietsList;
    }

    // Getting All Foods By Category
    public List<Foods> getAllFoodsByCategory(String category) {
        List<Foods> foodsList = new ArrayList<Foods>();
        Cursor cursor = null;
        Log.v("VERBOSE", "getAllFoodsByCategory");
        SQLiteDatabase db = this.getWritableDatabase();

        try{

            if ("es".equals(Locale.getDefault().getLanguage())){
                cursor = db.rawQuery("SELECT * FROM " + TABLE_FOODS + " WHERE categoryES = '" + category + "' ORDER BY nameES;", null);
            } else {
                cursor = db.rawQuery("SELECT * FROM " + TABLE_FOODS + " WHERE categoryEN = '" + category + "' ORDER BY nameEN;", null);
            }
            Log.v("VERBOSE", "Numero de filas " + cursor.getCount());
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

    public Foods getFood(int idFood) {
        Cursor cursor = null;
        Log.v("VERBOSE", "getFood");
        SQLiteDatabase db = this.getWritableDatabase();
        Foods food = null;

        try{
             cursor = db.rawQuery("SELECT * FROM " + TABLE_FOODS + " WHERE id = " + idFood + ";", null);

            Log.v("VERBOSE", "Tiene que ser una" + cursor.getCount());
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                    food = new Foods();
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
            }
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.selectError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }

        // return contact list
        return food;
    }

    public Statistics getLastStatistic(int idUser) {
        Cursor cursor = null;
        Log.v("VERBOSE", "getFood");
        SQLiteDatabase db = this.getWritableDatabase();
        Statistics statistics = null;

        try{
            cursor = db.rawQuery("SELECT MAX(dateStatistics), idStatistic, idUser, weight, age, sex, height, imc, water, pgc, sizeNeck, sizeWaist, sizeHip FROM " + TABLE_STATISTICS + " WHERE idUser = " + idUser + ";", null);

            Log.v("VERBOSE", "Tiene que ser una" + cursor.getCount());
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {

                // (idStatistic int(5), idUser INT( 11 ) NOT NULL ,dateStatistics timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,weight DOUBLE( 3, 2 ) NOT NULL ,
                // age INT( 3 ) NOT NULL, sex VARCHAR(50) NOT NULL, height INT( 3 ) NOT NULL,
                // imc DOUBLE( 2, 2 ), water DOUBLE( 2, 2 ), pgc DOUBLE( 2, 2 ), sizeNeck INT ( 3 ), sizeWaist INT ( 3 ), physicalType VARCHAR(20) NOT NULL


                statistics = new Statistics();
                statistics.setDateStatistics(cursor.getString(0));
                statistics.setIdStatistics(cursor.getInt(1));
                statistics.setIdUser(cursor.getInt(2));
                statistics.setWeight(cursor.getDouble(3));
                statistics.setAge((cursor.getInt(4)));
                statistics.setGender(cursor.getString(5));
                statistics.setHeight(cursor.getInt(6));
                statistics.setImc(cursor.getDouble(7));
                statistics.setWater(cursor.getDouble(8));
                statistics.setPgc(cursor.getDouble(9));
                statistics.setSizeNeck(cursor.getInt(10));
                statistics.setSizeWaist(cursor.getInt(11));
                statistics.setSizeHip(cursor.getInt(12));
            }
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.selectError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }

        // return contact list
        return statistics;
    }

    // Add new User
    public Users getUser(int idUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        Users user = null;
        Cursor cursor = null;
        try{  //        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (id INTEGER  PRIMARY KEY, name varchar(250) DEFAULT NULL, email varchar(255) UNIQUE NOT NULL, password text NOT NULL, api_key varchar(32) NOT NULL, created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, locationx decimal(18,14) DEFAULT NULL,  locationy decimal(18,14) DEFAULT NULL, picture varchar(200) DEFAULT NULL);");
            Log.v("VERBOSE", "Consulta de devolver usuario " + "SELECT id, name, email, password_hash, api_key, created_at, locationx, locationy, picture FROM " + TABLE_USERS + " WHERE id = " + idUser + ";");
            cursor = db.rawQuery("SELECT id, name, email, password_hash, api_key, created_at, locationx, locationy, picture FROM " + TABLE_USERS + " WHERE id = " + idUser + ";", null);
            Log.v("VERBOSE", "Filas " + cursor.getCount());

            if (cursor.moveToFirst()) {
                user = new Users();
                user.setId(cursor.getInt(0));
                user.setName(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setApi_key(cursor.getString(4));
                user.setCreated_at(cursor.getString(5));
                user.setLocationx(cursor.getDouble(6));
                user.setLocationy(cursor.getDouble(7));
                user.setPicture(cursor.getString(8));
            }


        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.dropError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }

        return user;

    }


    // Getting Training
    public Trainings getTraining(long idTraining) {
        List<Trainings> trainingsList = new ArrayList<Trainings>();
        Cursor cursor = null;
        Log.v("VERBOSE", "getAllTraining");
        SQLiteDatabase db = this.getWritableDatabase();
        int positionPoint = 0;
        Trainings training = null;
        try{

            //cursor = db.rawQuery("SELECT t.idTraining, t.idUser, t.typeTraining, t.caloriesBurned, t.duration, t.averageSpeed, t.averageRate, t.distance, t.dateTraining, p.id, p.longitude, p.latitude, p.speed FROM " + TABLE_TRAINING + " t, " + TABLE_POINTS + "p WHERE t.idTraining = p.idTraining AND t.idTraining = " + idTraining + ";", null);
            cursor = db.rawQuery("SELECT idTraining, idUser, typeTraining, caloriesBurned, duration, averageSpeed, averageRate, distance, dateTraining FROM " + TABLE_TRAINING + " WHERE idTraining = " + idTraining + ";", null);


            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                training = new Trainings();
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
            }
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.selectError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }

        // return contact list
        return training;
    }

    // Getting Points
    public List<Points> getPoints(long idTraining) {
        List<Points> pointsList = new ArrayList<Points>();
        Cursor cursor = null;
        Log.v("VERBOSE", "getPoints");
        SQLiteDatabase db = this.getWritableDatabase();
        try{

            cursor = db.rawQuery("SELECT id, longitude, latitude, speed FROM " + TABLE_POINTS + " WHERE idTraining = " + idTraining + ";", null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                Points point = new Points();
                Location location = new Location("Point");
                point.setId(Integer.parseInt(cursor.getString(0)));
                location.setLongitude(cursor.getDouble(1));
                location.setLatitude(cursor.getDouble(2));
                point.setLocation(location);
                point.setSpeed(cursor.getDouble(3));
                pointsList.add(point);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.selectError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }

        // return points list
        return pointsList;
    }

    // Getting Training
    public int getIdTrainingByDate(String date) {
        Cursor cursor = null;
        SQLiteDatabase db = this.getWritableDatabase();
        int positionPoint = 0;
        try{

            //cursor = db.rawQuery("SELECT t.idTraining, t.idUser, t.typeTraining, t.caloriesBurned, t.duration, t.averageSpeed, t.averageRate, t.distance, t.dateTraining, p.id, p.longitude, p.latitude, p.speed FROM " + TABLE_TRAINING + " t, " + TABLE_POINTS + "p WHERE t.idTraining = p.idTraining AND t.idTraining = " + idTraining + ";", null);
            cursor = db.rawQuery("SELECT idTraining FROM " + TABLE_TRAINING + " WHERE dateTraining LIKE '" + date + "';", null);


            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                return Integer.parseInt(cursor.getString(0));
            }
        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.selectError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }

        // return contact list
        return -1;
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
                    training.setTypeTraining(cursor.getString(2));
                    training.setCaloriesBurned(cursor.getDouble(3));
                    training.setDuration(cursor.getLong(4));
                    training.setAverageSpeed(cursor.getDouble(5));
                    training.setAverageRate(cursor.getDouble(6));
                    training.setDistance(cursor.getDouble(7));
                    Log.v("VERBOSE", "valor dateTraining " +  cursor.getString(8));
                    training.setDate(cursor.getString(8));

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

    //Hacer metodo de modificar el valor total de la dieta que añadimos el nuevo alimento
    public void updateDiet(Diet diet, double earnedCalories){
        Log.v("VERBOSE", "Entrando en el update");
        SQLiteDatabase db = this.getWritableDatabase();
        double caloriesTotal = diet.getTotalCalories() + earnedCalories;
        String[] args = {String.valueOf(diet.getIdDiet())};
        try{
        ContentValues cv = new ContentValues();
        cv.put("totalCalories",caloriesTotal); //These Fields should be your String values of actual column names
        db.update(TABLE_DIET, cv, "idDiet=?", args);
            Log.v("VERBOSE", "Saliendo en el update");

        } catch (SQLiteException sqlError){
            Toast toast = Toast.makeText(this.myContext, R.string.selectError, Toast.LENGTH_SHORT);
            toast.show();
        } finally{
            db.close(); // Closing database connection
        }
    }

    // Delete Meal
    public void deleteMeal(int idDiet, String typeMeal, int idFood, String dateMeal) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat formatedText = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = (Date) formatedText.parse(dateMeal);
            db.execSQL("DELETE FROM " + TABLE_DIETS  + " WHERE idDiet = '" + idDiet + "' AND typeMeal = '" + typeMeal + "' AND idFood = " + idFood + " AND dateMeal = " + date + ";");
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
