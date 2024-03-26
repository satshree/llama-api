package com.llama.api.exceptions;

import com.llama.api.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error {
    String message;
    List<String> details;
    Date timestamp;

    public String getTimestamp() {
        return Utils.parseDate(timestamp);
    }
}
