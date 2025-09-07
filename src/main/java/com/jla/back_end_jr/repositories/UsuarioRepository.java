package com.jla.back_end_jr.repositories;

import com.jla.back_end_jr.dtos.UserDto;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
  List<UserDto> findAll();
  Optional<UserDto> findById(Integer id);
  long count();
  List<UserDto> findAllFiltered(Boolean isActive, String role, String q,
                                Instant createdFrom, Instant createdTo, int page, int size);
  long countFiltered(Boolean isActive, String role, String q,
                     Instant createdFrom, Instant createdTo);
}