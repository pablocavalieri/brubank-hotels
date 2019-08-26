package com.brubank.hotels.api;

import org.apache.commons.lang3.Validate;

public class Hotel {

  /** The Hotel name */
  private final HotelName name;
  /** The Hotel location */
  private final HotelLocation hotelLocation;

  private Hotel(final HotelName theName, final HotelLocation theHotelLocation) {
    Validate.notNull(theName, "HotelName instance is null");
    Validate.notNull(theHotelLocation, "HotelLocation instance is null");

    name = theName;
    hotelLocation = theHotelLocation;
  }

  public static Hotel newInstance(final HotelName theName, final HotelLocation theHotelLocation) {
    return new Hotel(theName, theHotelLocation);
  }

  public HotelName getName() {
    return name;
  }

  public HotelLocation getHotelLocation() {
    return hotelLocation;
  }
}
