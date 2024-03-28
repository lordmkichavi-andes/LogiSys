package com.logisys.backend.service;

import com.logisys.backend.dto.ClienteDTO;
import com.logisys.backend.model.Cliente;
import com.logisys.backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        Cliente cliente = convertToEntity(clienteDTO);
        Cliente nuevoCliente = clienteRepository.save(cliente);
        return convertToDto(nuevoCliente);
    }

    public List<ClienteDTO> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ClienteDTO getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return convertToDto(cliente);
    }

    @Transactional
    public ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setCorreoElectronico(clienteDTO.getCorreoElectronico());
        cliente.setTelefono(clienteDTO.getTelefono());
        Cliente actualizadoCliente = clienteRepository.save(cliente);
        return convertToDto(actualizadoCliente);
    }

    public void deleteCliente(Long id) {
        clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        clienteRepository.deleteById(id);
    }

    private ClienteDTO convertToDto(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setCorreoElectronico(cliente.getCorreoElectronico());
        clienteDTO.setTelefono(cliente.getTelefono());
        return clienteDTO;
    }

    private Cliente convertToEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setId(clienteDTO.getId());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setCorreoElectronico(clienteDTO.getCorreoElectronico());
        cliente.setTelefono(clienteDTO.getTelefono());
        return cliente;
    }
}
