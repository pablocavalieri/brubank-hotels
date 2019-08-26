package com.brubank.hotels.api;

public class HotelLocation {

  /** The location denomination. ie: street and number */
  private final String address;
  /** The Hotel Location Geo Position */
  private final GeoPosition geoPosition;

  private HotelLocation(final String theAddress, final GeoPosition theGeoPosition) {
    address = theAddress;
    geoPosition = theGeoPosition;
  }

  /**
   * Builds a new {@link HotelLocation} instance with {@code theAddress} and {@code theGeoPosition}
   * @param theAddress the Hotel location denomination. ie: street and number
   * @param theGeoPosition the Hotel Location Geo Position
   * @return a new {@link HotelLocation} instance
   */
  public static HotelLocation newInstance(final String theAddress, final GeoPosition theGeoPosition) {
   return new HotelLocation(theAddress, theGeoPosition);
  }

  public String getAddress() {
    return address;
  }

  public GeoPosition getGeoPosition() {
    return geoPosition;
  }

  public static class GeoPosition {
    private final String lat;
    private final String lng;

    private GeoPosition(final String theLat, final String theLng) {
      lat = theLat;
      lng = theLng;
    }

    /**
     * Builds a new {@link GeoPosition} instance located in {@code theLat} and {@code theLng}
     * @param theLat Geo Position latitude
     * @param theLng Geo Position longitude
     * @return a new {@link GeoPosition} instance
     */
    public static GeoPosition newInstance(final String theLat, final String theLng) {
      return new GeoPosition(theLat, theLng);
    }

    public String getLat() {
      return lat;
    }

    public String getLng() {
      return lng;
    }
  }
}
