let CDCjoueur = null;
let listDesJoueurs = [];
let demande, enPartie = false;

function sleep(time) {
    return new Promise((resolve) => setTimeout(resolve, time));
}

function loadGame(pts, listeDesJoueurs) {
    enPartie = true;
    let quitter = window.addEventListener('keydown', event => {
        if (event.key === "Escape" && enPartie) {
            Swal.fire({
                icon: 'question',
                html: '<h2 style="font-weight:lighter; font-size:23px;">Voulez vous vraiment quitter ?</h2><br><p>Le groupe sera dissout.</p>',
                confirmButtonColor: 'rgb(0, 151, 0)',
                showDenyButton: true,
                denyButtonText: 'Oui',
                denyButtonText: 'Non'
            }).then((result) => {
                if (result.value) {
                    enPartie = false;
                    CDCsocket._leaveGame(CDCjoueur.getPseudo());
                }
            });
        }
    });

    document.body.innerHTML =
        `<main>
    <center>
        <img src="img/logo.png" alt="logo.png" id="logoGame">
        <div id="fieldset">
        <table id="ScorePartie"></table>
            <fieldset>
                <legend>La Chouette</legend>
                <img src="img/1.png" id="des1" class="fadeIn">
                <img src="img/1.png" id="des2" class="fadeIn">
            </fieldset>
            <fieldset>
                <legend>Le Cul</legend>
                <img src="img/1.png" id="des3" class="fadeIn">
            </fieldset>
        </div>
        <div id="lancer"></div>
        <div id="trapeze">
            <div id="score">0 / ${pts}</div>
        </div>
        <button id="suiteButton">Grelotte ça picotte !</button>
        <button id="CVButton">Pas mou le caillou !</button>
    </center>
    </main>`;

    tableHTML = '';
    listeDesJoueurs.forEach(j => {
        tableHTML += `<tr>
            <td>${j}</td>
            <td>0</td>
        </tr>`;
    });
}

function tourJoueur() {
    document.getElementById('lancer').innerHTML = `<input type="button" value="Lancer les dés" id="buttonDes">`;
    document.getElementById('buttonDes').addEventListener('click', event => {
        CDCsocket._lancerDes(CDCjoueur.getPseudo());
        event.target.remove();
    });
}

function UpdateScore(score) {
    tableHTML = '';
    score.forEach(elt => {
        tableHTML += `<tr>
            <td>${elt[0]}</td>
            <td>${elt[1]}</td>
        </tr>`;
    });
    document.getElementById('ScorePartie').innerHTML = tableHTML;
}

function UpdateBottomScore(score) {
    let bottomS = document.getElementById('score');
    let s = score.find(elt => elt[0] === CDCjoueur.getPseudo())[1];
    bottomS.innerText = s + ' / ' + bottomS.innerText.split(' / ').pop();
}

function montrerDes(msg) {
    document.getElementById("des1").src = "img/" + msg.lancer.valeurDes1 + ".png";
    document.getElementById("des2").src = "img/" + msg.lancer.valeurDes2 + ".png";
    document.getElementById("des3").src = "img/" + msg.lancer.valeurDes3 + ".png";
    // document.getElementById("buttonDes").style.display = "none";
    document.getElementById("des1").className = "fadeIn load";
    sleep(500).then(() => {
        document.getElementById("des2").className = "fadeIn load";
        sleep(500).then(() => {
            document.getElementById("des3").className = "fadeIn load";
            sleep(800).then(() => {
                UpdateScore(msg.score);
                UpdateBottomScore(msg.score);
                msg.combinaison != "none" ? Swal.fire({
                    position: 'top-end',
                    html: `<h2 style="font-weight:lighter; font-size:23px;">Combinaison obtenue !</h2><br><p>Le joueur a obtenu : ${msg.combinaison}</p>`,
                    backdrop: 'transparent',
                    showConfirmButton: false,
                    timer: 2500
                }) : '';

                msg.lancer.interaction ? Swal.fire({
                    icon: 'warning',
                    html: `<h2 style="font-weight:lighter; font-size:23px;">${msg.action}</h2><br><p>Cliquez sur le bouton avant les autres joueurs pour ne pas être pénalisé !</p>`,
                    confirmButtonText: `${msg.reponse}`,
                    confirmButtonColor: 'rgb(0, 151, 0)',
                }).then((result) => {
                    if (result.value) {
                        document.getElementById("des1").className = "fadeIn";
                        document.getElementById("des2").className = "fadeIn";
                        document.getElementById("des3").className = "fadeIn";
                        CDCsocket._actionJoueur(CDCjoueur.getPseudo(), msg.action, msg.reponse, msg.lancer.valeurDes3);
                    }
                }) : '';
            });
        });
    });

    if (!msg.lancer.interaction) {
        sleep(4000).then(() => {
            document.getElementById("des1").className = "fadeIn";
            document.getElementById("des2").className = "fadeIn";
            document.getElementById("des3").className = "fadeIn";
        });
    }
}

