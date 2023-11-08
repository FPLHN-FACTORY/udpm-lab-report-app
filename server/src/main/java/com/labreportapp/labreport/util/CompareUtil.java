package com.labreportapp.labreport.util;

import java.util.Objects;

/**
 * @author todo thangncph26123
 */
public class CompareUtil {

    private static final String DA_CAP_NHAT = "Đã cập nhật ";
    private static final String TU = " từ ";
    private static final String THANH = " thành ";

    public static String compareAndConvertMessage(String nameField, Object valueOld, Object valueNew, String customMessage) {
        if (areValuesDifferent(valueOld, valueNew)) {
            return convertMessage(nameField, valueOld, valueNew, customMessage);
        }
        return null;
    }

    private static boolean areValuesDifferent(Object valueOld, Object valueNew) {
        if (valueOld == null && valueNew == null) {
            return false;
        }

        if (valueOld != null && valueNew != null) {
            if (valueOld instanceof Number && valueNew instanceof Number) {
                if (valueOld == valueNew)
                    return false;
            }
            if (valueOld.equals(valueNew)) {
                return false;
            }
        }

        return true;
    }

    private static String convertMessage(String nameField, Object valueOld, Object valueNew, String customMessage) {
        StringBuilder message = new StringBuilder(DA_CAP_NHAT).append(nameField).append(TU);
        message.append(convertValueNullOrEmpty(valueOld)).append(THANH);
        if (customMessage == null || customMessage.equals("")) {
            message.append(convertValueNullOrEmpty(valueNew));
        } else {
            message.append(customMessage);
        }
        return message.toString();
    }

    private static String convertValueNullOrEmpty(Object value) {
        if (value == null) {
            return "Không có";
        }
        if (Objects.equals(value, "")) {
            return "Rỗng";
        }
        return value.toString();
    }

}
