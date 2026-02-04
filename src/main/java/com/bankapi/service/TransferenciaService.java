package com.bankapi.service;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class TransferenciaService {

    private final DataSource dataSource;

    public TransferenciaService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean realizarTransferencia(String contaOrigem, String contaDestino, double valor) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                if (!contaExiste(connection, contaOrigem)) {
                    throw new IllegalArgumentException("Conta de origem não encontrada: " + contaOrigem);
                }
                if (!contaExiste(connection, contaDestino)) {
                    throw new IllegalArgumentException("Conta de destino não encontrada: " + contaDestino);
                }

                if (!saldoSuficiente(connection, contaOrigem, valor)) {
                    connection.rollback();
                    return false;
                }

                realizarAtualizacaoSaldos(connection, contaOrigem, contaDestino, valor);
                connection.commit();
                return true;
            } catch (SQLException | RuntimeException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao realizar transferência: " + e.getMessage(), e);
        }
    }

    private boolean contaExiste(Connection connection, String numeroConta) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuario WHERE numeroConta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, numeroConta);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private boolean saldoSuficiente(Connection connection, String numeroConta, double valor) throws SQLException {
        String sql = "SELECT saldo FROM usuario WHERE numeroConta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, numeroConta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double saldo = rs.getDouble("saldo");
                    return saldo >= valor;
                }
            }
        }
        return false;
    }

    private void realizarAtualizacaoSaldos(Connection connection, String contaOrigem, String contaDestino, double valor) throws SQLException {
        String updateOrigem = "UPDATE usuario SET saldo = saldo - ? WHERE numeroConta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateOrigem)) {
            stmt.setDouble(1, valor);
            stmt.setString(2, contaOrigem);
            stmt.executeUpdate();
        }

        String updateDestino = "UPDATE usuario SET saldo = saldo + ? WHERE numeroConta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateDestino)) {
            stmt.setDouble(1, valor);
            stmt.setString(2, contaDestino);
            stmt.executeUpdate();
        }

        String insertTransacao = "INSERT INTO transacao (conta_origem, conta_destino, valor, data_transacao) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        try (PreparedStatement stmt = connection.prepareStatement(insertTransacao)) {
            stmt.setString(1, contaOrigem);
            stmt.setString(2, contaDestino);
            stmt.setDouble(3, valor);
            stmt.executeUpdate();
        }
    }
}
