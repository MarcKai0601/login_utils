#!/bin/sh

echo "正在啟動 Tailscale Daemon (Userspace 模式)..."
# 啟動 Tailscale 並開啟 SOCKS5 代理伺服器在 localhost:1055
/usr/sbin/tailscaled --tun=userspace-networking --socks5-server=localhost:1055 &

# 等待 daemon 啟動
sleep 3

if [ -z "${TAILSCALE_AUTHKEY}" ]; then
  echo "錯誤: 尚未設定 TAILSCALE_AUTHKEY 環境變數！"
  exit 1
fi

echo "正在登入 Tailscale..."
# 使用 authkey 登入，並設定機器名稱
/usr/bin/tailscale up --authkey="${TAILSCALE_AUTHKEY}" --hostname="gcp-cloudrun-login-mgr" --accept-routes

echo "正在啟動 Spring Boot 應用程式..."
# 透過 JVM 參數將 TCP 流量導入 Tailscale 的 SOCKS5 代理
# 並強制讓 Spring Boot 監聽 Cloud Run 指定的 $PORT
exec java \
  -DsocksProxyHost=localhost \
  -DsocksProxyPort=1055 \
  -jar /app/app.jar \
  --server.port="${PORT}"