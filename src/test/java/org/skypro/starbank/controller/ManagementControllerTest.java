package org.skypro.starbank.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skypro.starbank.model.mapper.ServiceInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ManagementController.class)
public class ManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BuildProperties buildProperties;

    @DisplayName("Должен возвращать информацию о сервисе, когда свойства заданы")
    @Test
    public void getServiceInfoReturnServiceInfo() throws Exception {

        String expectedName = "starbank-service";
        String expectedVersion = "1.0.0";

        when(buildProperties.getName()).thenReturn(expectedName);
        when(buildProperties.getVersion()).thenReturn(expectedVersion);

        ServiceInfoDto expectedDto = new ServiceInfoDto();
        expectedDto.setName(expectedName);
        expectedDto.setVersion(expectedVersion);

        mockMvc.perform(MockMvcRequestBuilders.get("/management/info"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.version").value(expectedVersion));
    }

    @DisplayName("Должен возвращать пустые поля, когда свойства не заданы")
    @Test
    public void getServiceInfoWhenPropertiesAreNullReturnEmptyFields() throws Exception {

        when(buildProperties.getName()).thenReturn(null);
        when(buildProperties.getVersion()).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/management/info"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.version").isEmpty());
    }
}
