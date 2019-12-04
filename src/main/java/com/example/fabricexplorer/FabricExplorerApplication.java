package com.example.fabricexplorer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.fabricexplorer.mapper")
public class FabricExplorerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FabricExplorerApplication.class, args);
    }

}
