package rpgapp.control;

import java.util.HashMap;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.entity.component.Component;

import javafx.geometry.Point2D;
import rpgapp.RPGApp;
import rpgapp.data.character.Monstre;
import rpgapp.data.elementInteractifs.Indice;
import rpgapp.data.elementInteractifs.PNJ;
import rpgapp.system.Quest;
import rpgapp.view.DisplayBasic;
import rpgapp.view.DisplayQuete;

public class QuestComponent extends Component{
	
	public static void verifQuest() {
		Quest q = RPGApp.hero.getCurrentquest();
		if(q.getNbCibles()==q.getNbCiblesMax()) {
			System.out.println("La quête est terminée");
			suiteQuete(q);
		}
		else {
			System.out.println("La quête n'est pas terminée");
		}
	}
	
	public static void suiteQuete(Quest q) {
		
		//Validation de la quête précedente
		q.validQuest();
		MusicComponent.soundPlay("succes");
		String notif= q.getName() +" accomplie !";
		RPGApp.notif = DisplayBasic.createNotif(notif);
		FXGL.getApp().getGameWorld().addEntity(RPGApp.notif);
		Quest nouvQ = null;
		Quest pnjQ = null;
		PNJ pnj = RPGApp.ListeMaps.get("mapMaison.json").getPNJList().get(new Point2D(1024,960));
		PNJ pnj1 = RPGApp.ListeMaps.get("mapPnj1.json").getPNJList().get(new Point2D(640,768));
		PNJ pnj2 = RPGApp.ListeMaps.get("mapPnj2.json").getPNJList().get(new Point2D(960,640));
		PNJ pnj3 = RPGApp.ListeMaps.get("mapPnj3.json").getPNJList().get(new Point2D(896,704));
		HashMap<String,HashMap<String,String[]>> nouvConversation=new HashMap<String,HashMap<String,String[]>>();
		HashMap<String,HashMap<String,String[]>> nouvConversation1=new HashMap<String,HashMap<String,String[]>>();
		HashMap<String,HashMap<String,String[]>> nouvConversation2=new HashMap<String,HashMap<String,String[]>>();
		HashMap<String,HashMap<String,String[]>> nouvConversation3=new HashMap<String,HashMap<String,String[]>>();
		
		//Actions selon la quête suivante
		switch(q.getId()) {
			
			case 0:			//Une fois l'indice de la cave trouvé >> Doit parler au père
				
				//Change la conversation du père
				String[] answer01 = new String[1];
				answer01[0] = "Non";
				String[] answer02 = new String[1];
				answer02[0] = "Ok";
				String[] answer0Finish = new String[1];
				answer0Finish[0] = "Non rien";
				String[] answer0Cours = new String[1];
				answer0Cours[0] = "D'accord";
				HashMap<String, String[]> conversation01 = RPGApp.Chat(answer01, "C'est bon, tu l'as trouvé ce rat ?");
				HashMap<String, String[]> conversation02 = RPGApp.Chat(answer02, "Hum... Il a du s'échapper, va voir si les habitants ont vu quelque chose.");
				HashMap<String, String[]> conversation03 = RPGApp.Chat(answer0Finish, "Alors ? Des nouvelles du rat ?");
				HashMap<String, String[]> conversation04 = RPGApp.Chat(answer0Cours, "Va voir les habitants. Peut être que l'un d'entre eux sait quelque chose.");
				
				nouvConversation.put("begin", conversation01);
				nouvConversation.put("Non", conversation02);
				nouvConversation.put("finish", conversation03);
				nouvConversation.put("en cours", conversation04);
				
				pnj.setListChat(nouvConversation);
				pnjQ = new Quest("Contact avec le Rat, Partie III:");
				pnj.setQuest(pnjQ, answer02[0]);
				
				//Change la quete
				nouvQ = new Quest("Contact avec le Rat, Partie II:", 100, "Parler à", "Père", 1, "Vous n'avez pas trouvé de rat dans la cave, mais des traces de son passage. Faites en part à votre père.", 1);
				break;
				
				
				
				
			case 1: 		//Une fois que le joueur a parlé à son père de l'indice de la cave >> Doit parler à un des pnjs
				
				//Change la conversation de Mr Bernard
				
				String[] answer11 = new String[1];
				answer11[0] = "Vous n'avez pas vu un rat ?";
				String[] answer12 = new String[1];
				answer12[0] = "Je m'en occupe";
				String[] answer1Finish = new String[1];
				answer1Finish[0] = "Pas de soucis";
				String[] answer1Cours = new String[1];
				answer1Cours[0] = "Pas encore";
				HashMap<String, String[]> conversation11 = RPGApp.Chat(answer11, "Ah te voila Ian.");
				HashMap<String, String[]> conversation12 = RPGApp.Chat(answer12, "Un rat ? Tu ne les as pas vu en venant ? C'est une véritable invasion dans le village !");
				HashMap<String, String[]> conversation13 = RPGApp.Chat(answer1Finish, "Ah Ian, merci !");
				HashMap<String, String[]> conversation14 = RPGApp.Chat(answer1Cours, "C'est bon, ils sont partis ?");
				
				nouvConversation.put("begin", conversation11);
				nouvConversation.put("Vous n'avez pas vu un rat ?", conversation12);
				nouvConversation.put("finish", conversation13);
				nouvConversation.put("en cours", conversation14);
				
				pnj2.setListChat(nouvConversation);
				pnjQ = new Quest("Une Invasion de Rat ? Partie I:");
				pnj2.setQuest(pnjQ, answer12[0]);
				
				//Change la quete
				
				nouvQ = new Quest("Contact avec le Rat, Partie III:", 200, "Parler à", "MrBernard", 1, "Votre père vous demande de vous informer sur le rat qui était dans la cave. Parlez aux habitants du village.", 2);
				break;
				
				
				
				
			case 2:			//Une fois que le joueur a parlé a Mr.Bernard >> Doit affronter les 3 rats à l'exterieur
				//Spawn des 3 rats du jardin
				RPGApp.createMonster("mapJardin.json", new Monstre("rat", 30, 20, 100,true,"Une Invasion de Rat ? Partie I:"), new Point2D(896, 2048));
				RPGApp.createMonster("mapJardin.json", new Monstre("rat", 30, 20, 100,true,"Une Invasion de Rat ? Partie I:"), new Point2D(2304, 2304));
				RPGApp.createMonster("mapJardin.json", new Monstre("rat", 30, 20, 100,true,"Une Invasion de Rat ? Partie I:"), new Point2D(2624, 2880));
				//Change la quete
				nouvQ = new Quest("Une Invasion de Rat ? Partie I:", 600, "Tuer", "Rats(s)", 3, "Mr.Bernard vous a prévenu d'une invasion de rats dans le village. Vous devriez vous en débarasser", 3);
				break;
				
				
				
				
			case 3:			//Une fois les rats battu >> Doit parler au père
				
				//Change la conversation du père
				
				String[] answer31 = new String[1];
				answer31[0] = "Une invasion";
				String[] answer32 = new String[1];
				answer32[0] = "Ok";
				String[] answer3Finish = new String[1];
				answer3Finish[0] = "D'accord";
				String[] answer3Cours = new String[1];
				answer3Cours[0] = "Pas encore";
				HashMap<String, String[]> conversation31 = RPGApp.Chat(answer31, "Alors ? Des nouvelles du rat ?");
				HashMap<String, String[]> conversation32 = RPGApp.Chat(answer32, "Comment ça une invasion ? Mais je m'en fiche moi ! J'ai entendu du bruit dans la cave, retournes y.");
				HashMap<String, String[]> conversation33 = RPGApp.Chat(answer3Finish, "");
				HashMap<String, String[]> conversation34 = RPGApp.Chat(answer3Cours, "Toujours pas finis ?");
				
				nouvConversation.put("begin", conversation31);
				nouvConversation.put("Une invasion", conversation32);
				nouvConversation.put("finish", conversation33);
				nouvConversation.put("en cours", conversation34);
				
				pnj.setListChat(nouvConversation);
				pnjQ = new Quest("Une Invasion de Rat ? Partie III:");
				pnj.setQuest(pnjQ, answer32[0]);
				
				//Change la quete
				nouvQ = new Quest("Une Invasion de Rat ? Partie II:", 200, "Parler à", "Père", 1, "Vous avez réussi à vaincre tout les rats. Prevenez votre père de votre exploit.", 4);
				break;
				
				
				
				
			case 4:			//Une fois que le joueur a parlé au père >> Doit Battre le rat dans la cave
				
				//Change la conversation de Mr Bernard en classique
				
				String[] answer41 = new String[2];
				answer41[0] = "Pas de soucis.";
				answer41[1] = "Desole, je n'ai pas le temps.";
				String[] answer42 = new String[1];
				answer42[0] = "D'accord.";
				
				HashMap<String, String[]> conversation41 = RPGApp.Chat(answer41, "Hey "+RPGApp.hero.getName()+" ! \nJ'ai faim, si tu vas dans ton jardin n'hesite \npas à me ramener des pommes de ton coffre.");
				HashMap<String, String[]> conversation421 = RPGApp.Chat(answer42, "Merci ! j'ai faim.");
				HashMap<String, String[]> conversation422 = RPGApp.Chat(answer42, "Tu es sûr ? Penses y si tu dois t'équiper");
				
				nouvConversation.put("begin", conversation41);
				nouvConversation.put("Pas de soucis.", conversation421);
				nouvConversation.put("Desole, je n'ai pas le temps.", conversation422);
				
				pnj2.setListChat(nouvConversation);
				pnjQ = new Quest();
				pnj2.setQuest(pnjQ, "");
				
				//Spawn du rat de la cave
				RPGApp.createMonster("mapCave.json", new Monstre("le Rat de la Cave", 50, 40, 100,true,"tuer le rat de la cave"), new Point2D(512, 704));
				//Change la quete
				nouvQ = new Quest("Une Invasion de Rat ? Partie III:", 800, "Tuer", "Rat de la cave", 1, "Vous avez contré l'invasion mais votre père entends de nouveau le rat de la cave. Débarassez vous en !", 5);
				break;
				
				
				
				
			case 5:			//Une fois que le rat est battu et s'est enfui >> Doit parler au père
				
				//Change la conversation du père
				
				String[] answer51 = new String[1];
				answer51[0] = "Oui";
				String[] answer52 = new String[1];
				answer52[0] = "Ok";
				String[] answer5Finish = new String[1];
				answer5Finish[0] = "Bien sûr";
				String[] answer5Cours = new String[1];
				answer5Cours[0] = "D'accord";
				HashMap<String, String[]> conversation51 = RPGApp.Chat(answer51, "Tu l'as trouvé ?");
				HashMap<String, String[]> conversation52 = RPGApp.Chat(answer52, "Je vois.. Il s'est encore enfuit. Cherches où il se terre. Moi et les villagois ont va t'aider à trouver des endroits où chercher.");
				HashMap<String, String[]> conversation53 = RPGApp.Chat(answer5Finish, "");
				HashMap<String, String[]> conversation54 = RPGApp.Chat(answer5Cours, "Commence par chercher chez Mr.Georges");
				
				nouvConversation.put("begin", conversation51);
				nouvConversation.put("Oui", conversation52);
				nouvConversation.put("finish", conversation53);
				nouvConversation.put("en cours", conversation54);
				
				pnj.setListChat(nouvConversation);
				pnjQ = new Quest("A la recherche d'un rat, Partie I:");
				pnj.setQuest(pnjQ, answer52[0]);
				
				//Change la quete
				nouvQ = new Quest("Une Invasion de Rat ? Partie IV:", 200, "Parler à", "Père", 1, "Ce maudit rat vous a échappé. Peut être que votre père sait ou il se cache ?", 6);
				break;
				
				
				
				
			case 6:			//Une fois que le joueur a parlé au père >> Doit trouver 4 indices sur les maps en étant aidé par les pnjs
				
				//Change la conversation du père et des 3 pnjs
				
				String[] answer6A1 = new String[1];
				answer6A1[0] = "Ok";
				String[] answer6B1 = new String[1];
				answer6B1[0] = "Ok";
				String[] answer6C1 = new String[1];
				answer6C1[0] = "Ok";
				String[] answer6Finish = new String[1];
				answer6Finish[0] = "Oui";
				
				HashMap<String, String[]> conversation6A1 = RPGApp.Chat(answer6A1, "Tu devrais chercher aux alentours de la route de l'est, j'y ai entendu des animaux.");
				HashMap<String, String[]> conversation6B1 = RPGApp.Chat(answer6B1, "Tu as pensé à vérifier si des rats ne s'était pas glisser dans ta chambre ? Tu devrais vite aller voir.");
				HashMap<String, String[]> conversation6C1 = RPGApp.Chat(answer6C1, "Si tu cherches des rats, j'en ai vu déchiqueter mon parterre de fleurs rouges au nord. Sauve mes fleurs chéries.");
				HashMap<String, String[]> conversation6A2 = RPGApp.Chat(answer6Finish, "Alors tu y a trouvé quelque chose ?");
				HashMap<String, String[]> conversation6B2 = RPGApp.Chat(answer6Finish, "Les rats sont bien venus dans ta chambre ?");
				HashMap<String, String[]> conversation6C2 = RPGApp.Chat(answer6Finish, "Tu as retrouvé des traces là-bas ?");
								
				nouvConversation1.put("en cours", conversation6A1);
				nouvConversation1.put("finish", conversation6A2);
				nouvConversation2.put("en cours", conversation6B1);
				nouvConversation2.put("finish", conversation6B2);
				nouvConversation3.put("en cours", conversation6C1);
				nouvConversation3.put("finish", conversation6C2);
				
				
				pnj1.setListChat(nouvConversation1);
				pnj2.setListChat(nouvConversation2);
				pnj3.setListChat(nouvConversation3);
				pnjQ = new Quest("A la recherche d'un rat, Partie I:");
				pnj1.setQuest(pnjQ, "");
				pnj3.setQuest(pnjQ, "");
				pnj2.setQuest(pnjQ, "");
				
				//Spawn des 4 indices
				RPGApp.createIndice("mapJardin.json", new Point2D(2496,704), new Indice("Crottes de rat", "Water_Icon.png"));
				RPGApp.createIndice("mapJardin.json", new Point2D(2816,1728), new Indice("Traces de pattes", "Water_Icon.png"));
				RPGApp.createIndice("mapMaison.json", new Point2D(1536,384), new Indice("Poils noirs", "Water_Icon.png"));
				RPGApp.createIndice("mapPnj1.json", new Point2D(1024,768),  new Indice("Pétales rouges", "Water_Icon.png"));
				//Change la quete
				nouvQ = new Quest("A la recherche d'un rat, Partie I:", 600, "Examiner", "Indice(s) du rat", 4, "Il semblerait que le rat laisse des traces ou il passe. Trouvez ces traces en demandant aux habitants.", 7);
				break;
				
				
				
				
			case 7:			//Une fois les 4 indices ramassés >> Doit parler au père
				
				//Change la conversation du père
				
				String[] answer71 = new String[1];
				answer71[0] = "Poils noirs";
				String[] answer72 = new String[1];
				answer72[0] = "Traces de pattes";
				String[] answer73 = new String[1];
				answer73[0] = "Pétales rouges";
				String[] answer74= new String[1];
				answer74[0] = "Crottes de rat";
				String[] answer75= new String[1];
				answer74[0] = "Ok";
				String[] answer7Finish = new String[1];
				answer7Finish[0] = "Oui";
				String[] answer7Cours = new String[1];
				answer7Cours[0] = "Pas encore";
				HashMap<String, String[]> conversation71 = RPGApp.Chat(answer71, "Qu'as tu trouvé ?");
				HashMap<String, String[]> conversation72 = RPGApp.Chat(answer72, "Des Poils noirs dans la maison ? Je vois, il doit encore être dans le coin. Autre chose ?");
				HashMap<String, String[]> conversation73 = RPGApp.Chat(answer73, "Des traces de pas sur la route de l'est ? C'est surement proche de son repère. Autre chose ?");
				HashMap<String, String[]> conversation74 = RPGApp.Chat(answer74, "Des fleurs rouges ? Se pourrait il que... Autre chose ?");
				HashMap<String, String[]> conversation75 = RPGApp.Chat(answer75, "Des crottes de rat dans le parterre ? J'en étais sûr ! C'est la qu'il se trouve.");
				HashMap<String, String[]> conversation76 = RPGApp.Chat(answer7Finish, "");
				HashMap<String, String[]> conversation77 = RPGApp.Chat(answer7Cours, "Pas encore mort ? Il est dans le parterre rouge.");
				
				nouvConversation.put("begin", conversation71);
				nouvConversation.put("Poils noirs", conversation72);
				nouvConversation.put("Traces de pattes", conversation73);
				nouvConversation.put("Pétales rouges", conversation74);
				nouvConversation.put("Crottes de rat", conversation75);
				nouvConversation.put("finish", conversation76);
				nouvConversation.put("en cours", conversation77);
				
				pnj.setListChat(nouvConversation);
				pnjQ = new Quest("A la recherche d'un rat, Partie III:");
				pnj.setQuest(pnjQ, answer75[0]);
				
				//Change la quete
				nouvQ = new Quest("A la recherche d'un rat, Partie II:", 200, "Parler à", "Père", 1, "Vous avez trouvé les traces du rat. Mais que signifient elles ? Votre père le sait sûrement", 8);
				break;
				
				
				
				
			case 8:			//Une fois que le joueur a parlé au père >> Doit affronter le rat réaparru.
				
				//Spawn du rat de la cave
				RPGApp.createMonster("mapJardin.json", new Monstre("le Rat de la Cave", 50, 40, 100,true,"A la recherche d'un rat, Partie III:"), new Point2D(2752, 448));
				//Change la quete
				nouvQ = new Quest("A la recherche d'un rat, Partie III:", 1000, "Tuer", "Rat de la cave", 1, "La cachette du rat est découverte. Éliminez le une bonne fois pour toute ICI", 9);
				break;
				
				
				
				
			case 9:			//Une fois le rat battu >> Doit parler au père
				
				//Change la conversation du père
				
				String[] answer91 = new String[1];
				answer91[0] = "Oui";
				String[] answer9Finish = new String[1];
				answer9Finish[0] = "Non rien";
				String[] answer9Cours = new String[1];
				answer9Cours[0] = "D'accord";
				HashMap<String, String[]> conversation91 = RPGApp.Chat(answer91, "C'est terminé ?");
				HashMap<String, String[]> conversation93 = RPGApp.Chat(answer9Finish, "C'est bien mon fils");
				HashMap<String, String[]> conversation94 = RPGApp.Chat(answer9Cours, "");
				
				nouvConversation.put("begin", conversation91);
				nouvConversation.put("finish", conversation93);
				nouvConversation.put("en cours", conversation94);
				
				pnj.setListChat(nouvConversation);
				pnjQ = new Quest("A la recherche d'un rat, Partie IV:");
				pnj.setQuest(pnjQ, "");
				
				
				//Change la quete
				nouvQ = new Quest("A la recherche d'un rat, Partie IV:", 100, "Parler à", "Père", 1, "Le dernier rat est mort près des fleurs. Retournez voir votre père pour enfin finir cette journée", 10);
				break;
				
				
				
				
			case 10:		//Une fois parler au père >> Quêtes terminées
				
				//Change la conversation des pnjs
				
				String[] answer10A = new String[1];
				answer10A[0] = "Pas de soucis";
				String[] answer10B = new String[1];
				answer10B[0] = "Aucun problème";
				String[] answer10C = new String[1];
				answer10C[0] = "Ce n'est rien";
				
				HashMap<String, String[]> conversation10A = RPGApp.Chat(answer10A, "Oh mon ptit "+RPGApp.hero.getName()+", merci de nous avoir sauvé des rats.");
				HashMap<String, String[]> conversation10B = RPGApp.Chat(answer10B, "Hey ! Merci " + RPGApp.hero.getName());
				HashMap<String, String[]> conversation10C = RPGApp.Chat(answer10C, "Merci d'avoir vengé mes fleurs chéries");
				
				nouvConversation1.put("begin", conversation10A);
				nouvConversation2.put("begin", conversation10B);
				nouvConversation3.put("begin", conversation10C);
				
				pnj1.setListChat(nouvConversation1);
				pnj2.setListChat(nouvConversation2);
				pnj3.setListChat(nouvConversation3);
				pnjQ = new Quest();
				pnj1.setQuest(pnjQ, "");
				pnj3.setQuest(pnjQ, "");
				pnj2.setQuest(pnjQ, "");
				
				break;
				
		}
		
		//Attribut la nouvelle quête au héros
		RPGApp.hero.setCurrentquest(nouvQ);
		DisplayQuete.updateQuete();
	}

}
