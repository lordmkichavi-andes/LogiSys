package com.logisys.backend.controller;

import com.logisys.backend.dto.ClienteDTO;
import com.logisys.backend.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @Test
    void createCliente() {
        ClienteDTO clienteDTO = new ClienteDTO();
        given(clienteService.createCliente(clienteDTO)).willReturn(clienteDTO);

        ResponseEntity<ClienteDTO> response = clienteController.createCliente(clienteDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(clienteDTO);
    }

    @Test
    void getAllClientes() {
        ClienteDTO clienteDTO = new ClienteDTO();
        List<ClienteDTO> clienteDTOList = Collections.singletonList(clienteDTO);
        given(clienteService.getAllClientes()).willReturn(clienteDTOList);

        ResponseEntity<List<ClienteDTO>> response = clienteController.getAllClientes();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(clienteDTOList);
    }

    @Test
    void deleteClienteTest() {
        Long clienteId = 1L;

        doNothing().when(clienteService).deleteCliente(clienteId);

        ResponseEntity<Void> response = clienteController.deleteCliente(clienteId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(clienteService, times(1)).deleteCliente(clienteId);
    }

    @Test
    void getClienteByIdTest() {
        Long clienteId = 1L;
        ClienteDTO mockCliente = new ClienteDTO();
        mockCliente.setId(clienteId);
        mockCliente.setNombre("Nombre de Prueba");
        mockCliente.setCorreoElectronico("correo@ejemplo.com");
        mockCliente.setTelefono("123456789");

        when(clienteService.getClienteById(eq(clienteId))).thenReturn(mockCliente);

        ResponseEntity<ClienteDTO> response = clienteController.getClienteById(clienteId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(clienteId);
        assertThat(response.getBody().getNombre()).isEqualTo("Nombre de Prueba");
        assertThat(response.getBody().getCorreoElectronico()).isEqualTo("correo@ejemplo.com");
        assertThat(response.getBody().getTelefono()).isEqualTo("123456789");
    }

    @Test
    void updateClienteTest() {
        Long clienteId = 1L;
        ClienteDTO clienteDTOToUpdate = new ClienteDTO();
        clienteDTOToUpdate.setNombre("Nombre Actualizado");
        clienteDTOToUpdate.setCorreoElectronico("correo.actualizado@ejemplo.com");
        clienteDTOToUpdate.setTelefono("987654321");

        ClienteDTO updatedClienteDTO = new ClienteDTO();
        updatedClienteDTO.setId(clienteId);
        updatedClienteDTO.setNombre(clienteDTOToUpdate.getNombre());
        updatedClienteDTO.setCorreoElectronico(clienteDTOToUpdate.getCorreoElectronico());
        updatedClienteDTO.setTelefono(clienteDTOToUpdate.getTelefono());

        when(clienteService.updateCliente(eq(clienteId), any(ClienteDTO.class))).thenReturn(updatedClienteDTO);

        ResponseEntity<ClienteDTO> response = clienteController.updateCliente(clienteId, clienteDTOToUpdate);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(clienteId);
        assertThat(response.getBody().getNombre()).isEqualTo("Nombre Actualizado");
        assertThat(response.getBody().getCorreoElectronico()).isEqualTo("correo.actualizado@ejemplo.com");
        assertThat(response.getBody().getTelefono()).isEqualTo("987654321");

        verify(clienteService).updateCliente(eq(clienteId), any(ClienteDTO.class));
    }
}
