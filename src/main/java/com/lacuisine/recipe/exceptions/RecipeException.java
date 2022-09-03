package com.lacuisine.recipe.exceptions;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final HttpStatus status;
  private final String statusCode;
  private final String statusMessage;

  /**
   * Constructor.
   *
   * @param message message
   * @param status status
   * @param statusMessage status message
   * @param statusCode status code
   */
  public RecipeException(HttpStatus status, String statusMessage, String statusCode) {
    super(statusMessage);
    this.status = status;
    this.statusMessage = statusMessage;
    this.statusCode = statusCode;
  }

}
