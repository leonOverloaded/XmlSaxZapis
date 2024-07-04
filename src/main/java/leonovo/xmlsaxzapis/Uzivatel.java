/**
 * uci se:
 * Leon Belligerrator
 */
package leonovo.xmlsaxzapis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * vyukove pasmo IT Network
 * @author itnetwork.cz
 */
public class Uzivatel {
    private final String jmeno;
    private final int vek;
    private final LocalDate registrovan;
    public static DateTimeFormatter formatData = DateTimeFormatter.ofPattern("d'.'MMMM yyyy");
    
    public Uzivatel(String jmeno, int vek, LocalDate registrovan){
        this.jmeno = jmeno;
        this.vek = vek;
        this.registrovan = registrovan;
    }
    @Override
    public String toString(){
        return getJmeno();
    }
    public String getJmeno(){
        return jmeno;
    }
    public int getVek(){
        return vek;
    }
    public LocalDate getRegistrovan(){
        return registrovan;
    }
}
