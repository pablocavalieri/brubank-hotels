package com.brubank.hotels.reservations;

import java.util.List;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

/**
 * This class exports the Flights Reservations Api from Brubank REST endpoint
 */
public class FlightsReservationsRestApi {

  /** {@link Logger} instance */
  private static final Logger LOGGER = LoggerFactory.getLogger(FlightsReservationsRestApi.class);
  /** Api endpoint to fetch reservations */
  private static final String FETCH_RESERVATIONS_ENDPOINT = "/flight-reservations";

  /** {@link RestTemplate} instance used to perform REST Service calls */
  private final RestTemplate restTemplate;
  /** {@link FlightsReservationsApiConfiguration} instance with the Venues Api configuration */
  private final FlightsReservationsApiConfiguration flightsReservationsApiConfiguration;

  private FlightsReservationsRestApi(
      final RestTemplate theRestTemplate,
      final FlightsReservationsApiConfiguration theFlightsReservationsApiConfiguration) {
    Validate.notNull(theRestTemplate, "RestTemplate instance is null");
    Validate.notNull(
        theFlightsReservationsApiConfiguration,
        "FlightsReservationsApiConfiguration instance is null");

    restTemplate = theRestTemplate;
    flightsReservationsApiConfiguration = theFlightsReservationsApiConfiguration;
  }

  /**
   * Creates a new instance of {@link FlightsReservationsRestApi}
   *
   * @param theRestTemplate the {@link RestTemplate} instance used to make REST calls to the Api
   * @param theFlightsReservationsApiConfiguration the REST connection data (just host for now)
   * @return {@link FlightsReservationsRestApi} instance
   */
  public static FlightsReservationsRestApi newInstance(
      final RestTemplate theRestTemplate,
      final FlightsReservationsApiConfiguration theFlightsReservationsApiConfiguration) {

    return new FlightsReservationsRestApi(theRestTemplate, theFlightsReservationsApiConfiguration);
  }

  /**
   * Returns the {@link FlightsReservation}s from the Brubank Api
   * @return the response of making a REST call to {@code FETCH_RESERVATIONS_ENDPOINT} to
   * {@code flightsReservationsApiConfiguration.host}
   */
  public List<FlightsReservation> getReservations() {
    String fetchReservationsUrl =
        flightsReservationsApiConfiguration.getHost()
            + FETCH_RESERVATIONS_ENDPOINT;
    LOGGER.debug(String.format("Making REST call to {%s}", fetchReservationsUrl));

    return restTemplate.exchange(
        fetchReservationsUrl,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<FlightsReservation>>() {})
        .getBody();
  }
}
