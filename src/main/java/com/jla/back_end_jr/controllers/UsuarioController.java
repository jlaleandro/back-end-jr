package com.jla.back_end_jr.controllers;

import com.jla.back_end_jr.dtos.UserDto;
import com.jla.back_end_jr.services.UsuarioService;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  private final UsuarioService service;

  public UsuarioController(UsuarioService service) {
    this.service = service;
  }

  @GetMapping
  public Map<String, Object> listar(
      @RequestParam(name = "isActive", required = false) Boolean isActive,
      @RequestParam(name = "role", required = false) String role,
      @RequestParam(name = "q", required = false) String q,
      @RequestParam(name = "createdFrom", required = false) Instant createdFrom,
      @RequestParam(name = "createdTo", required = false) Instant createdTo,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "20") int size) {
    List<UserDto> content = service.listar(isActive, role, q, createdFrom, createdTo, page, size);
    long total = service.contar(isActive, role, q, createdFrom, createdTo);
    Map<String, Object> body = new HashMap<>();
    body.put("content", content);
    body.put("page", page);
    body.put("size", size);
    body.put("total", total);
    return body;
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> get(@PathVariable Integer id) {
    return service
        .buscarPorId(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
