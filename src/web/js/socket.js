
class CDCsocket {

    service = null;

    static _connect() {
        this.service = new WebSocket("ws://localhost:8080/culDeChouette/WebSocket");
        
        this.service.onopen = () => {
            this.service.send(JSON.stringify({id: "handShacking"}));
        };

        this.service.onclose = (event) => {
            console.log("service.onclose... " + event.code);
        };

        this.service.onmessage = (event) => {
            console.log(event.data);
            let msg = JSON.parse(event.data);
            CDCsocket[msg.id](msg); // Appel à la fonction correspondant à la demande
        };

        this.service.onerror = () => {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Connexion avec le serveur perdue'
            });
        };
    }

    static _sendCreation(pseudo, mdp, sexe, ville, age) {
        this.service.send(JSON.stringify(
            {
                id: "creationJoueur",
                pseudoJoueur: pseudo,
                motDePasseJoueur: mdp,
                sexeJoueur: sexe,
                villeJoueur: ville,
                ageJoueur: age,
            })
        );      
    }

    static creationJoueur_reussie() {
        loadIndexConnected();
    }

    static creationJoueur_echec() {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Ce pseudo est déjà pris !'
        });
    }

    static _connexionJoueur(pseudo, mdp) {
        this.service.send(JSON.stringify(
            {
                id: "connexion",
                pseudoJoueur: pseudo,
                motDePasseJoueur: mdp,
            })
        );
    }

    static connexionJoueur_reussie() {
        loadIndexConnected();
    }

    static connexionJoueur_echec(msg) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: `${msg.raison}`
        });
    }

    static _creerPartie(pts, pseudo) {
        this.service.send(JSON.stringify({
            id: 'creationPartie',
            heurePartie: new Date().toLocaleString(),
            nbPointsAAtteindrePartie: pts,
            hebergeur: pseudo
        }));
    }

    static creationPartie_reussie() {

    }

    static creationPartie_echec() {
        Swal.fire({
            icon: 'error',
            title: 'Erreur survenue...',
            confirmButtonText: 'retour',
        }).then(() => {
            loadIndexConnected();
        });
    }

    static _getJoueurs(currentPlayer) {
        console.log(currentPlayer);
        this.service.send(JSON.stringify({
            id: 'Joueurliste',
            pseudoJoueur: currentPlayer
        }));
    }

    static listeDesJoueurs(msg) {
            let JoueursInv = '', JoueursLob = '';
            msg.joueursDisp.forEach(elt => {
                JoueursInv += `<tr>
                <td>
                    ${elt}
                </td>
                <td>
                    <button name="${elt}" class="inviter">Inviter</button>
                </td>
                </tr>`;
            });

            msg.joueursLobby.forEach(elt => {
                JoueursLob += `<tr>
                    <td>
                        ${elt}
                    </td>
                </tr>`;
            });

            document.getElementById('invitations').innerHTML = JoueursInv;
            document.getElementById('lobbyGame').innerHTML = JoueursLob;
            [].slice.call(document.getElementsByClassName('inviter')).forEach(elt => {
                elt.addEventListener('click', event => {
                    CDCsocket._sendInvitation(event.target.name, CDCjoueur.getPseudo());
                });
            });
    }

    static _disconnect(pseudo = null) {
        this.service.send(JSON.stringify({
            id: 'disconnect',
            pseudoJoueur: pseudo
        }));
    }

    static _sendInvitation(pseudo, host) {
        Swal.fire({
            icon: 'success',
            title: `${pseudo} invité !`,
        });

        this.service.send(JSON.stringify({
            id: 'invitation',
            pseudoJoueur: pseudo,
            hebergeur: host,
        }));
    }

    static invitationJoueur_reussie() {
        
    }

    static invitationPartie(msg) {
        Swal.fire({
            title: 'Viens jouer !',
            text: `${msg.hote} vous a invité`,
            confirmButtonText: 'Accepter',
            showDenyButton: true,
            denyButtonText: 'Refuser'
        }).then( (result) => {
            if (result.value) {
                console.log("here");
                this.service.send(JSON.stringify({
                    id: 'rejoindre',
                    pseudoJoueur: msg.invite,
                    hebergeur: msg.hote
                }));
            }
        });
    }

    static Rejoindre_partie(msg) {
        loadLobby();
    }

    static _leaveLobby(pseudo) {
        this.service.send(JSON.stringify({
            id: 'quitterLobby',
            pseudoJoueur: pseudo
        }));
    }

    static PartieSupprimee() {
        clearInterval(InterID);
        loadIndexConnected();
        Swal.fire({
            icon: 'info',
            title: 'Partie dissoute'
        });
    }

    static _quitterLobby(pseudo) {
        this.service.send(JSON.stringify({
            id: 'quitterLobby',
            pseudoJoueur: pseudo
        }));
    }

    static _lancerPartie(pseudo) {
        this.service.send(JSON.stringify({
            id: 'lancerPartie',
            pseudoJoueur: pseudo
        }));
    }

    static culDeChouette(msg) {
        clearInterval(demande);
        let joueurs = `<table><tr>`;
        msg.listeJoueurs.forEach(elt => {
            joueurs += `<td>${elt}</td>`;
        });
        joueurs += `</tr></table>`;
        loadGame(msg.points, joueurs);
    }

    static tour() {
        Swal.fire({
            icon: 'info',
            title: "c'est votre tour !"
        });

        tourJoueur();
    }

    static _lancerDes(pseudo) {
        this.service.send(JSON.stringify({
            id: 'lancerDes',
            pseudoJoueur: pseudo,
        }));
    }

    static resultatDesLanceur(msg) {
        montrerDesLanceur(msg);
    }

    static resultatDes(msg) {
        montrerDes(msg);
    }

    static _joueurSuivant(pseudo) {
        this.service.send(JSON.stringify({
            id: 'joueurSuivant',
            pseudoJoueur: pseudo
        }));
    }

    static _actionJoueur(pseudo, action, reponse, valeurDes3) {
        this.service.send(JSON.stringify({
            id: 'actionJoueur',
            pseudoJoueur: pseudo,
            nom: action,
            reponse: reponse,
            valeurCul: valeurDes3
        }));
    }

    static PartieTerminee(msg) {
        Swal.fire({
            title: 'Partie Terminée',
            text: `Le seul, le grand, l'unique gagnant est ${msg.gagnant}`
        }).then(() => {
            loadIndexConnected();
        });
    }
}

CDCsocket._connect();