package com.talesdev.core.text;

import java.util.HashMap;
import java.util.Map;

/**
 * Formatted message
 *
 * @author MoKunz
 */
public class FormattedMessage {
    protected String message = "";
    protected Map<String, String> patternMap = new HashMap<>();

    public FormattedMessage(String message) {
        if (message != null) {
            this.message = message;
        }
    }

    public FormattedMessage addPattern(String pattern, String value) {
        patternMap.put(pattern, value);
        return this;
    }

    public String getMessage() {
        String formatted = message;
        for (Map.Entry<String, String> entry : patternMap.entrySet()) {
            formatted = formatted.replaceAll("\\{" + entry.getKey() + "\\}", entry.getValue());
        }
        return formatted;
    }
}