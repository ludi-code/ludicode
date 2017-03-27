package fr.iutinfo.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ParseLevel {
	
	public Level level;
	
	public static void parseText(String file){
		try{
			InputStream ips=new FileInputStream(new File(file)); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while((ligne=br.readLine())!=null){
				System.out.println(ligne);
			}
			br.close(); 
		}catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	public static void main(String[] args){
		parseText("/home/infoetu/morelf/Agile/ludicode/Oui.txt");
	}
}
