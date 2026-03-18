package org.loginutils.mgr.controller.system;

import org.loginutils.common.dto.repsonse.MgrResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/system")
public class SystemController {

    @Value("${system.mgr.version:UNKNOWN}")
    private String systemVersion;

    @GetMapping("/version")
    public MgrResponseDto<Map<String, String>> getSystemVersion() {
        Map<String, String> data = new HashMap<>();
        data.put("version", systemVersion);
        return MgrResponseDto.success(data);
    }
}
