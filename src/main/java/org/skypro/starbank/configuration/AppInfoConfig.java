package org.skypro.starbank.configuration;

import org.springframework.boot.actuate.info.BuildInfoContributor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppInfoConfig {
    @Bean
    public BuildInfoContributor customBuildInfoContributor(BuildProperties buildProperties) {
        return new BuildInfoContributor(buildProperties);
    }
}
