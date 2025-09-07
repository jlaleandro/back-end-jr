package com.jla.back_end_jr.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jla.back_end_jr.dtos.UserDto;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

@Repository
@Profile("mock")
public class UsuarioRepositoryMock implements UsuarioRepository {

  private final ObjectMapper mapper;
  private List<UserDto> cache = new ArrayList<>();

  public UsuarioRepositoryMock(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @PostConstruct
  void load() {
    try (InputStream in = new ClassPathResource("mock/mock-users.json").getInputStream()) {
      cache = mapper.readValue(in, new TypeReference<List<UserDto>>() {});
    } catch (Exception e) {
      Throwable root = e;
      while (root.getCause() != null) root = root.getCause();
      System.err.println(">> Falha ao carregar mock-users.json (causa raiz): "
          + root.getClass().getSimpleName() + " - " + String.valueOf(root.getMessage()));
      throw new IllegalStateException("Falha ao carregar mock-users.json", e);
    }
  }

  @Override public List<UserDto> findAll() { return List.copyOf(cache); }

  @Override public Optional<UserDto> findById(Integer id) {
    return cache.stream().filter(u -> Objects.equals(u.id(), id)).findFirst();
  }

  @Override public long count() { return cache.size(); }

  @Override
  public List<UserDto> findAllFiltered(Boolean isActive, String role, String q,
                                       Instant createdFrom, Instant createdTo, int page, int size) {
    Stream<UserDto> s = cache.stream();
    if (isActive != null) {
      s = s.filter(u -> Objects.equals(u.isActive(), isActive));
    }
    if (role != null && !role.isBlank()) {
      String roleNorm = role.trim().toLowerCase();
      s = s.filter(u -> u.role() != null && u.role().toLowerCase().equals(roleNorm));
    }
    if (q != null && !q.isBlank()) {
      String term = q.toLowerCase();
      s = s.filter(u ->
          (u.name() != null && u.name().toLowerCase().contains(term)) ||
          (u.email() != null && u.email().toLowerCase().contains(term))
      );
    }
    if (createdFrom != null) {
      s = s.filter(u -> u.createdAt() != null && !u.createdAt().isBefore(createdFrom));
    }
    if (createdTo != null) {
      s = s.filter(u -> u.createdAt() != null && !u.createdAt().isAfter(createdTo));
    }
    s = s.sorted(Comparator.comparing(UserDto::createdAt,
         Comparator.nullsLast(Comparator.naturalOrder())).reversed());
    return s.skip((long) page * size).limit(size).toList();
  }

  @Override
  public long countFiltered(Boolean isActive, String role, String q,
                            Instant createdFrom, Instant createdTo) {
    return findAllFiltered(isActive, role, q, createdFrom, createdTo, 0, Integer.MAX_VALUE).size();
  }
}
