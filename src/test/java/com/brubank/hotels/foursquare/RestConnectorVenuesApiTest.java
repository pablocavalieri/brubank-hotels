package com.brubank.hotels.foursquare;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class RestConnectorVenuesApiTest {

  @Test
  public void nullRestTemplateInConstructor() {
    boolean exceptionTrowed = false;

    try {
      RestConnectorVenuesApi.newInstance(null, mock(VenuesApiConfiguration.class));
    } catch (NullPointerException e) {
      assertEquals("RestTemplate instance is null", e.getMessage());
      exceptionTrowed = true;
    }

    assertTrue(exceptionTrowed);
  }

  @Test
  public void nullVenuesApiConfigurationInConstructor() {
    boolean exceptionTrowed = false;

    try {
      RestConnectorVenuesApi.newInstance(mock(RestTemplate.class), null);
    } catch (NullPointerException e) {
      assertEquals("VenuesApiConfiguration instance is null", e.getMessage());
      exceptionTrowed = true;
    }

    assertTrue(exceptionTrowed);
  }

  @Test
  public void nullCityToSearchForHotels() {
    RestConnectorVenuesApi venuesApi = RestConnectorVenuesApi.newInstance(
        mock(RestTemplate.class),
        mock(VenuesApiConfiguration.class));
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
    RestConnectorVenuesApi venuesApi = RestConnectorVenuesApi.newInstance(
        mock(RestTemplate.class),
        mock(VenuesApiConfiguration.class));
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
  public void exceptionInRestTemplateWhenSearchingForHotelsInCity() {
    RestTemplate restTemplateToCallVenuesApi = mock(RestTemplate.class);
    RestClientException expectedRestTemplateException = mock(RestClientException.class);
    when(restTemplateToCallVenuesApi.getForObject(anyString(), any()))
        .thenThrow(expectedRestTemplateException);
    RestConnectorVenuesApi venuesApi = RestConnectorVenuesApi.newInstance(
        restTemplateToCallVenuesApi,
        mock(VenuesApiConfiguration.class));
    boolean exceptionTrowed = false;

    try {
      venuesApi.searchHotelsNearCity("some city");
    } catch (RestClientException e) {
      assertEquals(expectedRestTemplateException, e);
      exceptionTrowed = true;
    }

    assertTrue(exceptionTrowed);
  }

  @Test
  public void searchForValidCity() {
    RestTemplate restTemplateToCallVenuesApi = mock(RestTemplate.class);
    VenuesHotels expectedRestTemplateResponse = mock(VenuesHotels.class);
    List<Hotels> expectedHotelsInApiResponse = new LinkedList<>();
    when(expectedRestTemplateResponse.getHotels()).thenReturn(expectedHotelsInApiResponse);
    when(restTemplateToCallVenuesApi.getForObject(anyString(), any()))
        .thenReturn(expectedRestTemplateResponse);
    RestConnectorVenuesApi venuesApi = RestConnectorVenuesApi.newInstance(
        restTemplateToCallVenuesApi,
        mock(VenuesApiConfiguration.class));

    List<Hotels> hotelsInCity = venuesApi.searchHotelsNearCity("some city");
    assertEquals(expectedHotelsInApiResponse, hotelsInCity);
  }

}
