#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------


#------------------------------------------------------------
# Table: Joueur
#------------------------------------------------------------

CREATE TABLE Joueur(
        idJoueur                   Int  Auto_increment  NOT NULL ,
        pseudoJoueur               Varchar (50) NOT NULL ,
        motDePasseJoueur           Varchar (50) NOT NULL ,
        ageJoueur                  Int NOT NULL ,
        sexeJoueur                 Bool NOT NULL ,
        villeJoueur                Varchar (50) NOT NULL ,
        nbParties                  Int NOT NULL ,
        nbVictoires                Int NOT NULL ,
        nbMoyVictoires             Int NOT NULL ,
        scoreMoyen                 Int NOT NULL ,
        moySuitesGagnees           Int NOT NULL ,
        moyChouettesVelutesPerdues Int NOT NULL
	,CONSTRAINT Joueur_PK PRIMARY KEY (idJoueur)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Partie
#------------------------------------------------------------

CREATE TABLE Partie(
        idPartie                 Int  Auto_increment  NOT NULL ,
        heurePartie              Date NOT NULL ,
        nbPointsAAtteindrePartie Int NOT NULL
	,CONSTRAINT Partie_PK PRIMARY KEY (idPartie)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Lancer
#------------------------------------------------------------

CREATE TABLE Lancer(
        idLancer    Int  Auto_increment  NOT NULL ,
        valeurDes1  Int NOT NULL ,
        valeurDes2  Int NOT NULL ,
        valeurDes3  Int NOT NULL ,
        interaction Bool ,
        idJoueur    Int NOT NULL ,
        idPartie    Int NOT NULL
	,CONSTRAINT Lancer_PK PRIMARY KEY (idLancer)

	,CONSTRAINT Lancer_Joueur_FK FOREIGN KEY (idJoueur) REFERENCES Joueur(idJoueur)
	,CONSTRAINT Lancer_Partie0_FK FOREIGN KEY (idPartie) REFERENCES Partie(idPartie)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: POSSEDER
#------------------------------------------------------------

CREATE TABLE POSSEDER(
        idPartie                  Int NOT NULL ,
        idJoueur                  Int NOT NULL ,
        nbPoints                  Int NOT NULL ,
        nbSuitesGagnees           Int NOT NULL ,
        nbChouettesVelutesPerdues Int NOT NULL ,
        estGagnant                Bool NOT NULL
	,CONSTRAINT POSSEDER_PK PRIMARY KEY (idPartie,idJoueur)

	,CONSTRAINT POSSEDER_Partie_FK FOREIGN KEY (idPartie) REFERENCES Partie(idPartie)
	,CONSTRAINT POSSEDER_Joueur0_FK FOREIGN KEY (idJoueur) REFERENCES Joueur(idJoueur)
)ENGINE=InnoDB;

