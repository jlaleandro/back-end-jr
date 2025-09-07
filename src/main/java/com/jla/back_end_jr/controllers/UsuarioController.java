package com.jla.back_end_jr.controllers;

import com.jla.back_end_jr.common.ApiResponse;
import com.jla.back_end_jr.common.Pagination;
import com.jla.back_end_jr.dtos.UserDto;
import com.jla.back_end_jr.services.UsuarioService;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Boas práticas mínimas aplicadas: - Paginação 1-based: page (default 1), page_size (default 10,
 * máx configurável, default 50). - Envelope padrão de resposta: { data: [...], pagination: {...} }
 * ou { data: { ... } }. - GET /api/usuarios/{id} -> 200 (com data) ou 404 (erro no envelope). -
 * Logs simples. - Variáveis de ambiente via @Value com defaults.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

  private final UsuarioService service;

  /**
   * Máximo de itens por página. Pode ser sobrescrito com variável de ambiente APP_MAX_PAGE_SIZE.
   */
  private final int maxPageSize;

  public UsuarioController(
      UsuarioService service, @Value("${APP_MAX_PAGE_SIZE:50}") int maxPageSize) {
    this.service = service;
    this.maxPageSize = maxPageSize;
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<UserDto>>> list(
      @RequestParam(name = "isActive", required = false) Boolean isActive,
      @RequestParam(name = "role", required = false) String role,
      @RequestParam(name = "q", required = false) String q,
      @RequestParam(name = "created_from", required = false) Instant createdFrom,
      @RequestParam(name = "created_to", required = false) Instant createdTo,
      @RequestParam(name = "page", defaultValue = "1") Integer page,
      @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize) {

    // Sanitização básica
    if (page == null || page < 1) page = 1;
    if (pageSize == null || pageSize < 1) pageSize = 10;
    if (pageSize > maxPageSize) pageSize = maxPageSize;

    int offsetPage = page - 1; // internamente 0-based

    log.info(
        "Listando usuários - filtros: isActive={}, role={}, q={}, created_from={}, created_to={},"
            + " page={}, page_size={}",
        isActive,
        role,
        q,
        createdFrom,
        createdTo,
        page,
        pageSize);

    List<UserDto> data =
        service.buscarTodosFiltrados(
            isActive, role, q, createdFrom, createdTo, offsetPage, pageSize);
    long total = service.contar(isActive, role, q, createdFrom, createdTo);

    var pagination = new Pagination(page, pageSize, total);
    return ResponseEntity.ok(ApiResponse.of(data, pagination));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<UserDto>> get(@PathVariable Integer id) {
    log.info("Buscando usuário id={}", id);
    var user =
        service
            .buscarPorId(id)
            .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado: id=" + id));
    return ResponseEntity.ok(ApiResponse.of(user));
  }
}
