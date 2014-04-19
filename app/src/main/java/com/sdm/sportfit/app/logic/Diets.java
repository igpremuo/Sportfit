package com.sdm.sportfit.app.logic;

import java.util.Locale;

/**
 * Created by Jess on 8/04/14.
 */
public class Diets {

    public enum TimeMeal {
        TIME0000("00:00"), TIME0030("00:30"), TIME0100("01:00"), TIME0130("01:30"), TIME0200("02:00"), TIME0230("02:30"),
        TIME0300("03:00"), TIME0330("03:30"), TIME0400("04:00"), TIME0430("04:30"), TIME0500("05:00"), TIME0530("05:30"),
        TIME0600("06:00"), TIME0630("06:30"), TIME0700("07:00"), TIME0730("07:30"), TIME0800("08:00"), TIME0830("08:30"),
        TIME0900("09:00"), TIME0930("09:30"), TIME1000("10:00"), TIME1030("10:30"), TIME1100("11:00"), TIME1130("11:30"),
        TIME1200("12:00"), TIME1230("12:30"), TIME1300("13:00"), TIME1330("13:30"), TIME1400("14:00"), TIME1430("14:30"),
        TIME1500("15:00"), TIME1530("15:30"), TIME1600("16:00"), TIME1630("16:30"), TIME1700("17:00"), TIME1730("17:30"),
        TIME1800("18:00"), TIME1830("18:30"), TIME1900("19:00"), TIME1930("19:30"), TIME2000("20:00"), TIME2030("20:30"),
        TIME2100("21:00"), TIME2130("21:30"), TIME2200("22:00"), TIME2230("22:30"), TIME2300("23:00"), TIME2330("23:30");
        String mTimeMeal;

        TimeMeal(String timeMeal) {
            mTimeMeal = timeMeal;
        }

        @Override
        public String toString() {
            return mTimeMeal;
        }
    };

    public enum DateMeal {
        DATE1(1), DATE2(2), DATE3(3), DATE4(4), DATE5(5), DATE6(6),
        DATE7(7), DATE8(8), DATE9(9), DATE10(10), DATE11(11), DATE12(12),
        DATE13(13), DATE14(14), DATE15(15), DATE16(16), DATE17(17), DATE18(18),
        DATE19(19), DATE20(20), DATE21(21), DATE22(22), DATE23(23), DATE24(24),
        DATE25(25), DATE26(26), DATE27(27), DATE28(28), DATE29(29), DATE30(30),
        DATE31(31);

        int mDateMeal;

        DateMeal(int dateMeal) {
            mDateMeal = dateMeal;
        }

        @Override
        public String toString() {
            return String.valueOf(mDateMeal);
        }
    };

    public enum TypeMeal {
        BREAKFAST("Breakfast"), BRUNCH("Brunch"), LUNCH("Lunch"), AFTERNOONSNACK("Afternoon snack"), DINNER("Dinner"),
        DESAYUNO("Desayuno"), ALMUERZO("Almuerzo"), COMIDA("Comida"), MERIENDA("Merienda"), CENA("Cena");

        String mTypeMeal;

        TypeMeal(String typeMeal) {
            mTypeMeal = typeMeal;
        }

        @Override
        public String toString() {
            return String.valueOf(mTypeMeal);
        }
    };

    private int idDiet;
    private int idFood;
    private TypeMeal typeMeal;
    private TimeMeal timeMeal;
    private DateMeal dateMeal;
    private Double earnedCalories;
    private int quantity;



    public Diets() {
        this.idDiet = 0;
        this.idFood = 0;
        this.typeMeal = null;
        this.timeMeal = null;
        this.dateMeal = null;
        this.earnedCalories = 0.0;
        this.quantity = 0;
    }

