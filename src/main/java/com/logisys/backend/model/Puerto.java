package com.logisys.backend.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "puertos")
public class Puerto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "puertoEntrega", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EnvioMaritimo> enviosMaritimos;

    public Puerto() {
    }

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

    public Set<EnvioMaritimo> getEnviosMaritimos() {
        return enviosMaritimos;
    }

    public void setEnviosMaritimos(Set<EnvioMaritimo> enviosMaritimos) {
        this.enviosMaritimos = enviosMaritimos;
    }
}
