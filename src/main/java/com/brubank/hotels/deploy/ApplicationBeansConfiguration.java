package com.brubank.hotels.deploy;

import com.brubank.hotels.BrubankFlightsReservations;
import com.brubank.hotels.BrubankFlightsReservationsRepository;
import com.brubank.hotels.foursquare.CachedVenuesApi;
import com.brubank.hotels.reservations.FlightsReservationsRestApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Boot configuration for the application entities
 */
@Configuration
public class ApplicationBeansConfiguration {

  @Bean
  public BrubankFlightsReservations brubankFlightsReservations(
      final FlightsReservationsRestApi theFlightsReservationsRestApi,
      final BrubankFlightsReservationsRepository theBrubankFlightsReservationsRepository,
      final CachedVenuesApi theCachedVenuesApi) {
    return BrubankFlightsReservations.newInstance(
        theFlightsReservationsRestApi,
        theBrubankFlightsReservationsRepository,
        theCachedVenuesApi);
  }

}
