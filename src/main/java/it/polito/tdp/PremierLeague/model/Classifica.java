package it.polito.tdp.PremierLeague.model;

public class Classifica implements Comparable<Classifica>{
private Team t;
private int punti;
public Classifica(Team t, int punti) {
	super();
	this.t = t;
	this.punti = punti;
}
public Team getT() {
	return t;
}
public void setT(Team t) {
	this.t = t;
}
public int getPunti() {
	return punti;
}
public void setPunti(int punti) {
	this.punti = punti;
}
@Override
public int compareTo(Classifica o1) {
	return this.punti-o1.punti;
}

}
