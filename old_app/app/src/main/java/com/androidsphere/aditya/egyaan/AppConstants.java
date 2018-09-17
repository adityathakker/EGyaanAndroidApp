package com.androidsphere.aditya.egyaan;

/**
 * Created by aditya9172 on 13/12/15.
 */
public class AppConstants {

    public class URLs {
        public static final String LOGIN_CHECKER = "http://192.168.1.100/EGyaan/loginChecker.php";
        public static final String TIMETABLE = "http://192.168.1.100/EGyaan/timetable_json.php";
        public static final String NORMAL_NOTICE = "http://192.168.1.100/EGyaan/view_notice.php";
        public static final String URGENT_NOTICE = "http://192.168.1.100/EGyaan/view_urgent_notice.php";
    }

    public class SharedPrefs {
        public static final String PREF_NAME = "EGyaan";
        public static final String ALREADY_LOGGED_IN = "already_logged_in";
        public static final String GCM_REG_ID = "gcm_reg_id";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String UID = "uid";
        public static final String FNAME = "fname";
        public static final String LNAME = "lname";
        public static final String EMAIL = "email_address";
        public static final String PASSWORD = "password";
        public static final String BRANCH = "branch";
        public static final String FIRST_TIME_LOGIN_STUDENT = "first_time_login_student";


        public class GCM {
            public static final String PROJECT_ID = "egyaan-1183";
        }
    }
}
