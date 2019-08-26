package com.brubank.hotels.api;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DestinationTest {

  private DestinationMother destinationMother;

 @Before
 public void setUp() {
   destinationMother = new DestinationMother();
 }

  @Test
  public void correctToBrubankDestinationFormat() {
    Destination bsAs = destinationMother.buenosAires;

    assertEquals(bsAs.getCity() + ", " + bsAs.getCountry(),
        bsAs.toBrubankFlightReservationDestinationFormat());
  }

}
