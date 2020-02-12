package com.example.khoslalabsassignment.appUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppUtils {

    public static Date convertStringToDate(String dateInStr,String dateFormat){
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            Date date = format.parse(dateInStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkWhetherTwoDatesMatched(String apiDateInStr, String dateToMatchedStr,String dateFormat){
        Date apiDate = convertStringToDate(apiDateInStr,dateFormat);
        Date dateToMatched = convertStringToDate(dateToMatchedStr,dateFormat);
        if (apiDate.compareTo(dateToMatched) == 0)
            return true;
        else
            return false;
    }

    public static String convertDateFormat(String dateInStr, String dateFormatBefore,String dateFormatAfter){
        DateFormat originalFormat = new SimpleDateFormat(dateFormatBefore, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(dateFormatAfter);
        Date date = null;
        try {
            date = originalFormat.parse(dateInStr);
            return targetFormat.format(date);  // 20120821
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String convertKelvinToCelcius(double tempInKelvin){
        return String.valueOf((int)(tempInKelvin - 273.15));
    }

    public static String getDayOfWeekFromDate(String dateInStr,String dateFormat){
        String dayOfWeek = convertDateFormat(dateInStr,dateFormat, AppConstants.DATE_FORMAT_DAY_OF_WEEK);
        return convertDayOfWeekInProperDay(dayOfWeek);
    }

    private static String convertDayOfWeekInProperDay(String dayOfWeek){
        String dayOfWeekToReturn = null;
        switch (dayOfWeek){
            case "Mon":
                dayOfWeekToReturn = AppConstants.DAY_OF_WEEK_MONDAY;
                break;
            case "Tue":
                dayOfWeekToReturn = AppConstants.DAY_OF_WEEK_TUESDAY;
                break;
            case "Wed":
                dayOfWeekToReturn = AppConstants.DAY_OF_WEEK_WEDNESDAY;
                break;
            case "Thu":
                dayOfWeekToReturn = AppConstants.DAY_OF_WEEK_THURSDAY;
                break;
            case "Fri":
                dayOfWeekToReturn = AppConstants.DAY_OF_WEEK_FRIDAY;
                break;
            case "Sat":
                dayOfWeekToReturn = AppConstants.DAY_OF_WEEK_SATURDAY;
                break;
            case "Sun":
                dayOfWeekToReturn = AppConstants.DAY_OF_WEEK_SUNDAY;
                break;
        }
        return dayOfWeekToReturn;
    }

    public static long getDaysFromDate(Date currentDate, Date selectedDate){

        long different = selectedDate.getTime() - currentDate.getTime();
        long daysInMilli = 1000 * 60 * 60 * 24;
        if (different<0)
            return 0;
        else
            return different / daysInMilli;
    }
}
