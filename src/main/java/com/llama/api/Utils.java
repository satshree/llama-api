package com.llama.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {
    public static String parseDate(Date dateToParse) {
        if (dateToParse != null) {
            return new SimpleDateFormat("dd. MMM, yyyy hh:mm a").format(dateToParse);
        }

        return "";
    }

    public static List<String> getAdminURLs() {
        List<String> urls = new ArrayList<>();

        urls.add("/api/management/product");
        urls.add("/api/management/product-category");
        urls.add("/api/management/product-image");
        urls.add("/api/management/user");
        urls.add("/api/management/billing");
        urls.add("/api/management/analytics");
        // add more urls as project progresses

        return urls;
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateToConvert);

        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        return LocalDate.of(year, month, day);
    }

    public static LocalDate convertToLocalDate(String date) throws ParseException {
        Date dateToConvert = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        return convertToLocalDate(dateToConvert);
    }
}
