package com.staples.utilities;

import com.google.common.io.Resources;

public class Common {
    public static String getPathToResourceByFilename(String resourceName) {
        return Resources.getResource(resourceName).getPath();
    }

    public static String convertItemPrice(String strUIItemPrice) {
        return strUIItemPrice.trim().replaceAll("\\$|,", "");
    }
}
