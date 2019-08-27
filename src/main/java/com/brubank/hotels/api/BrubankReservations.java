package com.brubank.hotels.api;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This class represents the Reservation entity in our application
 */
public class BrubankReservations {

  /** The {@link Reservations} that represent the abstraction of "reservation" */
  private final List<Reservations> reservations;
  /** The {@link Hotel}s of this instance, built from the reservation destination */
  private final List<Hotel> hotels;

  private BrubankReservations(
      final List<Reservations> theReservations,
      final List<Hotel> theHotels) {
    checkNotNull(theReservations, "Reservations is null");
    checkNotNull(theHotels, "Hotels is null");

    reservations = theReservations;
    hotels = theHotels;
  }

  /**
   * Creates a {@link BrubankReservations} instance
   * @param theReservations the {@link Reservations} of this instance
   * @param theHotels the {@link Hotel}s of this instance
   * @return {@link BrubankReservations} instance
   */
  public static BrubankReservations newInstance(
      final List<Reservations> theReservations,
      final List<Hotel> theHotels) {

    return new BrubankReservations(
        Optional.ofNullable(theReservations)
            .orElseGet(Collections::emptyList),
        theHotels);
  }

  public List<Reservations> getReservations() {
    return reservations;
  }

  public List<Hotel> getHotels() {
    return hotels;
  }
}
