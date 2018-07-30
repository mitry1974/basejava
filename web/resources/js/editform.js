function deleteOrganization(section, id) {
    var table = document.getElementById(section + '_organizations');
    var row = document.getElementById(section + '_' + id);
    table.deleteRow(row.index);
}

function deletePosition(section, orgId, posId) {
    var table = document.getElementById(section + '_' + orgId + '_positions');
    var row = document.getElementById(section + '_' + orgId + '_' + posId);
    table.deleteRow(row.index);
}

function addOrganizationDialog(section) {
    var overlay = document.getElementById("organization_overlay");
    overlay.setAttribute("section", section);
    overlay.style.visibility = (overlay.style.visibility == "visible") ? "hidden" : "visible";
}

function addPositionDialog(section, orgId) {
    var overlay = document.getElementById("position_overlay");
    overlay.setAttribute("position_section", section);
    overlay.setAttribute("organization_id", orgId);
    overlay.style.visibility = (overlay.style.visibility == "visible") ? "hidden" : "visible";
}

function addOrganization() {
    var overlay = document.getElementById("organization_overlay");
    var section = overlay.getAttribute("section");
    var table = document.getElementById(section + "_organizations");
    var title = document.getElementById("organization_title").value;
    var url = document.getElementById("organization_url").value;

    var orgCount = table.rows.length;
    var newRow = table.insertRow(0);
    newRow.id = section + '_' + orgCount;

    var container = document.createElement('div');
    container.innerHTML = '<div id=' + orgCount + '>\
                                    <hr color="#5f9ea0" size="2" width="900">\
                                    <p style="border: 1px solid #C1FF0A;  padding: 10px;">\
                                        <a href="javascript:deleteOrganization(\'' + section + '\',\'' + orgCount + '\')">Удалить организацию </a><br>\
                                        <b>Название:</b><br>\
                                        <input type="text" size="100%" name=' + section + '_name\
                                       value=' + title + '><br>\
                                <b>Url:</b><br>\
                                <input type="text" size="100%" name=' + section + '_url\
                                       value=' + url + '><br>\
                                <a href=\"javascript:addPositionDialog(\'' + section + '\',\'' + orgCount + '\')\">Добавить\
                                    позицию</a>\
                            </p>\
                            <table style="width: 900px; border: 0px #2E6E9E" rules="none"\
                                   id=\"' + section + '_' + orgCount + '_positions\">\
                            </table>\
                        </div>';
    newRow.appendChild(container.firstChild);
    closeOrganizationDialog();
}


function addPosition() {
    var overlay = document.getElementById("position_overlay");
    var orgId = overlay.getAttribute("organization_id");
    var section = overlay.getAttribute("position_section");

    var title = document.getElementById("position_title").value;
    if (title == "") {
        alert("Заполните пожалуйста название позиции");
        return;
    }
    var sdate = document.getElementById("position_startdate").value;
    if (sdate == "") {
        alert("Заполните пожалуйста дату начала позиции");
        return;
    }
    var fdate = document.getElementById("position_finishdate").value;
    if (fdate == "") {
        alert("Заполните пожалуйста дату окончания позиции");
        return;
    }
    var descr = document.getElementById("position_description").value;

    var table = document.getElementById(section + '_' + orgId + '_positions');
    var posId = table.rows.length;
    var newRow = table.insertRow(0);
    newRow.id = section + '_' + orgId + '_' + posId;

    var container = document.createElement('div');
    container.innerHTML = '<p style="border: 1px solid #C1FF0A;  padding: 10px;">\
                        <b>Дата начала:</b><br>\
                    <input type="month" name=\"' + section + '_' + orgId + '_sdate\"\
                    value=\'' + sdate + '\'><br>\
                        <b>Дата окончания:</b><br>\
                    <input type="month" name=\"' + section + '_' + orgId + '_fdate\"\
                    value=\'' + fdate + '\'><br>\
                        <b>Позиция:</b><br>\
                    <input type="text" size="100%"\
                    name=\"' + section + '_' + orgId + '_title\"\
                    value=\"' + title + '\"><br>\
                        <b>Описание:</b><br>\
                    <input type="text" size="100%"\
                    name=\"' + section + '_' + orgId + '_descr\"\
                    value=\"' + descr + '\"><br>\
                    <a href=\"javascript:deletePosition(\'' + section + '\',\'' + orgId + '\',\'' + posId + '\')\">Удалить позицию:</a></p>';
    newRow.appendChild(container.firstChild);

    closePositionDialog();
}

function closeOrganizationDialog() {
    clearOrganizationDialog();
    document.getElementById("organization_overlay").style.visibility = 'hidden';
}


function clearOrganizationDialog() {
    document.getElementById("organization_title").value = "";
    document.getElementById("organization_url").value = "";
}


function closePositionDialog() {
    clearPositionDialog();
    document.getElementById("position_overlay").style.visibility = 'hidden';
}

function clearPositionDialog() {
    document.getElementById("position_title").value = "";
    document.getElementById("position_startdate").value = "";
    document.getElementById("position_finishdate").value = "";
    document.getElementById("position_description").value = "";
}

