<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            background: url(../resources/img/airline.jpg); /* Цвет фона и путь к файлу */
            background-size: 100%;
            color: black; /* Цвет текста */
        }
    </style>
</head>
<body>

<h1> Airline Crew Staff</h1>
<input id="staffID" type="text" name="quantity" placeholder="Please, enter staff ID"
       maxlength="2">
<input id="butt" type="button" onclick="getStaff()" value="Get Staff"/>
<table id="staff" width="25%" cellpadding="4" border="1">
    <tr>
        <th>Staff ID</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Profession</th>
    </tr>
</table>
<div id="loading"></div>
<script type="text/javascript">
    function isInt(value) {
        return !isNaN(value) &&
            parseInt(Number(value)) == value &&
            !isNaN(parseInt(value, 10));
    }

    function getStaff() {
        var id = document.getElementById('staffID').value;
        if (isInt(id) && id > 0) {
            document.getElementById('loading').innerHTML = "Loading Data...";
            request = new XMLHttpRequest();
            if (!request) {
                console.log('Unable to create XMLHTTP instance');
                return false;
            }
            request.open("GET", 'StaffController?id=' + id, true);
            request.responseType = 'json';
            request.send();
            request.onreadystatechange = function () {
                if (request.readyState == XMLHttpRequest.DONE) {
                    document.getElementById('loading').innerHTML = "";
                    if (request.status == 200) {
                        var table = document.getElementById('staff');
                        for (var i = 1; i < table.rows.length;) {
                            table.deleteRow(i);
                        }
                        var staff = request.response;
                        var row = table.insertRow(table.rows.length);
                        var cell = row.insertCell(0);
                        var cell1 = row.insertCell(1);
                        var cell2 = row.insertCell(2);
                        var cell3 = row.insertCell(3);
                        var id = document.createTextNode(staff.id);
                        var firstName = document.createTextNode(staff.firstName);
                        var lastName = document.createTextNode(staff.lastName);
                        var profession = document.createTextNode(staff.profession);
                        cell.appendChild(id);
                        cell1.appendChild(firstName);
                        cell2.appendChild(lastName);
                        cell3.appendChild(profession);
                    } else {
                        console.log('Something went wrong..!!');
                    }
                }
            }
        } else {
            alert("ID should be positive integer");
        }
    }
</script>
</body>
</html>