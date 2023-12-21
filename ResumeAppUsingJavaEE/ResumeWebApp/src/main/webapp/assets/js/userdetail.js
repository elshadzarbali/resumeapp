function showPower(i) {
    var powerRangeValue = document.getElementById("powerRange" + i).value;
    var powerLabel = document.getElementById("powerLabel" + i);
    powerLabel.innerHTML = powerRangeValue + "0%";
}

var deletedUserSkills = [];
let userSkillIndex = 0;

function deleteUserSkill(i, id) {
    var element = document.getElementById("userSkill" + i);
    element.remove();
    deletedUserSkills[userSkillIndex] = id;
    userSkillIndex++;
    document.getElementById("deletedUserSkills").value = deletedUserSkills.toString();
}

function showSkillName(i) {
    var selectedSkillElement = document.getElementById("selectedSkill" + i);
    var skillNameLabel = document.getElementById("skillName" + i);
    skillNameLabel.innerHTML = selectedSkillElement.options[selectedSkillElement.selectedIndex].text;
}

//----------------------------------Employment History----------------------------------------

var deletedEmploymentHistories = [];
let delEmpIndex = 0;

function deleteEmploymentHistory(index, employmentHistoryId) {
    var element = document.getElementById("employmentHistory" + index);
    element.remove();

    deletedEmploymentHistories[delEmpIndex] = employmentHistoryId;
    delEmpIndex++;
    // Set the array to a hidden input or perform other actions as needed
    document.getElementById("deletedEmploymentHistories").value = deletedEmploymentHistories.toString();

}

var addedEmpHistoryIndices = [];

let paramIndex = 1; // shows added Emp History id, name, parameter's index. (header4, begindate4 and etc.).
// In JSP page at 'Add new history' button (onclick="addEmploymentHistory(<%=i++%>)"), incrementing i doesn't work
// // continiously. It increments only first time. So, i create this variable and its value set at
// <script>paramIndex = <%=i%>;</script> in jsp page.

function addEmploymentHistory() {
    addNewEmploymentHistory(paramIndex);
    
    addedEmpHistoryIndices.push(paramIndex);
    paramIndex++;

    document.getElementById("addedEmpHistoryIndices").value = addedEmpHistoryIndices.toString();
}

function deleteAddedEmpHistory(valueToRemove) {
    // valueToRemove also indicates suffix of some attribute name
    var element = document.getElementById("employmentHistory" + valueToRemove);
    element.remove();

    // Find the index of the value to remove
    var indexToRemove = addedEmpHistoryIndices.indexOf(valueToRemove);

    // Check if the value is in the array
    if (indexToRemove !== -1) {
        // Use splice to remove the element at the specified index
        addedEmpHistoryIndices.splice(indexToRemove, 1);
        document.getElementById("addedEmpHistoryIndices").value = addedEmpHistoryIndices.toString();
    }
}

