package com.higuti.bank.repository;

import com.higuti.bank.model.Cliente;
import com.higuti.bank.model.Conta;
import com.higuti.bank.model.ContaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findByCliente(Cliente cliente);

    Optional<Conta> findByNumeroContaAndAgenciaAndContaStatus(String numeroConta, String agencia, ContaStatus contaStatus);

    Optional<Conta> findByClienteAndNumeroConta(Cliente cliente, String numeroConta);

    Optional<Conta> findByIdAndContaStatus(Long id, ContaStatus contaStatus);

    @Modifying
    @Query("UPDATE Conta c SET c.contaStatus = :novoStatus WHERE c.cliente.id = :clienteId")
    void atualizarStatusContasDoCliente(@Param("clienteId") Long clienteId, @Param("novoStatus") ContaStatus novoStatus);
}
