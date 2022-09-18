package it.polito.tdp.gestioneFlussiMigratori.model;

public class Adiacenza {
	
	private String country1;
	private String country2;
	
	public Adiacenza(String country1, String country2) {
		super();
		this.country1 = country1;
		this.country2 = country2;
	}

	public String getCountry1() {
		return country1;
	}

	public String getCountry2() {
		return country2;
	}

	@Override
	public String toString() {
		return this.country1 + this.country2;
	}
	
	
}