function addNewEmploymentHistory(index) {
    // Create elements for the new employment history
    var newHistoryContainer = document.createElement("div");
    newHistoryContainer.classList.add("accordion-item");
    newHistoryContainer.setAttribute("id", "employmentHistory" + index);

    var newHistoryRow = document.createElement("div");
    newHistoryRow.classList.add("row");
    newHistoryRow.setAttribute("style", "margin: inherit");

    var newHistoryHeader = document.createElement("h2");
    newHistoryHeader.classList.add("accordion-header", "col-11");
    newHistoryHeader.setAttribute("style", "padding-left: 0px;");

    var newHistoryButton = document.createElement("button");
    newHistoryButton.classList.add("accordion-button", "collapsed");
    newHistoryButton.setAttribute("type", "button");
    newHistoryButton.setAttribute("data-bs-toggle", "collapse");
    newHistoryButton.setAttribute("data-bs-target", "#collapse" + index);
    newHistoryButton.setAttribute("aria-expanded", "false");
    newHistoryButton.setAttribute("aria-controls", "collapse" + index);
    newHistoryButton.innerText = "New Employment History";

    var newHistoryCollapse = document.createElement('div');
    newHistoryCollapse.classList.add('accordion-collapse', 'collapse', 'col-11');
    newHistoryCollapse.setAttribute('id', 'collapse' + index);
    newHistoryCollapse.setAttribute('data-bs-parent', '#accordionExample');

    var newHistoryBody = document.createElement('div');
    newHistoryBody.classList.add('accordion-body');

    var newHistoryDeleteButton = document.createElement("button");
    newHistoryDeleteButton.classList.add("btn", "btn-danger", "col-1");
    newHistoryDeleteButton.setAttribute("type", "button");
    newHistoryDeleteButton.setAttribute("onclick", "deleteAddedEmpHistory(" + index + ")");

    var newHistoryDeleteIcon = document.createElement("i");
    newHistoryDeleteIcon.classList.add("fa-solid", "fa-square-minus");

    var bodyHeaderDiv = document.createElement('div');
    bodyHeaderDiv.classList.add('mb-3');

    var bodyHeaderLabel = document.createElement('label');
    bodyHeaderLabel.classList.add('form-label');
    bodyHeaderLabel.setAttribute("for", "header" + index);
    bodyHeaderLabel.innerText = "Header:";

    var bodyHeaderInput = document.createElement('input');
    bodyHeaderInput.classList.add('form-control');
    bodyHeaderInput.setAttribute("type", "text");
    bodyHeaderInput.setAttribute("id", "header" + index);
    bodyHeaderInput.setAttribute("name", "header" + index);

    var bodyBeginDateDiv = document.createElement('div');
    bodyBeginDateDiv.classList.add('mb-3');

    var bodyBeginDateLabel = document.createElement('label');
    bodyBeginDateLabel.classList.add('form-label');
    bodyBeginDateLabel.setAttribute("for", "begindate" + index);
    bodyBeginDateLabel.innerText = "Begin date:";

    var bodyBeginDateInput = document.createElement('input');
    bodyBeginDateInput.classList.add('form-control');
    bodyBeginDateInput.setAttribute("type", "date");
    bodyBeginDateInput.setAttribute("id", "begindate" + index);
    bodyBeginDateInput.setAttribute("name", "begindate" + index);

    var bodyEndDateDiv = document.createElement('div');
    bodyEndDateDiv.classList.add('mb-3');

    var bodyEndDateLabel = document.createElement('label');
    bodyEndDateLabel.classList.add('form-label');
    bodyEndDateLabel.setAttribute("for", "enddate" + index);
    bodyEndDateLabel.innerText = "End date:";

    var bodyEndDateInput = document.createElement('input');
    bodyEndDateInput.classList.add('form-control');
    bodyEndDateInput.setAttribute("type", "date");
    bodyEndDateInput.setAttribute("id", "enddate" + index);
    bodyEndDateInput.setAttribute("name", "enddate" + index);

    var bodyJobDescDiv = document.createElement('div');
    bodyJobDescDiv.classList.add('mb-3');

    var bodyJobDescLabel = document.createElement('label');
    bodyJobDescLabel.classList.add('form-label');
    bodyJobDescLabel.setAttribute("for", "job_desc" + index);
    bodyJobDescLabel.innerText = "Job description:";

    var bodyJobDescTxt = document.createElement('textarea');
    bodyJobDescTxt.classList.add('form-control');
    bodyJobDescTxt.setAttribute("id", "job_desc" + index);
    bodyJobDescTxt.setAttribute("name", "job_desc" + index);

    // Append the new elements to the accordion container
    document.getElementById('accordionExample').appendChild(newHistoryContainer);

    newHistoryContainer.appendChild(newHistoryRow);

    newHistoryRow.appendChild(newHistoryHeader);
    newHistoryRow.appendChild(newHistoryCollapse);
    newHistoryRow.appendChild(newHistoryDeleteButton);

    newHistoryHeader.appendChild(newHistoryButton);

    newHistoryCollapse.appendChild(newHistoryBody);

    newHistoryDeleteButton.appendChild(newHistoryDeleteIcon);

    bodyHeaderDiv.appendChild(bodyHeaderLabel);
    bodyHeaderDiv.appendChild(bodyHeaderInput);

    bodyBeginDateDiv.appendChild(bodyBeginDateLabel);
    bodyBeginDateDiv.appendChild(bodyBeginDateInput);

    bodyEndDateDiv.appendChild(bodyEndDateLabel);
    bodyEndDateDiv.appendChild(bodyEndDateInput);

    bodyJobDescDiv.appendChild(bodyJobDescLabel);
    bodyJobDescDiv.appendChild(bodyJobDescTxt);

    newHistoryBody.appendChild(bodyHeaderDiv);
    newHistoryBody.appendChild(bodyBeginDateDiv);
    newHistoryBody.appendChild(bodyEndDateDiv);
    newHistoryBody.appendChild(bodyJobDescDiv);
}