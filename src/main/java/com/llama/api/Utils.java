package com.llama.api;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String parseDate(Date dateToParse) {
        if (dateToParse != null) {
            return new SimpleDateFormat("dd. MMM, yyyy hh:mm a").format(dateToParse);
        }

        return "";
    }
}
