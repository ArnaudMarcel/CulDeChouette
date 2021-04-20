
class CDCsocket {

    service = null;

    static _connect() {
        this.service = new WebSocket("ws://localhost:8080/culDeChouette/WebSocket");
        
        this.service.onopen = () => {
            window.alert("service.onopen...");
            let response = window.confirm(this.service.url + " just opened... Say 'Hi!'?");
            if (response) this.service.send(JSON.stringify({id: "handShacking"}));
        };

        this.service.onclose = (event) => { /*:CloseEvent*/
            console.log("service.onclose... " + event.code);
            window.alert("Bye! See you later...");
        };

        this.service.onerror = () => {
            window.alert("service.onerror...");
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
            event.data === "Creation ok" ? loadIndexConnected() : 
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Ce pseudo est déjà pris !'
                });
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
            event.data === "Connexion ok" ? loadIndexConnected() : 
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Mot de passe incorrect'
                });
        };
    }

    static _creerPartie(pts, myInter) {
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
                    clearInterval(myInter);
                    loadIndexConnected();
                });
            }
        };
    }
}

CDCsocket._connect();