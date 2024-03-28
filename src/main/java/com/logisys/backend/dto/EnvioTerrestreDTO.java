package com.logisys.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EnvioTerrestreDTO extends EnvioDTO {

    @NotBlank(message = "La placa del vehículo es obligatoria")
    private String placaVehiculo;

    @NotNull(message = "El ID de la bodega de entrega no puede ser nulo")
    private Long bodegaId;

    @Override
    public double calcularDescuento() {
        return this.getCantidad() > 10 ? 0.05 : 0;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public Long getBodegaId() {
        return bodegaId;
    }

    public void setBodegaId(Long bodegaId) {
        this.bodegaId = bodegaId;
    }
}
