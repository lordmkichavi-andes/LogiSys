package com.logisys.backend.model;

import javax.persistence.*;

@Entity
@Table(name = "envios_maritimos")
public class EnvioMaritimo extends Envio {

    @Column(name = "numero_flota", nullable = false)
    private String numeroFlota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puerto_id", nullable = false)
    private Puerto puertoEntrega;

    public EnvioMaritimo() {}

    public String getNumeroFlota() {
        return numeroFlota;
    }

    public void setNumeroFlota(String numeroFlota) {
        this.numeroFlota = numeroFlota;
    }

    public Puerto getPuertoEntrega() {
        return puertoEntrega;
    }

    public void setPuertoEntrega(Puerto puertoEntrega) {
        this.puertoEntrega = puertoEntrega;
    }
}