function montrerDesLanceur(msg) {
    document.getElementById("des1").src = "img/" + msg.lancer.valeurDes1 + ".png";
    document.getElementById("des2").src = "img/" + msg.lancer.valeurDes2 + ".png";
    document.getElementById("des3").src = "img/" + msg.lancer.valeurDes3 + ".png";
    document.getElementById("des1").className = "fadeIn load";
    sleep(500).then(() => {
        document.getElementById("des2").className = "fadeIn load";
        sleep(500).then(() => {
            document.getElementById("des3").className = "fadeIn load";
            sleep(800).then(() => {
                UpdateScore(msg.score);
                UpdateBottomScore(msg.score);
                msg.combinaison != "none" ? Swal.fire({
                    position: 'top-end',
                    html: `<h2 style="font-weight:lighter; font-size:23px;">Combinaison obtenue !</h2><br><p>Vous avez obtenu : ${msg.combinaison}</p>`,
                    backdrop: 'transparent',
                    showConfirmButton: false,
                    timer: 2500
                }) : '';

                msg.lancer.interaction ? Swal.fire({
                    icon: 'warning',
                    html: `<h2 style="font-weight:lighter; font-size:23px;">${msg.action}</h2><br><p>Cliquez sur le bouton avant les autres joueurs pour ne pas être pénalisé !</p>`,
                    confirmButtonText: `${msg.reponse}`,
                    confirmButtonColor: 'rgb(0, 151, 0)',
                }).then((result) => {
                    if (result.value) {
                        document.getElementById("des1").className = "fadeIn";
                        document.getElementById("des2").className = "fadeIn";
                        document.getElementById("des3").className = "fadeIn";
                        CDCsocket._actionJoueur(CDCjoueur.getPseudo(), msg.action, msg.reponse, msg.lancer.valeurDes3);
                    }
                }) : '';
            });
        });
    });

    if (!msg.lancer.interaction) {
        sleep(4000).then(() => {
            document.getElementById("des1").className = "fadeIn";
            document.getElementById("des2").className = "fadeIn";
            document.getElementById("des3").className = "fadeIn";
            CDCsocket._joueurSuivant(CDCjoueur.getPseudo());
        });
    }
}

function loadConnexion() {
    document.body.innerHTML = `
    <main>
        <center>
            <img src="img/logo.png" alt="logo.png" id="logoConnexion">
            <div id="connexion">
                <h1>Connexion</h1>
                <p>Connectez vous à votre compte.</p><br>
                <form>
                    <label for="pseudo">Pseudo :</label>
                    <input id="pseudo" name="pseudo" type="text" required><br><br>
                    <label for="mdp">Mot de passe :</label>
                    <input id="mdp" name="mdp" type="password" required><br><br>
                    <input type="reset" value="Annuler" id="button">
                    <input type="button" value="Connexion" id="button">
                </form>
                <br><br>
                <a onClick="loadCreation();">Je n'ai pas encore de compte</a>
            </div><br><br>
        </center>
    </main>`;

    document.addEventListener('click', event => {
        switch (event.target.value) {

            case 'Annuler':
                loadIndex();
                break;

            case 'Connexion':
                event.stopImmediatePropagation();
                let pseudo = document.getElementById('pseudo').value;
                let mdp = document.getElementById('mdp').value;
                CDCjoueur = new Joueur(pseudo);
                if (pseudo != '' && mdp != '') {
                    CDCsocket._connexionJoueur(pseudo, mdp);
                }
                break;
        }
    });
}

