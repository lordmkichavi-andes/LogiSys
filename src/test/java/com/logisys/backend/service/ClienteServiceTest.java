package com.logisys.backend.service;

import com.logisys.backend.dto.ClienteDTO;
import com.logisys.backend.model.Cliente;
import com.logisys.backend.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private ClienteDTO clienteDTO;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNombre("John Doe");
        clienteDTO.setCorreoElectronico("john.doe@example.com");
        clienteDTO.setTelefono("123456789");

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("John Doe");
        cliente.setCorreoElectronico("john.doe@example.com");
        cliente.setTelefono("123456789");
    }

    @Test
    void whenCreateCliente_thenReturnsClienteDTO() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDTO savedClienteDTO = clienteService.createCliente(clienteDTO);

        assertThat(savedClienteDTO).isNotNull();
        assertThat(savedClienteDTO.getId()).isEqualTo(clienteDTO.getId());
        assertThat(savedClienteDTO.getNombre()).isEqualTo(clienteDTO.getNombre());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void whenGetAllClientes_thenReturnsListClienteDTO() {
        when(clienteRepository.findAll()).thenReturn(Collections.singletonList(cliente));

        List<ClienteDTO> clientes = clienteService.getAllClientes();

        assertThat(clientes).isNotNull();
        assertThat(clientes.size()).isEqualTo(1);
        assertThat(clientes.get(0).getNombre()).isEqualTo(clienteDTO.getNombre());
    }

    @Test
    void whenGetClienteById_thenReturnsClienteDTO() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteDTO foundClienteDTO = clienteService.getClienteById(1L);

        assertThat(foundClienteDTO).isNotNull();
        assertThat(foundClienteDTO.getId()).isEqualTo(clienteDTO.getId());
    }

    @Test
    void whenUpdateCliente_thenReturnsUpdatedClienteDTO() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDTO updatedClienteDTO = clienteService.updateCliente(1L, clienteDTO);

        assertThat(updatedClienteDTO).isNotNull();
        assertThat(updatedClienteDTO.getNombre()).isEqualTo(clienteDTO.getNombre());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void whenDeleteCliente_thenSuccess() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).deleteById(1L);

        assertDoesNotThrow(() -> clienteService.deleteCliente(1L));

        verify(clienteRepository).deleteById(1L);
    }
}
