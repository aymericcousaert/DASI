package metier;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Service {

    public static final DateFormat USER_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    
    public List<Personne> consulterListePersonnes() {
     
        ArrayList<Personne> liste = new ArrayList<Personne>();
        for (int index = 0; index < PERSONNE_DATA.length; index++) {
            try {
                String[] personneData = PERSONNE_DATA[index];
                Personne personne = new Personne(personneData[0], personneData[1], personneData[2], CSV_DATE_FORMAT.parse(personneData[3]), personneData[4], personneData[6]);
                personne.setId(PERSONNE_ID + index);
                liste.add(personne);
            } catch (ParseException ex) {
                // Ignore
            }
        }
        
        return liste;
    }
    
    public Personne consulterDetailPersonne(long idPersonne) {
        Personne personne = null;
        int index = (int) (idPersonne - PERSONNE_ID);
        try {
                String[] personneData = PERSONNE_DATA[index];
                personne = new Personne(personneData[0], personneData[1], personneData[2], CSV_DATE_FORMAT.parse(personneData[3]), personneData[4], personneData[6]);
                personne.setId(PERSONNE_ID + index);
            } catch (ParseException ex) {
                // Ignore
            }
        return personne;
    }
    
    protected static final long PERSONNE_ID = 1024;
    protected static final DateFormat CSV_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    public static class Personne {

        protected Long id;
        protected String civilite;
        protected String nom;
        protected String prenom;
        protected Date dateNaissance;
        protected String adresse;
        protected String mail;

        public Personne(String civilite, String nom, String prenom, Date dateNaissance, String adresse, String mail) {
            this.civilite = civilite;
            this.nom = nom;
            this.prenom = prenom;
            this.dateNaissance = dateNaissance;
            this.adresse = adresse;
            this.mail = mail;
        }

        public Long getId() {
            return id;
        }

        public String getCivilite() {
            return civilite;
        }

        public String getNom() {
            return nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public Date getDateNaissance() {
            return dateNaissance;
        }

        public String getAdresse() {
            return adresse;
        }

        public String getMail() {
            return mail;
        }

        protected void setId(Long id) {
            this.id = id;
        }

    }

    protected static final String[][] PERSONNE_DATA = {
        {"Mme", "BLIM", "Hanae", "1987-04-18", "18 rue des Brosses, Strasbourg", "0193217034", "hanae.blim@hotmail.com"},
        {"M.", "DEUVAOR", "Romaric", "1987-10-31", "195 rue de Saint-Ouen, Arès", "0717730490", "rdeuvaor1749@gmail.com"},
        {"Mme", "DUBIURG", "Nataly", "1971-11-18", "13 rue de Boulogne-Billancourt, Niort", "0756499181", "ndubiurg529@free.fr"},
        {"M.", "GOSCHE", "Moulay Mamoun", "1994-11-23", "56 rue de Vincennes, Jouè-lès-Tours", "0475756550", "moulay-mamoun.gosche@yahoo.com"},
        {"Mme", "DREUYOR", "Alexandra", "1991-11-07", "127 rue de Angers, Sarcelles", "0289080871", "alexandra.dreuyor@yahoo.com"},
        {"M.", "VOLLANUOVA GINZALEZ", "Imane", "1992-02-01", "43 rue Malherbe, Antony", "0309122521", "ivollanuovaginzalez@outlook.com"},
        {"Mme", "PHON", "Anna Christina", "1994-09-24", "85 rue des Teinturiers, Metz", "0634976341", "anna-christina.phon@laposte.net"},
        {"M.", "LULÉ", "Jonathan", "1994-05-08", "155 rue de Le Port, St Quentin", "0435985000", "jlule@yahoo.com"}
    };

}
