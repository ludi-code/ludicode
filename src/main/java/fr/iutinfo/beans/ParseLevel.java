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
 * @author François
 */
public class ParseLevel{
	
	public static Level parseText(String file){
		boolean nonConforme=false;
		Level level = new Level();
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
						}else{
							if(type.equals("cartes")||type.equals("carte")){
								level.setlevelType("cartes");
							}else if(type.equals("python")){
								level.setlevelType(type);
							}else{
								nonConforme=true;
							}
							if(ligne.length()>13 && ligne.substring(0,13).equals("Orientation: ")){
								String orientation=ligne.substring(13,ligne.length()).toLowerCase();
								if(orientation.equals("absoluex")||orientation.equals("absolu")){
									level.setOrientation(false);
								}else if(orientation.equals("relative")||orientation.equals("relatif")){
									level.setOrientation(true);
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
					if(ligne.length()>18 && ligne.substring(0,18).equals("Max_instructions: ")){
						String max_instru=ligne.substring(18,ligne.length());
						level.setMaxInstructions(Integer.parseInt(max_instru));
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
