let music = true;

//initialize musics and sounds
const backgroundMusic = new buzz.sound("./music/music.mp3");
buzz.all().setVolume(50);

window.addEventListener('load', init);
window.addEventListener('keydown', toggleMusic);

//activate music when launching the game
function init() {
    backgroundMusic.play().loop();
}

//when the user press M anytime, stop/launch the music
function toggleMusic() {
    var key = event.which || event.keyCode;
    if (key === 77) {
        if (music) {
            console.log("Music Off");
            buzz.all().setVolume(0);
            music = false;
        } else {
            console.log("Music On");
            buzz.all().setVolume(50);
            music = true;
        }
    }
}