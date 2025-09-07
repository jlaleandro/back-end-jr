package com.jla.back_end_jr.dtos;

import java.time.Instant;

public record UserDto(
    Integer id,
    String name,
    String email,
    String role,
    Boolean isActive,
    Instant createdAt
) {}
