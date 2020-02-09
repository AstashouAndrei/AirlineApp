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
let planeTypelnp = document.getElementById("pl-type");
let passengerCapacityInp = document.getElementById("pas-cap");
let flightRangeInp = document.getElementById("fl-range");
let fuelConsumptionInp = document.getElementById("f-cons");

let addFlightBtn = document.getElementById("addFlight");
let addFlightClearBtn = document.getElementById("addFlightCancel");

let flightID = document.getElementById('flightID');
let operatingPanel = document.getElementById("mng-panel");

let administratorLogin = document.getElementById('admin-inf');

let flightCodeRegX = /^RW \d{3,4}$/;
let cityRegX = /^[a-zA-Z]+(?:[\s-][a-zA-Z]+)*$/;
let planeTyperRegX = /^([A-Za-z]+[ ]?|[A-Za-z0-9]?['-]?)+$/;
let passengerCapacityRegX = /[1-3]\d\d/;

let action;
let state;

let table = document.getElementById('flight-table'),
    rIndex;

function init() {
    action = 'INITIALIZE';
    let requestData = {
        action: action
    };
    callServlet(requestData, action);
}

showFlightByIdBtn.addEventListener('click', function () {
    operatingPanel.style.display = "none";
    removeFlightBtn.style.visibility = "hidden";
    let flightIDValue = flightID.value.trim();
    action = 'SHOW_FLIGHT_BY_ID';
    let requestData = {
        flightID: flightIDValue,
        action: action
    };
    callServlet(requestData, action);
});

showAllFlightsBtn.addEventListener('click', function () {
    operatingPanel.style.display = "none";
    removeFlightBtn.style.visibility = "hidden";
    action = 'SHOW_ALL_FLIGHTS';
    let requestData = {
        action: action
    };
    callServlet(requestData, action);
});

addFlightBtn.addEventListener('click', function () {
    operatingPanel.style.display = "none";
    removeFlightBtn.style.visibility = "hidden";
    action = 'ADD_FLIGHT';

    let flightCode = flightCodeInp.value;
    let departure = departureInp.value;
    let arrival = arrivalInp.value;
    let planeType = planeTypelnp.value;
    let passengerCapacity = passengerCapacityInp.value;
    let flightRange = flightRangeInp.value;
    let fuelConsumption = fuelConsumptionInp.value;

    if (isFlightValid(flightCode, departure, arrival, planeType, passengerCapacity, flightRange, fuelConsumption)) {
        let requestData = {
            flightCode: flightCode,
            departure: departureInp.value,
            arrival: arrivalInp.value,
            planeType: planeTypelnp.value,
            passengerCapacity: passengerCapacityInp.value,
            flightRange: flightRangeInp.value,
            fuelConsumption: fuelConsumptionInp.value,
            action: action
        };
        callServlet(requestData, action);
    }
});

showManagebleFlightsBtn.addEventListener('click', function () {
    operatingPanel.style.display = "block";
    removeFlightBtn.style.visibility = "hidden";
    action = 'SHOW_MANAGEABLE_FLIGHTS';
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
    action = 'SHOW_REMOVABLE_FLIGHTS';
    state = 'Standby';
    let requestData = {
        state: state,
        action: action
    };
    callServlet(requestData, action);
});

removeFlightBtn.addEventListener('click', function () {
    operatingPanel.style.display = "none";
    action = 'REMOVE_FLIGHT';
    let requestData = {
        flightCode: flightCodeInp.value,
        action: action
    };
    callServlet(requestData, action);
});

startFlightBtn.addEventListener('click', function () {
    action = 'START_FLIGHT';
    let requestData = {
        flightCode: flightCodeInp.value,
        action: action
    };
    callServlet(requestData, action);
});

finishFlightBtn.addEventListener('click', function () {
    action = 'FINISH_FLIGHT';
    let requestData = {
        flightCode: flightCodeInp.value,
        action: action
    };
    callServlet(requestData, action);
});

delayFlightBtn.addEventListener('click', function () {
    action = 'DELAY_FLIGHT';
    let requestData = {
        flightCode: flightCodeInp.value,
        action: action
    };
    callServlet(requestData, action);
});

cancelFlightCancelBtn.addEventListener('click', function () {
    action = 'CANCEL_FLIGHT';
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
            planeTypelnp.value = this.cells[4].innerHTML;
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
        case 'SHOW_FLIGHT_BY_ID':
            showFlight(data);
            break;
        case 'INITIALIZE':
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

function isFlightValid(code, departure, arrival, plane, capacity, range, fuel) {
    let isCodeValid = isFlightCodeValid(code, flightCodeInp);
    let isDepartureValid = isCitiesValid(departure, departureInp);
    let isArrivalValid = isCitiesValid(arrival, arrivalInp);
    let isTypeValid = isPlaneTypeValid(plane, planeTypelnp);
    let isCapacityValid = isPlaneCapacityValid(capacity, passengerCapacityInp);
    let isFuelConsValid = isRangeValid(fuel, 3000, 8000, fuelConsumptionInp);
    let isFlightRangeValid = isRangeValid(range, 6000, 15000, flightRangeInp);
    return (isCodeValid && isDepartureValid && isArrivalValid && isTypeValid &&
        isCapacityValid && isFuelConsValid && isFlightRangeValid);
}

function isRangeValid(range, min, max, form) {
    let isValid = false;
    if (range === '' || !isInRange(range, min, max)) {
        showError(form);
    } else {
        showSuccess(form);
        isValid = true;
    }
    return isValid;
}

function isFlightCodeValid(code, form) {
    let isValid = false;
    if (code === '' || !flightCodeRegX.test(code)) {
        showError(form);
    } else {
        showSuccess(form);
        isValid = true;
    }
    return isValid;
}

function isCitiesValid(city, form) {
    let isValid = false;
    if (city === '' || !cityRegX.test(city)) {
        showError(form);
    } else {
        showSuccess(form);
        isValid = true;
    }
    return isValid;
}

function isPlaneTypeValid(type, form) {
    let isValid = false;
    if (type === '' || !planeTyperRegX.test(type)) {
        showError(form);
    } else {
        showSuccess(form);
        isValid = true;
    }
    return isValid;
}

function isPlaneCapacityValid(capacity, form) {
    let isValid = false;
    if (capacity === '' || !passengerCapacityRegX.test(capacity)) {
        showError(form);
    } else {
        showSuccess(form);
        isValid = true;
    }
    return isValid;
}


function showError(inputForm) {
    let formControl = inputForm.parentElement;
    formControl.className = 'form-group error';
}

function showSuccess(inputForm) {
    let formControl = inputForm.parentElement;
    formControl.className = 'form-group success';
}

function isInRange(value, min, max) {
    let parserValue = parseInt(value);
    return parserValue > min && parserValue < max;
}