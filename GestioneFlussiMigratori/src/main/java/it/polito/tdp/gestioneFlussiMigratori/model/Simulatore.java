package it.polito.tdp.gestioneFlussiMigratori.model;

import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {
	
	
	//Coda degli Eventi
	private PriorityQueue<Evento> queue;
	
	//Parametri simulazione
	private Integer nMigrantiIniziale;
	private Country partenza;
	private String tipo;
	
	//Output della simulazione
	private Integer nTotMorti;
	private Integer nTotMigrantiNordAfrica;
	
	//Stato del Mondo
	private Map<String, Country> mappa;
	private Graph<Country, DefaultEdge> mondo;
	
	
	public Simulatore(Graph<Country, DefaultEdge> grafo, Map<String, Country> mappa) {
		this.mappa = mappa;
		this.mondo = grafo;
		this.nTotMigrantiNordAfrica = 0;
		this.nTotMorti = 0;
	}
	
	public void Init(String tipo, Country partenza, int gravità) {
		this.tipo = tipo;
		this.partenza = partenza;
		double fattore = this.creaFattore()*gravità;
		this.nMigrantiIniziale =  (int) ((int) this.partenza.getPopulation()*fattore);
		this.partenza.setMigranti(this.nMigrantiIniziale);
		
		this.queue = new PriorityQueue<Evento>();
		this.queue.add(new Evento(1, this.partenza));
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Evento e = this.queue.poll();
			this.processEvento(e);
		}
		for(Country c: this.mappa.values()) {
			this.nTotMorti = this.nTotMorti+c.getMorti();
			if(c.getCountry().trim().equals("Algeria") || c.getCountry().equals("Libya") || c.getCountry().equals("Egypt") || c.getCountry().equals("Tunisia") || c.getCountry().equals("Morocco")) {
				this.nTotMigrantiNordAfrica = this.nTotMigrantiNordAfrica + c.getMigranti();
			}
		}
	}
	
	private void processEvento(Evento e) {
		if(!e.getCountry().getCountry().trim().equals("Algeria") && !e.getCountry().getCountry().equals("Libya") && !e.getCountry().getCountry().equals("Egypt") && !e.getCountry().getCountry().equals("Tunisia") && !e.getCountry().getCountry().equals("Morocco")) {
			int partono = (int)(e.getCountry().getMigranti()/3)*2;
			int morti = e.getCountry().getMigranti()-partono;
			this.mappa.get(e.getCountry().getCountry()).setMorti(this.mappa.get(e.getCountry().getCountry()).getMorti() + morti);
			int statiConfinanti = this.mondo.degreeOf(e.getCountry());
			if(statiConfinanti!=0) {
				int migranti = (int) (partono/statiConfinanti);
				if(migranti>=1) {
					for(Country c: Graphs.neighborListOf(this.mondo, e.getCountry())) {
						this.mappa.get(c.getCountry()).setMigranti(migranti+this.mappa.get(c.getCountry()).getMigranti());
						c.setMigranti(c.getMigranti()+migranti);
						this.queue.add(new Evento(e.getTime()+1, c));
					}
				}
				e.getCountry().setMigranti(0);
			}
		}
	}
	
	private double creaFattore() {
		double fattore = 0.0;
		switch(this.tipo) {
		case "Epidemia":
			fattore = 0.07;
			break;
		case "Guerra":
			fattore = 0.1;
			break;
		case "Carestia":
			fattore = 0.07;
			break;
		case "Disastro Ambientale":
			fattore = 0.07;
			break;
		case "Persecuzione":
			fattore = 0.05;
			break;
		case "Povertà":
			fattore = 0.05;
			break;
		default:
			fattore = 0;
			break;
		}
		return fattore;
	}
	
	public int getNTotMigrantiNordAfrica() {
		return this.nTotMigrantiNordAfrica;
	}
	
	public int getNTotMorti() {
		return this.nTotMorti;
	}
}
