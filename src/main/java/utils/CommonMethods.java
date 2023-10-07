package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author bipin
 */
public class CommonMethods {

    //Method for showing an alert
    public static void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }

    //Method to hash password
    public static String HashConvert(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            //convert the byte to hex format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static List<String> generateHourList(int startHour, int endHour) {
        List<String> hourList = new ArrayList<>();

        for (int i = startHour; i <= endHour; i++) {
            if (i == 0) {
                hourList.add("12am");
            } else if (i == 12) {
                hourList.add("12pm");
            } else if (i > 12) {
                hourList.add((i - 12) + "pm");
            } else {
                hourList.add(i + "am");
            }
        }

        return hourList;
    }

    /**
     * Converts a 12-hour formatted hour string (e.g., "1pm", "10am") to a
     * 24-hour format integer.
     *
     */
    public static int convertFormattedHourTo24Hour(String formattedHour) {
        formattedHour = formattedHour.toLowerCase().trim();

        if (formattedHour.endsWith("am")) {
            formattedHour = formattedHour.replace("am", "").trim();
            if ("12".equals(formattedHour)) {
                return 0;  // 12am is 0 in 24-hour format
            }
            return Integer.parseInt(formattedHour);
        } else if (formattedHour.endsWith("pm")) {
            formattedHour = formattedHour.replace("pm", "").trim();
            if ("12".equals(formattedHour)) {
                return 12; // 12pm remains 12 in 24-hour format
            }
            return Integer.parseInt(formattedHour) + 12;
        } else {
            throw new IllegalArgumentException("Invalid hour format: " + formattedHour);
        }
    }

    public static boolean isStringEmptyOrNull(String value) {
        return value == null || value.trim().isEmpty();
    }

}
