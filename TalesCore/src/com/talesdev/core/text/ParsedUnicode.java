package com.talesdev.core.text;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parsed unicode string
 *
 * @author MoKunz
 */
public class ParsedUnicode {
    private String message = "";

    public ParsedUnicode(String message) {
        this.message = message;
    }

    public String toString() {
        return StringEscapeUtils.unescapeJava(message);
    }
}
