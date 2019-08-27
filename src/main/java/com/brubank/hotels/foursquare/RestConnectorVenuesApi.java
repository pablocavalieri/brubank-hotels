package com.brubank.hotels.foursquare;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * This class exports the Venues Api from foursquare REST endpoint
 */
public class RestConnectorVenuesApi implements VenuesApi {

  /** {@link Logger} instance */
  private static final Logger LOGGER = LoggerFactory.getLogger(RestConnectorVenuesApi.class);
  /** Api endpoint to Search Hotels by City */
  private static final String HOTELS_IN_CITY_API_CALL_TEMPLATE = "/search?near=%s&intent=browse&query=hotel&client_id=%s&client_secret=%s&v=%s";

  /** {@link RestTemplate} instance used to perform REST Service calls */
  private final RestTemplate restTemplate;
  /** {@link VenuesApiConfiguration} instance with the Venues Api configuration */
  private final VenuesApiConfiguration venuesApiConfiguration;

  private RestConnectorVenuesApi(
      final RestTemplate theRestTemplate,
      final VenuesApiConfiguration theVenuesApiConfiguration) {
    checkNotNull(theRestTemplate, "RestTemplate instance is null");
    checkNotNull(theVenuesApiConfiguration, "VenuesApiConfiguration instance is null");

    restTemplate = theRestTemplate;
    venuesApiConfiguration = theVenuesApiConfiguration;
  }

  /**
   * Creates a new instance of {@link RestConnectorVenuesApi}
   *
   * @param theRestTemplate the {@link RestTemplate} instance used to make REST calls to the Api
   * @param theVenuesApiConfiguration the REST connection data (host, auth keys, etc)
   * @return {@link RestConnectorVenuesApi} instance
   */
  public static RestConnectorVenuesApi newInstance(
      final RestTemplate theRestTemplate,
      final VenuesApiConfiguration theVenuesApiConfiguration) {

    return new RestConnectorVenuesApi(theRestTemplate, theVenuesApiConfiguration);
  }

  /**
   * Makes a REST call to endpoint in {@code HOTELS_IN_CITY_API_CALL_TEMPLATE} with connection
   * data in {@code venuesApiConfiguration} to retrieve the {@link Hotels} for {@code theCity}
   *
   * @param theCity the City to search hotels in
   * @return {@code List<Hotels>} of the Hotels in {@code theCity}
   */
  public List<Hotels> searchHotelsNearCity(final String theCity) {
    checkNotNull(theCity, "The City is blank, it's not valid");
    checkArgument(!theCity.isEmpty(), "The City is blank, it's not valid");

    String searchHotelsInCityUrl = venuesApiConfiguration.getHost()
        + String.format(
        HOTELS_IN_CITY_API_CALL_TEMPLATE,
        theCity,
        venuesApiConfiguration.getClientId(),
        venuesApiConfiguration.getClientSecret(),
        venuesApiConfiguration.getVersion());
    LOGGER.debug("Making REST call to {}", searchHotelsInCityUrl);
    return Optional.ofNullable(
        restTemplate.getForObject(
            searchHotelsInCityUrl,
            VenuesHotels.class))
        .map(VenuesHotels::getHotels)
        .orElseGet(Collections::emptyList);
  }
}