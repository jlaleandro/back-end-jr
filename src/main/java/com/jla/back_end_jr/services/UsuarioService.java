package com.jla.back_end_jr.services;

import com.jla.back_end_jr.dtos.UserDto;
import com.jla.back_end_jr.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

  private final UsuarioRepository repo;

  public UsuarioService(UsuarioRepository repo) {
    this.repo = repo;
  }

  public List<UserDto> listar(Boolean isActive, String role, String q,
                              LocalDateTime createdFrom, LocalDateTime createdTo,
                              int page, int size) {
    return repo.findAllFiltered(isActive, role, q, createdFrom, createdTo, page, size);
  }

  public long contar(Boolean isActive, String role, String q,
                     LocalDateTime createdFrom, LocalDateTime createdTo) {
    return repo.countFiltered(isActive, role, q, createdFrom, createdTo);
  }

  public Optional<UserDto> buscarPorId(Integer id) {
    return repo.findById(id);
  }
}
