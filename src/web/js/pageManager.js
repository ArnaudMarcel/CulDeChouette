function loadGame() {
    document.body.innerHTML =
    ``;
}

function loadConnexion() {
    
}

function loadCreation() {
    document.body.innerHTML =
    `<main>
    <center>
        <img src="img/logo.png" alt="logo.png" id="logo">
        <div id="connexion">
            <h1>Création de compte</h1>
            <p>Créez vous un compte.</p><br>
            <form method="post">
                <label for="pseudo">Pseudo :</label>
                <input id="pseudo" type="text" required><br><br>
                <label for="mdp">Mot de passe :</label>
                <input id="mdp" type="password" required><br><br>
                <label for="sexe">Sexe :</label>
                <div id="radio">
                <input type="radio" id="sexe" /> Homme <input type="radio" name="sexe" /> Femme
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

    let form = document.addEventListener('click', event => {
        switch (event.target.value) {
            case 'Annuler':
                loadIndex();
                break;
            
            case 'Valider':
                console.log("tetetete1");
                let pseudo = document.getElementById('pseudo').value;
                let mdp = document.getElementById('mdp').value;
                let sexe = document.getElementById('sexe').value;
                let ville = document.getElementById('ville').value;
                let age = document.getElementById('age').value;
                console.log("tetetete");
                CDCsocket._sendCreation(pseudo, mdp, sexe, ville, age);
                loadIndexConnected();
                break;
        }
    })
}

function loadIndexConnected() {
    document.body.innerHTML = `
    <main>
        <center>
            <img src="img/logo.png" alt="logo.png" id="logo">
            <div id="menu">
                <ul>
                    <li>
                        <a id="game">Jouer</a><br><br>
                        <a id="regles">Règles du jeu</a><br><br>
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
            <img src="img/logo.png" alt="logo.png" id="logo">
            <div id="menu">
                <ul>
                    <li>
                        <a id="connexion">Se connecter</a><br><br>
                        <a id="creation">Créer un compte</a><br><br>
                        <a id="regles">Règles du jeu</a><br><br>
                    </li>
                </ul>
            </div>
        </center>
    </main>
    `;
}

function loadRegles() {
    document.body.innerHTML =
    `<center>
    <img src="img/logo.png" alt="logo.png" id="logo">
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
        <a href="./index.html">Retour</a>
    </div>
</center>`;
}


let pageManager = document.addEventListener('click', event => {
    switch (event.target.id) {
        case 'game':
            loadGame();
            break;

        case 'connexion':
            loadConnexion();
            break;

        case 'creation':
            loadCreation();
            break;

        case 'regles':
            loadRegles();
            break;
    }
});
