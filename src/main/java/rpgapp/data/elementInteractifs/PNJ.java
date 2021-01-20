package rpgapp.data.elementInteractifs;

import java.util.HashMap;

import rpgapp.system.Quest;

public class PNJ {
	
	private String name;
	private String text;
	private String image;
	private Quest quest;
	private String giveQuest="null";
	private HashMap<String,String[]> chat;
	private HashMap<String,HashMap<String,String[]>> listChat;
	

	public String getText() {
		return text;
	}
	public PNJ(String name, String image,HashMap<String,HashMap<String,String[]>> listChat, Quest Quest,String giveQuest) {
		this.listChat=listChat;
		this.name = name;
		this.image = image;
		setQuest(Quest,giveQuest);
	}
	public PNJ(String name, String image, String message) {
		
		this.name = name;
		this.text = message;
		this.image = image;
		this.chat=new HashMap<String,String[]>();
		this.listChat=new HashMap<String,HashMap<String,String[]>>();
		String[] answers = new String[1];
		answers[0]="OK";
		this.chat.put("answers",answers);
		String[] listMessage = new String[1];
		listMessage[0]=text;
		this.chat.put("message",listMessage);
		this.listChat.put("begin", chat);
	}
	
	public PNJ(String name, String image,HashMap<String,HashMap<String,String[]>> conversation) {

		this.listChat=conversation;
		this.name = name;
		this.image = image;
		setQuest(new Quest(),"");
	}
	
	public HashMap<String,HashMap<String,String[]>> getListChat() {
		return this.listChat;
	}
	
	public void setListChat(HashMap<String,HashMap<String,String[]>> listChat) {
		this.listChat=listChat;
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
	
	public void setGiveQuest(String giveQuest) {
		this.giveQuest = giveQuest;
	}
	
	public String getImage() {
		return image;
	}
	public String getImage(String ajout) {
		String[] a =image.split("_");
		return a[0]+ajout+".png";
	}
		
}
