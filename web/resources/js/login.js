let container = document.querySelector('#container');
let loginContainer = document.querySelector('#logincontainer');
let registrationContainer = document.querySelector('#registercontainer');

let welcomeContainer = document.getElementById('welcome');
let logAction = 'login';
let regAction = 'register';

let logButton = document.getElementById("btnlog");
let regButton = document.getElementById("btnreg");

let logUserName = document.getElementById('login-username');
let logUserPass = document.getElementById('login-password');

let regUserName = document.getElementById('register-username');
let regUserEmail = document.getElementById('register-email');
let regUserPass = document.getElementById('register-password');
let regUserConfPass = document.getElementById('register-confpassword');

let userAccessLevels = document.getElementsByName('access');

let passwordRegX = /^([a-zA-Z0-9]{3,10})$/;
let emailRegX = /^([A-Za-z0-9_\-.])+@([A-Za-z0-9_\-.])+\.([A-Za-z]{2,5})$/;

document.querySelector('.registerlink').addEventListener('click', function () {
    loginContainer.style.display = 'none';
    registrationContainer.style.display = 'block';
});

document.querySelector('.loginlink').addEventListener('click', function () {
    loginContainer.style.display = 'block';
    registrationContainer.style.display = 'none';
});

logButton.addEventListener('click', function () {
    let logUserNameValue = logUserName.value.trim();
    let logUserPassValue = logUserPass.value.trim();
    if (validateLog(logUserNameValue, logUserPassValue)) {
        let requestData = {
            login: logUserNameValue,
            password: logUserPassValue,
            action: logAction
        };
        callServlet(requestData);
    }
});

regButton.addEventListener('click', function () {
    let regUserNameValue = regUserName.value.trim();
    let regUserEmailValue = regUserEmail.value.trim();
    let regUserPassValue = regUserPass.value.trim();
    let regUserConfPassValue = regUserConfPass.value.trim();
    let reqUserAccessValue;
    if (validateReg(regUserNameValue, regUserEmailValue, regUserPassValue, regUserConfPassValue)) {
        for (let i = 0; i < userAccessLevels.length; i++) {
            if (userAccessLevels[i].checked) {
                reqUserAccessValue = userAccessLevels[i].value;
                break;
            }
        }
        let requestData = {
            login: regUserNameValue,
            password: regUserPassValue,
            email: regUserEmailValue,
            access: reqUserAccessValue,
            action: regAction
        };
        callServlet(requestData);
    }
});

function validateLog(login, password) {
    let isLoginValid = validateLogin(login, logUserName);
    let isPasswordValid = validatePassword(password, logUserPass);
    return (isLoginValid && isPasswordValid);
}

function validateReg(login, email, password, confirmationPassword) {
    let isLoginValid = validateLogin(login, regUserName);
    let isEmailValid = validateEmail(email, regUserEmail)
    let isPasswordValid = validatePassword(password, regUserPass);
    let isPasswordsMatch = validatePasswordMatching(regUserConfPass, password, confirmationPassword);
    return (isLoginValid && isEmailValid && isPasswordValid && isPasswordsMatch);
}

function validateLogin(login, form) {
    let isValid = false;
    if (login === '') {
        showError(form, 'User name can not be blank');
    } else {
        showSuccess(form);
        isValid = true;
    }
    return isValid;
}

function validatePassword(password, form) {
    let isValid = false;
    if (password === '') {
        showError(form, 'Password can not be blank');
    } else if (!passwordRegX.test(password)) {
        showError(form, 'Invalid password');
    } else {
        showSuccess(form);
        isValid = true;
    }
    return isValid;
}

function validatePasswordMatching(form, password, confPassowrd) {
    let isValid = false;
    if (confPassowrd === '') {
        showError(form, 'Password confirmation form can not be blank');
    } else if (password !== confPassowrd) {
        showError(form, 'Passwords does not match');
    } else {
        showSuccess(form);
        isValid = true;
    }
    return isValid;
}

function validateEmail(email, form) {
    let isValid = false;
    if (email === '') {
        showError(form, 'Email can not be blank');
    } else if (!emailRegX.test(email)) {
        showError(form, 'Invalid email');
    } else {
        showSuccess(form);
        isValid = true;
    }
    return isValid;
}

function showError(inputForm, message) {
    let formControl = inputForm.parentElement;
    formControl.className = 'form-group error';
    let errorTextForm = formControl.querySelector('small');
    errorTextForm.innerText = message;
}

function showSuccess(inputForm) {
    let formControl = inputForm.parentElement;
    formControl.className = 'form-group success';
}

function callServlet(data) {
    let request = new XMLHttpRequest();
    request.open("POST", 'UserController', true);
    request.setRequestHeader("Content-Type", "application/json");
    request.responseType = 'json';
    request.send(JSON.stringify(data));

    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            let respData = request.response;
            let message = respData.message;
            let login = respData.login;
            switch (message) {
                case 'Administrator':
                    window.location.replace("admin.html");
                    break;
                case 'Dispatcher':
                    window.location.replace("dispatcher.html")
                    break;
                default:
                    showResult(message, login);
                    break;
            }
        }
    };
}

function showResult(message, login) {
    switch (message) {
        case 'wrongpassword':
            welcomeContainer.innerHTML = "Wrong password for user " + login + ". Please try again or sign up.";
            break;
        case 'notexist':
            welcomeContainer.innerHTML = "User with login " + login + " not found. Please try again or sign up.";
            break;
        case 'exist':
            welcomeContainer.innerHTML = "User with login " + login + " already exist. Please try again.";
            break;
        case 'regsuccess':
            welcomeContainer.innerHTML = "User with login " + login + " successfully registered.";
            break;
        default:
            welcomeContainer.innerHTML = "Error....";
            break;
    }
}