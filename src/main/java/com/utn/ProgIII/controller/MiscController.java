package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.ViewDolarDTO;
import com.utn.ProgIII.service.interfaces.MiscService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Clase que se usa para cosas miscelaneas, no necesariamente relacionadas con el modelo funcional
 */
@RestController
@RequestMapping("/misc")
@Tag(name = "Miscelaneos", description = "Operaciones relacionadas con acciones miscelaneas")
/**
 * Clase que maneja requests sobre cosas miscelaneas
 */
public class MiscController {

    @Autowired
    private MiscService miscservice;


    @GetMapping("/dollar")
    @Operation(summary = "Valor actual del dolar oficial", description = "Muestra un objeto del valor actual del dolar oficial\n Puede retornar otros codigos de errores externo a este servicio.")
    @ApiResponse(responseCode = "200",description = "Precio encontrado", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ViewDolarDTO.class)
    ))
    @ApiResponse(responseCode = "500",description = "Error interno en el servidor", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "Un error inesperado ocurrio en el servicio.")
    ))
    public ResponseEntity<ViewDolarDTO> viewDollarPrice()
    {
        return ResponseEntity.ok().body(miscservice.searchDollarPrice());
    }
}
