package com.brubank.hotels.api;

public class DestinationMother {

  public final Destination buenosAires;
  public final Destination montevideo;

  public DestinationMother() {
    buenosAires = new Destination();
    buenosAires.setCity("Buenos Aires");
    buenosAires.setCountry("Argentina");

    montevideo = new Destination();
    montevideo.setCity("Montevideo");
    montevideo.setCountry("Uruguay");
  }

}