function loadCreation() {
    document.body.innerHTML =
        `<main>
    <center>
    <img src="img/logo.png" alt="logo.png" id="logoCreation">
        <div id="creation">
            <h1>Création de compte</h1>
            <p>Créez vous un compte.</p><br>
            <form method="post">
                <label for="pseudo">Pseudo :</label>
                <input id="pseudo" type="text" required><br><br>
                <label for="motDePasse">Mot de passe :</label>
                <input id="motDePasse" type="password" required><br><br>
                <label for="sexe">Sexe :</label>
                <div id="radio">
                <input type="checkbox" id="Homme" class="gender"/> Homme <input type="checkbox" id="Femme" class="gender"/> Femme
                </div><br>
                <label for="ville">Ville :</label>
                <input id="ville" type="text" required><br><br>
                <label for="age">Age :</label>
                <input id="age" type="number" required min="1" max="120"><br><br>
                <input type="button" value="Annuler" class="button">
                <input type="button" value="Valider" class="button">
            </form>
            <br><br>
        </div><br><br>
    </center>
    </main>`;

    let sexe = null;
    [].slice.call(document.getElementsByClassName('gender')).forEach(elt => {
        elt.addEventListener('click', event => {
            sexe = event.target.id;
            switch (event.target.id) {
                case 'Homme':
                    document.getElementById('Homme').checked = true;
                    document.getElementById('Femme').checked = false;
                    break;
                case 'Femme':
                    document.getElementById('Femme').checked = true;
                    document.getElementById('Homme').checked = false;
                    break;
            }
        });
    });

    document.addEventListener('click', event => {
        switch (event.target.value) {
            case 'Annuler':
                loadIndex();
                break;
            case 'Valider':
                let pseudo = document.getElementById('pseudo').value;
                let mdp = document.getElementById('motDePasse').value;
                let ville = document.getElementById('ville').value;
                let ageJ = document.getElementById('age').value;
                CDCjoueur = new Joueur(pseudo);
                if (sexe != null && ageJ != '' && pseudo != '' && mdp != '' && ville != '') {
                    event.stopImmediatePropagation();
                    CDCsocket._sendCreation(pseudo, mdp, sexe, ville, ageJ);
                }
                break;
        }
    });
}

function loadIndexConnected() {
    enPartie = false;
    document.body.innerHTML = `
    <main>
        <center>
            <img src="img/logo.png" alt="logo.png" id="logoAccueil">
            <div id="menu">
                <ul>
                    <li>
                        <a id="create_game">Créer une partie</a><br><br>
                        <a id="stats">Statistiques</a><br><br>
                        <a id="bregles">Règles du jeu</a><br><br>
                        <a id="deconnexion">Déconnexion</a>
                    </li>
                </ul>
            </div>
        </center>
    </main>
    `;
}

function loadIndex() {
    document.body.innerHTML = `
    <main>
        <center>
            <img src="img/logo.png" alt="logo.png" id="logoAccueil">
            <div id="menu">
                <ul>
                    <li>
                        <a id="bconnexion">Se connecter</a><br><br>
                        <a id="bcreation">Créer un compte</a><br><br>
                        <a id="bregles">Règles du jeu</a><br><br>
                    </li>
                </ul>
            </div>
        </center>
    </main>
    `;
}

