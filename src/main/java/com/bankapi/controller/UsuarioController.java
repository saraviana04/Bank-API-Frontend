package com.bankapi.controller;

import com.bankapi.dao.UsuarioDAO;
import com.bankapi.dto.ApiError;
import com.bankapi.dto.UsuarioCriadoResponse;
import com.bankapi.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/users")
public class UsuarioController {

    private final DataSource dataSource;

    public UsuarioController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getIdade() < 18) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError("A idade mínima para cadastro é 18 anos."));
        }

        try (Connection connection = dataSource.getConnection()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(connection);

            if (usuarioDAO.cpfExiste(usuario.getCpf())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ApiError("CPF já cadastrado."));
            }

            String numeroConta = usuarioDAO.cadastrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new UsuarioCriadoResponse("Usuário cadastrado com sucesso!", numeroConta));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError("Erro ao acessar o banco de dados: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable int id) {
        try (Connection connection = dataSource.getConnection()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
            Usuario usuario = usuarioDAO.buscarUsuarioPorId(id);

            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiError("Usuário com ID " + id + " não encontrado."));
            }

            return ResponseEntity.ok(usuario);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError("Erro ao acessar o banco de dados: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTodosUsuarios() {
        try (Connection connection = dataSource.getConnection()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
            return ResponseEntity.ok(usuarioDAO.listarTodosUsuarios());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError("Erro ao acessar o banco de dados: " + e.getMessage()));
        }
    }
}
