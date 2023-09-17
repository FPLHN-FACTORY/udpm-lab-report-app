package com.labreportapp.portalprojects.infrastructure.constant;

/**
 * @author thangncph26123
 */

public final class Constants {

    private Constants() {
    }

    public static final String VERSION = "v1.2";

    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String ID_USER = "c5cf1e20-bdd4-11ed-afa1-0242ac120002";
    public static final String COLOR_7AA1E4 = "#7AA1E4";
    public static final String COLOR_ADFF2F = "#ADFF2F";
    public static final String COLOR_FF4500 = "#FF4500";
    public static final String COLOR_FFA500 = "#FFA500";
    public static final String COLOR_FFFF00 = "#FFFF00";
    public static final String COLOR_FFD700 = "#FFD700";
    public static final String COLOR_FF6347 = "#FF6347";
    public static final String COLOR_FA8072 = "#FA8072";
    public static final String COLOR_47799C = "#47799C";
    public static final String COLOR_EE82EE = "#EE82EE";

    public class FileProperties {
        private FileProperties() {
        }

        public static final String PROPERTIES_APPLICATION = "application.properties";
        public static final String PROPERTIES_VALIDATION = "messages.properties";
    }

    public static final String REGEX_EMAIL_FE = "\\w+@fe.edu.vn";

    public static final String REGEX_EMAIL_FPT = "\\w+@fpt.edu.vn";

    public static final String REGEX_PHONE_NUMBER = "(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}";

    public static final String REGEX_DATE ="^(0[1-9]|1[012])/(0[1-9]|[12][0-9]|[3][01])/\\\\d{4}$";

}
