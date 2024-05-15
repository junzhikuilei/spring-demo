package xyz.kuilei.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import xyz.kuilei.spring.demo.common.util.SpringContextUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@Slf4j
public class DemoApplication {
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(DemoApplication.class, args);

        Environment environment = SpringContextUtil.getApplicationContext().getEnvironment();

        String host = InetAddress.getLocalHost().getHostAddress();
        String port = environment.getProperty("server.port", "");
        String hostPort = host + ":" + port;
        String contextPath = environment.getProperty("server.servlet.context-path", "");

        log.info("************************************************");
        log.info("demo 已启动，在线接口文档请访问 http://" + hostPort + contextPath + "/doc.html");
        log.info("************************************************");
    }
}
