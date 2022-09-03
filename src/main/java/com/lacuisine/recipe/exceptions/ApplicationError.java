package com.lacuisine.recipe.exceptions;


public class ApplicationError extends com.lacuisine.recipe.model.Error {

  private static final long serialVersionUID = 1L;

  public ApplicationError() {}

  public ApplicationError(String errorMessage, String errorCode) {
    this();
    this.setErrorCode(errorCode);
    this.setErrorMessage(errorMessage);
  }

}
