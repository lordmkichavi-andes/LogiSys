package com.logisys.backend.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "tipo", visible = true, include = JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EnvioMaritimoDTO.class, name = "envioMaritimo"),
        @JsonSubTypes.Type(value = EnvioTerrestreDTO.class, name = "envioTerrestre")
})
public class EnvioDTO {

    private Long id;
    @NotBlank(message = "El tipo de producto es obligatorio")
    private String tipoProducto;

    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    @NotNull(message = "La fecha de registro no puede ser nula")
    private Date fechaRegistro;
    @NotNull(message = "La fecha de entrega no puede ser nula")
    private Date fechaEntrega;

    @NotNull(message = "El precio de envío no puede ser nulo")
    @Positive(message = "El precio de envío debe ser positivo")
    private Double precioEnvio;
    @NotBlank(message = "El número de guía es obligatorio")
    private String numeroGuia;

    @NotNull(message = "El ID del cliente no puede ser nulo")
    private Long clienteId;
    private String tipo;

    public double calcularDescuento() {
        return 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Double getPrecioEnvio() {
        return precioEnvio;
    }

    public void setPrecioEnvio(Double precioEnvio) {
        this.precioEnvio = precioEnvio;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
