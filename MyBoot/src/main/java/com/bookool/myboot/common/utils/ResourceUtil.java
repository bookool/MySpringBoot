package com.bookool.myboot.common.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class ResourceUtil {

    public static String readResource(String path, String lineBreak) {
        try {
            InputStream is = ResourceUtil.class.getClassLoader()
                    .getResourceAsStream(getLinuxResourcePath(path));
            if (is == null) {
                File file = new File("MyBoot/src/main/resources" + path);
                is = new FileInputStream(file);
            }
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            String string;
            StringBuilder stringBuilder = new StringBuilder();
            while((string = reader.readLine()) != null){
                stringBuilder.append(string).append(lineBreak);
            }
            try {
                reader.close();
                isr.close();
                is.close();
            } catch (Exception e) {
                //
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return null;
        }

    }

    private static String getLinuxResourcePath(String name) {
        if (!"/".equals(File.separator)) {
            return name.replaceAll(Pattern.quote(File.separator), "/");
        }
        return name;
    }

}