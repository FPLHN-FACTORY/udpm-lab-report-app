package com.labreportapp.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author thangncph26123
 */
public class ConvertListIdToString {

    public static String convert(List<String> listId) {
        if (listId == null || listId.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (String id : listId) {
            if (result.length() > 0) {
                result.append("|");
            }
            result.append(id);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        List<String> listId =new ArrayList<>();
        listId.add("dddhfsdhfsdhfsdf");
        listId.add("hsdfsdfgdsgfsdgfdg");
        System.out.println(convert(listId));
    }

}
