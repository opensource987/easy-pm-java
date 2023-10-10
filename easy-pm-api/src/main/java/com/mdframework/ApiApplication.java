package com.mdframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author mdframework
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ApiApplication
{
    public static void main(String[] args)
    {
        System.setProperty("spring.devtools.restart.enabled", "true");
        SpringApplication.run(ApiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
