package com.brubank.hotels.foursquare;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.brubank.hotels.api.Hotel;
import com.brubank.hotels.api.HotelLocation;
import com.brubank.hotels.api.HotelName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class represents the Hotels from the foursquare Venues Api
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hotels {

  /** The Hotel Name */
  private String name;
  /** The Hotel Name */
  private Location location;

  /**
   * Default constructor needed by Spring injection
   */
  protected Hotels() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  /**
   * Class representing the Hotel Location
   */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Location {

    private String address;
    private String crossStreet;
    private String lat;
    private String lng;

    /**
     * Default constructor needed by Spring injection
     */
    protected Location() {
    }

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    public String getCrossStreet() {
      return crossStreet;
    }

    public void setCrossStreet(String crossStreet) {
      this.crossStreet = crossStreet;
    }

    public String getLat() {
      return lat;
    }

    public void setLat(String lat) {
      this.lat = lat;
    }

    public String getLng() {
      return lng;
    }

    public void setLng(String lng) {
      this.lng = lng;
    }
  }

  public Hotel toApiHotel() {
    checkArgument(!getName().isEmpty(), "Name not valid");
    checkNotNull(getLocation(), "Location not valid");

    return Hotel.newInstance(
        HotelName.newLocalInstance(getName()),
        HotelLocation.newInstance(
            getLocation().getAddress(),
            HotelLocation.GeoPosition.newInstance(getLocation().getLat(), getLocation().getLng())));
  }
}