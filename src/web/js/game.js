let scoreGoal = 343;
let personnalScore = 0;

// Swal.fire({
//   position: 'top-end',
//   text: ' Tour de ' + ...
//   showConfirmButton: false,
//   timer: 1500
// })

window.addEventListener("load", function (event) {
    const inputValue = scoreGoal;
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
        onOpen: () => {
            const inputRange = Swal.getInput();
            const inputNumber = Swal.getContent().querySelector('#range-value');

            // remove default output
            inputRange.nextElementSibling.style.display = 'none';
            inputRange.style.width = '100%';

            // sync input[type=number] with input[type=range]
            inputRange.addEventListener('input', () => {
                inputNumber.value = inputRange.value;
            })

            // sync input[type=range] with input[type=number]
            inputNumber.addEventListener('change', () => {
                inputRange.value = inputNumber.value;
            })
        }
    }).then(function (result) {
        if (result.value) {
            scoreGoal = result.value;
            Swal.fire({
                html: '<br><h2 style="font-weight:lighter; font-size:23px;">Le score à atteindre pour cette partie est de ' + scoreGoal + '.</h2><br>',
                confirmButtonText: 'Valider',
                confirmButtonColor: 'rgb(0, 151, 0)'
            });
            document.getElementById("score").innerHTML = "0 / " + scoreGoal;
        }
    })
});

//Fonction permettant de lancer les des
function rollDices() {
    var dice1 = Math.floor(Math.random() * (7 - 1)) + 1;
    var dice2 = Math.floor(Math.random() * (7 - 1)) + 1;
    var dice3 = Math.floor(Math.random() * (7 - 1)) + 1;
    document.getElementById("des1").src = "img/" + dice1 + ".png";
    document.getElementById("des2").src = "img/" + dice2 + ".png";
    document.getElementById("des3").src = "img/" + dice3 + ".png";
    // document.getElementById("buttonDes").style.display = "none";
    document.getElementById("des1").className = "fadeIn load";
    sleep(500).then(() => {
        document.getElementById("des2").className = "fadeIn load";
        sleep(500).then(() => {
            document.getElementById("des3").className = "fadeIn load";
            sleep(500).then(() => {
                calculateScore(dice1, dice2, dice3);
            });
        });
    });
}

//Fonction permettant de calculer le score obtenue et de l'afficher
function calculateScore(dice1, dice2, dice3) {
    //Velute
    if ((dice1 + dice2) == dice3) {
        console.log("Velute + " + Math.pow((dice3), 2) + " points !");
        Swal.fire({
            position: 'top-end',
            text: "Velute + " + Math.pow((dice3), 2) + " points !",
            backdrop: 'transparent',
            showConfirmButton: false,
            timer: 1500
        })
        personnalScore += Math.pow((dice3), 2);
        console.log(personnalScore);
    }
    //Chouette
    else if (dice1 == dice2 && (dice1 != dice3)) {
        console.log("Chouette + " + Math.pow((dice1), 2) + " points !");
        Swal.fire({
            position: 'top-end',
            text: 'Chouette + ' + Math.pow((dice1), 2) + ' points !',
            backdrop: 'transparent',
            showConfirmButton: false,
            timer: 1500
        })
        personnalScore += Math.pow((dice1), 2);
        console.log(personnalScore);
    }
    //Cul de Chouette
    else if ((dice1 == dice2) && (dice1 == dice3)) {
        if (dice1 == 1) {
            console.log("Cul de Chouette + 50 points !");
            Swal.fire({
                position: 'top-end',
                text: 'Cul de Chouette + 50 points !',
                backdrop: 'transparent',
                showConfirmButton: false,
                timer: 1500
            })
            personnalScore += 50;
        } else if (dice1 == 2) {
            console.log("Cul de Chouette + 60 points !");
            Swal.fire({
                position: 'top-end',
                text: 'Cul de Chouette + 60 points !',
                backdrop: 'transparent',
                showConfirmButton: false,
                timer: 1500
            })
            personnalScore += 60;
        } else if (dice1 == 3) {
            console.log("Cul de Chouette + 70 points !");
            Swal.fire({
                position: 'top-end',
                text: 'Cul de Chouette + 70 points !',
                backdrop: 'transparent',
                showConfirmButton: false,
                timer: 1500
            })
            personnalScore += 70;
        } else if (dice1 == 4) {
            console.log("Cul de Chouette + 80 points !");
            Swal.fire({
                position: 'top-end',
                text: 'Cul de Chouette + 80 points !',
                backdrop: 'transparent',
                showConfirmButton: false,
                timer: 1500
            })
            personnalScore += 80;
        } else if (dice1 == 5) {
            console.log("Cul de Chouette + 90 points !");
            Swal.fire({
                position: 'top-end',
                text: 'Cul de Chouette + 90 points !',
                backdrop: 'transparent',
                showConfirmButton: false,
                timer: 1500
            })
            personnalScore += 90;
        } else if (dice1 == 6) {
            console.log("Cul de Chouette + 100 points !");
            Swal.fire({
                position: 'top-end',
                text: 'Cul de Chouette + 100 points !',
                backdrop: 'transparent',
                showConfirmButton: false,
                timer: 1500
            })
            personnalScore += 100;
        }
        console.log(personnalScore);
    }
    //Suite
    if (((dice1 == dice2 + 1) && (dice1 == dice3 + 2)) || ((dice1 == dice3 + 1) && (dice1 == dice2 + 2)) || ((dice2 == dice1 + 1) && (dice2 == dice3 + 2)) || ((dice2 == dice3 + 1) && (dice2 == dice1 + 2)) || ((dice3 == dice1 + 1) && (dice3 == dice2 + 2)) || ((dice3 == dice2 + 1) && (dice3 == dice1 + 2))) {
        console.log("Suite");
        Swal.fire({
            position: 'top-end',
            text: 'Suite',
            backdrop: 'transparent',
            showConfirmButton: false,
            timer: 1500
        })
    }
    //Chouette Velute
    if ((dice1 == dice2) && (dice1 + dice2 == dice3)) {
        console.log("Chouette Velute");
        Swal.fire({
            position: 'top-end',
            text: 'Chouette Velute !',
            backdrop: 'transparent',
            showConfirmButton: false,
            timer: 1500
        })
    }
    document.getElementById("score").innerHTML = personnalScore + " / " + scoreGoal;
    if (personnalScore >= scoreGoal) {
        console.log("Vous avez gagné la partie !");
        Swal.fire({
            text: 'Vous remportez cette partie !',
            imageUrl: 'https://png.pngtree.com/png-vector/20190819/ourlarge/pngtree-gold-trophy-icon-trophy-icon-winner-icon-png-image_1694365.jpg',
            imageWidth: 400,
            imageHeight: 400,
            imageAlt: 'Trophee',
            confirmButtonText: 'Super !',
            confirmButtonColor: 'rgb(0, 151, 0)',
        })
    }
}

// Fonction sleep, permet d'attendre un certain temps en millisecondes
function sleep(time) {
    return new Promise((resolve) => setTimeout(resolve, time));
}