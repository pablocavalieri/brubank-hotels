package com.brubank.hotels;

import com.brubank.hotels.api.BrubankReservations;
import com.brubank.hotels.api.Destination;
import com.brubank.hotels.api.Hotel;
import com.brubank.hotels.api.Reservations;
import com.brubank.hotels.foursquare.Hotels;
import com.brubank.hotels.foursquare.VenuesApi;
import com.brubank.hotels.reservations.FlightsReservation;
import com.brubank.hotels.reservations.FlightsReservationsRestApi;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * This class represents the Brubank Flights reservations.
 * It handles the volatility of the reservations in the Api consumed by
 * {@code flightsReservationsRestApi}
 */
public class BrubankFlightsReservations {

  /** {@link Logger} instance */
  private static final Logger LOGGER = LoggerFactory.getLogger(BrubankFlightsReservations.class);

  /** The interface with the Brubank Flight Api */
  private final FlightsReservationsRestApi flightsReservationsRestApi;
  /** The Repository used to persist the Brubank Flights Reservations */
  private final BrubankFlightsReservationsRepository brubankFlightsReservationsRepository;
  /** The {@link VenuesApi} instance, used to communicate with foursquare Api */
  private final VenuesApi venuesApi;

  private BrubankFlightsReservations(
      final FlightsReservationsRestApi theFlightsReservationsRestApi,
      final BrubankFlightsReservationsRepository theBrubankFlightsReservationsRepository,
      final VenuesApi theVenuesApi) {

    Validate.notNull(
        theFlightsReservationsRestApi,
        "FlightsReservationsRestApi instance is null");
    Validate.notNull(
        theBrubankFlightsReservationsRepository,
        "BrubankFlightsReservationsRepository instance is null");
    Validate.notNull(
        theVenuesApi,
        "CachedVenuesApi instance is null");

    flightsReservationsRestApi = theFlightsReservationsRestApi;
    brubankFlightsReservationsRepository = theBrubankFlightsReservationsRepository;
    venuesApi = theVenuesApi;
    fetchReservationsFromApi();
  }

  /**
   * Creates a {@link BrubankFlightsReservations} instance, and run {@code fetchReservationsFromApi}
   * method to populate the {@code brubankFlightsReservationsRepository}
   *
   * @param theFlightsReservationsRestApi {@link FlightsReservationsRestApi} instance used to
   * populate the {@code brubankFlightsReservationsRepository}
   * @param theBrubankFlightsReservationsRepository {@link BrubankFlightsReservationsRepository}
   * instance used to fetch the {@link FlightsReservation}s
   * @param theVenuesApi {@link VenuesApi} instance used to consume the Hotels in
   * {@code FlightsReservation.destination}
   * @return {@link BrubankFlightsReservations} instance
   */
  public static BrubankFlightsReservations newInstance(
      final FlightsReservationsRestApi theFlightsReservationsRestApi,
      final BrubankFlightsReservationsRepository theBrubankFlightsReservationsRepository,
      final VenuesApi theVenuesApi) {

    return new BrubankFlightsReservations(
        theFlightsReservationsRestApi,
        theBrubankFlightsReservationsRepository,
        theVenuesApi);
  }

  /**
   * Transform the Brubank Flights reservations in {@code brubankFlightsReservationsRepository}
   * to {@link BrubankReservations}, this means all the reservations, in Api model, and the
   * {@link Hotel}s for {@code theDestination.city}
   *
   * @param theDestination the destinations to get the Brubank Flights reservations and foursquare
   * Hotels from
   * @return {@link BrubankReservations} instance
   */
  public BrubankReservations toBrubankReservations(
      final Destination theDestination) {
    List<FlightsReservation> flightsReservations = getFlightsReservationsForDestinationSortedByDate(
        theDestination);
    List<Hotels> hotelsForDestination = venuesApi.searchHotelsNearCity(
        theDestination.getCity());
    List<Reservations> apiReservations = flightsReservations.stream()
        .map(FlightsReservation::toApiReservations)
        .collect(Collectors.toList());
    List<Hotel> apiHotels = hotelsForDestination.stream()
        .map(Hotels::toApiHotel)
        .collect(Collectors.toList());

    return BrubankReservations.newInstance(apiReservations, apiHotels);
  }

  /**
   * Returns all the {@link FlightsReservation} present in
   * {@code brubankFlightsReservationsRepository} made in {@code theDestination} sorted ascending
   * by date.
   *
   * @param theDestination {@link Destination} instance
   * @return {@link FlightsReservation}s present in {@code brubankFlightsReservationsRepository} for
   * {@code theDestination}
   */
  private List<FlightsReservation> getFlightsReservationsForDestinationSortedByDate(
      final Destination theDestination) {
    Validate.notNull(theDestination, "Destination is null");

    return brubankFlightsReservationsRepository.findAllByDestination(
        theDestination.toBrubankFlightReservationDestinationFormat(),
        Sort.by("date").ascending());
  }

  /**
   * Fetch the reservations from {@code flightsReservationsRestApi} every 5 minutes and save it to
   * {@code brubankFlightsReservationsRepository}. This is needed due to the {@code
   * flightsReservationsRestApi} discarding the reservations if not consumed
   */
  @Scheduled(fixedRate = 5 * 60 * 1000)
  public void fetchReservationsFromApi() {
    LOGGER.debug("Executing Brubank Flight Reservations retrieve from Api");
    try {
      List<FlightsReservation> reservations = flightsReservationsRestApi.getReservations();
      LOGGER.debug(String.format("Persisting {%d} reservations", reservations.size()));
      brubankFlightsReservationsRepository.saveAll(reservations);
    } catch (Exception e) {
      LOGGER.error("Error in Brubank Flights Reservations Api", e);
    }
  }
}