function loadRegles() {
    document.body.innerHTML =
        `<main>
    <center>
    <img src="img/logo.png" alt="logo.png" id="logoRegles">
    <div id="regles">
        <h1>Règles du jeu</h1>
        <fieldset>
            <legend>Principe général</legend>
            <p>
                Le Cul de Chouette se joue avec 3 dés à 6 faces et un maximum de 6 joueurs. Le joueur gagnant
                est
                celui
                qui atteint ou dépasse le premier le score de 343. <br>Chaque joueur joue à tour de rôle et
                marque des points en fonction des combinaisons de son lancer de 3 dés. Certaines combinaisons
                impliquent une interaction
                entre les joueurs qui déterminera les points gagnés ou perdus.
                Un joueur lance une partie en invitant un ou plusieurs autres joueurs et en précisant l'ordre de
                jeu de chacun. C'est le joueur qui a lancé la partie qui commence à jouer.
            </p>
        </fieldset>
        <fieldset>
            <legend>Combinaisons</legend>
            <p>
                Les dés se lancent en 2 fois : d'abord 2 dés puis le troisième. Le jet des 2 premiers dés est
                appelé
                la
                chouette et le troisième dé est appelé le cul.
            </p>
            <p>
                <u>Les combinaisons sans interaction :</u><br>
            </p>
            <p>
                La velute : la somme des dés de la chouette donne la valeur du cul. <br>Exemple : (1,3) avec 4.
                Les points marqués par le joueur sont le carré de la velute. Dans l'exemple précédent, la velute
                est
                de 4, donc 16 points sont
                marqués.<br><br>
                La chouette : les deux dés de la chouette sont identiques. <br>Exemple : (5,5). Les points
                marqués sont le carré
                de cette valeur identique, soit 25 pour l'exemple.<br><br>
                Le cul de chouette : les trois dés sont identiques. 50 points sont marqués pour un cul de
                chouette de 1, 60
                pour un de 2, 70 pour un de 3, 80 pour un de 4, 90 pour un de 5 et 100 pour un de 6.
            </p>
            <p>
                <u>Les combinaisons avec interaction :</u><br>
            </p>
            <p>
                La suite : toute combinaison de 3 dés donnant 3 valeurs consécutives (1,2,3), (3,4,5) ... En
                cas de
                suite, chaque joueur doit crier "Grelotte ça picote !". Le dernier joueur à le faire perd 10
                points.<br><br>
                La chouette velute : il s'agit d'une chouette qui avec le cul donne une velute, ce qui est le
                cas pour
                les 3 combinaisons (1,1) avec 2, (2,2) avec 4 et (3,3) avec 6. Dans ce cas, chaque joueur doit
                crier
                "Pas mou
                le caillou !". Le premier à crier gagne les points de la velute.
            </p>
        </fieldset><br><br>
        <a id="back">Retour</a>
    </div>
    </center>
    </main>`;

    document.getElementById('back').addEventListener('click', event => {
        CDCjoueur === null ? loadIndex() : loadIndexConnected();
    });
}

function getPointsGame() {
    const inputValue = 343;
    const inputStep = 1;
    Swal.fire({
        icon: 'question',
        html: `<h2 style="font-weight:lighter; font-size:23px;">Quel score devez vous atteindre pour gagner la partie ?</h2><br>
    <input
      type="number"
      value="${inputValue}"
      step="${inputStep}"
      class="swal2-input"
      id="range-value">`,
        input: 'range',
        inputValue,
        inputAttributes: {
            min: 50,
            max: 1000,
            step: inputStep
        },
        confirmButtonText: 'Valider',
        confirmButtonColor: 'rgb(0, 151, 0)',
        didOpen: () => {
            const inputRange = Swal.getInput();
            const inputNumber = Swal.getContent().querySelector('#range-value');

            inputRange.nextElementSibling.style.display = 'none';
            inputRange.style.width = '100%';

            inputRange.addEventListener('input', () => {
                inputNumber.value = inputRange.value;
            })

            inputNumber.addEventListener('change', () => {
                inputRange.value = inputNumber.value;
            })
        }
    }).then(function (result) {
        if (result.value) {
            CDCjoueur != null ? CDCsocket._creerPartie(result.value, CDCjoueur.getPseudo()) : '';
            Swal.fire({
                html: '<br><h2 style="font-weight:lighter; font-size:23px;">Le score à atteindre pour cette partie est de ' + result.value + '.</h2><br>',
                confirmButtonText: 'Valider',
                confirmButtonColor: 'rgb(0, 151, 0)'
            });
        }
    });
}

