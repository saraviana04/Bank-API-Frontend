package com.bankapi.controller;

import com.bankapi.dto.ApiError;
import com.bankapi.dto.ApiMessage;
import com.bankapi.dto.TransferenciaRequest;
import com.bankapi.service.TransferenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacoes")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    public TransferenciaController(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    @PostMapping("/realizar")
    public ResponseEntity<?> realizarTransferencia(@RequestBody TransferenciaRequest request) {
        try {
            boolean transferenciaRealizada = transferenciaService.realizarTransferencia(
                    request.getContaOrigem(),
                    request.getContaDestino(),
                    request.getValor()
            );

            if (transferenciaRealizada) {
                return ResponseEntity.ok(new ApiMessage("Transferência realizada com sucesso!"));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError("Saldo insuficiente para realizar a transferência."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiError(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError("Erro inesperado: " + e.getMessage()));
        }
    }
}
