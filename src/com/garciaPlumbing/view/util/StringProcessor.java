/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author FURIOUS
 */
public class StringProcessor {

    public static String getUsername(String email) {
        if (email.contains("@")) {
            int x = email.indexOf("@");
            return email.substring(0, x);
        } else {
            return null;
        }
    }

    public static boolean isEmail(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
