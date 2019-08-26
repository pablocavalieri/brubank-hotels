package com.brubank.hotels.foursquare;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class VenuesHotelsTest {

  @Test
  public void getHotelsForNullResponse() {
    VenuesHotels venuesHotelsWithNullResponse = new VenuesHotels();

    List<Hotels> hotelsForVenuesWithNullResponse = venuesHotelsWithNullResponse.getHotels();

    assertTrue(hotelsForVenuesWithNullResponse.isEmpty());
  }
}
