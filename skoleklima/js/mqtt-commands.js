/**
 * @author Ciok
 */
// here all frontend mqqt messages

// detete a given thing from a given rpi
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

    alert('Thing has been removed');
    refreshDevicesTableWithButton();
}

// add a given thing
function approve_thing(thing_uid, rp_uid) {
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
            controlType: 'ADD',
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

    alert('The thing was added');
    refreshDevicesTableListenWithButton();
}

// change value of a numerical actuator
function set_actuator_number() {
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

// change value of a switch actuator
function set_actuator_on_off() {
    var x = document.getElementById("set_on_off");
    var on_off = '';
    if (x.checked) {
        on_off = 'ON';
    } else {
        on_off = 'OFF';
    }
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
            value: on_off
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
    var rp;
    var sUrl = "api/api-get-rpID.php";
    $.post(sUrl, {
        roomID: roomID,
    }, function (data) {
        var jData = JSON.parse(data);
        rp = jData[0].UID;

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
                binding: "ZWAVE"
            };
            msg_text = JSON.stringify(msg);
            message = new Paho.MQTT.Message(msg_text);
            message.destinationName = "deviceDiscovery/" + rp;
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

    });

    // countdown and refresh table after 12 sec
    var i = 12;
    document.getElementById("listen_button").disabled = true;
    document.getElementById("listen_button").style.opacity = "0.3";
    var label = document.getElementById("p_countdown");
    var interval = setInterval(function () {
        label.innerHTML = "Listening for new devices... " + i;
        i--;
        if (i < 0) {
            clearInterval(interval);
            refresh_table_listening();
        }
    }, 1000);

}


// linking items and channels
function link_channel_with_item(itemName, channelUID, category, type, channelLabel) {
    var drop = document.getElementById("select_room_manage_devices");
    var roomID = drop.options[drop.selectedIndex].value;
    var sUrl = "api/api-get-rpID.php";
    $.post(sUrl, {
        roomID: roomID,
    }, function (data) {
        var jData = JSON.parse(data);
        var rp = jData[0].UID;

        // console.log(type, category, itemName, channelUID, channelLabel, rp);

        // Create a client instance
        client = new Paho.MQTT.Client("iot.eclipse.org", Number(443), "/wss");
        client.startTrace();
        // set callback handlers
        client.onConnectionLost = onConnectionLost;
        client.onMessageArrived = onMessageArrived;
        // connect the client
        client.connect({
            onSuccess: onConnect,
            useSSL: true
        });
        console.log("attempting to connect...");

        // called when the client connects
        function onConnect() {
            // Once a connection has been made, make a subscription and send a message.
            console.log("onConnect");
            //client.subscribe("testse2");
            msg = {
                controlType: 'ADD',
                uid: itemName,
                channelUID: channelUID,
                createItem: {
                    name: itemName,
                    category: category,
                    type: type,
                    label: channelLabel
                }
            };
            msg_text = JSON.stringify(msg);
            message = new Paho.MQTT.Message(msg_text);
            message.destinationName = "deviceControl/Item/" + rp;
            client.publish(message);
        }

        // called when the client loses its connection
        function onConnectionLost(responseObject) {
            if (responseObject.errorCode !== 0) {
                console.log("onConnectionLost:" + responseObject.errorMessage);
            }
        }

        // called when a message arrives
        function onMessageArrived(message) {
            msg = JSON.parse(message.payloadString);
            console.log("MessageArrived\n" + "Message id: " + msg['uid'] + " message text: " + msg['channelUID']);
        }
    });

}


// unlinking items and channels
function unlink_channel_with_item(itemName, channelUID) {
    var drop = document.getElementById("select_room_manage_devices");
    var roomID = drop.options[drop.selectedIndex].value;
    var sUrl = "api/api-get-rpID.php";
    $.post(sUrl, {
        roomID: roomID,
    }, function (data) {
        var jData = JSON.parse(data);
        var rp = jData[0].UID;

        // console.log(type, category, itemName, channelUID, channelLabel, rp);

        // Create a client instance
        client = new Paho.MQTT.Client("iot.eclipse.org", Number(443), "/wss");
        client.startTrace();
        // set callback handlers
        client.onConnectionLost = onConnectionLost;
        client.onMessageArrived = onMessageArrived;
        // connect the client
        client.connect({
            onSuccess: onConnect,
            useSSL: true
        });
        console.log("attempting to connect...");

        // called when the client connects
        function onConnect() {
            // Once a connection has been made, make a subscription and send a message.
            console.log("onConnect");
            //client.subscribe("testse2");
            msg = {
                controlType: 'REMOVE',
                uid: itemName,
                channelUID: channelUID,
                createItem: {}
            };
            msg_text = JSON.stringify(msg);
            message = new Paho.MQTT.Message(msg_text);
            message.destinationName = "deviceControl/Item/" + rp;
            client.publish(message);
        }

        // called when the client loses its connection
        function onConnectionLost(responseObject) {
            if (responseObject.errorCode !== 0) {
                console.log("onConnectionLost:" + responseObject.errorMessage);
            }
        }

        // called when a message arrives
        function onMessageArrived(message) {
            msg = JSON.parse(message.payloadString);
            console.log("MessageArrived\n" + "Message id: " + msg['uid'] + " message text: " + msg['channelUID']);
        }
    });
}