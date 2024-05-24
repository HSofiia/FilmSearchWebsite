import { header, token } from "../util/csrf.js";

const titleInput = document.getElementById("actorName");
const nationalityInput = document.getElementById("nationality");
const genderInput = document.getElementById("gender");
const addButton = document.getElementById("addButton");

console.log("add actor class")
async function addNewActor() {
    console.log("addNewFilm response done");

    const xmlData = `
        <actor>
            <actorName>${titleInput.value}</actorName>
            <nationality>${nationalityInput.value}</nationality>
            <gender>${genderInput.value}</gender>
        </actor>
    `;

    const response = await fetch('/api/addActor', {
        method: "POST",
        headers: {
            "Accept": "application/xml",
            "Content-Type": "application/xml",
            [header]: token
        },
        body: xmlData
    });

    if (response.status === 201) {
        window.location.replace("/actors");
        console.log("fetched");

        const actorXml = await response.text();
        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(actorXml, "text/xml");

        const actor = {
            id: xmlDoc.querySelector("id").textContent,
            actorName: xmlDoc.querySelector("actorName").textContent,
            nationality: xmlDoc.querySelector("nationality").textContent,
            gender: xmlDoc.querySelector("gender").textContent
        };

        addFilmToHtmlTable(actor);
    } else {
        alert("Something went wrong!");
        console.log("issue with adding the actor to the table");
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

addButton?.addEventListener("click", addNewActor);
