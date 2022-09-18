package it.polito.tdp.gestioneFlussiMigratori.model;

public class Evento implements Comparable<Evento>{
	
	private int time;
	private Country country;
	
	
	public Evento(int time, Country country) {
		this.time = time;
		this.country = country;
	}


	public int getTime() {
		return time;
	}



	public Country getCountry() {
		return country;
	}


	@Override
	public int compareTo(Evento o) {
		return this.time-o.time;
	}

	
	
}
