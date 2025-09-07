package com.jla.back_end_jr.configurations;

import com.jla.back_end_jr.common.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Tratamento global de erros mínimos. - 404 quando um recurso não é encontrado
 * (NoSuchElementException). - 400 em erros de parâmetro. - 500 padrão para erros inesperados.
 *
 * <p>Dica: loga erros de forma simples.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorResponse> notFound(NoSuchElementException ex, HttpServletRequest req) {
    log.warn("Recurso não encontrado: {}", ex.getMessage());
    var body = new ErrorResponse(404, "Not Found", ex.getMessage(), req.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }

  @ExceptionHandler({
    MethodArgumentTypeMismatchException.class,
    MissingServletRequestParameterException.class,
    IllegalArgumentException.class
  })
  public ResponseEntity<ErrorResponse> badRequest(Exception ex, HttpServletRequest req) {
    log.info("Parâmetro inválido: {}", ex.getMessage());
    var body = new ErrorResponse(400, "Bad Request", ex.getMessage(), req.getRequestURI());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> generic(Exception ex, HttpServletRequest req) {
    log.error("Erro inesperado", ex);
    var body =
        new ErrorResponse(500, "Internal Server Error", "Erro inesperado", req.getRequestURI());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }
}
