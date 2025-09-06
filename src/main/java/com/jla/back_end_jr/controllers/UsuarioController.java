package com.jla.back_end_jr.controllers;

import com.jla.back_end_jr.dtos.UserDto;
import com.jla.back_end_jr.services.UsuarioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  private final UsuarioService service;

  public UsuarioController(UsuarioService service) {
    this.service = service;
  }

  @GetMapping
  public Map<String, Object> listar(
      @RequestParam(required = false) Boolean isActive,
      @RequestParam(required = false) String role,
      @RequestParam(required = false) String q,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdFrom,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdTo,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size
  ) {
    var content = service.listar(isActive, role, q, createdFrom, createdTo, page, size);
    var total = service.contar(isActive, role, q, createdFrom, createdTo);

    Map<String, Object> body = new HashMap<>();
    body.put("content", content);
    body.put("page", page);
    body.put("size", size);
    body.put("total", total);
    return body;
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> get(@PathVariable Integer id) {
    return service.buscarPorId(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
