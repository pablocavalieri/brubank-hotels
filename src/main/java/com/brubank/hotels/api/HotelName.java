package com.brubank.hotels.api;

/**
 * Instances of this class represents an Hotel name.
 * This abstraction is created in case i18n is needed. Now, due to the catalog limitations, we only
 * have the Hotel name in the Hotel location language
 */
public class HotelName {

  private final String localName;

  private HotelName(String theLocalName) {
    localName = theLocalName;
  }

  /**
   * Returns an {@link HotelName} instance build with the Hotel Name in the Hotel location language
   * @param theLocalName the Name of the Hotel in the local language
   * @return a new {@link HotelName} instance
   */
  public static HotelName newLocalInstance(final String theLocalName) {
    return new HotelName(theLocalName);
  }

  public String getLocalName() {
    return localName;
  }
}
