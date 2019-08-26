package com.brubank.hotels.api;

import com.brubank.hotels.BrubankFlightsReservations;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Interface with the system Reservations
 */
@RestController
public class ReservationsController {

  @Autowired
  private BrubankFlightsReservations brubankFlightsReservations;

  /**
   * Endpoint to fetch the
   * Reservations for {@code theDestination}
   *
   * @param theDestination {@link Destination} instance with the data of the Reservations
   * Destinations to search for
   */
  @GetMapping("/reservationsForDestination")
  public ResponseEntity<BrubankReservations> reservationsForDestination(
      final Destination theDestination) {
    Validate.notNull(theDestination, "Destination is null");
    Validate.notBlank(theDestination.getCity(), "Destination city not valid");
    Validate.notBlank(theDestination.getCountry(), "Destination country not valid");

    return ResponseEntity.ok(
        brubankFlightsReservations.toBrubankReservations(theDestination));
  }

}
