import {header, token} from "./util/csrf.js";
import moment from 'moment';
import axios from "axios";

const deleteButtons = document.querySelectorAll('button.btn-outline-danger');

for (const deleteButton of deleteButtons) {
    deleteButton.addEventListener("click", handleDeleteActor);
}

const actorId = document.getElementById("actorId")

async function handleDeleteActor() {
    console.log("deleting...")
    console.log(actorId);
    const response = await fetch(`/api/extraActorInfo/${actorId.value}`, {
        method: "DELETE",
        headers: {
            [header]: token
        }

    })
    if (response.status === 204) {
        console.log("Method delete is successful")
        window.location.replace("/actors")
    }
}

const actorIdInput = document.getElementById("actorId");
const toggleFilmsButton = document.getElementById("buttonFilmsInActors");
const filmsTable = document.getElementById("filmsInActors");
const buttonWrapper = document.getElementById("dropdownButtonWrapper");
const tableBody = document.getElementById("actor_film_table");

async function toggleActorsTable() {
    try {
        if (filmsTable.style.display === "table") {
            hideActorsTable();
        } else {
            const response = await axios.get(`/api/extraActorInfo/${actorIdInput.value}/films`, {
                headers: {"Accept": "application/json"}
            });
            if (response.status === 200) {
                const films = response.data;
                tableBody.innerHTML = '';
                for (const film of films) {
                    tableBody.innerHTML += `
                            <tr>
                                <td>${film.filmName}</td>
                                <td>${film.boxOffice}</td>
                                <td>${film.genre}</td>
                                <td>${moment(film.year).format('MMMM Do YYYY')}</td>
                            </tr>
                        `;
                }
                showActorsTable();
            }
        }
    } catch (error) {
        console.error("Error fetching films:", error);
    }
}

function hideActorsTable() {
    filmsTable.style.display = "none";
    buttonWrapper.classList.remove("dropup");
    if (!buttonWrapper.classList.contains("dropdown")) {
        buttonWrapper.classList.add("dropdown");
    }
}

function showActorsTable() {
    filmsTable.style.display = "table";
    buttonWrapper.classList.remove("dropdown");
    if (!buttonWrapper.classList.contains("dropup")) {
        buttonWrapper.classList.add("dropup");
    }
}

toggleFilmsButton.addEventListener("click", toggleActorsTable);

const genderText = document.getElementById("gender");
const nationalityText = document.getElementById("nationality");

/**
 * @type {HTMLButtonElement}
 */
const updateButton = document.getElementById("updateButtonActor");

async function changeActor() {
    console.log(actorIdInput.value)
    const response = await fetch(`/api/extraActorInfo/${actorIdInput.value}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            [header]: token
        },
        body: JSON.stringify({
            gender: genderText.value,
            nationality: nationalityText.value
        })
    })
    if (response.status === 204) {
        console.log("success")
    } else {
        alert('Something went wrong!');
    }
}

updateButton?.addEventListener("click", changeActor);

