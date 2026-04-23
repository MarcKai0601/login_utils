# ==========================================
# Stage 1: Builder 階段 (負責編譯與打包)
# ==========================================
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /build

# 複製根目錄的 pom.xml
COPY pom.xml .

# 複製所需的子模組源碼
COPY login-utils-common login-utils-common/
COPY login-utils-dal login-utils-dal/
COPY login-utils-mgr login-utils-mgr/

# 執行 Maven 打包。
# -pl 指定目標模組，-am 同時編譯其依賴模組，-DskipTests 加快打包速度
RUN mvn clean package -pl login-utils-mgr -am -DskipTests

# ==========================================
# Stage 2: Runtime 階段 (輕量級執行環境)
# ==========================================
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# 安裝必要的工具與 Tailscale
RUN apt-get update && apt-get install -y curl \
    && curl -fsSL https://tailscale.com/install.sh | sh \
    && rm -rf /var/lib/apt/lists/*

# 從 Builder 階段複製編譯完成的 JAR 檔
COPY --from=builder /build/login-utils-mgr/target/login-utils-mgr-1.0.0.jar app.jar

# 複製啟動腳本
COPY start.sh .
RUN chmod +x start.sh

# Cloud Run 會自動注入 PORT 環境變數，預設為 8080
ENV PORT=8080

# 執行啟動腳本
CMD ["/app/start.sh"]