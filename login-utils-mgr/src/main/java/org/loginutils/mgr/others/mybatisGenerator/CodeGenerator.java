package org.loginutils.mgr.others.mybatisGenerator; // 注意：確認你的 package 是否正確

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CodeGenerator {

    public static void main(String[] args) {

        // 1. 讀取配置
        Properties props = loadLocalProperties();
        String url = props.getProperty("spring.datasource.url");
        String username = props.getProperty("spring.datasource.username");
        String password = props.getProperty("spring.datasource.password");

        if (url == null) throw new RuntimeException("找不到 DB URL，請檢查 application.properties");

        // 2. 計算路徑
        String projectPath = System.getProperty("user.dir");
        String dalModulePath;
        if (new File(projectPath + "/login-utils-dal").exists()) {
            dalModulePath = projectPath + "/login-utils-dal";
        } else {
            dalModulePath = projectPath + "/../login-utils-dal";
        }

        System.out.println("偵測到 DAL 模組路徑：" + dalModulePath);

        // ★關鍵準備：建立一個路徑 Map，明確把不要的設為 null
        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.xml, dalModulePath + "/src/main/resources/org/loginutils/dal/mappers");
        pathInfo.put(OutputFile.controller, null);  // 🔥 強制路徑為 null
        pathInfo.put(OutputFile.service, null);     // 🔥 強制路徑為 null
        pathInfo.put(OutputFile.serviceImpl, null); // 🔥 強制路徑為 null

        // 3. 執行生成
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("Kai")
                            .enableSwagger()
                            .outputDir(dalModulePath + "/src/main/java");
                })
                .packageConfig(builder -> {
                    builder.parent("org.loginutils.dal")
                            .entity("model")
                            .mapper("mappers")
                            // 這裡不用再設 .xml("...")，因為下面 pathInfo 會覆蓋

                            // ❌ 不要設定 .controller("")，那會導致生成在根目錄

                            // ✅ 使用我們剛剛建好的 Map，這會覆蓋所有預設路徑計算
                            .pathInfo(pathInfo);
                })
                .strategyConfig(builder -> {
                    builder.addInclude("role")
                            .entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .formatFileName("%sDo");

                    builder.mapperBuilder()
                            .enableBaseResultMap()
                            .enableBaseColumnList();
                })
                .templateConfig(builder -> {
                    // 雙重保險：禁用模板
                    builder.disable(TemplateType.CONTROLLER, TemplateType.SERVICE, TemplateType.SERVICE_IMPL);
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

        System.out.println("代碼生成完畢！請檢查 DAL 模組是否乾淨。");
    }

    private static Properties loadLocalProperties() {
        Properties props = new Properties();
        String currentDir = System.getProperty("user.dir");
        String[] possiblePaths = {
                currentDir + "/login-utils-mgr/src/main/resources/application.properties",
                currentDir + "/src/main/resources/application.properties"
        };
        File targetFile = null;
        for (String path : possiblePaths) {
            File f = new File(path);
            if (f.exists()) {
                targetFile = f;
                break;
            }
        }
        if (targetFile == null) {
            throw new RuntimeException("找不到 application.properties，搜索路徑包含：" + currentDir);
        }
        try (FileInputStream fis = new FileInputStream(targetFile)) {
            props.load(new InputStreamReader(fis, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("讀取設定檔失敗", e);
        }
        return props;
    }
}