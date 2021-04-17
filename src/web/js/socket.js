
class CDCsocket {

    service = null;

    static _connect() {
        this.service = new WebSocket("ws://localhost:8080/culDeChouette/WebSocket");
        
        this.service.onopen = () => {
            window.alert("service.onopen...");
            let response = window.confirm(this.service.url + " just opened... Say 'Hi!'?");
            if (response) this.service.send(JSON.stringify({Response: "connect"}));
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
                id: "creation",
                pseudo: pseudo,
                password: mdp,
                sexe: sexe,
                city: ville,
                age: age,
            }));
    }
}

CDCsocket._connect();