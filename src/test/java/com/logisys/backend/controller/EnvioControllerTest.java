package com.logisys.backend.controller;

import com.logisys.backend.dto.EnvioDTO;
import com.logisys.backend.service.EnvioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnvioControllerTest {

    @Mock
    private EnvioService envioService;

    @InjectMocks
    private EnvioController envioController;

    @Test
    void whenCreateEnvio_thenReturnsEnvioDTO() {
        EnvioDTO envioDTORequest = new EnvioDTO();
        EnvioDTO envioDTOResponse = new EnvioDTO();

        when(envioService.createEnvio(any(EnvioDTO.class))).thenReturn(envioDTOResponse);

        ResponseEntity<EnvioDTO> responseEntity = envioController.createEnvio(envioDTORequest);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(envioDTOResponse);
    }

    @Test
    void whenGetAllEnvios_thenReturnsListOfEnvioDTO() {
        List<EnvioDTO> expectedEnvios = Collections.singletonList(new EnvioDTO());

        when(envioService.getAllEnvios()).thenReturn(expectedEnvios);

        ResponseEntity<List<EnvioDTO>> responseEntity = envioController.getAllEnvios();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(expectedEnvios);
    }
}
