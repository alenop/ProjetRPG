package rpgapp.data.map;

import java.util.HashMap;


import rpgapp.data.character.Monstre;
import rpgapp.data.elementInteractifs.Chest;
import rpgapp.data.elementInteractifs.PNJ;
import javafx.geometry.Point2D;

public class ModeleMap {
	private HashMap<Point2D,String> PortalList;
	private HashMap<String,Point2D> ReturnPortalList;
	private HashMap<Point2D,Monstre> MonsterList;
	private Point2D positionHero;
	private HashMap<Point2D,Chest> CoffreList;
	private HashMap<Point2D,PNJ> PNJList;
	public ModeleMap() {
		this.PortalList=PortalList;
		this.ReturnPortalList=ReturnPortalList;
		this.MonsterList=MonsterList;
		this.positionHero=positionHero;
		this.CoffreList=CoffreList;
		this.setPNJList(PNJList);
		
	}
	public void setPortalList( HashMap<Point2D,String> PortalList) {
		this.PortalList=PortalList;
	}
	public HashMap<Point2D,String> getPortalList() {
		return this.PortalList;
	}
	public void setReturnPortalList( HashMap<String,Point2D> ReturnPortalList) {
		this.ReturnPortalList=ReturnPortalList;
	}
	public HashMap<String,Point2D> getReturnPortalList() {
		return this.ReturnPortalList;
	}
	public void setMonsterList( HashMap<Point2D,Monstre> MonsterList) {
		this.MonsterList=MonsterList;
	}
	public HashMap<Point2D,Monstre> getMonsterList() {
		return this.MonsterList;
	}
	public void setCoffreList( HashMap<Point2D,Chest> CoffreList) {
		this.CoffreList=CoffreList;
	}
	public HashMap<Point2D,Chest> getCoffreList() {
		return this.CoffreList;
	}
	public void init() {
		PortalList=new HashMap<Point2D,String>();
		ReturnPortalList=new HashMap<String,Point2D>();
		MonsterList=new HashMap<Point2D, Monstre>();
		CoffreList=new HashMap<Point2D,Chest>();
		setPNJList(new HashMap<Point2D, PNJ>());
	}
	public void setPositionHero( Point2D PositionHero) {
		this.positionHero=PositionHero;
	}
	public Point2D getPositionHero() {
		return this.positionHero;
	}
	public HashMap<Point2D,PNJ> getPNJList() {
		return PNJList;
	}
	public void setPNJList(HashMap<Point2D,PNJ> PNJList) {
		this.PNJList = PNJList;
	}
}
