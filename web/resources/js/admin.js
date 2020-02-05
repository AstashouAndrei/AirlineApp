let showFlightByIdBtn = document.getElementById("showByID");
let showAllFlightsBtn = document.getElementById("showAll");
let showManagebleFlightsBtn = document.getElementById("showOperate");
let showRemovableFlightsBtn = document.getElementById("showRemovable");
let removeFlightBtn = document.getElementById("removeFlight");

let startFlightBtn = document.getElementById("startFlight");
let finishFlightBtn = document.getElementById("finishFlight");
let delayFlightBtn = document.getElementById("delayFlight");
let cancelFlightCancelBtn = document.getElementById("cancelFlight");

let flightCodeInp = document.getElementById("f-code");
let departureInp = document.getElementById("dep");
let arrivalInp = document.getElementById("arr");
let planeTypelInp = document.getElementById("pl-type");
let passengerCapacityInp = document.getElementById("pas-cap");
let flightRangeInp = document.getElementById("fl-range");
let fuelConsumptionInp = document.getElementById("f-cons");

let addFlightBtn = document.getElementById("addFlight");
let addFlightClearBtn = document.getElementById("addFlightCancel");

let flightID = document.getElementById('flightID');
let operatingPanel = document.getElementById("mng-panel");

let administratorLogin = document.getElementById('admin-inf');

let action;
let state;

let table = document.getElementById('flight-table'),
    rIndex;

function init() {
    action = 'init';
    let requestData = {
        action: action
    };
    callServlet(requestData, action);
}

showFlightByIdBtn.addEventListener('click', function () {
    operatingPanel.style.display = "none";
    removeFlightBtn.style.visibility = "hidden";
    let flightIDValue = flightID.value.trim();
    action = 'showFlightByID';
    let requestData = {
        flightID: flightIDValue,
        action: action
    };
    callServlet(requestData, action);
});

showAllFlightsBtn.addEventListener('click', function () {
    operatingPanel.style.display = "none";
    removeFlightBtn.style.visibility = "hidden";
    action = 'showAllFlights';
    let requestData = {
        action: action
    };
    callServlet(requestData, action);
});

addFlightBtn.addEventListener('click', function () {
    operatingPanel.style.display = "none";
    removeFlightBtn.style.visibility = "hidden";
    action = 'addFlight';
    let requestData = {
        flightCode: flightCodeInp.value,
        departure: departureInp.value,
        arrival: arrivalInp.value,
        planeType: planeTypelInp.value,
        passengerCapacity: passengerCapacityInp.value,
        flightRange: flightRangeInp.value,
        fuelConsumption: fuelConsumptionInp.value,
        action: action
    };
    callServlet(requestData, action);
});

showManagebleFlightsBtn.addEventListener('click', function () {
    operatingPanel.style.display = "block";
    removeFlightBtn.style.visibility = "hidden";
    action = 'showManageble';
    state = 'Standby';
    let requestData = {
        state: state,
        action: action
    };
    callServlet(requestData, action);
});

showRemovableFlightsBtn.addEventListener('click', function () {
    removeFlightBtn.style.visibility = "visible";
    operatingPanel.style.display = "none";
    action = 'showRemovable';
    state = 'Standby';
    let requestData = {
        state: state,
        action: action
    };
    callServlet(requestData, action);
});

removeFlightBtn.addEventListener('click', function () {
    operatingPanel.style.display = "none";
    action = 'removeFlight';
    let requestData = {
        flightCode: flightCodeInp.value,
        action: action
    };
    callServlet(requestData, action);
});

startFlightBtn.addEventListener('click', function () {
    action = 'startFlight';
    let requestData = {
        flightCode: flightCodeInp.value,
        action: action
    };
    callServlet(requestData, action);
});

finishFlightBtn.addEventListener('click', function () {
    action = 'finishFlight';
    let requestData = {
        flightCode: flightCodeInp.value,
        action: action
    };
    callServlet(requestData, action);
});

