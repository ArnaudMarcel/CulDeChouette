
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
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Mot de passe incorrect'
                });
            }
        };
    }

    static _creerPartie(pts) {
        this.service.send(JSON.stringify({
            id: 'creationPartie',
            heurePartie: new Date().toLocaleString(),
            nbPointsAAtteindrePartie: pts,
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
        }));

        this.service.onmessage = (event) => {
            let joueurs = event.data.slice(1, event.data.length-1).split(', ');
            let htmlarray = '';
            joueurs.forEach(elt => {
                currentPlayer != elt ?
                htmlarray += `<tr>
                <td>
                    ${elt}
                </td>
                <td>
                    <button>Inviter</button>
                </td>
                </tr>` : ``;
            });
            document.getElementById('invitations').innerHTML = htmlarray;
        };
    }

    static _disconnect(pseudo = null) {
        this.service.send(JSON.stringify({
            id: 'disconnect',
            pseudoJoueur: pseudo
        }));
    }

    static _getJoueurs() {
        
    }
}

CDCsocket._connect();