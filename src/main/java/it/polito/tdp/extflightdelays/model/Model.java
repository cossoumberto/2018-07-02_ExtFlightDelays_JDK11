package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private ExtFlightDelaysDAO dao;
	private Map<Integer, Airport> idMap;
	private Graph<Airport, DefaultWeightedEdge> grafo;
	private List<Airport> cittaVisitate;
	private Double totali;
	
	public Model() {
		dao = new ExtFlightDelaysDAO();
		idMap = new HashMap<>();
		dao.loadAllAirports(idMap);
	}
	
	public void creaGrafo(Double distanza) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		for(CoppiaAirport ca : dao.listCoppieAirport(distanza, idMap)) {
			Graphs.addEdgeWithVertices(grafo, ca.getA1(), ca.getA2(), ca.getDistance());
		}
	}
	
	public Graph<Airport, DefaultWeightedEdge> getGrafo() {
		return this.grafo;
	}
	
	public List<AirportDistanza> getDistanzeAirport(Airport airport){
		List<AirportDistanza> list = new ArrayList<>();
		for(DefaultWeightedEdge e : grafo.edgesOf(airport)) {
			list.add(new AirportDistanza(Graphs.getOppositeVertex(grafo, e, airport), grafo.getEdgeWeight(e)));
		}
		Collections.sort(list);
		return list;
	}
	
	public List<Airport> viaggio(Airport partenza, Double disponibili){
		cittaVisitate = new ArrayList<>();
		List<Airport> parziale = new ArrayList<>();
		parziale.add(partenza);
		cerca(parziale, 0.0, disponibili);
		return cittaVisitate;
	}

	private void cerca(List<Airport> parziale, Double percorse, Double disponibili) {
		if(percorse>disponibili) {
			if(parziale.size()-1>cittaVisitate.size()) {
				cittaVisitate = new ArrayList<>(parziale);
				totali = percorse -  grafo.getEdgeWeight(grafo.getEdge(cittaVisitate.get(cittaVisitate.size()-2),
						cittaVisitate.get(cittaVisitate.size()-1)));
				cittaVisitate.remove(cittaVisitate.size()-1);
			}
			return;
		} else {
			for(Airport a : Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1)))
				if(!parziale.contains(a)) {
					parziale.add(a);
					cerca(parziale, percorse + grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-2), a)), disponibili);
					parziale.remove(parziale.size()-1);
				}
		}
	}
	
	public Double getMigliaPercorse () {
		return totali;
	}
}
