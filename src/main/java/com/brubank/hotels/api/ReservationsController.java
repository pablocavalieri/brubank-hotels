package com.brubank.hotels.api;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.brubank.hotels.BrubankFlightsReservations;
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
    checkNotNull(theDestination, "Destination is null");
    checkArgument(!theDestination.getCity().isEmpty(), "Destination city not valid");
    checkArgument(!theDestination.getCountry().isEmpty(), "Destination country not valid");

    return ResponseEntity.ok(
        brubankFlightsReservations.toBrubankReservations(theDestination));
  }

}
