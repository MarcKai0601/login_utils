package org.loginutils.mgr.utils;

import jakarta.servlet.http.HttpServletRequest;

public class IpUtility {

    /**
     * 從 HttpServletRequest 中獲取真實的客戶端 IP 地址。
     * 考慮了常見的反向代理和負載平衡器的 Header。
     *
     * @param request HttpServletRequest 對象
     * @return 客戶端的真實 IP 地址
     */
    public static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 如果透過多層代理，X-Forwarded-For 可能會有逗號分隔的多個 IP
        // 第一個通常是真實的客戶端 IP (例如: "192.168.1.1, 10.0.0.1")
        if (ip != null && ip.contains(",")) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }

        // 處理由 localhost 訪問時的 IPv6 格式
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }

        return ip;
    }
}
