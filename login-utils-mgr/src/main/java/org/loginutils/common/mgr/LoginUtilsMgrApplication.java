package org.loginutils.common.mgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages = {"org.loginutils.dal", "org.loginutils.mgr"})
//@MapperScan("org.loginutils.dal.mappers")
public class LoginUtilsMgrApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginUtilsMgrApplication.class, args);
    }

}
