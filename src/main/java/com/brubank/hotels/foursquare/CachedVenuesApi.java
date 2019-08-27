package com.brubank.hotels.foursquare;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.cache.Cache;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class caches the Venues retrieved using the {@code venuesApiToRetrieveData}
 */
public class CachedVenuesApi implements VenuesApi {

  /** {@link Logger} instance */
  private static final Logger LOGGER = LoggerFactory.getLogger(CachedVenuesApi.class);

  /**
   * {@link VenuesApi} implementation to retrieve the Venues from foursquare to Search Hotels by
   * City
   */
  private final VenuesApi venuesApiToRetrieveData;
  /** Cached {@link Hotels} by city */
  private final Cache<String, List<Hotels>> hotelsByCityCache;

  private CachedVenuesApi(
      final VenuesApi theVenuesApiToRetrieveData,
      final Cache<String, List<Hotels>> theHotelsByCityCache) {
    checkNotNull(theVenuesApiToRetrieveData, "VenuesApi instance is null");
    checkNotNull(theHotelsByCityCache, "Cache instance is null");

    venuesApiToRetrieveData = theVenuesApiToRetrieveData;
    hotelsByCityCache = theHotelsByCityCache;
  }

  /**
   * Creates a new instance of {@link CachedVenuesApi}
   *
   * @param theVenuesApiToRetrieveData {@link VenuesApi} implementation used to get the data from
   * foursquare
   * @param theHotelsByCityCache cache used to {@link Hotels} by City
   * @return {@code CachedVenuesApi} instance
   */
  public static CachedVenuesApi newInstance(
      final VenuesApi theVenuesApiToRetrieveData,
      final Cache<String, List<Hotels>> theHotelsByCityCache) {

    return new CachedVenuesApi(theVenuesApiToRetrieveData, theHotelsByCityCache);
  }

  /**
   * Returns the {@link Hotels} in {@code hotelsByCityCache} if present. If not, search them using
   * {@code venuesApiToRetrieveData} and store them in the cache.
   *
   * @param theCity the City to search hotels in
   * @return {@code List<Hotels>} of the Hotels in {@code theCity}
   */
  public List<Hotels> searchHotelsNearCity(final String theCity) {
    checkNotNull(theCity, "The City is blank, it's not valid");
    checkArgument(!theCity.isEmpty(), "The City is blank, it's not valid");

    return Optional.ofNullable(hotelsByCityCache.getIfPresent(theCity))
        .orElseGet(() -> {
          LOGGER.debug("Cache miss for {}", theCity);
          List<Hotels> hotelsForCity = venuesApiToRetrieveData.searchHotelsNearCity(theCity);
          hotelsByCityCache.put(theCity, hotelsForCity);
          LOGGER.debug(
              "Added {} Hotels for miss for {}",
              hotelsForCity.size(),
              theCity);
          return hotelsForCity;
        });
  }
}
