package com.campusfp.lectura;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.campusfp.modelo.Item;

public class PruebaLeer {

	public static void main(String[] args) {

		StaxParser parsearLectura = new StaxParser();
		System.out.println("\n-- ARCHIVO XML --");
		List<Item> listado = parsearLectura.leerXML("config.xml");

		JSONArray listaItems = new JSONArray();

		for (Item item : listado) {
			JSONObject json = new JSONObject();
			json.put("Nombre", item.getNombre());
			json.put("Ciudad", item.getCiudad());
			json.put("Salario", item.getSalario());
			JSONObject lista = new JSONObject();
			lista.put("Item", json);
			listaItems.add(lista);
			System.out.println(item);
		}
		
		StaXWriter configFile = new StaXWriter();
        configFile.setFile("config2.xml");
        List<Item> listado2 = parsearLectura.leerXML("config2.xml");
        
        System.out.println("\n-- ARCHIVO XML MODIFICADO --");
        
        for(Item item : listado2) {
        	System.out.println(item);
        }
        
        try {
            configFile.saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

		try (FileWriter file = new FileWriter("items.json")) {

			file.write(listaItems.toJSONString());
			file.flush();

			String carpeta = System.getProperty("user.dir");

			File archivo = new File(carpeta, "items.txt");

			FileWriter fw = new FileWriter(archivo);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(listaItems.toJSONString().toString());
			bw.close();
			
			System.out.println("\n-- ARCHIVO TXT --");

			String cadena;
			FileReader f = new FileReader(archivo);
			BufferedReader b = new BufferedReader(f);
			while ((cadena = b.readLine()) != null) {
				System.out.println(cadena);
			}
			b.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
