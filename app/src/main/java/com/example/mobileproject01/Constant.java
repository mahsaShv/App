package com.example.mobileproject01;

public class Constant {
    static int appTheme = 1;

    static boolean changeMain = false;

    public static void setChangeMain(boolean changeMain) {
        Constant.changeMain = changeMain;
    }

    public static boolean isChangeMain() {
        return changeMain;
    }

    public static void setAppTheme(int appTheme) {
        Constant.appTheme = appTheme;
    }

    public static int getAppTheme() {
        return appTheme;
    }
}
