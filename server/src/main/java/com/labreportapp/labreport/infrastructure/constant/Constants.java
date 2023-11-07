package com.labreportapp.labreport.infrastructure.constant;

/**
 * @author thangncph26123
 */

public final class Constants {

    private Constants() {
    }

    public static final String VERSION = "v1.0";

    public static final String ENCODING_UTF8 = "UTF-8";

    public class FileProperties {
        private FileProperties() {
        }

        public static final String PROPERTIES_APPLICATION = "application.properties";
        public static final String PROPERTIES_VALIDATION = "messages.properties";
    }

    public static final String REGEX_EMAIL_FE = "\\w+@fe.edu.vn";

    public static final String REGEX_EMAIL_FPT = "\\w+@fpt.edu.vn";

    public static final String REGEX_PHONE_NUMBER = "(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}";

    public static final String REGEX_DATE = "^(0[1-9]|1[012])/(0[1-9]|[12][0-9]|[3][01])/\\\\d{4}$";

}
