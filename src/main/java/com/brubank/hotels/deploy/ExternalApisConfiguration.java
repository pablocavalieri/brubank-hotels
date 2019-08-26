package com.brubank.hotels.deploy;

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

  @Bean
  @ConfigurationProperties(prefix = "foursquare.venues")
  public VenuesApiConfiguration venuesApiConfiguration() {
    return new VenuesApiConfiguration();
  }

  @Bean
  public RestTemplate restTemplate(final RestTemplateBuilder builder) {
    return builder.build();
  }

  @Bean
  public CachedVenuesApi cachedVenuesApiGuavaCacheRestConnector(
      final RestConnectorVenuesApi restConnectorVenuesApi) {
    int hotelsByCityDurationInMinutes = 20;
    int maxCitiesToStoreInCache = 200;
    Cache<String, List<Hotels>> hotelsCache = CacheBuilder.newBuilder()
        .expireAfterWrite(hotelsByCityDurationInMinutes, TimeUnit.MINUTES)
        .maximumSize(maxCitiesToStoreInCache)
        .build();
    return CachedVenuesApi.newInstance(restConnectorVenuesApi, hotelsCache);
  }

  @Bean
  public RestConnectorVenuesApi restConnectorVenuesApi(
      final RestTemplate restTemplate,
      final VenuesApiConfiguration venuesApiConfiguration) {
    return RestConnectorVenuesApi.newInstance(restTemplate, venuesApiConfiguration);
  }

  @Bean
  @ConfigurationProperties(prefix = "brubank.flights.reservations")
  public FlightsReservationsApiConfiguration flightsReservationsApiConfiguration() {
    return new FlightsReservationsApiConfiguration();
  }

 @Bean
 public FlightsReservationsRestApi flightsReservationsRestApi(
     final RestTemplate restTemplate,
     final FlightsReservationsApiConfiguration flightsReservationsApiConfiguration) {
  return FlightsReservationsRestApi.newInstance(restTemplate, flightsReservationsApiConfiguration);
 }

}
