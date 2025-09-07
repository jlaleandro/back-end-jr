package com.jla.back_end_jr.common;

/**
 * Metadados de paginação.
 * Padrões: page inicia em 1 (1-based), page_size máximo 50.
 */
public class Pagination {
  private final int page;
  private final int pageSize;
  private final long total;
  private final int totalPages;

  public Pagination(int page, int pageSize, long total) {
    this.page = page;
    this.pageSize = pageSize;
    this.total = total;
    this.totalPages = (int) Math.ceil(total / (double) Math.max(pageSize, 1));
  }

  public int getPage() { return page; }
  public int getPageSize() { return pageSize; }
  public long getTotal() { return total; }
  public int getTotalPages() { return totalPages; }
}