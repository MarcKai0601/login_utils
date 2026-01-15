package org.loginutils.mgr;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.config.TemplateType; // 記得加這個 import

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Properties;

public class CodeGenerator {

    public static void main(String[] args) {

        // 1. 讀取配置 (自動判斷 .properties 路徑)
        Properties props = loadLocalProperties();

        String url = props.getProperty("spring.datasource.url");
        String username = props.getProperty("spring.datasource.username");
        String password = props.getProperty("spring.datasource.password");

        if (url == null) throw new RuntimeException("找不到 DB URL，請檢查 application.properties");

        // 2. 計算 dal 模組的路徑
        String projectPath = System.getProperty("user.dir");
        String dalModulePath;

        if (new File(projectPath + "/login-utils-dal").exists()) {
            // 情況 A：從專案根目錄 (workspace) 執行
            dalModulePath = projectPath + "/login-utils-dal";
        } else {
            // 情況 B：從 mgr 模組目錄執行 -> 往上一層找
            dalModulePath = projectPath + "/../login-utils-dal";
        }

        System.out.println("偵測到 DAL 模組路徑：" + dalModulePath);

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
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("mappers")
                            .xml("mappers.xml")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, dalModulePath + "/src/main/resources/org/loginutils/dal/mappers"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("role") // 設定你的表名
                            .entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            // ▼ 重點在這裡：設定檔名格式 ▼
                            // %s 會被替換成表名 (例如 Role)，後面加上 Do
                            .formatFileName("%sDo")
                            // ▼ 新增這段 Mapper 的配置 ▼
                            .mapperBuilder()
                            .enableBaseResultMap()  // 生成 <resultMap>
                            .enableBaseColumnList(); // 生成 <sql id="Base_Column_List">
//                            .controllerBuilder()
//                            .enableRestStyle()
                    ;
                })
                // ✅ 新增這一段：禁用 Controller 模板
                .templateConfig(builder -> {
                    builder.disable(TemplateType.CONTROLLER, TemplateType.SERVICE, TemplateType.SERVICE_IMPL);

                    // 💡 如果你連 Service 也不想要 (只想留純粹的 DAL 層)，可以寫成：
                    // builder.disable(TemplateType.CONTROLLER, TemplateType.SERVICE, TemplateType.SERVICE_IMPL);
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

        System.out.println("代碼生成完畢！");
    }

    // --- 修正：讀取 .properties 檔案 ---
    private static Properties loadLocalProperties() {
        Properties props = new Properties();
        String currentDir = System.getProperty("user.dir");

        // 定義可能的路徑
        // 1. 從 Root 跑: login-utils-mgr/src/main/resources/application.properties
        // 2. 從 Mgr 跑: src/main/resources/application.properties
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
            // 使用 UTF-8 讀取，避免中文亂碼
            props.load(new InputStreamReader(fis, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("讀取設定檔失敗", e);
        }

        return props;
    }
}
