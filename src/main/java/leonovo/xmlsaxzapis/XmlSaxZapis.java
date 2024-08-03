/**
 * uci se:
 * Leon Belligerrator
 */
package leonovo.xmlsaxzapis;

/**
 * vyukove pasmo IT Network
 * @author itnetwork.cz
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * vyukove pasmo IT Network
 * @author itnetwork.cz
 */
public class XmlSaxZapis {

    public static void main(String[] args)throws IOException {
        Path soubor = Paths.get(System.getProperty("user.home"), "itnetwork", "soubor.xml");
        try{
            Files.createDirectories(soubor.getParent()); //vytvori potrebne adresare, v pripade, ze neexistuji
        }
        catch(IOException ex){
            System.err.println("Chyba pri vytvareni potrebnych adresaru: " + ex.getLocalizedMessage());
        }
        ArrayList<Uzivatel> uzivatele = new ArrayList<>();
        LocalDate datum1 = LocalDate.of(2000,Month.MARCH, 21);
        LocalDate datum2 = LocalDate.of(2012, Month.OCTOBER, 30);
        LocalDate datum3 = LocalDate.of(2011, Month.JANUARY, 1);
        uzivatele.add(new Uzivatel("Pavel Slavik", 22, datum1));
        uzivatele.add(new Uzivatel("Jan Novak", 31, datum2));
        uzivatele.add(new Uzivatel("Tomas Marny", 16, datum3));
        
        XMLOutputFactory xof = XMLOutputFactory.newInstance() ;
        XMLStreamWriter xsw = null;
        try{
            xsw = xof.createXMLStreamWriter(new FileWriter(soubor.toString(), StandardCharsets.UTF_8));
            xsw.writeStartDocument();
            xsw.writeStartElement("uzivatele");
            
            for(Uzivatel u : uzivatele){
                xsw.writeStartElement("uzivatel");
                xsw.writeAttribute("vek", Integer.toString(u.getVek()));
                xsw.writeStartElement("jmeno");
                xsw.writeCharacters(u.getJmeno());
                xsw.writeEndElement();
                xsw.writeStartElement("registrovan");
                xsw.writeCharacters(Uzivatel.formatData.format(u.getRegistrovan()));
                xsw.writeEndElement();
                xsw.writeEndElement();
            }
            
            xsw.writeEndElement();
            xsw.writeEndDocument();
            xsw.flush();
        }
        catch(Exception e){
            System.err.println("Chyba pri zapisu: " + e.getLocalizedMessage());
        }
        finally{
            try{
                if(xsw != null){
                    xsw.close();
                }
            }
            catch(Exception e){
                System.err.println("Chyba pri uzavirani souboru: " + e.getLocalizedMessage());
            }
        }
        try{
            formatuj(soubor.toString());
        }
        catch(IOException | TransformerException | ParserConfigurationException | SAXException ex){
            System.err.println("Chyba pri formatovani souboru: "+ ex.getMessage());
        }

    }
    static public void formatuj(String soubor) throws IOException, TransformerException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("file:///" +soubor);

        //ziskame novou instanci transformeru
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        //nastavime formatovani pro XML
        xformer.setOutputProperty(OutputKeys.METHOD, "xml");
        //nastavime odsazeni
        xformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Source source = new DOMSource(document);
        Result result = new StreamResult(new File(soubor));
        xformer.transform(source, result);
    }
        
}
