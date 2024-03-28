package com.logisys.backend.service;

import com.logisys.backend.dto.EnvioDTO;
import com.logisys.backend.dto.EnvioMaritimoDTO;
import com.logisys.backend.dto.EnvioTerrestreDTO;
import com.logisys.backend.model.*;
import com.logisys.backend.repository.BodegaRepository;
import com.logisys.backend.repository.ClienteRepository;
import com.logisys.backend.repository.EnvioRepository;
import com.logisys.backend.repository.PuertoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnvioService {

    public static final String ENVIO_TERRESTRE = "envioTerrestre";
    public static final String ENVIO_MARITIMO = "envioMaritimo";
    private final EnvioRepository envioRepository;
    private final ClienteRepository clienteRepository;
    private final PuertoRepository puertoRepository;
    private final BodegaRepository bodegaRepository;

    @Autowired
    public EnvioService(EnvioRepository envioRepository, ClienteRepository clienteRepository, PuertoRepository puertoRepository, BodegaRepository bodegaRepository) {
        this.envioRepository = envioRepository;
        this.clienteRepository = clienteRepository;
        this.puertoRepository = puertoRepository;
        this.bodegaRepository = bodegaRepository;
    }

    @Transactional
    public EnvioDTO createEnvio(EnvioDTO envioDTO) {
        Envio envio = convertToEntity(envioDTO);
        Envio nuevoEnvio = envioRepository.save(envio);
        return convertToDto(nuevoEnvio);
    }

    public List<EnvioDTO> getAllEnvios() {
        List<Envio> envios = envioRepository.findAll();
        return envios.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private EnvioDTO convertToDto(Envio envio) {
        EnvioDTO dto = new EnvioDTO();
        dto.setId(envio.getId());
        dto.setTipoProducto(envio.getTipoProducto());
        dto.setCantidad(envio.getCantidad());
        dto.setFechaRegistro(envio.getFechaRegistro());
        dto.setFechaEntrega(envio.getFechaEntrega());
        dto.setPrecioEnvio(envio.getPrecioEnvio());
        dto.setNumeroGuia(envio.getNumeroGuia());
        dto.setClienteId(envio.getCliente() != null ? envio.getCliente().getId() : null);
        return dto;
    }

    private Envio convertToEntity(EnvioDTO dto) {
        Envio envio;
        double descuento = dto.calcularDescuento();

        if (dto.getTipo().equals(ENVIO_MARITIMO)) {
            EnvioMaritimoDTO maritimoDTO = (EnvioMaritimoDTO) dto;
            EnvioMaritimo envioMaritimo = new EnvioMaritimo();
            envioMaritimo.setPuertoEntrega(puertoRepository.findById(maritimoDTO.getPuertoId())
                    .orElseThrow(() -> new RuntimeException("Puerto no encontrado")));
            envioMaritimo.setNumeroFlota(maritimoDTO.getNumeroFlota());
            envio = envioMaritimo;
        } else if (dto.getTipo().equals(ENVIO_TERRESTRE)) {
            EnvioTerrestreDTO terrestreDTO = (EnvioTerrestreDTO) dto;
            EnvioTerrestre envioTerrestre = new EnvioTerrestre();
            envioTerrestre.setBodegaEntrega(bodegaRepository.findById(terrestreDTO.getBodegaId())
                    .orElseThrow(() -> new RuntimeException("Bodega no encontrada")));
            envioTerrestre.setPlacaVehiculo(terrestreDTO.getPlacaVehiculo());
            envio = envioTerrestre;
        } else {
            throw new RuntimeException("Tipo de envío desconocido: " + dto.getTipo());
        }

        if (descuento > 0) {
            double precioConDescuento = dto.getPrecioEnvio() * (1 - descuento);
            dto.setPrecioEnvio(precioConDescuento);
        }

        setCommonEnvioProperties(envio, dto);

        return envio;
    }


    private void setCommonEnvioProperties(Envio envio, EnvioDTO dto) {
        envio.setId(dto.getId());
        envio.setTipoProducto(dto.getTipoProducto());
        envio.setCantidad(dto.getCantidad());
        envio.setFechaRegistro(dto.getFechaRegistro());
        envio.setFechaEntrega(dto.getFechaEntrega());
        envio.setPrecioEnvio(dto.getPrecioEnvio());
        envio.setNumeroGuia(dto.getNumeroGuia());

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + dto.getClienteId()));
        envio.setCliente(cliente);
    }
}
