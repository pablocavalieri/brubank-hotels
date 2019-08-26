package com.brubank.hotels.foursquare;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.cache.Cache;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

public class CachedVenuesApiTest {

  @Test
  public void nullVenuesApiInConstructor() {
    boolean exceptionTrowed = false;

    try {
      CachedVenuesApi.newInstance(null, mock(Cache.class));
    } catch (NullPointerException e) {
      assertEquals("VenuesApi instance is null", e.getMessage());
      exceptionTrowed = true;
    }

    assertTrue(exceptionTrowed);
  }

  @Test
  public void nullVenuesApiConfigurationInConstructor() {
    boolean exceptionTrowed = false;

    try {
      CachedVenuesApi.newInstance(mock(VenuesApi.class), null);
    } catch (NullPointerException e) {
      assertEquals("Cache instance is null", e.getMessage());
      exceptionTrowed = true;
    }

    assertTrue(exceptionTrowed);
  }

  @Test
  public void nullCityToSearchForHotels() {
    CachedVenuesApi venuesApi = CachedVenuesApi.newInstance(
        mock(VenuesApi.class),
        mock(Cache.class));
    boolean exceptionTrowed = false;

    try {
      venuesApi.searchHotelsNearCity(null);
    } catch (NullPointerException e) {
      assertEquals("The City is blank, it's not valid", e.getMessage());
      exceptionTrowed = true;
    }

    assertTrue(exceptionTrowed);
  }

  @Test
  public void blankCityToSearchForHotels() {
    CachedVenuesApi venuesApi = CachedVenuesApi.newInstance(
        mock(VenuesApi.class),
        mock(Cache.class));
    boolean exceptionTrowed = false;

    try {
      venuesApi.searchHotelsNearCity(" ");
    } catch (IllegalArgumentException e) {
      assertEquals("The City is blank, it's not valid", e.getMessage());
      exceptionTrowed = true;
    }

    assertTrue(exceptionTrowed);
  }

  @Test
  public void cityCacheHitInCache() {
    VenuesApi apiDataFetch = mock(VenuesApi.class);
    Cache hotelsCache = mock(Cache.class);
    String city = "the city";
    List<Hotels> expectedHotelsForCity = new LinkedList<>();
    when(hotelsCache.getIfPresent(city)).thenReturn(expectedHotelsForCity);
    CachedVenuesApi venuesApi = CachedVenuesApi.newInstance(apiDataFetch, hotelsCache);

    List<Hotels> hotelsForCity = venuesApi.searchHotelsNearCity(city);

    assertEquals(expectedHotelsForCity, hotelsForCity);
    verify(hotelsCache, never()).put(any(), any());
  }

  @Test
  public void cityCacheMissInCache() {
    VenuesApi apiDataFetch = mock(VenuesApi.class);
    Cache hotelsCache = mock(Cache.class);
    String city = "the city";
    List<Hotels> expectedHotelsForCity = new LinkedList<>();
    when(apiDataFetch.searchHotelsNearCity(city)).thenReturn(expectedHotelsForCity);
    CachedVenuesApi venuesApi = CachedVenuesApi.newInstance(apiDataFetch, hotelsCache);

    List<Hotels> hotelsForCity = venuesApi.searchHotelsNearCity(city);

    assertEquals(expectedHotelsForCity, hotelsForCity);
    verify(hotelsCache, times(1)).put(city, expectedHotelsForCity);
  }

}
