package com.brubank.hotels.api;

/**
 * This class represents the Reservations in our application
 */
public class Reservations {

  private final String date;
  private final String id;

  private Reservations(final String theDate, final String theId) {
    date = theDate;
    id = theId;
  }

  public static Reservations newInstance(final String theDate, final String theId) {
    return new Reservations(theDate, theId);
  }

  public String getDate() {
    return date;
  }

  public String getId() {
    return id;
  }
}
