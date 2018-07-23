
function addAchievement(){
    addInputInTable(document.getElementById("achievs"));
}

function addQalification() {
    addInputInTable(document.getElementById("qualifications"));
}

function addInputInTable(table){
    var s = prompt("Add achievement");
    if( s != null){
        var newrow = document.createElement("tr");

        var newcol1 = document.createElement("td");
        var input = document.createElement("input");
        input.type = "text";
        input.value = s;
        input.size = "100";
        newcol1.appendChild(input);
        newrow.appendChild(newcol1);

        var newcol2 = document.createElement("td");
        var buttonR = document.createElement("button");
        buttonR.type = "button";
        buttonR.name = "deleteChiev";
        buttonR.onclick = deleteAchievement;
        var img = document.createElement("img");
        img.src = "resources/img/delete.png";
        buttonR.appendChild(img);
        newcol2.appendChild(buttonR);

        newrow.appendChild(newcol1);
        newrow.appendChild(newcol2);

        table.appendChild(newrow);
    }
}
function addQalification() {

}

function deleteRow(e){
    var target = e.target || window.event.srcElement;
    var tableRow = target.parentElement.parentElement.parentElement;
    tableRow.parentElement.removeChild(tableRow);
}

function deleteAchievement() {
    
}

function saveResume() {

}