    public Diets(int idDiet, int idFood, String typeMeal, String timeMeal, int dateMeal,Double earnedCalories, int quantity) {
        this.idDiet = idDiet;
        this.idFood = idFood;
        this.typeMeal = getTypeMeal(typeMeal);
        this.timeMeal = getTimeMeal(timeMeal);
        this.dateMeal = getDateMeal(dateMeal);
        this.earnedCalories = earnedCalories;
        this.quantity = quantity;
    }

    public Double getEarnedCalories() {
        return earnedCalories;
    }

    public void setEarnedCalories(Double earnedCalories) {
        this.earnedCalories = earnedCalories;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIdDiet() {
        return idDiet;
    }

    public void setIdDiet(int idDiet) {
        this.idDiet = idDiet;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public String getTypeMeal() {
        return typeMeal.toString();
    }

    public void setTypeMeal(String typeMeal) {
        this.typeMeal= getTypeMeal(typeMeal);
    }

    public String getTimeMeal() {
       return timeMeal.toString();
    }

    public void setTimeMeal(String timeMeal)    {
            this.timeMeal= getTimeMeal(timeMeal);
    }

    public int getDateMeal() {
        return Integer.parseInt(dateMeal.toString());
    }

    public void setDateMeal(int dateMeal)  {
        this.dateMeal= getDateMeal(dateMeal);
    }

    public TimeMeal getTimeMeal(String timeMeal) {
        if (timeMeal.equals(TimeMeal.TIME0000.toString()))
            return TimeMeal.TIME0000;
        if (timeMeal.equals(TimeMeal.TIME0030.toString()))
            return TimeMeal.TIME0030;
        if (timeMeal.equals(TimeMeal.TIME0100.toString()))
            return TimeMeal.TIME0100;
        if (timeMeal.equals(TimeMeal.TIME0130.toString()))
            return TimeMeal.TIME0130;
        if (timeMeal.equals(TimeMeal.TIME0200.toString()))
            return TimeMeal.TIME0200;
        if (timeMeal.equals(TimeMeal.TIME0230.toString()))
            return TimeMeal.TIME0230;
        if (timeMeal.equals(TimeMeal.TIME0300.toString()))
            return TimeMeal.TIME0300;
        if (timeMeal.equals(TimeMeal.TIME0330.toString()))
            return TimeMeal.TIME0330;
        if (timeMeal.equals(TimeMeal.TIME0400.toString()))
            return TimeMeal.TIME0400;
        if (timeMeal.equals(TimeMeal.TIME0430.toString()))
            return TimeMeal.TIME0430;
        if (timeMeal.equals(TimeMeal.TIME0500.toString()))
            return TimeMeal.TIME0500;
        if (timeMeal.equals(TimeMeal.TIME0530.toString()))
            return TimeMeal.TIME0530;
        if (timeMeal.equals(TimeMeal.TIME0600.toString()))
            return TimeMeal.TIME0600;
        if (timeMeal.equals(TimeMeal.TIME0630.toString()))
            return TimeMeal.TIME0630;
        if (timeMeal.equals(TimeMeal.TIME0700.toString()))
            return TimeMeal.TIME0700;
        if (timeMeal.equals(TimeMeal.TIME0730.toString()))
            return TimeMeal.TIME0730;
        if (timeMeal.equals(TimeMeal.TIME0800.toString()))
            return TimeMeal.TIME0800;
        if (timeMeal.equals(TimeMeal.TIME0830.toString()))
            return TimeMeal.TIME0830;
        if (timeMeal.equals(TimeMeal.TIME0900.toString()))
            return TimeMeal.TIME0900;
        if (timeMeal.equals(TimeMeal.TIME0930.toString()))
            return TimeMeal.TIME0930;
        if (timeMeal.equals(TimeMeal.TIME1000.toString()))
            return TimeMeal.TIME1000;
        if (timeMeal.equals(TimeMeal.TIME1030.toString()))
            return TimeMeal.TIME1030;
        if (timeMeal.equals(TimeMeal.TIME1100.toString()))
            return TimeMeal.TIME1100;
        if (timeMeal.equals(TimeMeal.TIME1130.toString()))
            return TimeMeal.TIME1130;
        if (timeMeal.equals(TimeMeal.TIME1200.toString()))
            return TimeMeal.TIME1200;
        if (timeMeal.equals(TimeMeal.TIME1230.toString()))
            return TimeMeal.TIME1230;
        if (timeMeal.equals(TimeMeal.TIME1300.toString()))
            return TimeMeal.TIME1300;
        if (timeMeal.equals(TimeMeal.TIME1330.toString()))
            return TimeMeal.TIME1330;
        if (timeMeal.equals(TimeMeal.TIME1400.toString()))
            return TimeMeal.TIME1400;
        if (timeMeal.equals(TimeMeal.TIME1430.toString()))
            return TimeMeal.TIME1430;
        if (timeMeal.equals(TimeMeal.TIME1500.toString()))
            return TimeMeal.TIME1500;
        if (timeMeal.equals(TimeMeal.TIME1530.toString()))
            return TimeMeal.TIME1530;
        if (timeMeal.equals(TimeMeal.TIME1600.toString()))
            return TimeMeal.TIME1600;
        if (timeMeal.equals(TimeMeal.TIME1630.toString()))
            return TimeMeal.TIME1630;
        if (timeMeal.equals(TimeMeal.TIME1700.toString()))
            return TimeMeal.TIME1700;
        if (timeMeal.equals(TimeMeal.TIME1730.toString()))
            return TimeMeal.TIME1730;
        if (timeMeal.equals(TimeMeal.TIME1730.toString()))
            return TimeMeal.TIME1730;
        if (timeMeal.equals(TimeMeal.TIME1800.toString()))
            return TimeMeal.TIME1800;
        if (timeMeal.equals(TimeMeal.TIME1830.toString()))
            return TimeMeal.TIME1830;
        if (timeMeal.equals(TimeMeal.TIME1900.toString()))
            return TimeMeal.TIME1900;
        if (timeMeal.equals(TimeMeal.TIME1930.toString()))
            return TimeMeal.TIME1930;
        if (timeMeal.equals(TimeMeal.TIME2000.toString()))
            return TimeMeal.TIME2000;
        if (timeMeal.equals(TimeMeal.TIME2030.toString()))
            return TimeMeal.TIME2030;
        if (timeMeal.equals(TimeMeal.TIME2100.toString()))
            return TimeMeal.TIME2100;
        if (timeMeal.equals(TimeMeal.TIME2130.toString()))
            return TimeMeal.TIME2130;
        if (timeMeal.equals(TimeMeal.TIME2200.toString()))
            return TimeMeal.TIME2200;
        if (timeMeal.equals(TimeMeal.TIME2230.toString()))
            return TimeMeal.TIME2230;
        if (timeMeal.equals(TimeMeal.TIME2300.toString()))
            return TimeMeal.TIME2300;
        if (timeMeal.equals(TimeMeal.TIME2330.toString()))
            return TimeMeal.TIME2330;
        return TimeMeal.TIME0000;
    }

    public DateMeal getDateMeal(int date) {
        String dateMeal = String.valueOf(date);
        if (dateMeal.equals(DateMeal.DATE1.toString()))
            return DateMeal.DATE1;
        if (dateMeal.equals(DateMeal.DATE2.toString()))
            return DateMeal.DATE2;
        if (dateMeal.equals(DateMeal.DATE3.toString()))
            return DateMeal.DATE3;
        if (dateMeal.equals(DateMeal.DATE4.toString()))
            return DateMeal.DATE4;
        if (dateMeal.equals(DateMeal.DATE5.toString()))
            return DateMeal.DATE5;
        if (dateMeal.equals(DateMeal.DATE6.toString()))
            return DateMeal.DATE6;
        if (dateMeal.equals(DateMeal.DATE7.toString()))
            return DateMeal.DATE7;
        if (dateMeal.equals(DateMeal.DATE8.toString()))
            return DateMeal.DATE8;
        if (dateMeal.equals(DateMeal.DATE9.toString()))
            return DateMeal.DATE9;
        if (dateMeal.equals(DateMeal.DATE10.toString()))
            return DateMeal.DATE10;
        if (dateMeal.equals(DateMeal.DATE11.toString()))
            return DateMeal.DATE11;
        if (dateMeal.equals(DateMeal.DATE12.toString()))
            return DateMeal.DATE12;
        if (dateMeal.equals(DateMeal.DATE13.toString()))
            return DateMeal.DATE13;
        if (dateMeal.equals(DateMeal.DATE14.toString()))
            return DateMeal.DATE14;
        if (dateMeal.equals(DateMeal.DATE15.toString()))
            return DateMeal.DATE15;
        if (dateMeal.equals(DateMeal.DATE16.toString()))
            return DateMeal.DATE16;
        if (dateMeal.equals(DateMeal.DATE17.toString()))
            return DateMeal.DATE17;
        if (dateMeal.equals(DateMeal.DATE18.toString()))
            return DateMeal.DATE18;
        if (dateMeal.equals(DateMeal.DATE19.toString()))
            return DateMeal.DATE19;
        if (dateMeal.equals(DateMeal.DATE20.toString()))
            return DateMeal.DATE20;
        if (dateMeal.equals(DateMeal.DATE21.toString()))
            return DateMeal.DATE21;
        if (dateMeal.equals(DateMeal.DATE22.toString()))
            return DateMeal.DATE22;
        if (dateMeal.equals(DateMeal.DATE23.toString()))
            return DateMeal.DATE23;
        if (dateMeal.equals(DateMeal.DATE24.toString()))
            return DateMeal.DATE24;
        if (dateMeal.equals(DateMeal.DATE25.toString()))
            return DateMeal.DATE25;
        if (dateMeal.equals(DateMeal.DATE26.toString()))
            return DateMeal.DATE26;
        if (dateMeal.equals(DateMeal.DATE27.toString()))
            return DateMeal.DATE27;
        if (dateMeal.equals(DateMeal.DATE28.toString()))
            return DateMeal.DATE28;
        if (dateMeal.equals(DateMeal.DATE29.toString()))
            return DateMeal.DATE29;
        if (dateMeal.equals(DateMeal.DATE30.toString()))
            return DateMeal.DATE30;
        if (dateMeal.equals(DateMeal.DATE31.toString()))
            return DateMeal.DATE31;

        return DateMeal.DATE1;
    }

    public TypeMeal getTypeMeal(String date) {
        String typeMeal = String.valueOf(date);
        if (typeMeal.equals(TypeMeal.BREAKFAST.toString()))
            return TypeMeal.BREAKFAST;
        if (typeMeal.equals(TypeMeal.BRUNCH.toString()))
            return TypeMeal.BRUNCH;
        if (typeMeal.equals(TypeMeal.AFTERNOONSNACK.toString()))
            return TypeMeal.AFTERNOONSNACK;
        if (typeMeal.equals(TypeMeal.DINNER.toString()))
            return TypeMeal.DINNER;
        if (typeMeal.equals(TypeMeal.DESAYUNO.toString()))
            return TypeMeal.DESAYUNO;
        if (typeMeal.equals(TypeMeal.ALMUERZO.toString()))
            return TypeMeal.ALMUERZO;
        if (typeMeal.equals(TypeMeal.COMIDA.toString()))
            return TypeMeal.COMIDA;
        if (typeMeal.equals(TypeMeal.MERIENDA.toString()))
            return TypeMeal.MERIENDA;
        if (typeMeal.equals(TypeMeal.CENA.toString()))
            return TypeMeal.CENA;

        if ("es".equals(Locale.getDefault().getLanguage())){
            return TypeMeal.DESAYUNO;
        } else {
            return TypeMeal.BREAKFAST;
        }
    }

}
