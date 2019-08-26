package com.brubank.hotels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.brubank.hotels.api.BrubankReservations;
import com.brubank.hotels.api.Destination;
import com.brubank.hotels.api.DestinationMother;
import com.brubank.hotels.foursquare.Hotels;
import com.brubank.hotels.foursquare.HotelsMother;
import com.brubank.hotels.foursquare.VenuesApi;
import com.brubank.hotels.reservations.FlightsReservation;
import com.brubank.hotels.reservations.FlightsReservationMother;
import com.brubank.hotels.reservations.FlightsReservationsRestApi;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Sort;

public class BrubankFlightsReservationsTest {

  private FlightsReservationMother flightsReservationMother;
  private DestinationMother destinationMother;
  private HotelsMother hotelsMother;

  @Before
  public void setUo() {
    flightsReservationMother = FlightsReservationMother.newInstance();
    destinationMother = new DestinationMother();
    hotelsMother = new HotelsMother();
  }

  @Test
  public void fetchReservationsFromApiWhenFlightApiThrowsException() {
    FlightsReservationsRestApi flightsReservationsRestApi = mock(FlightsReservationsRestApi.class);
    BrubankFlightsReservationsRepository brubankFlightsReservationsRepository =
        mock(BrubankFlightsReservationsRepository.class);
    when(flightsReservationsRestApi.getReservations()).thenThrow(new RuntimeException());
    BrubankFlightsReservations brubankFlightsReservations = BrubankFlightsReservations.newInstance(
        flightsReservationsRestApi,
        brubankFlightsReservationsRepository,
        mock(VenuesApi.class));

    brubankFlightsReservations.fetchReservationsFromApi();

    verify(brubankFlightsReservationsRepository, never()).saveAll(any());
  }

  @Test
  public void fetchReservationsFromApiWhenFlightApiReturnsReservations() {
    FlightsReservationsRestApi flightsReservationsRestApi = mock(FlightsReservationsRestApi.class);
    BrubankFlightsReservationsRepository brubankFlightsReservationsRepository =
        mock(BrubankFlightsReservationsRepository.class);
    List<FlightsReservation> expectedReservationsFromApi = Collections.singletonList(
        flightsReservationMother.toMontevideo);
    when(flightsReservationsRestApi.getReservations()).thenReturn(expectedReservationsFromApi);
    BrubankFlightsReservations brubankFlightsReservations = BrubankFlightsReservations.newInstance(
        flightsReservationsRestApi,
        brubankFlightsReservationsRepository,
        mock(VenuesApi.class));

    brubankFlightsReservations.fetchReservationsFromApi();

    verify(brubankFlightsReservationsRepository, times(2))
        .saveAll(expectedReservationsFromApi);
  }

  @Test
  public void fetchReservationsFromApiAtNewInstance() {
    FlightsReservationsRestApi flightsReservationsRestApi = mock(FlightsReservationsRestApi.class);
    BrubankFlightsReservationsRepository brubankFlightsReservationsRepository =
        mock(BrubankFlightsReservationsRepository.class);
    List<FlightsReservation> expectedReservationsFromApi = Collections.singletonList(
        flightsReservationMother.toMontevideo);
    when(flightsReservationsRestApi.getReservations()).thenReturn(expectedReservationsFromApi);
    BrubankFlightsReservations.newInstance(
        flightsReservationsRestApi,
        brubankFlightsReservationsRepository,
        mock(VenuesApi.class));

    verify(brubankFlightsReservationsRepository, times(1))
        .saveAll(expectedReservationsFromApi);
  }

  @Test
  public void toBrubankReservationsResponseWhenNoReservations() {
    FlightsReservationsRestApi flightsReservationsRestApi = mock(FlightsReservationsRestApi.class);
    when(flightsReservationsRestApi.getReservations()).thenReturn(Collections.emptyList());
    BrubankFlightsReservations brubankFlightsReservations = BrubankFlightsReservations.newInstance(
        flightsReservationsRestApi,
        mock(BrubankFlightsReservationsRepository.class),
        mock(VenuesApi.class));

    BrubankReservations brubankReservations =
        brubankFlightsReservations.toBrubankReservations(destinationMother.buenosAires);

    assertTrue(brubankReservations.getReservations().isEmpty());
  }

  @Test
  public void toBrubankReservationsResponseWhenVenuesApiReturnsNoHotels() {
    VenuesApi venuesApi = mock(VenuesApi.class);
    when(venuesApi.searchHotelsNearCity(anyString())).thenReturn(Collections.emptyList());
    BrubankFlightsReservations brubankFlightsReservations = BrubankFlightsReservations.newInstance(
        mock(FlightsReservationsRestApi.class),
        mock(BrubankFlightsReservationsRepository.class),
        venuesApi);

    BrubankReservations brubankReservations =
        brubankFlightsReservations.toBrubankReservations(destinationMother.buenosAires);

    assertTrue(brubankReservations.getHotels().isEmpty());
  }

  @Test
  public void toBrubankReservationsResponseWhenVenuesApiThrowsException() {
    VenuesApi venuesApi = mock(VenuesApi.class);
    RuntimeException expectedVenuesApiException = new RuntimeException();
    when(venuesApi.searchHotelsNearCity(anyString())).thenThrow(expectedVenuesApiException);
    BrubankFlightsReservations brubankFlightsReservations = BrubankFlightsReservations.newInstance(
        mock(FlightsReservationsRestApi.class),
        mock(BrubankFlightsReservationsRepository.class),
        venuesApi);
    boolean exceptionTrowed = false;
    try {
      brubankFlightsReservations.toBrubankReservations(destinationMother.buenosAires);
    } catch (Exception e) {
      assertEquals(expectedVenuesApiException, e);
      exceptionTrowed = true;
    }

    assertTrue(exceptionTrowed);
  }

  @Test
  public void toBrubankReservationsResponseWhenReservationsAndHotelsPresent() {
    VenuesApi venuesApi = mock(VenuesApi.class);
    List<Hotels> expectedVenuesHotels = Collections.singletonList(hotelsMother.sheratonBsAs);
    Destination bsAs = destinationMother.buenosAires;
    when(venuesApi.searchHotelsNearCity(bsAs.getCity())).thenReturn(expectedVenuesHotels);
    BrubankFlightsReservationsRepository reservationsRepository =
        mock(BrubankFlightsReservationsRepository.class);
    List<FlightsReservation> expectedReservationsFromRepo = Collections.singletonList(
        flightsReservationMother.toMontevideo);
    when(reservationsRepository.findAllByDestination(
        bsAs.toBrubankFlightReservationDestinationFormat(),
        Sort.by("date").ascending())).thenReturn(expectedReservationsFromRepo);
    BrubankFlightsReservations brubankFlightsReservations = BrubankFlightsReservations.newInstance(
        mock(FlightsReservationsRestApi.class),
        reservationsRepository,
        venuesApi);

    BrubankReservations brubankReservations =
        brubankFlightsReservations.toBrubankReservations(bsAs);

    assertEquals(expectedReservationsFromRepo.size(), brubankReservations.getReservations().size());
    assertEquals(expectedVenuesHotels.size(), brubankReservations.getHotels().size());
  }
}
