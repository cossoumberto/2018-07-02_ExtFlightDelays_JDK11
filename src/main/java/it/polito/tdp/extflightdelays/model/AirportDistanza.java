package it.polito.tdp.extflightdelays.model;

public class AirportDistanza implements Comparable<AirportDistanza>{

	private Airport airport;
	private Double distanza;
	
	public AirportDistanza(Airport airport, Double distanza) {
		super();
		this.airport = airport;
		this.distanza = distanza;
	}

	public Airport getAirport() {
		return airport;
	}

	public void setAirport(Airport airport) {
		this.airport = airport;
	}

	public Double getDistanza() {
		return distanza;
	}

	public void setDistanza(Double distanza) {
		this.distanza = distanza;
	}

	@Override
	public String toString() {
		return "airport: " + airport.getId() + " " + airport.getAirportName() + " distanza: " + distanza;
	}

	@Override
	public int compareTo(AirportDistanza o) {
		return -this.distanza.compareTo(o.distanza);
	}
	
	
}
