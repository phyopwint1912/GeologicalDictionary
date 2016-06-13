package com.dictionary.util;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class ComFunction {

    private static ComFunction _comfunction;

    public static ComFunction instance() {
        if (_comfunction == null)
            _comfunction = new ComFunction();

        return _comfunction;
    }


    public boolean isnullorEmpty(String s) {

        if (s.equals(null) || s.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    public  String getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }



    public String gettableName(Character firstChar) {
        String TableName;
        switch (firstChar) {
            case 'a':
                TableName = "A";
                break;
            case 'A':
                TableName = "A";
                break;
            case 'b':
                TableName = "B";
                break;
            case 'B':
                TableName = "B";
                break;
            case 'c':
                TableName = "C";
                break;
            case 'C':
                TableName = "C";
                break;
            case 'd':
                TableName = "D";
                break;
            case 'D':
                TableName = "D";
                break;
            case 'e':
                TableName = "E";
                break;
            case 'E':
                TableName = "E";
                break;
            case 'f':
                TableName = "F";
                break;
            case 'F':
                TableName = "F";
                break;
            case 'g':
                TableName = "G";
                break;
            case 'G':
                TableName = "G";
                break;
            case 'h':
                TableName = "H";
                break;
            case 'H':
                TableName = "H";
                break;
            case 'i':
                TableName = "I";
                break;
            case 'I':
                TableName = "I";
                break;
            case 'j':
                TableName = "J";
                break;
            case 'J':
                TableName = "J";
                break;
            case 'k':
                TableName = "K";
                break;
            case 'K':
                TableName = "K";
                break;
            case 'l':
                TableName = "L";
                break;
            case 'L':
                TableName = "L";
                break;
            case 'm':
                TableName = "M";
                break;
            case 'M':
                TableName = "M";
                break;
            case 'n':
                TableName = "N";
                break;
            case 'N':
                TableName = "N";
                break;
            case 'o':
                TableName = "O";
                break;
            case 'O':
                TableName = "O";
                break;
            case 'p':
                TableName = "P";
                break;
            case 'P':
                TableName = "P";
                break;
            case 'q':
                TableName = "Q";
                break;
            case 'Q':
                TableName = "Q";
                break;
            case 'r':
                TableName = "R";
                break;
            case 'R':
                TableName = "R";
                break;

            case 's':
                TableName = "S";
                break;
            case 'S':
                TableName = "S";
                break;
            case 't':
                TableName = "T";
                break;
            case 'T':
                TableName = "T";
                break;
            case 'u':
                TableName = "U";
                break;
            case 'U':
                TableName = "U";
                break;
            case 'v':
                TableName = "V";
                break;
            case 'V':
                TableName = "V";
                break;
            case 'w':
                TableName = "W";
                break;
            case 'W':
                TableName = "W";
                break;
            case 'x':
                TableName = "X";
                break;
            case 'X':
                TableName = "X";
                break;
            case 'Y':
                TableName = "Y";
                break;
            case 'y':
                TableName = "Y";
                break;
            case 'z':
                TableName = "Z";
                break;
            case 'Z':
                TableName = "Z";
                break;
            default:
                TableName = "";
                break;
        }
        return TableName;
    }
}
