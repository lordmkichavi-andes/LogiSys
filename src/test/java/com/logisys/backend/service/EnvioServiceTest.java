package com.logisys.backend.service;

import com.logisys.backend.dto.EnvioDTO;
import com.logisys.backend.dto.EnvioMaritimoDTO;
import com.logisys.backend.dto.EnvioTerrestreDTO;
import com.logisys.backend.model.*;
import com.logisys.backend.repository.BodegaRepository;
import com.logisys.backend.repository.ClienteRepository;
import com.logisys.backend.repository.EnvioRepository;
import com.logisys.backend.repository.PuertoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.logisys.backend.service.EnvioService.ENVIO_MARITIMO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnvioServiceTest {

    public static final String TIPO_MARITIMO = "Tipo Maritimo";
    public static final String TIPO_TERRESTRE = "Tipo Terrestre";
    @Mock
    private EnvioRepository envioRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PuertoRepository puertoRepository;

    @Mock
    private BodegaRepository bodegaRepository;

    @InjectMocks
    private EnvioService envioService;

    private Cliente cliente;
    private Puerto puerto;
    private Bodega bodega;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);

        puerto = new Puerto();
        puerto.setId(1L);

        bodega = new Bodega();
        bodega.setId(1L);
    }

    @ParameterizedTest
    @CsvSource({
            "envioMaritimo, 11, 1000.0, 0.97",
            "envioMaritimo, 9, 1000.0, 1.0",
            "envioTerrestre, 11, 1000.0, 0.95",
            "envioTerrestre, 9, 1000.0, 1.0"
    })
    void testEnvioConYSinDescuento(String tipo, int cantidad, double precioBase, double factorPrecioEsperado) {
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        configurarMocksSegunTipo(tipo);
        EnvioDTO envioDTORequest = establecerEnvioDTO(tipo, cantidad, precioBase);
        double precioEsperado = precioBase * factorPrecioEsperado;

        EnvioDTO result = envioService.createEnvio(envioDTORequest);

        assertThat(result).isNotNull();
        assertThat(result.getPrecioEnvio()).isEqualTo(precioEsperado);
    }

    @Test
    void getAllEnvios_ShouldReturnEnvioDTOList() {
        EnvioMaritimo envioMaritimo = new EnvioMaritimo();
        envioMaritimo.setId(1L);
        envioMaritimo.setTipoProducto(TIPO_MARITIMO);
        envioMaritimo.setCantidad(10);
        envioMaritimo.setPrecioEnvio(1000.0);
        envioMaritimo.setFechaRegistro(new Date());
        envioMaritimo.setNumeroGuia("GU123456M");

        EnvioTerrestre envioTerrestre = new EnvioTerrestre();
        envioTerrestre.setId(2L);
        envioTerrestre.setTipoProducto(TIPO_TERRESTRE);
        envioTerrestre.setCantidad(5);
        envioTerrestre.setPrecioEnvio(500.0);
        envioTerrestre.setFechaRegistro(new Date());
        envioTerrestre.setNumeroGuia("GU654321T");

        when(envioRepository.findAll()).thenReturn(Arrays.asList(envioMaritimo, envioTerrestre));

        List<EnvioDTO> result = envioService.getAllEnvios();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(envioMaritimo.getId());
        assertThat(result.get(1).getId()).isEqualTo(envioTerrestre.getId());
    }

    private void configurarMocksSegunTipo(String tipo) {
        if (ENVIO_MARITIMO.equals(tipo)) {
            when(puertoRepository.findById(puerto.getId())).thenReturn(Optional.of(puerto));
        } else {
            when(bodegaRepository.findById(bodega.getId())).thenReturn(Optional.of(bodega));
        }
        when(envioRepository.save(any(Envio.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    private EnvioDTO establecerEnvioDTO(String tipo, int cantidad, double precioEnvio) {
        EnvioDTO dto;
        if (ENVIO_MARITIMO.equals(tipo)) {
            EnvioMaritimoDTO maritimoDTO = new EnvioMaritimoDTO();
            maritimoDTO.setPuertoId(puerto.getId());
            dto = maritimoDTO;
        } else {
            EnvioTerrestreDTO terrestreDTO = new EnvioTerrestreDTO();
            terrestreDTO.setBodegaId(bodega.getId());
            dto = terrestreDTO;
        }
        dto.setClienteId(cliente.getId());
        dto.setTipo(tipo);
        dto.setCantidad(cantidad);
        dto.setPrecioEnvio(precioEnvio);
        dto.setTipoProducto("Producto de prueba");
        dto.setNumeroGuia("GU123ABC");
        return dto;
    }
}