function loadLobby() {
    document.body.innerHTML = `<main>
    <center>
        <div id="liste">
            <img src="img/logo.png" alt="logo.png" id="logoListe"><br><br>
            <fieldset>
                <legend>Joueurs disponibles</legend>
                <table id="invitations">
                </table>
            </fieldset>
            <fieldset>
                <legend>Joueurs dans la partie</legend>
                <table id="lobbyGame">
                </table>
            </fieldset><br><br>
            <a id="back">Retour</a>
        </div>
    </center>
    </main>`;

    demande = setInterval(() => {
        CDCsocket._getJoueurs(CDCjoueur.getPseudo());
    }, 1000);

    document.getElementById('back').addEventListener('click', event => {
        clearInterval(demande)
        CDCsocket._leaveLobby(CDCjoueur.getPseudo());
        loadIndexConnected();
    });
}

function loadStatistiques(joueur) {
    document.body.innerHTML = `<main>
    <center>
        <img src="img/logo.png" alt="logo.png" id="logoStat"><br><br>
        <div id="statistiques">
            <h1>Statistiques</h1>
                <fieldset>
                    <legend>Statistiques de ${joueur.pseudoJoueur}</legend>
                    <table id="joueurStats">
                        <tr>
                            <td>Nombre de parties</td>
                            <td>${joueur.nbParties}</td>
                        <td>
                        <tr>
                            <td>Nombre de victoires</td>
                            <td>${joueur.nbVictoires}</td>
                        <td>
                        <tr>
                            <td>Nombre moyen de victoires</td>
                            <td>${joueur.nbMoyVictoires}</td>
                        <td>
                        <tr>
                            <td>Score moyen</td>
                            <td>${joueur.scoreMoyen}</td>
                        <td>
                        <tr>
                            <td>Moyenne de chouettes velutes perdues</td>
                            <td>${joueur.moyChouettesVelutesPerdues}</td>
                        <td>
                        <tr>
                            <td>Moyenne de suites gagnées</td>
                            <td>${joueur.moySuitesGagnees}</td>
                        <td>
                    </table>
                </fieldset><br><br>
                <a id="retour">Retour</a>
            </div>
        </center>
    </main>`;

    document.getElementById('retour').addEventListener('click', event => {
        loadIndexConnected();
    });
}

function loadCreateGame() {

    document.body.innerHTML = `<main>
    <center>
        <div id="liste">
            <img src="img/logo.png" alt="logo.png" id="logoListe"><br><br>
            <fieldset>
                <legend>Joueurs disponibles</legend>
                <table id="invitations">
                </table>
            </fieldset>
            <fieldset>
                <legend>Joueurs dans la partie</legend>
                <table id="lobbyGame">
                </table>
            </fieldset><br><br>
            <a id="start">Lancer la partie</a><br><br>
            <a id="back">Retour</a><br><br>
        </div>
    </center>
    </main>`;

    getPointsGame();
    demande = setInterval(() => {
        CDCsocket._getJoueurs(CDCjoueur.getPseudo());
    }, 1000);

    document.getElementById('back').addEventListener('click', event => {
        clearInterval(demande)
        CDCsocket._quitterLobby(CDCjoueur.getPseudo());
    });

    document.getElementById('start').addEventListener('click', event => {
        CDCsocket._lancerPartie(CDCjoueur.getPseudo());
    }, 1000);
}

window.addEventListener('beforeunload', (event) => {
    CDCsocket._disconnect(CDCjoueur.getPseudo());
});

let pageManager = document.addEventListener('click', event => {
    switch (event.target.id) {
        case 'deconnexion':
            CDCsocket._disconnect(CDCjoueur.getPseudo());
            CDCjoueur = null;
            loadIndex();
            break;

        case 'create_game':
            loadCreateGame();
            break;

        case 'bconnexion':
            loadConnexion();
            break;

        case 'bcreation':
            loadCreation();
            break;

        case 'bregles':
            loadRegles();
            break;

        case 'stats':
            CDCsocket._statsJoueur(CDCjoueur.getPseudo());
            break;
    }
});
