/*
*
 */

$("#container2").load("/login2");

function login() {
    const username = document.getElementById("load-username").value;
    const password = document.getElementById("load-password").value;
    console.log("Try to Login with username="+encodeURIComponent(username)+" & password="+encodeURIComponent(password));
    $("#container2").load("/validate2?username="+encodeURIComponent(username)+"&password="+encodeURIComponent(password));
}

function createUser() {
    const username = document.getElementById("created-username").value;
    const password = document.getElementById("created-password").value;
    console.log("Try to create user");
    $("#container2").load("/validate2?username="+encodeURIComponent(username)+"&password="+encodeURIComponent(password));
}

function deleteTask( index ){
    $("#container2").load("/deleteTask2?index="+index);
}

function deleteTask( index ){
    const newTask = document.getElementById("new-task-input").value;
    $("#container2").load("/addTask2?index="+newTask);
}

