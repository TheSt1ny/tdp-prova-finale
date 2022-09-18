package it.polito.tdp.gestioneFlussiMigratori.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.gestioneFlussiMigratori.db.GestioneFlussiMigratoriDAO;

public class Model {
	
	private Map<String, Country> idMap;
	private Graph<Country, DefaultEdge> grafo;
	private GestioneFlussiMigratoriDAO dao;
	private int nNordAfrica;
	private int nTotMorti;
	
	public Model() {
		this.idMap = new HashMap<String, Country>();
		this.dao = new GestioneFlussiMigratoriDAO();
	}
	
	public void creaGrafo() {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
		//Vertici
		this.dao.getAllAfricanCountries(this.idMap);
		this.dao.getAllEuropeanCountries(this.idMap);
		Graphs.addAllVertices(this.grafo, this.idMap.values());
		
		//Archi
		List<Adiacenza> archi = this.dao.getAdiacenza();
		for(Adiacenza a: archi) {
			if(this.idMap.get(a.getCountry1()) != null && this.idMap.get(a.getCountry2()) != null) {
				this.grafo.addEdge(this.idMap.get(a.getCountry1()), this.idMap.get(a.getCountry2()));
			}
		}
		this.grafo.addEdge(this.idMap.get("South Africa"), this.idMap.get("Nigeria"));
		this.grafo.addEdge(this.idMap.get("Congo, Dem. Rep."), this.idMap.get("Nigeria"));
		this.grafo.addEdge(this.idMap.get("Central African Rep."), this.idMap.get("Nigeria"));
		this.grafo.addEdge(this.idMap.get("Cote d'Ivoire"), this.idMap.get("Nigeria"));
		
		System.out.println("Grafo creato");
		System.out.println("# Vertici: " + this.grafo.vertexSet().size());
		System.out.println("# Archi: " + this.grafo.edgeSet().size());
		
		ConnectivityInspector<Country, DefaultEdge> con = new ConnectivityInspector<Country, DefaultEdge>(this.grafo);
		System.out.println(con.connectedSets());
		
	}
	
	public List<Country> getAfricanCountries() {
		return this.dao.getAllAfricanCountries(idMap);
	}
	
	public List<Country> getEuropeanCountries() {
		return this.dao.getAllEuropeanCountries(idMap);
	}
	
	
	public void Simula(Country n1, Country n2, Country n3, String e1, String e2, String e3, Integer g1, Integer g2, Integer g3) {
		for(Country c: this.idMap.values()) {
			c.setMorti(0);
			c.setMigranti(0);
		}
		Simulatore sim = new Simulatore(this.grafo, this.idMap);
		if(n1!=null) {
			sim.Init(e1, n1, g1);
			sim.run();
		}
		if(n2!=null) {
			sim.Init(e2, n2, g2);
			sim.run();
		}
		if(n3!=null) {
			sim.Init(e3, n3, g3);
			sim.run();
		}
		this.nNordAfrica = sim.getNTotMigrantiNordAfrica();
		this.nTotMorti = sim.getNTotMorti();
	}
	
	public List<Country> getCountries() {
		List<Country> lista = new ArrayList<Country>();
		for(Country c: this.idMap.values()) {
			if(c.getRegion().contains("AFRICA")) {
				lista.add(c);
			}
		}
		Collections.sort(lista, new ComparatorePerMorti());
		return lista;
	}
	
	public int getNNordAfrica() {
		return this.nNordAfrica;
	}
	
	public int getNTotMorti() {
		return this.nTotMorti;
	}
	
	public List<Integer> gestisci(Country c, double approccio, int migranti){
		double segno1 = Math.random();
		double segno2 = Math.random();
		double v1 = Math.random();
		double v2 = Math.random();
		
		if(segno1<0.5) 
			segno1 = 1;
		else
			segno1 = -1;
		
		if(segno2<0.5) 
			segno2 = 1;
		else
			segno2 = -1;
		
		double approccioVero = approccio+(segno1*v1)+(segno2*v2);
		
		List<Integer> lista = new ArrayList<Integer>();
		lista.add((int)((approccioVero/10)*migranti));
		lista.add(migranti - lista.get(0));
		lista.add(10000*lista.get(1));
		double num = lista.get(1);
		double den = this.idMap.get(c.getCountry()).getPopulation();
		lista.add((int)(den/num));
		return lista;
	}
	
	public String bandiera(Country c) {
		String uri = "";
		switch(c.getCountry()) {
		case "Italy":
			uri="italia.png";
			break;
		case "France":
			uri="francia.png";
			break;
		case "Germany":
			uri="germania.png";
			break;
		case "Spain":
			uri="spagna.png";
			break;
		case "United Kingdom":
			uri="uk.png";
			break;
		default:
			uri = "ue.png";
			break;
		}
		return uri;
	}
}
