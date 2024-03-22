package com.llama.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

        urls.add("/api/product");
        
        // add more urls as project progresses

        return urls;
    }
}