delayFlightBtn.addEventListener('click', function () {
    action = 'delayFlight';
    let requestData = {
        flightCode: flightCodeInp.value,
        action: action
    };
    callServlet(requestData, action);
});

cancelFlightCancelBtn.addEventListener('click', function () {
    action = 'cancelFlight';
    let requestData = {
        flightCode: flightCodeInp.value,
        action: action
    };
    callServlet(requestData, action);
});

addFlightClearBtn.addEventListener('click', function () {
    clearInputs();
});

function clearInputs() {
    let elements = [];
    elements = document.getElementsByClassName("form-control inputbox");
    for (let i = 0; i < elements.length; i++) {
        elements[i].value = "";
    }
}

function makeTableActive(table) {
    for (let i = 1; i < table.rows.length; i++) {
        table.rows[i].onclick = function () {
            flightCodeInp.value = this.cells[1].innerHTML;
            departureInp.value = this.cells[2].innerHTML;
            arrivalInp.value = this.cells[3].innerHTML;
            planeTypelInp.value = this.cells[4].innerHTML;
            passengerCapacityInp.value = this.cells[5].innerHTML;
            flightRangeInp.value = this.cells[6].innerHTML;
            fuelConsumptionInp.value = this.cells[7].innerHTML;
        }
    }
}

function callServlet(data, action) {
    let request = new XMLHttpRequest();
    request.open("POST", 'AdminController', true);
    request.setRequestHeader("Content-Type", "application/json");
    request.responseType = 'json';
    request.send(JSON.stringify(data));
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            let respData = request.response;
            showResult(respData, action);
        }
    };
}

function showResult(data, action) {
    for (let i = 1; i < table.rows.length;) {
        table.deleteRow(i);
    }
    switch (action) {
        case 'showFlightByID':
            showFlight(data);
            break;
        case 'init':
            initUser(data);
            break;
        default:
            showFlights(data);
            break;
    }
    clearInputs();
    makeTableActive(table);
}

function initUser(data) {
    administratorLogin.textContent = data.administrator;
}

function showFlights(data) {
    for (let i = 0; i < data.length; i++) {
        let row = table.insertRow(table.rows.length);
        row.insertCell(0).appendChild(document.createTextNode(data[i].id));
        row.insertCell(1).appendChild(document.createTextNode(data[i].itinerary.flightCode));
        row.insertCell(2).appendChild(document.createTextNode(data[i].itinerary.departure));
        row.insertCell(3).appendChild(document.createTextNode(data[i].itinerary.arrival));
        row.insertCell(4).appendChild(document.createTextNode(data[i].plane.planeType));
        row.insertCell(5).appendChild(document.createTextNode(data[i].plane.passengerCapacity));
        row.insertCell(6).appendChild(document.createTextNode(data[i].plane.flightRange));
        row.insertCell(7).appendChild(document.createTextNode(data[i].plane.fuelConsumption));
        row.insertCell(8).appendChild(document.createTextNode(data[i].state));
    }
}

function showFlight(data) {
    let row = table.insertRow(table.rows.length);
    row.insertCell(0).appendChild(document.createTextNode(data.id));
    row.insertCell(1).appendChild(document.createTextNode(data.itinerary.flightCode));
    row.insertCell(2).appendChild(document.createTextNode(data.itinerary.departure));
    row.insertCell(3).appendChild(document.createTextNode(data.itinerary.arrival));
    row.insertCell(4).appendChild(document.createTextNode(data.plane.planeType));
    row.insertCell(5).appendChild(document.createTextNode(data.plane.passengerCapacity));
    row.insertCell(6).appendChild(document.createTextNode(data.plane.flightRange));
    row.insertCell(7).appendChild(document.createTextNode(data.plane.fuelConsumption));
    row.insertCell(8).appendChild(document.createTextNode(data.state));
}