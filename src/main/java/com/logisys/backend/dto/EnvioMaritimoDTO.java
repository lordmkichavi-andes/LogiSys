package com.logisys.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EnvioMaritimoDTO extends EnvioDTO {

    @NotBlank(message = "El número de flota es obligatorio")
    private String numeroFlota;

    @NotNull(message = "El ID del puerto de entrega no puede ser nulo")
    private Long puertoId;

    @Override
    public double calcularDescuento() {
        return this.getCantidad() > 10 ? 0.03 : 0;
    }

    public String getNumeroFlota() {
        return numeroFlota;
    }

    public void setNumeroFlota(String numeroFlota) {
        this.numeroFlota = numeroFlota;
    }

    public Long getPuertoId() {
        return puertoId;
    }

    public void setPuertoId(Long puertoId) {
        this.puertoId = puertoId;
    }
}
