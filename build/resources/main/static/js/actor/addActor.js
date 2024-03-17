const titleInput = document.getElementById("actorName");
const nationalityInput = document.getElementById("nationality");
const genderInput = document.getElementById("gender");
const addButton = document.getElementById("addButton");

console.log("add actor class")
async function addNewActor() {

    console.log("addNewFilm response done")
    const response = await fetch('/api/addActor', {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            actorName: titleInput.value,
            nationality: nationalityInput.value,
            gender: genderInput.value
        })
    });

    if (response.status === 201) {
        window.location.replace("/actors")
        console.log("fetched")
        /**
         * @type {{id: number, actorName: string, nationality: string, gender: string }}
         */
        const actor = await response.json();
        addFilmToHtmlTable(actor);
    } else {
        alert("Something went wrong!"); // alerts are "bad"...
        console.log("issue with adding the actor to the table")
    }
}


const actorTableBody = document.getElementById("actor_table");

/**
 * @param {{id: number, actorName: string, nationality: string, gender: string }} actor
 */
function addFilmToHtmlTable(actor) {
    const tableRow = document.createElement("tr");
    tableRow.id = `actor_${actor.id}`;
    tableRow.innerHTML = `
        <td>
            <a>${actor.actorName}</a>
        </td>
    `
    actorTableBody.appendChild(tableRow);
    console.log(actorTableBody)
}

addButton.addEventListener("click", addNewActor);
