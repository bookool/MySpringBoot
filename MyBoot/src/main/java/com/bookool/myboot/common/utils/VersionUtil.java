package com.bookool.myboot.common.utils;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * maven版本号获取
 *
 * @author Tommy
 * @date 190529
 */
public class VersionUtil {

    private static String appVersion = null;

    /**
     * @return 获取版本号
     *
     * @throws FileNotFoundException 文件不存在
     * @throws IOException 读写错误
     * @throws XmlPullParserException xml解析错误
     */
    @NotNull
    public static String getVersion() {
        if (appVersion == null) {
            String jarPath = VersionUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
            try {
                jarPath = java.net.URLDecoder.decode(jarPath, "UTF-8");
            } catch (Exception e) {
                appVersion = "";
                return "";
            }
            try {
                URL url = new URL("jar:file:" + jarPath + "!/META-INF/maven/com/loveU/pom.properties");
                InputStream inputStream = url.openStream();
                Properties properties = new Properties();
                properties.load(inputStream);
                appVersion = properties.getProperty("version");
            } catch (Exception e) {
                MavenXpp3Reader reader = new MavenXpp3Reader();
                String basePath;
                try {
                    basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
                } catch (NullPointerException e1) {
                    appVersion = "";
                    return "";
                }
                if (File.separator.contains("\\") && basePath.startsWith("/")) {
                    basePath = basePath.substring(1);
                }
                if (basePath.contains("/target/")) {
                    basePath = basePath.substring(0, basePath.indexOf("/target/"));
                }
                Model model;
                try {
                    model = reader.read(new FileReader(new File(basePath + "\\pom.xml")));
                } catch (Exception e2) {
                    appVersion = "";
                    return "";
                }
                appVersion = model.getVersion();
            }
        }
        return appVersion;
    }

}
