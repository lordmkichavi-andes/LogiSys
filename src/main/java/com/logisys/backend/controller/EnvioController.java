package com.logisys.backend.controller;

import com.logisys.backend.dto.EnvioDTO;
import com.logisys.backend.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    private final EnvioService envioService;

    @Autowired
    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @PostMapping("/")
    public ResponseEntity<EnvioDTO> createEnvio(@Valid @RequestBody EnvioDTO envioDTO) {
        EnvioDTO nuevoEnvio = envioService.createEnvio(envioDTO);
        return ResponseEntity.ok(nuevoEnvio);
    }

    @GetMapping("/")
    public ResponseEntity<List<EnvioDTO>> getAllEnvios() {
        List<EnvioDTO> envios = envioService.getAllEnvios();
        return ResponseEntity.ok(envios);
    }
}
