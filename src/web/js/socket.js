class CDCsocket {

    service = null;

    static _connect() {
        this.service = new WebSocket("ws://localhost:8080/culDeChouette/WebSocket");

        this.service.onopen = () => {
            this.service.send(JSON.stringify({
                id: "handShacking"
            }));
        };

        this.service.onclose = (event) => {
            console.log("Fermeture du service. Code : " + event.code);
        };
        
        this.service.onopen = () => {
            this.service.send(JSON.stringify({id: "handShacking"}));
        };

        this.service.onclose = (event) => {
            console.log("service.onclose... " + event.code);
        };

        this.service.onmessage = (event) => {
            let msg = JSON.parse(event.data);
            CDCsocket[msg.id](msg); // Appel à la fonction correspondant à la demande
        };

        this.service.onerror = () => {
            Swal.fire({
                icon: 'error',
                html: '<h2 style="font-weight:lighter; font-size:23px;">Erreur de connexion</h2><br><p>Connexion avec le serveur perdue</p>',
                confirmButtonColor: 'rgb(0, 151, 0)',
            });
        };
    }

    static hello() {
        console.log("connected");
    }

    static _sendCreation(pseudo, mdp, sexe, ville, age) {
        this.service.send(JSON.stringify({
            id: "creationJoueur",
            pseudoJoueur: pseudo,
            motDePasseJoueur: mdp,
            sexeJoueur: sexe,
            villeJoueur: ville,
            ageJoueur: age,
        }));
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
            html: '<h2 style="font-weight:lighter; font-size:23px;">Erreur</h2><br><p>Le pseudo est déjà utilisé</p>',
            confirmButtonColor: 'rgb(0, 151, 0)',
            title: 'Oops...',
            text: 'Ce pseudo est déjà pris !'
        });
    }

    static _connexionJoueur(pseudo, mdp) {
        this.service.send(JSON.stringify({
            id: "connexion",
            pseudoJoueur: pseudo,
            motDePasseJoueur: mdp,
        }));
    }

    static connexionJoueur_reussie() {
        loadIndexConnected();
    }

    static connexionJoueur_echec(msg) {
        Swal.fire({
            icon: 'error',
            title: 'Erreur de connexion',
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
            html: '<h2 style="font-weight:lighter; font-size:23px;">Erreur</h2><br><p>Une erreur est survenue lors de la création de la partie</p>',
            confirmButtonColor: 'rgb(0, 151, 0)',
            confirmButtonText: 'Retour',
            title: 'Erreur survenue...',
            confirmButtonText: 'retour',
        }).then(() => {
            loadIndexConnected();
        });
    }

    static _getJoueurs(currentPlayer) {
        this.service.send(JSON.stringify({
            id: 'Joueurliste',
            pseudoJoueur: currentPlayer
        }));
    }

    static listeDesJoueurs(msg) {
        let JoueursInv = '',
            JoueursLob = '';
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
            html: `<h2 style="font-weight:lighter; font-size:23px;">Joueur invité !</h2><br><p>Le joueur ${pseudo} à été invité au groupe de votre partie</p>`,
            title: `${pseudo} invité !`,
            showConfirmButton: false,
            timer: 1500
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
            html: `<h2 style="font-weight:lighter; font-size:23px;">Le joueur ${msg.hote} vous a invité à jouer !</h2><br><p>Voulez vous rejoindre sa partie ?</p>`,
            confirmButtonText: 'Accepter',
            confirmButtonColor: 'rgb(0, 151, 0)',
            showDenyButton: true,
            denyButtonText: 'Refuser'
        }).then( (result) => {
            if (result.value) {
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
        clearInterval(demande);
        loadIndexConnected();
        Swal.fire({
            icon: 'info',
            html: '<h2 style="font-weight:lighter; font-size:23px;">Partie dissoute</h2><br><p>Le groupe de la partie a été dissout</p>',
            confirmButtonColor: 'rgb(0, 151, 0)',
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
            html: '<h2 style="font-weight:lighter; font-size:23px;">À vous de jouer !</h2><br><p>C\'est à votre tour de lancer les dès</p>',
            confirmButtonColor: 'rgb(0, 151, 0)',
            showConfirmButton: false,
            timer: 1000
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

    static _statsJoueur(pseudo) {
        this.service.send(JSON.stringify({
            id: 'statistiques',
            pseudoJoueur: pseudo
        }));
    }

    static statistiques(msg) {
        msg.joueur != null ? 
            loadStatistiques(msg.joueur) : '';
    }

    static _leaveGame(pseudo) {
        this.service.send(JSON.stringify({
            id: 'dissoudrePartie',
            pseudoJoueur: pseudo
        }));
    }
}

CDCsocket._connect();