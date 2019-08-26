package com.brubank.hotels.reservations;

import static org.junit.Assert.assertEquals;

import com.brubank.hotels.api.Reservations;
import org.junit.Before;
import org.junit.Test;

public class FlightsReservationTest {

  private FlightsReservationMother flightsReservationMother;

  @Before
  public void setUp() {
    flightsReservationMother = FlightsReservationMother.newInstance();
  }

  @Test
  public void correctToApiReservations() {
    FlightsReservation toMontevideo = flightsReservationMother.toMontevideo;

    Reservations apiReservation = toMontevideo.toApiReservations();

    assertEquals(toMontevideo.getDate().toString(), apiReservation.getDate());
    assertEquals(toMontevideo.getReservationId(), apiReservation.getId());
  }
}
