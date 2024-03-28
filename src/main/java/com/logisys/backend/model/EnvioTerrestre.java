package com.logisys.backend.model;

import javax.persistence.*;

@Entity
@Table(name = "envios_terrestres")
public class EnvioTerrestre extends Envio {

    @Column(name = "placa_vehiculo", nullable = false)
    private String placaVehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bodega_id", nullable = false)
    private Bodega bodegaEntrega;

    public EnvioTerrestre() {
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public Bodega getBodegaEntrega() {
        return bodegaEntrega;
    }

    public void setBodegaEntrega(Bodega bodegaEntrega) {
        this.bodegaEntrega = bodegaEntrega;
    }
}
