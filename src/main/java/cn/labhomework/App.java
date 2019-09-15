package cn.labhomework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {"cn.labhomework"})
@RestController
@MapperScan("cn.labhomework.dao")
public class App {

    @RequestMapping("/")
    public String home() {
        return "this is home";
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
