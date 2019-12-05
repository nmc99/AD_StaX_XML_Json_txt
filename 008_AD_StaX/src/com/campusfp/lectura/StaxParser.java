package com.campusfp.lectura;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.campusfp.modelo.Item;

public class StaxParser {

	static final String NOMBRE = "nombre";
	static final String CIUDAD = "ciudad";
	static final String SALARIO = "salario";
	static final String DATE = "date";
	static final String ITEM = "item";

	public List<Item> leerXML(String archivoXML) {
		
		List<Item> listadoItems = new ArrayList<Item>();


		try {
			XMLInputFactory factoria = XMLInputFactory.newInstance();
			InputStream entrada = new FileInputStream(archivoXML);

			XMLEventReader lector = factoria.createXMLEventReader(entrada);
			Item item = null;

			while (lector.hasNext()) {
				// Object object = (Object) lector.next();
				// System.out.println(object);
				XMLEvent event = lector.nextEvent();

				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					// If we have an item element, we create a new item
					if (startElement.getName().getLocalPart().equals(ITEM)) {
						item = new Item();
						// We read the attributes from this tag and add the date
						// attribute to our object
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals(DATE)) {
								item.setDate(attribute.getValue());
							}

						}
					}

					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart().equals(NOMBRE)) {
							event = lector.nextEvent();
							item.setNombre(event.asCharacters().getData());
							continue;
						}
					}
					if (event.asStartElement().getName().getLocalPart().equals(CIUDAD)) {
						event = lector.nextEvent();
						item.setCiudad(event.asCharacters().getData());
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals(SALARIO)) {
						event = lector.nextEvent();
						item.setSalario(event.asCharacters().getData());
						continue;
					}

				}
				// If we reach the end of an item element, we add it to the list
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart().equals(ITEM)) {
						listadoItems.add(item);
					}
				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listadoItems;

	} // Cierra el metodo leerXML

} // Cierra la clase
