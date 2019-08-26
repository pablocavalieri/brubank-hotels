package com.brubank.hotels.foursquare;

import java.util.List;

/**
 * Methods to be consumed from the foursquare Venues Api
 */
public interface VenuesApi {

  /**
   * Search all the Hotels in {@code theCity}
   *
   * @param theCity the City to search hotels in
   * @return {@code List<Hotels>} of the Hotels in {@code theCity}
   */
  List<Hotels> searchHotelsNearCity(final String theCity);
}
