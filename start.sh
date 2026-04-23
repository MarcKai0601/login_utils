#!/bin/sh

echo "Starting Tailscale Daemon in Userspace mode..."
/usr/sbin/tailscaled --tun=userspace-networking --socks5-server=localhost:1055 &
sleep 3

if [ -z "${TAILSCALE_AUTHKEY}" ]; then
  echo "Error: TAILSCALE_AUTHKEY is not set!"
  exit 1
fi

echo "Authenticating Tailscale..."
/usr/bin/tailscale up --authkey="${TAILSCALE_AUTHKEY}" --hostname="gcp-cloudrun-login" --accept-routes

echo "Starting Spring Boot application..."
# 透過 JVM 參數將連線導向 Tailscale 的 SOCKS5 代理
exec java \
  -DsocksProxyHost=localhost \
  -DsocksProxyPort=1055 \
  -jar /app/app.jar \
  --server.port="${PORT}"