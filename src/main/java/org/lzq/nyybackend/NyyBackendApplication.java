package org.lzq.nyybackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("org.lzq.nyybackend.mapper")
public class NyyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NyyBackendApplication.class, args);
    }

}
