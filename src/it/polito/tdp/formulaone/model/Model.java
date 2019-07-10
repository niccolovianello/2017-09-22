package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	private List<Race> gare;
	// private List<Driver> piloti;
	private List<Adiacenza> adiacenzeMax;
	
	private Graph<Race, Adiacenza> grafo;
	
	public Model() {
		
	}

	public void creaGrafo(Season season) {
		
		grafo = new SimpleWeightedGraph<>(Adiacenza.class);
		
		// vertici
		FormulaOneDAO dao = new FormulaOneDAO();
		gare = new ArrayList<>(dao.getAllRacesBySeason(season));
		
		Graphs.addAllVertices(grafo, gare);
		
		// archi e pesi
		for(Race r1 : gare) {
			for(Race r2 : gare) {
				if(!r1.equals(r2)) {
					Adiacenza a = new Adiacenza(r1, r2);
					a.setPeso(dao.getPilotiArrivatiAlTraguardo(season.getYear(), r1)+dao.getPilotiArrivatiAlTraguardo(season.getYear(), r2));
					grafo.addEdge(r1, r2, a);
				}
				
			}
		}
		
		System.out.println("Grafo creato!\nVertici: " + grafo.vertexSet().size() + "\nArchi: " + grafo.edgeSet().size());
		
	}
	
	public List<Adiacenza> getAdiacenzaPesoMax() {
		
		Adiacenza init = new Adiacenza(null, null);
		
		for(Adiacenza a : grafo.edgeSet()) {
			if(a.getPeso() > init.getPeso()) {
				init = a;
			}
		}
		
		adiacenzeMax = new ArrayList<Adiacenza>();
		
		for(Adiacenza a : grafo.edgeSet()) {
			if(a.getPeso() == init.getPeso()) {
				adiacenzeMax.add(a);
			}
		}
		
		return adiacenzeMax;
	}

	public void simula(Race race, Season stagione, double P, int T) {
		Simulatore sim = new Simulatore();
		sim.init(P, T, stagione, race, grafo);
	}
	


}
