package com.treblemaker.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationProvider {

    @Value("${spring.datasource.url}")
    String springDatasourceUrl;

    @Value("${spring.datasource.username}")
    String springDatasourceUsername;

    @Value("${spring.datasource.password}")
    String springDatasourcePassword;

    @Value("${s3.bucket}")
    String s3Bucket;

    @Value("${server.port}")
    String serverPort;

    public String getSpringDatasourceUrl() {
        return springDatasourceUrl;
    }

    public String getSpringDatasourceUsername() {
        return springDatasourceUsername;
    }

    public String getSpringDatasourcePassword() {
        return springDatasourcePassword;
    }

    public String getS3Bucket() {
        return s3Bucket;
    }

    public String getServerPort(){return serverPort;}
}
