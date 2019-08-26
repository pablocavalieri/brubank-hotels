package com.brubank.hotels.foursquare;

import com.brubank.hotels.foursquare.Hotels.Location;

public class HotelsMother {

  public final Hotels sheratonBsAs;

  public HotelsMother() {
    sheratonBsAs = new Hotels();
    sheratonBsAs.setName("Sheraton");
    Location sheratonLocation = new Location();
    sheratonLocation.setAddress("Libertaxor 1234");
    sheratonLocation.setCrossStreet("Pampa");
    sheratonLocation.setLat("1");
    sheratonLocation.setLng("2");
    sheratonBsAs.setLocation(sheratonLocation);
  }

}
