package org.loginutils.dal.utils;

import java.util.Collection;

public final class Ognl {

    /**
     * 判断字串,集合,阵列不为空且长度大于0
     *
     * @param object
     * @return
     */
    public static boolean check(Object object) {
        if (object == null) {
            return false;
        } else if (object instanceof String) {
            return ((String) object).trim().length() != 0;
        } else if (object instanceof Collection) {
            return !((Collection) object).isEmpty();
        } else if (object.getClass().isArray()) {
            Object[] array = (Object[]) object;
            return array.length != 0;
        } else {
            return true;
        }
    }

    /**
     * 判断不是null
     *
     * @param object
     * @return
     */
    public static boolean checkNotNull(Object object) {
        if (object == null) {
            return false;
        } else {
            return true;
        }
    }

    private Ognl() {
    }
}
