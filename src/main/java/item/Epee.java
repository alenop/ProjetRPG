package item;

import java.io.Writer;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

public class Epee implements Comment {

	public int getEventType() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isStartElement() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAttribute() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isNamespace() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEndElement() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEntityReference() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isProcessingInstruction() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCharacters() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isStartDocument() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEndDocument() {
		// TODO Auto-generated method stub
		return false;
	}

	public StartElement asStartElement() {
		// TODO Auto-generated method stub
		return null;
	}

	public EndElement asEndElement() {
		// TODO Auto-generated method stub
		return null;
	}

	public Characters asCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	public QName getSchemaType() {
		// TODO Auto-generated method stub
		return null;
	}

	public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
		// TODO Auto-generated method stub

	}

	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

}
