package it.polito.tdp.imdb.model;

public class MovieWeight {
	
	Movie movieMax; 
	int pesoTot;
	
	
	
	public MovieWeight(Movie movieMax, int pesoTot) {
		
		this.movieMax = movieMax;
		this.pesoTot = pesoTot;
	}
	
	public Movie getMovieMax() {
		return movieMax;
	}
	public void setMovieMax(Movie movieMax) {
		this.movieMax = movieMax;
	}
	public int getPesoTot() {
		return pesoTot;
	}
	public void setPesoTot(int pesoTot) {
		this.pesoTot = pesoTot;
	}
	
	

}
