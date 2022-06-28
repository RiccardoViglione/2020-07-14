package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	private Graph<Team,DefaultWeightedEdge>grafo;
	private PremierLeagueDAO dao;
	private List<Adiacenza>adiacenze=new ArrayList<>();
	
	public Model() {
		dao=new PremierLeagueDAO();
	}
	public void creaGrafo() {
		this.grafo=new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
	Graphs.addAllVertices(this.grafo, this.dao.listAllTeams());
	
	//Aggiungo archi
	List<Match>partite=dao.listAllMatches();
	List<Classifica> classifica=new ArrayList<>();
	for(Team t:this.grafo.vertexSet()) {
		int punti=0;
		for(Match p:partite) {
			if(t.getTeamID().equals(p.getTeamHomeID())) {
				if(p.getResultOfTeamHome()==0) {
					punti+=1;
				}else if(p.getResultOfTeamHome()==-1) {
					punti+=0;
				}else if(p.getResultOfTeamHome()==1) {
					punti+=3;
				}
			}
			if(t.getTeamID().equals(p.getTeamAwayID())) {
				if(p.getResultOfTeamHome()==0) {
					punti+=1;
				}else if(p.getResultOfTeamHome()==-1) {
					punti +=3;
				}
				else if(p.getReaultOfTeamHome()==1) {
					punti +=0;
				}
				
			}
		}
	classifica.add(new Classifica(t,punti));
		
	}
	Collections.sort(classifica);
	for(Classifica t1:classifica) {
		for(Classifica t2:classifica) {
			if(t1.getPunti()-t2.getPunti()>0) {
				Graphs.addEdgeWithVertices(this.grafo, t1.getT(), t2.getT(),t1.getPunti()-t2.getPunti());
			adiacenze.add( new Adiacenza(t1.getT(), t2.getT(),t1.getPunti()-t2.getPunti()));
			}
			if(t1.getPunti()-t2.getPunti()<0) {
				Graphs.addEdgeWithVertices(this.grafo, t2.getT(), t1.getT(),t2.getPunti()-t1.getPunti());
				adiacenze.add( new Adiacenza(t2.getT(), t1.getT(),t2.getPunti()-t1.getPunti()));
			}
			
		}
		
	}
	}public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}public List<Team> getVertici(){
		return new ArrayList<>(this.grafo.vertexSet());
	}
	public boolean grafoCreato() {
		if(this.grafo == null)
			return false;
		else 
			return true;
	}
	public List<SquadraPeggiore> SquadreBattute(Team team){
		List<SquadraPeggiore> temp= new ArrayList<>();
		for (DefaultWeightedEdge e : grafo.outgoingEdgesOf(team)) {
				temp.add( new SquadraPeggiore(grafo.getEdgeTarget(e),grafo.getEdgeWeight(e) ));
		}
		Collections.sort(temp);
		return temp;
	}
	
	public List<SquadraMigliore> SquadreCheBattono(Team team){
		List<SquadraMigliore> temp= new ArrayList<>();
		for (DefaultWeightedEdge e : grafo.incomingEdgesOf(team)) {
				temp.add( new SquadraMigliore(grafo.getEdgeSource(e),grafo.getEdgeWeight(e) ));
		}
		Collections.sort(temp);
		return temp;
	}
	
}
