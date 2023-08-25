console.log("Running js!");

$('#randomText').click(function () {
    $('#random').load("/random");
});

const stringText = document.getElementById("randomString");
stringText.onclick = () => {
    const lengthInput = document.getElementById("inputLength");
    const url = "/randomString/" + lengthInput.value;
    console.log(url);
    fetch(url).then((response) => {
        console.log(response);
        fetch(url).then((response) =>{
            return response.text()
        }).then((responseText) => {
            const updatedRandomString = document.getElementById("randomWord")
            console.log(responseText)
            updatedRandomString.innerHTML = responseText
        })
    });
}

