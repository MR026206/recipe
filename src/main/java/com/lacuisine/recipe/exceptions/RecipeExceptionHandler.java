package com.lacuisine.recipe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.lacuisine.recipe.constants.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RecipeExceptionHandler {


  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApplicationError> handleNullPointerExceptions(Exception e) {
    log.error(e.getMessage());
    return buildAndResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),
        ApplicationConstants.ERROR_STATUS_CODE);
  }



  @ExceptionHandler(RecipeException.class)
  public ResponseEntity<ApplicationError> handleRecipeExceptions(RecipeException e) {
    log.error(e.getMessage());
    return buildAndResponseEntity(e.getStatus(), e.getStatusMessage(), e.getStatusCode());
  }


  private ResponseEntity<ApplicationError> buildAndResponseEntity(HttpStatus internalServerError,
      String errorMsg, String errorStatusCode) {
    return ResponseEntity.status(internalServerError)
        .body(new ApplicationError(errorMsg, errorStatusCode));
  }

}
