package com.brubank.hotels.api;

public class Destination {

  private String city;
  private String country;

  /** Default constructor needed for Spring injection */
  protected Destination() {
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * Transform the Destination to the Brubank Flight reservation format
   * @return {@code this} instance Brubank Flight reservation format
   */
  public String toBrubankFlightReservationDestinationFormat() {
    return getCity() + ", " + getCountry();
  }
}
