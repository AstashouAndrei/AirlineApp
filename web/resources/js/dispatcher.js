let showAllFlightsBtn = document.getElementById('showFlights');
let showNoCrewBtn = document.getElementById('showFlightsWithoutCrew');
let prepareCrewBtn = document.getElementById('prepareCrew');

let flightInfo = document.getElementById('flightInfo');
let crewInfo = document.getElementById('crewInfo');
let currentFlight = document.getElementById('currentFlight');
let crewManagePanel = document.getElementById('crew-mng-panel');

let showCaptainsBtn = document.getElementById('captainsList');
let showOfficersBtn = document.getElementById('officersList');
let showNavigatorsBtn = document.getElementById('navigatorsList');
let showRadiomansBtn = document.getElementById('radiomansList');
let showFlightAttendantsBtn = document.getElementById('attendantsList');

let flightTable = document.getElementById('flight-table');
let staffTable = document.getElementById('staff-table');

let staffFirstNameInp = document.getElementById("firstName");
let staffLastNameInp = document.getElementById("lastName");
let staffProfession = document.getElementsByName('profession');

let addStaffBtn = document.getElementById("addStaff");
let addStaffClearBtn = document.getElementById("addStaffClear");

let resultFlightCode = document.getElementById("codeResult");
let resultPlane = document.getElementById("planeResult");
let resultDeparture = document.getElementById("departureResult");
let resultArrival = document.getElementById("arrivalResult");

let captain = document.getElementById("crewCaptain");
let officer = document.getElementById("crewOfficer");
let navigator = document.getElementById("crewNavigator");
let radioman = document.getElementById("crewRadioman");
let attendant1 = document.getElementById("crewAttendant1");
let attendant2 = document.getElementById("crewAttendant2");
let attendant3 = document.getElementById("crewAttendant3");
let attendant4 = document.getElementById("crewAttendant4");

let submitCrewBtn = document.getElementById("submitCrew");
let clearCrewBtn = document.getElementById("clearCrew");

let dispatcherLogin = document.getElementById('dispatcher-inf');

let action;
let staffProfessionID;
let state;

function init() {
    action = 'init';
    let requestData = {
        action: action
    };
    callServlet(requestData, action);
}

showAllFlightsBtn.addEventListener('click', function () {
    prepareCrewBtn.style.visibility = "hidden";
    flightInfo.style.display = "block";
    crewInfo.style.display = "none";
    currentFlight.style.display = "none";
    crewManagePanel.style.display = "none";
    action = 'showAllFlights';
    let requestData = {
        action: action
    };
    callServlet(requestData);
});

showNoCrewBtn.addEventListener('click', function () {
    prepareCrewBtn.style.visibility = "visible";
    flightInfo.style.display = "block";
    currentFlight.style.display = "block";
    crewInfo.style.display = "none";
    crewManagePanel.style.display = "none";
    state = 'Standby';
    action = 'showNoCrewFlights';
    let requestData = {
        action: action,
        state: state
    };
    callServlet(requestData);
});

prepareCrewBtn.addEventListener('click', function () {
    flightInfo.style.display = "none";
    crewInfo.style.display = "block";
    crewManagePanel.style.display = "block";
});

showCaptainsBtn.addEventListener('click', function () {
    action = 'showStaffs';
    staffProfessionID = 1;
    let requestData = {
        action: action,
        profession: staffProfessionID
    };
    callServlet(requestData, action);
});

showOfficersBtn.addEventListener('click', function () {
    action = 'showStaffs';
    staffProfessionID = 2;
    let requestData = {
        action: action,
        profession: staffProfessionID
    };
    callServlet(requestData, action);
});

showNavigatorsBtn.addEventListener('click', function () {
    action = 'showStaffs';
    staffProfessionID = 3;
    let requestData = {
        action: action,
        profession: staffProfessionID
    };
    callServlet(requestData, action);
});

showRadiomansBtn.addEventListener('click', function () {
    action = 'showStaffs';
    staffProfessionID = 4;
    let requestData = {
        action: action,
        profession: staffProfessionID
    };
    callServlet(requestData, action);
});

showFlightAttendantsBtn.addEventListener('click', function () {
    action = 'showStaffs';
    staffProfessionID = 5;
    let requestData = {
        action: action,
        profession: staffProfessionID
    };
    callServlet(requestData, action);
});

addStaffBtn.addEventListener('click', function () {
    action = 'addStaff';
    let proffessionID;
    for (let i = 0; i < staffProfession.length; i++) {
        if (staffProfession[i].checked) {
            proffessionID = staffProfession[i].value;
            break;
        }
    }
    let requestData = {
        action: action,
        firstName: staffFirstNameInp.value,
        lastName: staffLastNameInp.value,
        professionID: proffessionID
    };
    callServlet(requestData);
});

