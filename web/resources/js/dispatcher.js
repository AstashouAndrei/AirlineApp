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

let nameRegX = /^([A-Za-z]+[ ]?|[A-Za-z]+['-]?)+$/;

let submitCrewBtn = document.getElementById("submitCrew");
let clearCrewBtn = document.getElementById("clearCrew");

let dispatcherLogin = document.getElementById('dispatcher-inf');

let action;
let state;

function init() {
    action = 'INITIALIZE';
    let requestData = {
        action: action
    };
    callServlet(requestData, action);
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

function showAllFlights() {
    prepareCrewBtn.style.visibility = "hidden";
    flightInfo.style.display = "block";
    crewInfo.style.display = "none";
    currentFlight.style.display = "none";
    crewManagePanel.style.display = "none";
    action = 'showAllFlights';
    let requestData = {
        action: action
    };
    callServlet(requestData, action);
}

showAllFlightsBtn.addEventListener('click', function () {
    showAllFlights();
});

showNoCrewBtn.addEventListener('click', function () {
    clearInputs();
    prepareCrewBtn.style.visibility = "visible";
    flightInfo.style.display = "block";
    currentFlight.style.display = "block";
    crewInfo.style.display = "none";
    crewManagePanel.style.display = "none";
    state = 'Standby';
    action = 'SHOW_NO_CREW_FLIGHTS';
    let requestData = {
        action: action,
        state: state
    };
    callServlet(requestData, action);
});

prepareCrewBtn.addEventListener('click', function () {
    clearCrewInputs("form-control crewInp");
    clearCrewInputs("form-control crewInp");
    flightInfo.style.display = "none";
    crewInfo.style.display = "block";
    crewManagePanel.style.display = "block";
});

function showStaff(id) {
    action = 'SHOW_STAFFS';
    let requestData = {
        action: action,
        profession: id
    };
    callServlet(requestData, action);
}

showCaptainsBtn.addEventListener('click', function () {
    showStaff(1);
});

showOfficersBtn.addEventListener('click', function () {
    showStaff(2);
});

showNavigatorsBtn.addEventListener('click', function () {
    showStaff(3);
});

showRadiomansBtn.addEventListener('click', function () {
    showStaff(4);
});

showFlightAttendantsBtn.addEventListener('click', function () {
    showStaff(5);
});

addStaffBtn.addEventListener('click', function () {
    action = 'ADD_STAFF';
    let staffFirstName = staffFirstNameInp.value;
    let staffLastName = staffLastNameInp.value;
    let proffessionID;
    for (let i = 0; i < staffProfession.length; i++) {
        if (staffProfession[i].checked) {
            proffessionID = staffProfession[i].value;
            break;
        }
    }
    if (validateStaff(staffFirstName, staffLastName)) {
        let requestData = {
            action: action,
            firstName: staffFirstName,
            lastName: staffLastName,
            professionID: proffessionID
        };
        callServlet(requestData, action);
    }
});

submitCrewBtn.addEventListener('click', function () {
    action = 'SUBMIT_CREW';
    let captainName = captain.value;
    let officerName = officer.value;
    let navigatorName = navigator.value;
    let radiomanName = radioman.value;
    let attendantName = attendant1.value;
    if (validateCrew(captainName, officerName, navigatorName, radiomanName, attendantName)) {
        let crew = [{
            staff: captainName
        },
            {
                staff: officerName
            },
            {
                staff: navigatorName
            },
            {
                staff: radiomanName
            },
            {
                staff: attendantName
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
        callServlet(requestData, action);
    }

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
    clearCrewInputs("form-control crewInp");
});

addStaffClearBtn.addEventListener('click', function () {
    clearCrewInputs("form-control inputbox");
});

function clearCrewInputs(className) {
    let elements = [];
    elements = document.getElementsByClassName(className);
    for (let i = 0; i < elements.length; i++) {
        elements[i].value = "";
    }
}

function showResult(data, action) {
    if (action) {
        switch (action) {
            case 'ADD_STAFF':
                showStaffs(data);
                break;
            case 'SHOW_STAFFS':
                showStaffs(data);
                break;
            case 'INITIALIZE':
                initUser(data);
                break;
            case 'SUBMIT_CREW':
                showAllFlights();
                break;
            default:
                showFlights(data, action);
                break;
        }
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
    if (action === 'SHOW_NO_CREW_FLIGHTS') {
        activeFlightTable();
    }
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

function clearInputs() {
    resultFlightCode.textContent = "";
    resultPlane.textContent = "";
    resultDeparture.textContent = "";
    resultArrival.textContent = "";
}

function validateCrew(captainName, officerName, navigatorName, radiomanName, attendantName) {
    let isCapValid = validateName(captainName, captain);
    let isOffValid = validateName(officerName, officer);
    let isNavValid = validateName(navigatorName, navigator);
    let isRadValid = validateName(radiomanName, radioman);
    let isFaNameValid = validateName(attendantName, attendant1);
    return (isCapValid && isOffValid && isNavValid && isRadValid && isFaNameValid);
}

function validateStaff(firstName, lastName) {
    let isFirstNameValid = validateName(firstName, staffFirstNameInp);
    let isLastNameValid = validateName(lastName, staffLastNameInp);
    return (isFirstNameValid && isLastNameValid);
}


function validateName(name, form) {
    let isValid = false;
    if (name === '' || !nameRegX.test(name)) {
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