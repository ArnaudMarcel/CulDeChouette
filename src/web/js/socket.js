
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

        this.service.onmessage = (event) => {
            if (event.data != "Creation failed") {
                loadIndexConnected();
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Ce pseudo est déjà pris !'
                });
            }
        };       
    }

    static _connexionJoueur(pseudo, mdp) {
        this.service.send(JSON.stringify(
            {
                id: "connexion",
                pseudoJoueur: pseudo,
                motDePasseJoueur: mdp,
            })
        );

        this.service.onmessage = (event) => {
            if (event.data != "Connexion failed") {
                loadIndexConnected();
                this.service.onmessage = (event) => {
                    if (event.data.includes('rejoindre:')) {
                        let don = event.data.split(':');
                        Swal.fire({
                            title: `${don[don.length-1]} vous invite à jouer !`,
                            confirmButtonText: 'Accepter'
                        }).then(() => {
                            this.service.send(JSON.stringify({
                                id: 'rejoindre',
                                hebergeur: don[don.length-1],
                                pseudoJoueur: pseudo
                            }));
                            this.service.onmessage = (event) => {
                                event.data.includes('Rejoindre partie') ?
                                    CDCsocket._lobbyData(event.data) : '';
                            }
                            loadLobby();
                        });
                    }
                }
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Mot de passe incorrect'
                });
            }
        };
    }

    static _creerPartie(pts, pseudo) {
        this.service.send(JSON.stringify({
            id: 'creationPartie',
            heurePartie: new Date().toLocaleString(),
            nbPointsAAtteindrePartie: pts,
            hebergeur: pseudo
        }));

        this.service.onmessage = (event) => {
            if (event.data === 'Creation game failed') {
                Swal.fire({
                    icon: 'error',
                    title: 'Erreur survenue...',
                    confirmButtonText: 'retour',
                }).then(() => {
                    loadIndexConnected();
                });
            } 
        };
    }

    static _getJoueurs(currentPlayer) {
        this.service.send(JSON.stringify({
            id: 'Joueurliste',
            pseudoJoueur: currentPlayer
        }));

        this.service.onmessage = (event) => {
            let joueurs = JSON.parse(event.data);
            console.log(joueurs);
            let JoueursInv = '', JoueursLob = '';
            joueurs.joueursDisp.forEach(elt => {
                JoueursInv += `<tr>
                <td>
                    ${elt}
                </td>
                <td>
                    <button name="${elt}" class="inviter">Inviter</button>
                </td>
                </tr>`;
            });

            joueurs.joueursLobby.forEach(elt => {
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
                    CDCsocket._sendInvitation(event.target.name, currentPlayer);
                });
            });
        };
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

        this.service.onmessage = (event) => {}
    }

    static _lobbyData(data) {
        console.log(data);
    }
}

CDCsocket._connect();