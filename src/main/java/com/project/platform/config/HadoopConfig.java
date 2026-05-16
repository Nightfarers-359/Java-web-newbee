package com.project.platform.config;

import org.apache.hadoop.conf.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class HadoopConfig {
    @Value("${hadoop.hdfsUri}")
    private String hdfsUri;
    @Value("${hadoop.user}")
    private String user;

    @Bean()
    public Configuration configuration() {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", hdfsUri);
        System.setProperty("HADOOP_USER_NAME", user);
        System.setProperty("hadoop.home.dir", "D:\\hadoop-3.4.0");
        return configuration;
    }
}   

