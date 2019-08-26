package com.brubank.hotels.reservations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class FlightsReservationsRestApiTest {

  @Test
  public void nullRestTemplateInConstructor() {
    boolean exceptionTrowed = false;

    try {
      FlightsReservationsRestApi.newInstance(
          null,
          mock(FlightsReservationsApiConfiguration.class));
    } catch (NullPointerException e) {
      assertEquals("RestTemplate instance is null", e.getMessage());
      exceptionTrowed = true;
    }

    assertTrue(exceptionTrowed);
  }

  @Test
  public void nullFlightsReservationsApiConfigurationInConstructor() {
    boolean exceptionTrowed = false;

    try {
      FlightsReservationsRestApi.newInstance(mock(RestTemplate.class), null);
    } catch (NullPointerException e) {
      assertEquals("FlightsReservationsApiConfiguration instance is null", e.getMessage());
      exceptionTrowed = true;
    }

    assertTrue(exceptionTrowed);
  }

  @Test
  public void exceptionInRestTemplate() {
    RestTemplate restTemplateToCallFlightsApi = mock(RestTemplate.class);
    RestClientException expectedRestTemplateException = mock(RestClientException.class);
    when(restTemplateToCallFlightsApi.exchange(
        anyString(),
        any(HttpMethod.class),
        any(),
        any(ParameterizedTypeReference.class)))
        .thenThrow(expectedRestTemplateException);
    FlightsReservationsRestApi flightsReservationApi = FlightsReservationsRestApi.newInstance(
        restTemplateToCallFlightsApi,
        mock(FlightsReservationsApiConfiguration.class));
    boolean exceptionTrowed = false;

    try {
      flightsReservationApi.getReservations();
    } catch (RestClientException e) {
      assertEquals(expectedRestTemplateException, e);
      exceptionTrowed = true;
    }

    assertTrue(exceptionTrowed);
  }

  @Test
  public void getReservationsRestResponseOk() {
    RestTemplate restTemplateToCallFlightsApi = mock(RestTemplate.class);
    List<FlightsReservation> expectedRestTemplateResponse = new LinkedList<>();
    ResponseEntity expectedResponseEntity = mock(ResponseEntity.class);
    when(expectedResponseEntity.getBody()).thenReturn(expectedRestTemplateResponse);
    when(restTemplateToCallFlightsApi.exchange(
        anyString(),
        any(HttpMethod.class),
        any(),
        any(ParameterizedTypeReference.class)))
        .thenReturn(expectedResponseEntity);
    FlightsReservationsRestApi flightsReservationsApi = FlightsReservationsRestApi.newInstance(
        restTemplateToCallFlightsApi,
        mock(FlightsReservationsApiConfiguration.class));

    List<FlightsReservation> reservations = flightsReservationsApi.getReservations();
    assertEquals(expectedRestTemplateResponse, reservations);
  }
}
