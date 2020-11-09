package rpgapp.data.map;

import java.util.HashMap;


import rpgapp.data.character.Monstre;
import rpgapp.data.elementInteractifs.Coffre;
import javafx.geometry.Point2D;

public class ModeleMap {
	private HashMap<Point2D,String> PortalList;
	private HashMap<Point2D,Monstre> MonsterList;
	private Point2D positionHero;
	private HashMap<Point2D,Coffre> CoffreList;
	public ModeleMap() {
		this.PortalList=PortalList;
		this.MonsterList=MonsterList;
		this.positionHero=positionHero;
		this.CoffreList=CoffreList;
	}
	public void setPortalList( HashMap<Point2D,String> PortalList) {
		this.PortalList=PortalList;
	}
	public HashMap<Point2D,String> getPortalList() {
		return this.PortalList;
	}
	public void setMonsterList( HashMap<Point2D,Monstre> MonsterList) {
		this.MonsterList=MonsterList;
	}
	public HashMap<Point2D,Monstre> getMonsterList() {
		return this.MonsterList;
	}
	public void setCoffreList( HashMap<Point2D,Coffre> CoffreList) {
		this.CoffreList=CoffreList;
	}
	public HashMap<Point2D,Coffre> getCoffreList() {
		return this.CoffreList;
	}
	public void init() {
		PortalList=new HashMap<Point2D,String>();
		MonsterList=new HashMap<Point2D, Monstre>();
		CoffreList=new HashMap<Point2D,Coffre>();
	}
	public void setPositionHero( Point2D PositionHero) {
		this.positionHero=PositionHero;
	}
	public Point2D getPositionHero() {
		return this.positionHero;
	}
}