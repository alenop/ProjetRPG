package rpgapp.data.map;

import java.io.Serializable;
import java.util.HashMap;

import javafx.geometry.Point2D;
import rpgapp.data.character.Monstre;
import rpgapp.data.elementInteractifs.Chest;
import rpgapp.data.elementInteractifs.Indice;
import rpgapp.data.elementInteractifs.PNJ;

public class ModeleMap implements Serializable {
	private HashMap<Point2D,String> portalList;
	private HashMap<String,Point2D> returnPortalList;
	private HashMap<Point2D,Monstre> monsterList;
	private HashMap<Point2D,Monstre> monsterListActual;
	private Point2D positionHero;
	private HashMap<Point2D,Chest> coffreList;
	private HashMap<Point2D,PNJ> pNJList;
	private HashMap<Point2D, Indice> indiceList;
	public ModeleMap() {
		this.portalList=portalList;
		this.returnPortalList=returnPortalList;
		this.monsterList=monsterList;
		this.positionHero=positionHero;
		this.coffreList=coffreList;
		this.indiceList=indiceList;
		this.setPNJList(pNJList);
		
	}
	public void setPortalList( HashMap<Point2D,String> portalList) {
		this.portalList=portalList;
	}
	public HashMap<Point2D,String> getPortalList() {
		return this.portalList;
	}
	public void setReturnPortalList( HashMap<String,Point2D> returnPortalList) {
		this.returnPortalList=returnPortalList;
	}
	public HashMap<String,Point2D> getReturnPortalList() {
		return this.returnPortalList;
	}
	public void setMonsterList( HashMap<Point2D,Monstre> monsterList) {
		this.monsterList=monsterList;
	}
	public HashMap<Point2D,Monstre> getMonsterList() {
		return this.monsterList;
	}
	public void setMonsterListActual( HashMap<Point2D,Monstre> monsterListActual) {
		this.monsterListActual=monsterListActual;
	}
	public HashMap<Point2D,Monstre> getMonsterListActual() {
		return this.monsterListActual;
	}
	public void setCoffreList( HashMap<Point2D,Chest> CoffreList) {
		this.coffreList=CoffreList;
	}
	public HashMap<Point2D,Chest> getCoffreList() {
		return this.coffreList;
	}
	
	public void setIndiceList( HashMap<Point2D,Indice> indiceList) {
		this.indiceList=indiceList;
	}
	public HashMap<Point2D,Indice> getIndiceList() {
		return this.indiceList;
	}
	
	public void init() {
		portalList=new HashMap<Point2D,String>();
		returnPortalList=new HashMap<String,Point2D>();
		monsterList=new HashMap<Point2D, Monstre>();
		monsterListActual=new HashMap<Point2D, Monstre>();
		indiceList = new HashMap<Point2D, Indice>();
		coffreList=new HashMap<Point2D,Chest>();
		setPNJList(new HashMap<Point2D, PNJ>());
	}
	public void setPositionHero( Point2D PositionHero) {
		this.positionHero=PositionHero;
	}
	public Point2D getPositionHero() {
		return this.positionHero;
	}
	public HashMap<Point2D,PNJ> getPNJList() {
		return pNJList;
	}
	public void setPNJList(HashMap<Point2D,PNJ> pNJList) {
		this.pNJList = pNJList;
	}
}
