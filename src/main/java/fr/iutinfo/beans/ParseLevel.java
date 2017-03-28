package fr.iutinfo.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * Permet de créer un niveau à partir d'un fichier envoyé par un enseignant
 * Permmettra à celui-ci de créer des niveaux rapidement
 * Attention : format du fichier extrémement précis !
 * @author François Morel
 */
public class ParseLevel{
	
	public static Level parseText(String file){
		Level level = new Level();
		try{
			InputStream ips=new FileInputStream(new File(file)); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while((ligne=br.readLine())!=null){
				if(ligne.charAt(0)!='#'){
					if(ligne.length()>5 && ligne.substring(0,5).equals("Nom: ")){
						String nom=ligne.substring(5,ligne.length());
						level.setName(nom);
					}
					if(ligne.length()>13 && ligne.substring(0,13).equals("Orientation: ")){
						String orientation=ligne.substring(13,ligne.length());
						if(orientation.equals("true")){
							level.setOrientation(true);
						}else{
							level.setOrientation(false);
						}
					}
				}
			}
			br.close(); 
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
