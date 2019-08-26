package com.brubank.hotels.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handler for exceptions. Right now, we just avoid exception to be trowed to the used
 */
@ControllerAdvice
public class ControllerAdviceForExceptionHandling {

  /** {@link Logger} instance */
  private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAdviceForExceptionHandling.class);
  /** Generic error message */
  private static final String GENERIC_ERROR_MESSAGE = "There was an error. Please try again";

  /**
   * Handles all {@link Exception}s, display {@code GENERIC_ERROR_MESSAGE}, logs
   * {@code theException} as error
   * @param theException the {@link Exception} handled
   * @return {@code ResponseEntity<String>} with {@code GENERIC_ERROR_MESSAGE} as message and
   * {@code HttpStatus.INTERNAL_SERVER_ERROR} as status
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleAllExceptions(final Exception theException) {
    LOGGER.error("Exception handled.", theException);
    return new ResponseEntity(GENERIC_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
