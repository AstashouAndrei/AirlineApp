let logUserName = document.getElementById('login-username');
let logUserPass = document.getElementById('login-password');

let passwordRegX = /^([a-zA-Z0-9]{3,10})$/;

function isAllValid() {
    let logUserNameValue = logUserName.value.trim();
    let logUserPassValue = logUserPass.value.trim();
    return validateLog(logUserNameValue, logUserPassValue);
}

function validateLog(login, password) {
    let isLoginValid = validateLogin(login, logUserName);
    let isPasswordValid = validatePassword(password, logUserPass);
    return (isLoginValid && isPasswordValid);
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