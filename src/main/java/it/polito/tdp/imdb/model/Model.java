package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	ImdbDAO dao; 
    private SimpleWeightedGraph<Movie, DefaultWeightedEdge> graph;  // SEMPLICE, PESATO, NON ORIENTATO
    private List<Movie> allMovie ; 

	
    
public Model() {
    	
    	this.dao= new ImdbDAO();  
    	this.graph= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    	this.allMovie= new ArrayList<>();
    	
    }
	

public void creaGrafo(float rankInput) {
	  
	allMovie= dao.listAllMoviesRank();
	  

		 /** VERTICI */
	    	Graphs.addAllVertices(this.graph, allMovie);
	 		System.out.println("NUMERO vertici GRAFO: " +this.graph.vertexSet().size());
	 		
	 		
	 		/** ARCHI */

	 		/*
c'è un arco se: 

1. entrambi hanno un rank maggiore o uguale del valore r inserito dall’utente;
2. almeno un attore ha recitato in entrambi i film.
		
	 		*/
	
	//cerco tutti gli attori di ciascun film 
	 		
	 		for(Movie a1: this.allMovie) {
				for(Movie a2 : this.allMovie) {
					
					if(!a1.equals(a2)) {
					double peso= calcolaPeso(a1,a2);
					
					if(peso > 0 && a1.getRank()>=rankInput && a2.getRank()>=rankInput ) {
						Graphs.addEdgeWithVertices(this.graph, a1, a2, peso);
						
					  }
					}
				}
			}
			
			System.out.println("\nVERTICI: "+this.graph.vertexSet().size());
			System.out.println("\nARCHI: "+this.graph.edgeSet().size());

	


}


private double calcolaPeso(Movie a1, Movie a2) {
	List <Integer> listActor1= dao.getAttoriFilm(a1);  
	List <Integer> listActor2= dao.getAttoriFilm(a2);  
	
	List <Integer> temp= new ArrayList<>();

	for(Integer ii:listActor1 ) {
		  if(listActor2.contains(ii)) {
			  temp.add(ii);
		  }
	  }
	
	return temp.size();
}


public MovieWeight getFilmGradoMax() {
	
	int sumMax= 0; 
	Movie movMax= null; 
	
	for(Movie m: this.graph.vertexSet()) {
		int sum= getSum(m);
		if(sum>sumMax) {
			sumMax=sum;
			movMax= m; 
		}
		
	}
	MovieWeight mw= new MovieWeight(movMax,sumMax ); 
	return  mw; 
}



private int getSum(Movie m) {
	int summ= 0; 
	
	for(DefaultWeightedEdge ee: this.graph.edgesOf(m)) {
		summ+= graph.getEdgeWeight(ee); 
		
	}
	return summ;
}



List <Movie> bestPercorso; 
double bestSize; 


public List <Movie> getPercorsoMovie ( Movie mStart) { // input in ms  ...
	
	List <Movie> parziale = new ArrayList<>() ; 
	
	
	this.bestPercorso= new ArrayList<>() ; 
	this.bestSize = 0; 
	double pesoTemp=0; 
	
	
	parziale.add(mStart);
	ricorsione(parziale, mStart, pesoTemp); 
	
	
	return bestPercorso ;
	
}

/*
1. parta da m
2. abbia una sequenza di pesi incrementale. Il peso dell’arco esplorato al passo t+1, 
in particolare, deve sempre essere maggiore o uguale al peso dell’arco esplorato al passo t.

*/




private void ricorsione(List<Movie> parziale, Movie mStart, Double pesoTemp) {
		
	Movie current = parziale.get(parziale.size()-1);
		

	/** soluzione migliore **/ 
	
	if(parziale.size() > bestSize) {
		
		bestSize= parziale.size(); 
		bestPercorso= new ArrayList<>(parziale); 
	}

	/** continuo ad aggiungere elementi in parziale **/ 
    
//    List<Movie> successori= Graphs.successorListOf(graph, current);
    List<Movie> successori= Graphs.neighborListOf(graph, current);
	List<Movie> newSuccessori= new ArrayList<>();  

	
	for(Movie rr: successori) {
	    if(!parziale.contains(rr)) {
	    	newSuccessori.add(rr);  //QUI METTO SOLO I VERTICI CHE NON SONO GIA' STATI USATI
	    	
	    }
  }
	 
	/** condizione uscita  **/ 

	if(newSuccessori.size()==0) {
		System.out.println("\nNella CLAUSULA DI USCITA, con dim di parziale "+parziale.size()); 
		return; 
	}
	
    /** continuo ad aggiungere elementi in parziale **/ 
	
	for(Movie tt : newSuccessori) {
		double peso = this.graph.getEdgeWeight(this.graph.getEdge(current, tt));
		
		if(peso>= pesoTemp) { // se il nuovo peso è maggiore di quello vecchio

			parziale.add(tt);
			
			System.out.println("\nPEso VECCHIO: "+pesoTemp); 
			System.out.println(" ----  PEso NUOVO: "+peso); 

			pesoTemp= peso; //aggiorno il nuovo peso da confrontare
			ricorsione(parziale,mStart,pesoTemp); 
			parziale.remove(tt);
			
	 }
	}
}




public int getNumVerex() {
	return this.graph.vertexSet().size();
}
public List<Movie> getVertex() {
	return this.allMovie;
}
public int getNumEdges() {
	return this.graph.edgeSet().size();
}


















}
