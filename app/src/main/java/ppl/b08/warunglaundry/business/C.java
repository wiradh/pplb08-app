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
}
