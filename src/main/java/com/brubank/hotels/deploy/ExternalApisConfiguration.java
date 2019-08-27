package com.brubank.hotels.deploy;


import static com.google.common.base.Preconditions.checkNotNull;

import com.brubank.hotels.foursquare.CachedVenuesApi;
import com.brubank.hotels.foursquare.Hotels;
import com.brubank.hotels.foursquare.RestConnectorVenuesApi;
import com.brubank.hotels.foursquare.VenuesApiConfiguration;
import com.brubank.hotels.reservations.FlightsReservationsApiConfiguration;
import com.brubank.hotels.reservations.FlightsReservationsRestApi;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Spring Boot configuration file for external Rest Api configuration
 */
@Configuration
public class ExternalApisConfiguration {

  /**
   * {@link VenuesApiConfiguration} data loader, from configuration file
   * @return {@link VenuesApiConfiguration}
   */
  @Bean
  @ConfigurationProperties(prefix = "foursquare.venues")
  public VenuesApiConfiguration venuesApiConfiguration() {
    return new VenuesApiConfiguration();
  }

  /**
   * Creates the {@link RestTemplate} instance to be used in the Application Spring context
   * @param builder {@link RestTemplateBuilder} instance
   * @return {@link RestTemplate} instance
   */
  @Bean
  public RestTemplate restTemplate(final RestTemplateBuilder builder) {
    checkNotNull(builder, "Builder is not valid");
    return builder.build();
  }

  /**
   * Creates the {@link com.brubank.hotels.foursquare.VenuesApi} interface with LRU in memory Guava
   * cache.
   * We need to know more about the Foursquare Venues Api and our application to fine tune the
   * cache elements limit and eviction policy
   * @param restConnectorVenuesApi {@link RestConnectorVenuesApi} implementation used to fetch the
   * foursquare Venues from their API
   * @return {@link CachedVenuesApi} instance
   */
  @Bean
  public CachedVenuesApi cachedVenuesApiGuavaCacheRestConnector(
      final RestConnectorVenuesApi restConnectorVenuesApi) {
    int hotelsByCityEvictionTime = 20;
    int maxCitiesToStoreInCache = 200;
    Cache<String, List<Hotels>> hotelsCache = CacheBuilder.newBuilder()
        .expireAfterAccess(hotelsByCityEvictionTime, TimeUnit.MINUTES)
        .maximumSize(maxCitiesToStoreInCache)
        .build();
    return CachedVenuesApi.newInstance(restConnectorVenuesApi, hotelsCache);
  }

  /**
   * Creates the {@link RestConnectorVenuesApi} instance to be used in the Application Spring
   * context
   *
   * @param restTemplate {@link RestTemplate} instance used as REST connector
   * @param venuesApiConfiguration {@link FlightsReservationsApiConfiguration} instance
   * with the {@link FlightsReservationsRestApi} connection data
   * @return {@link RestConnectorVenuesApi} instance
   */
  @Bean
  public RestConnectorVenuesApi restConnectorVenuesApi(
      final RestTemplate restTemplate,
      final VenuesApiConfiguration venuesApiConfiguration) {
    checkNotNull(restTemplate, "RestTemplate is null");
    checkNotNull(venuesApiConfiguration, "VenuesApiConfiguration is null");
    return RestConnectorVenuesApi.newInstance(restTemplate, venuesApiConfiguration);
  }

  /**
   * {@link FlightsReservationsApiConfiguration} data loader, from configuration file
   * @return {@link FlightsReservationsApiConfiguration}
   */
  @Bean
  @ConfigurationProperties(prefix = "brubank.flights.reservations")
  public FlightsReservationsApiConfiguration flightsReservationsApiConfiguration() {
    return new FlightsReservationsApiConfiguration();
  }

  /**
   * Creates the {@link FlightsReservationsRestApi} instance to be used in the Application Spring
   * context
   * @param restTemplate {@link RestTemplate} instance used as REST connector
   * @param flightsReservationsApiConfiguration {@link FlightsReservationsApiConfiguration} instance
   * with the {@link FlightsReservationsRestApi} connection data
   * @return {@link FlightsReservationsRestApi} instance
   */
 @Bean
 public FlightsReservationsRestApi flightsReservationsRestApi(
     final RestTemplate restTemplate,
     final FlightsReservationsApiConfiguration flightsReservationsApiConfiguration) {
  return FlightsReservationsRestApi.newInstance(restTemplate, flightsReservationsApiConfiguration);
 }

}