submitCrewBtn.addEventListener('click', function () {
    action = 'submitCrew';
    let crew = [{
        staff: captain.value
    },
        {
            staff: officer.value
        },
        {
            staff: navigator.value
        },
        {
            staff: radioman.value
        },
        {
            staff: attendant1.value
        }
        // {staff: attendant2.value},
        // {staff: attendant3.value},
        // {staff: attendant4.value}
    ]
    let requestData = {
        action: action,
        flightCode: resultFlightCode.textContent,
        crew: crew
    };
    callServlet(requestData);
});

function activeFlightTable() {
    for (let i = 1; i < flightTable.rows.length; i++) {
        flightTable.rows[i].onclick = function () {
            resultFlightCode.textContent = this.cells[1].innerHTML;
            resultPlane.textContent = this.cells[4].innerHTML;
            resultDeparture.textContent = this.cells[2].innerHTML;
            resultArrival.textContent = this.cells[3].innerHTML;
        }
    }
}

function activeStaffTable(staff) {
    for (let i = 1; i < staffTable.rows.length; i++) {
        staffTable.rows[i].onclick = function () {
            switch (staff) {
                case 'CAPTAIN':
                    captain.value = this.cells[1].innerHTML + " " + this.cells[2].innerHTML;
                    break;
                case 'OFFICER':
                    officer.value = this.cells[1].innerHTML + " " + this.cells[2].innerHTML;
                    break;
                case 'NAVIGATOR':
                    navigator.value = this.cells[1].innerHTML + " " + this.cells[2].innerHTML;
                    break;
                case 'RADIOMAN':
                    radioman.value = this.cells[1].innerHTML + " " + this.cells[2].innerHTML;
                    break;
                case 'ATTENDANT':
                    attendant1.value = this.cells[1].innerHTML + " " + this.cells[2].innerHTML;
                    break;
                default:
                    showFlights(data);
                    break;
            }
        }
    }
}

clearCrewBtn.addEventListener('click', function () {
    clearInputs("form-control crewInp");
});

addStaffClearBtn.addEventListener('click', function () {
    clearInputs("form-control inputbox");
});

function clearInputs(className) {
    let elements = [];
    elements = document.getElementsByClassName(className);
    for (let i = 0; i < elements.length; i++) {
        elements[i].value = "";
    }
}

function callServlet(data, action) {
    let request = new XMLHttpRequest();
    request.open("POST", 'DispatchController', true);
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
    switch (action) {
        case 'showStaffs':
            showStaffs(data);
            break;
        case 'init':
            initUser(data);
            break;
        default:
            showFlights(data);
            break;
    }
}

function initUser(data) {
    dispatcherLogin.textContent = data.dispatcher;
}

function showFlights(data) {

    for (let i = 1; i < flightTable.rows.length;) {
        flightTable.deleteRow(i);
    }

    for (let i = 0; i < data.length; i++) {
        let row = flightTable.insertRow(flightTable.rows.length);
        row.insertCell(0).appendChild(document.createTextNode(data[i].id));
        row.insertCell(1).appendChild(document.createTextNode(data[i].itinerary.flightCode));
        row.insertCell(2).appendChild(document.createTextNode(data[i].itinerary.departure));
        row.insertCell(3).appendChild(document.createTextNode(data[i].itinerary.arrival));
        row.insertCell(4).appendChild(document.createTextNode(data[i].plane.planeType));
        row.insertCell(5).appendChild(document.createTextNode(toTitleCase(data[i].state)));
    }
    activeFlightTable();
}

function showStaffs(data) {
    for (let i = 1; i < staffTable.rows.length;) {
        staffTable.deleteRow(i);
    }
    let staff;
    for (let i = 0; i < data.length; i++) {
        let row = staffTable.insertRow(staffTable.rows.length);
        row.insertCell(0).appendChild(document.createTextNode(data[i].id));
        row.insertCell(1).appendChild(document.createTextNode(data[i].firstName));
        row.insertCell(2).appendChild(document.createTextNode(data[i].lastName));
        row.insertCell(3).appendChild(document.createTextNode(toTitleCase(data[i].profession)));
        row.insertCell(4).appendChild(document.createTextNode(toTitleCase(data[i].state)));
        staff = data[i].profession;
    }
    activeStaffTable(staff);
}

function toTitleCase(str) {
    return str.replace(
        /\w\S*/g,
        function (txt) {
            return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
        }
    );
}