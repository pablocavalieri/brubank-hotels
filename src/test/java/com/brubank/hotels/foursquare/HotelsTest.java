package com.brubank.hotels.foursquare;

import com.brubank.hotels.api.Hotel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HotelsTest {

  private HotelsMother hotelsMother;

  @Before
  public void setUp() {
    hotelsMother = new HotelsMother();
  }

  @Test
  public void correctToApiHotel() {
    Hotels sheraton = hotelsMother.sheratonBsAs;

    Hotel apiHotelSheraton = sheraton.toApiHotel();

    assertEquals(sheraton.getName(), apiHotelSheraton.getName().getLocalName());
    assertEquals(
        sheraton.getLocation().getAddress(),
        apiHotelSheraton.getHotelLocation().getAddress());
    assertEquals(
        sheraton.getLocation().getLat(),
        apiHotelSheraton.getHotelLocation().getGeoPosition().getLat());
    assertEquals(
        sheraton.getLocation().getLng(),
        apiHotelSheraton.getHotelLocation().getGeoPosition().getLng());
  }

}
