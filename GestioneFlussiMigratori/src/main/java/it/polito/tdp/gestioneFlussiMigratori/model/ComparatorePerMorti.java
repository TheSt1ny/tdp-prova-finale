package it.polito.tdp.gestioneFlussiMigratori.model;

import java.util.Comparator;

public class ComparatorePerMorti implements Comparator<Country>{

	@Override
	public int compare(Country o1, Country o2) {
		return -(o1.getMorti()-o2.getMorti());
	}

}
