package fr.iutinfo.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * Permet de créer un niveau à partir d'un fichier envoyé par un enseignant
 * Permettra à celui-ci de créer des niveaux rapidement
 * Attention : format du fichier extrémement précis !
 * Voir pour comprendre: Comment ça marche:Niveaux ludicode en texte
 * @author François
 */
public class ParseLevel{
	
	public static Level parseText(String file){
		boolean nonConforme=false;
		Level level = new Level();
		String typeSauv="";
		boolean orienSauv=false;
		int x = 0;
		int y = 0;
		try{
			InputStream ips=new FileInputStream(new File(file)); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while((ligne=br.readLine())!=null && nonConforme==false){
				if(ligne.charAt(0)!='#'){
					if(ligne.length()>5 && ligne.substring(0,5).equals("Nom: ")){
						String nom=ligne.substring(5,ligne.length());
						level.setName(nom);
					}
					if(ligne.length()>6 && ligne.substring(0,6).equals("Type: ")){
						String type=ligne.substring(6,ligne.length()).toLowerCase();
						if(type.equals("blocks")||type.equals("block")){
							level.setOrientation(true);
							level.setlevelType("blocks");
							typeSauv ="blocks";
							orienSauv=true;
						}else{
							if(type.equals("cartes")||type.equals("carte")){
								level.setlevelType("cartes");
								typeSauv ="cartes";
							}else if(type.equals("python")){
								level.setlevelType(type);
								typeSauv ="python";
							}else{
								nonConforme=true;
							}
							if(ligne.length()>13 && ligne.substring(0,13).equals("Orientation: ")){
								String orientation=ligne.substring(13,ligne.length()).toLowerCase();
								if(orientation.equals("absoluex")||orientation.equals("absolu")){
									level.setOrientation(false);
									orienSauv=false;
								}else if(orientation.equals("relative")||orientation.equals("relatif")){
									level.setOrientation(true);
									orienSauv=true;
								}else{
									nonConforme=true;
								}
							}
						}
					}
					if(ligne.length()>18 && ligne.substring(0,18).equals("Max_instructions: ")){
						String max_instru=ligne.substring(18,ligne.length());
						level.setMaxInstructions(Integer.parseInt(max_instru));
					}
					if(ligne.length()>12 && ligne.substring(0,12).equals("Dimensions: ")){
						String[] tab= ligne.substring(12,ligne.length()).split(";");
						x=Integer.parseInt(tab[0]);
						y=Integer.parseInt(tab[1]);
					}
					if(ligne.length()>=8 && ligne.substring(0,8).equals("Plateau:")){
						String result="";
						String[] tab;
						for(int i=0;i<y;i++){
							ligne=br.readLine();
							tab = ligne.split(",");
							for(int j=0; j<tab.length;j++){
								if(typeSauv=="cartes" && orienSauv==false){
									if(tab[j].equals("-")){
										result+=0;
									}
									if(tab[j].equals("X")){
										result+=1;
									}
									if(tab[j].equals("H")){
										result+=2;
									}
									if(tab[j].equals("C")){
										result+=3;
									}
									if(j!=tab.length-1){
										result+=" ";
									}
								}
								System.out.println("#MDR JPP DE L'AGILE ET DE MON PARSAGE");
							}
							if(i!=y-1){
								result+=",";
							}
						}
						level.setContent(result);
					}
				}
			}
			br.close();
			if(nonConforme==true){
				System.out.println("ATTENTION: votre fichier n'est pas à un format conforme! Prenez exemple sur le fichier type!");
			}
		}catch (Exception e){
			System.out.println(e.toString());
		}
		System.out.println(level);
		return level;
	}
	
	public static void main(String[] args){
		parseText("/home/infoetu/morelf/Agile/ludicode/Test.txt");
	}
}
