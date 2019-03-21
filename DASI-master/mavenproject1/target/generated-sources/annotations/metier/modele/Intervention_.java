package metier.modele;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Client;
import metier.modele.Employe;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-21T09:10:47")
@StaticMetamodel(Intervention.class)
public class Intervention_ { 

    public static volatile SingularAttribute<Intervention, Employe> employe;
    public static volatile SingularAttribute<Intervention, String> description;
    public static volatile SingularAttribute<Intervention, Client> client;
    public static volatile SingularAttribute<Intervention, Date> heureDebut;
    public static volatile SingularAttribute<Intervention, Long> id;
    public static volatile SingularAttribute<Intervention, Date> heureFin;
    public static volatile SingularAttribute<Intervention, String> commentaire;
    public static volatile SingularAttribute<Intervention, String> statut;

}