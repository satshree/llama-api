package com.llama.api;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String parseDate(Date dateToParse) {
        return new SimpleDateFormat("dd. MMM, yyyy HH:mm a").format(dateToParse);
    }
}
