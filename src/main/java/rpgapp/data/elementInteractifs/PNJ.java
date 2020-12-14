package rpgapp.data.elementInteractifs;

import java.util.HashMap;

import rpgapp.system.Quest;

public class PNJ {
	
	private String name;
	private String texte;
	private String image;
	private Quest quest;
	private String giveQuest="null";
	private HashMap<String,String[]> conversation;
	private HashMap<String,HashMap<String,String[]>> traceConversation;
	

	public String getTexte() {
		return texte;
	}
	public PNJ(String name, String image,HashMap<String,HashMap<String,String[]>> conversation, Quest Quest,String giveQuest) {
		this.traceConversation=conversation;
		this.name = name;
		this.image = image;
		setQuest(Quest,giveQuest);
	}
	public PNJ(String name, String image, String message) {
		
		this.name = name;
		this.texte = message;
		this.image = image;
		this.conversation=new HashMap<String,String[]>();
		this.traceConversation=new HashMap<String,HashMap<String,String[]>>();
		String[] a = new String[1];
		a[0]="OK";
		this.conversation.put("answers",a);
		String[] b = new String[1];
		b[0]=texte;
		this.conversation.put("message",b);
		this.traceConversation.put("begin", conversation);
	}
	
	public PNJ(String name, String image,HashMap<String,HashMap<String,String[]>> conversation) {
		this.traceConversation=conversation;
		this.name = name;
		this.image = image;
		setQuest(new Quest(),"");
	}
	
	public HashMap<String,HashMap<String,String[]>> getTraceConversation() {
		return this.traceConversation;
	}
	public void setTraceConversation(HashMap<String,HashMap<String,String[]>> a) {
		this.traceConversation=a;
	}
	public String getName() {
		return name;
	}
	public Quest getQuest() {
		return quest;
	}
	public void setQuest(Quest Quest,String givequest) {
		quest=Quest;
		giveQuest=givequest;
	}
	public String getgiveQuest() {
		return giveQuest;
	}
	public String getImage() {
		return image;
	}
		
}
