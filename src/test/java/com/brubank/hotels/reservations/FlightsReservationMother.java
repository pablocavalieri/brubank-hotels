package com.brubank.hotels.reservations;

import com.brubank.hotels.api.DestinationMother;
import java.time.LocalDateTime;

public class FlightsReservationMother {

  public final FlightsReservation toMontevideo;

  private FlightsReservationMother(final DestinationMother theDestinationMother) {
    toMontevideo = new FlightsReservation();
    toMontevideo.setDate(LocalDateTime.now());
    toMontevideo.setReservationId(Integer.toString(toMontevideo.hashCode()));
    toMontevideo.setDestination(
        theDestinationMother.montevideo.toBrubankFlightReservationDestinationFormat());
  }

  public static FlightsReservationMother newInstance() {
    return new FlightsReservationMother(new DestinationMother());
  }
}
