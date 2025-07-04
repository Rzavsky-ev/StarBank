package org.skypro.starbank.controller;

import org.skypro.starbank.model.mapper.ServiceInfoDto;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class ManagementController {

    private final BuildProperties buildProperties;

    public ManagementController(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @GetMapping("/info")
    public ServiceInfoDto getServiceInfo() {
        ServiceInfoDto info = new ServiceInfoDto();
        info.setName(buildProperties.getName());
        info.setVersion(buildProperties.getVersion());
        return info;
    }
}