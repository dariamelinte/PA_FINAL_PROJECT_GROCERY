package com.example.grocery.utils;

public class Messages {
    public final static String noAccessAllowed = "You do not have access to this resource.";
    public final static String somethingWentWrong = "Something went wrong.";
    public static String nonExistingResource (String resource) {
        return "The respective " + resource + " does not exist.";
    }
    public static String successful (String action) {
        return action + " successful.";
    }
    public static String createSuccessful (String resource) {
        return successful(resource + " created");
    }
    public static String fetchSuccessful (String resource) {
        return successful(resource + " fetched");
    }
    public static String updateSuccessful (String resource) {
        return successful(resource + " updated");
    }
    public static String deleteSuccessful (String resource) {
        return successful(resource + " deleted");
    }
}
