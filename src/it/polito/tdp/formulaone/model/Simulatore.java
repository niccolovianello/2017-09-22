package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;
import it.polito.tdp.formulaone.model.Evento.TipoEvento;

public class Simulatore {
	
	private Double P; // probabilità di fermarsi per un pit stop durante il giro
	private Integer T; // durata in secondi di un pit stop
	
	private Season stagione;
	private Race gara;
	
	private Map<Driver, Integer> puntiPilota;
	private List<Driver> piloti;
	
	private Graph<Race, Adiacenza> grafo;
	
	private PriorityQueue<Evento> queue;
	
	public void init(Double P, Integer T, Season stagione, Race gara, Graph<Race, Adiacenza> grafo) {
		// inizializzazione struttura dati
		this.P = P;
		this.T= T;
		
		this.stagione = stagione;
		this.gara = gara;
		
		this.grafo = grafo;
		
		FormulaOneDAO dao = new FormulaOneDAO();
		piloti = new ArrayList<Driver>(dao.getPilotiByRace(gara));
		puntiPilota = new HashMap<>();
		for(Driver d : dao.getPilotiByRace(gara)) {
			puntiPilota.put(d, 0);
		}
		
		// probabilmente sarebbe stato meglio creare eventi che riguardassero il singolo giro della gara, più che la partecipazione alla stessa
		for(Driver p : piloti) {
			queue.add(new Evento(TipoEvento.GAREGGIA, gara, p));
		}
	}
	
	public void run() {
		
		Evento e;
		
		while((e = queue.poll()) != null) {
			switch(e.getTipo()) {
				case GAREGGIA:
					break;
			
				case TAGLIA_TRAGUARDO:
					// vedo se è il primo, in tal caso creo un evento di tipo PRIMO assegnato a quel pilota, aumentando il suo punteggio
					break;
				
				case PRIMO:
					Integer punti = puntiPilota.get(e.getPilota());
					puntiPilota.put(e.getPilota(), punti+1);
					break;
					
				case PITSTOP:
					// aumento la durata del giro in cui avviene il pit stop di un tempo equivalente ai secondi che ha scelto l'utente
					
					break;
			}
		}
	}

}
