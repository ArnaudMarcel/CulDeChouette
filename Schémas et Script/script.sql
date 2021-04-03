------------------------------------------------------------
-- Table: Joueur
------------------------------------------------------------
CREATE TABLE Joueur(
	idJoueur                    NUMBER NOT NULL ,
	pseudoJoueur                VARCHAR2 (50) NOT NULL  ,
	motDePasseJoueur            VARCHAR2 (50) NOT NULL  ,
	ageJoueur                   NUMBER(10,0)  NOT NULL  ,
	sexeJoueur                  NUMBER (1) NOT NULL  ,
	villeJoueur                 VARCHAR2 (50) NOT NULL  ,
	nbParties                   NUMBER(10,0)  NOT NULL  ,
	nbVictoires                 NUMBER(10,0)  NOT NULL  ,
	nbMoyVictoires              NUMBER(10,0)  NOT NULL  ,
	scoreMoyen                  NUMBER(10,0)  NOT NULL  ,
	moySuitesGagnees            NUMBER(10,0)  NOT NULL  ,
	moyChouettesVelutesPerdues  NUMBER(10,0)  NOT NULL  ,
	CONSTRAINT Joueur_PK PRIMARY KEY (idJoueur),
	CONSTRAINT CHK_BOOLEAN_sexeJoueur CHECK (sexeJoueur IN (0,1))
);

------------------------------------------------------------
-- Table: Partie
------------------------------------------------------------
CREATE TABLE Partie(
	idPartie     NUMBER NOT NULL ,
	heurePartie  DATE  NOT NULL  ,
	CONSTRAINT Partie_PK PRIMARY KEY (idPartie)
);

------------------------------------------------------------
-- Table: Lancer
------------------------------------------------------------
CREATE TABLE Lancer(
	idLancer     NUMBER NOT NULL ,
	valeurDes1   NUMBER(10,0)  NOT NULL  ,
	valeurDes2   NUMBER(10,0)  NOT NULL  ,
	valeurDes3   NUMBER(10,0)  NOT NULL  ,
	combinaison  VARCHAR2 (50)  ,
	idJoueur     NUMBER(10,0)  NOT NULL  ,
	idPartie     NUMBER(10,0)  NOT NULL  ,
	CONSTRAINT Lancer_PK PRIMARY KEY (idLancer)

	,CONSTRAINT Lancer_Joueur_FK FOREIGN KEY (idJoueur) REFERENCES Joueur(idJoueur)
	,CONSTRAINT Lancer_Partie0_FK FOREIGN KEY (idPartie) REFERENCES Partie(idPartie)
);

------------------------------------------------------------
-- Table: POSSEDER
------------------------------------------------------------
CREATE TABLE POSSEDER(
	idPartie                   NUMBER(10,0)  NOT NULL  ,
	idJoueur                   NUMBER(10,0)  NOT NULL  ,
	nbPoints                   NUMBER(10,0)  NOT NULL  ,
	nbSuitesGagnees            NUMBER(10,0)  NOT NULL  ,
	nbChouettesVelutesPerdues  NUMBER(10,0)  NOT NULL  ,
	estGagnant                 NUMBER (1) NOT NULL  ,
	CONSTRAINT POSSEDER_PK PRIMARY KEY (idPartie,idJoueur),
	CONSTRAINT CHK_BOOLEAN_estGagnant CHECK (estGagnant IN (0,1))

	,CONSTRAINT POSSEDER_Partie_FK FOREIGN KEY (idPartie) REFERENCES Partie(idPartie)
	,CONSTRAINT POSSEDER_Joueur0_FK FOREIGN KEY (idJoueur) REFERENCES Joueur(idJoueur)
);





CREATE SEQUENCE Seq_Joueur_idJoueur START WITH 1 INCREMENT BY 1 NOCYCLE;
CREATE SEQUENCE Seq_Partie_idPartie START WITH 1 INCREMENT BY 1 NOCYCLE;
CREATE SEQUENCE Seq_Lancer_idLancer START WITH 1 INCREMENT BY 1 NOCYCLE;


CREATE OR REPLACE TRIGGER Joueur_idJoueur
	BEFORE INSERT ON Joueur 
  FOR EACH ROW 
	WHEN (NEW.idJoueur IS NULL) 
	BEGIN
		 select Seq_Joueur_idJoueur.NEXTVAL INTO :NEW.idJoueur from DUAL; 
	END;
CREATE OR REPLACE TRIGGER Partie_idPartie
	BEFORE INSERT ON Partie 
  FOR EACH ROW 
	WHEN (NEW.idPartie IS NULL) 
	BEGIN
		 select Seq_Partie_idPartie.NEXTVAL INTO :NEW.idPartie from DUAL; 
	END;
CREATE OR REPLACE TRIGGER Lancer_idLancer
	BEFORE INSERT ON Lancer 
  FOR EACH ROW 
	WHEN (NEW.idLancer IS NULL) 
	BEGIN
		 select Seq_Lancer_idLancer.NEXTVAL INTO :NEW.idLancer from DUAL; 
	END;

