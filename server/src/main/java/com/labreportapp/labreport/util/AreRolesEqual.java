package com.labreportapp.labreport.util;

/**
 * @author thangncph26123
 */
public class AreRolesEqual {

    public static Boolean compareObjects(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }

        if (obj1 == null || obj2 == null) {
            return false;
        }

        if (obj1.getClass() != obj2.getClass()) {
            return false;
        }

        if (obj1.equals(obj2)) {
            return true;
        }

        return false;
    }
}
