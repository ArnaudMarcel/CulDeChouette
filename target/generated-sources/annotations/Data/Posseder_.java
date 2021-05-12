package Data;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-05-12T08:41:45")
@StaticMetamodel(Posseder.class)
public class Posseder_ { 

    public static volatile SingularAttribute<Posseder, Long> idPartie;
    public static volatile SingularAttribute<Posseder, Integer> nbPoints;
    public static volatile SingularAttribute<Posseder, Integer> nbChouettesVelutesPerdues;
    public static volatile SingularAttribute<Posseder, Integer> nbSuitesGagnees;
    public static volatile SingularAttribute<Posseder, Long> idJoueur;
    public static volatile SingularAttribute<Posseder, Boolean> estGagnant;

}