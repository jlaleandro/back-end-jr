package com.jla.back_end_jr.common;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Envelope de resposta padrão.
 * - Campo data: payload de sucesso.
 * - Campo pagination: presente apenas em respostas paginadas.
 * 
 * Obs.: O JacksonConfig usa SNAKE_CASE globalmente, então os nomes JSON
 * serão "data" e "pagination" (e campos internos como page, page_size).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
  private final T data;
  private final Pagination pagination;

  public ApiResponse(T data, Pagination pagination) {
    this.data = data;
    this.pagination = pagination;
  }

  public static <T> ApiResponse<T> of(T data) {
    return new ApiResponse<>(data, null);
  }

  public static <T> ApiResponse<T> of(T data, Pagination pagination) {
    return new ApiResponse<>(data, pagination);
  }

  public T getData() { return data; }
  public Pagination getPagination() { return pagination; }
}