function delete_thing(thing_uid, rp_uid) {
    console.log(thing_uid, rp_uid);
    client = new Paho.MQTT.Client("iot.eclipse.org", Number(443), "/wss");
    client.startTrace();
    // set callback handlers
    // client.onConnectionLost = onConnectionLost;
    // client.onMessageArrived = onMessageArrived;
    // connect the client
    client.connect({
        onSuccess: onConnect,
        useSSL: true
    });

    // called when the client connects
    function onConnect() {
        // Once a connection has been made, make a subscription and send a message.
        console.log("onConnect");
        client.subscribe("deviceControl/Thing/" + rp_uid);
        msg = {
            controlType: 'REMOVE',
            uid: thing_uid
        };
        msg_text = JSON.stringify(msg);
        message = new Paho.MQTT.Message(msg_text);
        message.destinationName = "deviceControl/Thing/" + rp_uid;
        client.publish(message);
    }

    // called when the client loses its connection
    // function onConnectionLost(responseObject) {
    //     if (responseObject.errorCode !== 0) {
    //         // console.log("onConnectionLost:" + responseObject.errorMessage);
    //     }
    // }

    // called when a message arrives
    // function onMessageArrived(message) {
    //     msg = JSON.parse(message.payloadString);
    //     console.log("MessageArrived\n" + "Message id: " + msg['controlType'] + " message text: " + msg['text']);
    // }

    refreshThingsTableWithButton();

    alert('Thing has been removed');
}


function set_actuator_number () {
    var x = document.getElementById("set_input_number");
    var text = x.elements[0].value;
    // Create a client instance
    client = new Paho.MQTT.Client("iot.eclipse.org", Number(443), "/wss");
    client.startTrace();
    // set callback handlers
    client.onConnectionLost = onConnectionLost;
    //client.onMessageArrived = onMessageArrived;
    // connect the client
    client.connect({
        onSuccess: onConnect,
        useSSL: true
    });
    // console.log("attempting to connect...");

    // called when the client connects
    function onConnect() {
        // Once a connection has been made, make a subscription and send a message.
        // console.log("onConnect");
        //client.subscribe("testse2");
        msg = {
            name: chosen_actuator,
            value: text
        };
        msg_text = JSON.stringify(msg);
        message = new Paho.MQTT.Message(msg_text);
        message.destinationName = "commandse2/" + chosen_actuator;
        client.publish(message);
    }

    // called when the client loses its connection
    function onConnectionLost(responseObject) {
        if (responseObject.errorCode !== 0) {
            // console.log("onConnectionLost:" + responseObject.errorMessage);
        }
    }

    // called when a message arrives
    function onMessageArrived(message) {
        msg = JSON.parse(message.payloadString);
        // console.log("MessageArrived\n" + "Message id: " + msg['id'] + " message text: " + msg['text']);
    }
}


// listen for new devices in a chosen room



function listen() {
    var drop = document.getElementById("select_room_devices_listen");
    var roomID = drop.options[drop.selectedIndex].value;
    var rp = "";
    var sUrl = "api/api-get-rpID.php";
    // $.post(sUrl, {
    //     roomID: roomID,
    // }, function (data) {
    //     var jData = JSON.parse(data);
    //     rp = jData[0].UID;
    //
    //     // Create a client instance
    //     client = new Paho.MQTT.Client("iot.eclipse.org", Number(443), "/wss");
    //     client.startTrace();
    //     // set callback handlers
    //     client.onConnectionLost = onConnectionLost;
    //     //client.onMessageArrived = onMessageArrived;
    //     // connect the client
    //     client.connect({
    //         onSuccess: onConnect,
    //         useSSL: true
    //     });
    //     // console.log("attempting to connect...");
    //
    //     // called when the client connects
    //     function onConnect() {
    //         // Once a connection has been made, make a subscription and send a message.
    //         // console.log("onConnect");
    //         //client.subscribe("testse2");
    //         msg = {
    //             binding: "ZWAVE"
    //         };
    //         msg_text = JSON.stringify(msg);
    //         message = new Paho.MQTT.Message(msg_text);
    //         message.destinationName = "deviceDiscovery/" + rp;
    //         client.publish(message);
    //     }
    //
    //     // called when the client loses its connection
    //     function onConnectionLost(responseObject) {
    //         if (responseObject.errorCode !== 0) {
    //             // console.log("onConnectionLost:" + responseObject.errorMessage);
    //         }
    //     }
    //
    //     // called when a message arrives
    //     function onMessageArrived(message) {
    //         msg = JSON.parse(message.payloadString);
    //         // console.log("MessageArrived\n" + "Message id: " + msg['id'] + " message text: " + msg['text']);
    //     }
    //
    // });

    var i = 12;
    var interval = setInterval( function() {
        console.log(i);
        i--;
        if(i<0) {
            clearInterval(interval);
            refresh_table_approval();
        }
    }, 1000);

}

function refresh_table_approval() {
    console.log('refresh');
}