package com.logisys.backend.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "bodegas")
public class Bodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "bodegaEntrega", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EnvioTerrestre> enviosTerrestres;

    public Bodega() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<EnvioTerrestre> getEnviosTerrestres() {
        return enviosTerrestres;
    }

    public void setEnviosTerrestres(Set<EnvioTerrestre> enviosTerrestres) {
        this.enviosTerrestres = enviosTerrestres;
    }
}
