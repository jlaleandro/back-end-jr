package com.jla.back_end_jr.common;

import java.time.OffsetDateTime;

/**
 * Envelope de erro simples.
 * status: HTTP status code (ex.: 404)
 * error:  razão resumida (ex.: "Not Found")
 * message: detalhe humano
 * path: endpoint requisitado
 */
public class ErrorResponse {
  private final OffsetDateTime timestamp = OffsetDateTime.now();
  private final int status;
  private final String error;
  private final String message;
  private final String path;

  public ErrorResponse(int status, String error, String message, String path) {
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
  }

  public OffsetDateTime getTimestamp() { return timestamp; }
  public int getStatus() { return status; }
  public String getError() { return error; }
  public String getMessage() { return message; }
  public String getPath() { return path; }
}