import { header, token } from "../util/csrf.js";

const titleInput = document.getElementById("filmName");
const yearInput = document.getElementById("year");
const boxOfficeInput = document.getElementById("boxOffice");
const genreInput = document.getElementById("genre");
const addButton = document.getElementById("addButton");
// const addButton = document.querySelector("button btn-primary");
console.log("add film class")
async function addNewFilm() {

    console.log("addNewFilm response done")
    const response = await fetch('/api/addFilm', {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
            [header]: token
        },
        body: JSON.stringify({
            filmName: titleInput.value,
            year: yearInput.value,
            boxOffice: boxOfficeInput.value,
            genre: genreInput.value
        })
    });

    if (response.status === 201) {
        window.location.replace("/films")
        console.log("fetched")
        /**
         * @type {{id: number, filmName: string, year: number, boxOffice: number,genre: string }}
         */
        const film = await response.json();
        addFilmToHtmlTable(film);
    } else {
        alert("Something went wrong!"); // alerts are "bad"...
        console.log("issue with adding the film to the table")
    }
}


const filmTableBody = document.getElementById("film_table");

/**
 * @param {{id: number, filmName: string, year: number, boxOffice: number, genre: string }} film
 */
function addFilmToHtmlTable(film) {
    const tableRow = document.createElement("tr");
    tableRow.id = `film_${film.id}`;
    tableRow.innerHTML = `
        <td>
            <a>${film.filmName}</a>
        </td>
    `
    filmTableBody.appendChild(tableRow);
    console.log(filmTableBody)
}

addButton?.addEventListener("click", addNewFilm);
