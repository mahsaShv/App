package com.example.mobileproject01;

public class Constant {
    static int appTheme = 1;

    static int changeMain = 0;

    public static void setChangeMain(int changeMain) {
        Constant.changeMain = changeMain;
    }

    public static int getChangeMain() {
        return changeMain;
    }

    public static void setAppTheme(int appTheme) {
        Constant.appTheme = appTheme;
    }

    public static int getAppTheme() {
        return appTheme;
    }
}
