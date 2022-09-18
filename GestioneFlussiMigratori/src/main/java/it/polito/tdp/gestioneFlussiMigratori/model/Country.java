package it.polito.tdp.gestioneFlussiMigratori.model;

public class Country {
	
	private String country;
	private String region;
	private Integer population;
	private Integer morti;
	private Integer migranti;
	
	
	public Country(String country, String region, Integer population, Integer morti, Integer migranti) {
		super();
		this.country = country;
		this.region = region;
		this.population = population;
		this.morti = morti;
		this.migranti = migranti;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public Integer getPopulation() {
		return population;
	}


	public void setPopulation(Integer population) {
		this.population = population;
	}


	public Integer getMorti() {
		return morti;
	}


	public void setMorti(Integer morti) {
		this.morti = morti;
	}


	public Integer getMigranti() {
		return migranti;
	}


	public void setMigranti(Integer migranti) {
		this.migranti = migranti;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return this.country; 
	}
	
	
}
