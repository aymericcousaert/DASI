package metier.modele;

import java.sql.Time;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Intervention;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-21T09:10:47")
@StaticMetamodel(Employe.class)
public class Employe_ extends Personne_ {

    public static volatile SingularAttribute<Employe, Time> finTravail;
    public static volatile SingularAttribute<Employe, Boolean> estDispo;
    public static volatile SingularAttribute<Employe, Time> debutTravail;
    public static volatile SingularAttribute<Employe, Intervention> intervention;

}