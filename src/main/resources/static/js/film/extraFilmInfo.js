import { header, token } from "../util/csrf.js";

const deleteButtons = document.querySelectorAll('button.btn-outline-danger');

for (const deleteButton of deleteButtons) {
    deleteButton.addEventListener("click", handleDeleteFilm);
}

const filmId = document.getElementById("filmId")

async function handleDeleteFilm() {
    console.log("deleting...")
    console.log(filmId);
    const response = await fetch(`/api/extraFilmInfo/${filmId.value}`, {
        method: "DELETE",
        headers: {
            [header]: token
        }
    })
    if (response.status === 204) {
        console.log("Method delete is successful")
        window.location.replace("/films")
    }
}

const filmIdInput = document.getElementById("filmId");
const toggleFilmsButton = document.getElementById("buttonActorsInFilms");
const actorsTable = document.getElementById("actorsInFilms");
const buttonWrapper = document.getElementById("dropdownButtonWrapper");
const tableBody = document.getElementById("film_casting_table");

async function toggleActorsTable() {
    if (actorsTable.style.display === "table") {
        hideActorsTable();
    } else {
        const response = await fetch(`/api/extraFilmInfo/${filmIdInput.value}/actors`,
            { headers: { "Accept": "application/json" } });
        if (response.status === 200) {
            const actors = await response.json();
            tableBody.innerHTML = '';
            for (const actor of actors) {
                tableBody.innerHTML += `
                    <tr>
                        <td>${actor.actorName}</td>
                        <td>${actor.gender}</td>
                        <td>${actor.nationality}</td>
                    </tr>
                `;
            }
            showActorsTable();
        }
    }
}

function hideActorsTable() {
    actorsTable.style.display = "none";
    buttonWrapper.classList.remove("dropup");
    if (!buttonWrapper.classList.contains("dropdown")) {
        buttonWrapper.classList.add("dropdown");
    }
}

function showActorsTable() {
    actorsTable.style.display = "table";
    buttonWrapper.classList.remove("dropdown");
    if (!buttonWrapper.classList.contains("dropup")) {
        buttonWrapper.classList.add("dropup");
    }
}

toggleFilmsButton.addEventListener("click", toggleActorsTable);

const boxOfficeText = document.getElementById("boxOffice");
const genreText = document.getElementById("genre");
const releaseYearText = document.getElementById("releaseYear");

/**
 * @type {HTMLButtonElement}
 */
const updateButton = document.getElementById("updateButton");

async function changeFilm() {
    console.log(filmIdInput.value)
    const response = await fetch(`/api/extraFilmInfo/${filmIdInput.value}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            [header]: token
        },
        body: JSON.stringify({
            year: releaseYearText.value,
            boxOffice: boxOfficeText.value,
            genre: genreText.value
        })
    })
    if (response.status === 204) {
        console.log("success")
    } else {
        alert('Something went wrong!');
    }
}

updateButton?.addEventListener("click", changeFilm);
