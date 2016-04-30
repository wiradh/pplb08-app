package ppl.b08.warunglaundry.business;

import android.text.TextUtils;

/**
 * Created by Andi Fajar on 07/04/2016.
 * Untuk menyimpan konstanta variable
 */
public class C {
    public final static String HOME_URL = "www.lalala.com";
    public final static String MAP_KEY = "AIzaSyBe9V4dLUdlvPUolgDTww8owSuW7E82PEg";
    public static final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 1000;

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist =  (earthRadius * c);

        return dist;
    }
}
