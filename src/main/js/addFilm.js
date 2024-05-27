import validator from 'validator';
import { header, token } from './util/csrf.js';

const form = document.getElementById('film-form');
const titleInput = document.getElementById('filmName');
const yearInput = document.getElementById('year');
const boxOfficeInput = document.getElementById('boxOffice');
const genreInput = document.getElementById('genre');
const addButton = form.querySelector('button[type="submit"]');

function showError(input, message) {
    const formGroup = input.parentElement;
    let alertDiv = formGroup.querySelector('.alert.alert-danger');
    if (!alertDiv) {
        alertDiv = document.createElement('div');
        alertDiv.className = 'alert alert-danger mt-2';
        formGroup.appendChild(alertDiv);
    }
    alertDiv.textContent = message;
}

function clearError(input) {
    const formGroup = input.parentElement;
    const alertDiv = formGroup.querySelector('.alert.alert-danger');
    if (alertDiv) {
        alertDiv.remove();
    }
}

function validateForm() {
    let isValid = true;

    const filmName = titleInput.value.trim();
    if (validator.isEmpty(filmName)) {
        showError(titleInput, 'Film name is required.');
        isValid = false;
    } else {
        clearError(titleInput);
    }

    const genre = genreInput.value;
    if (validator.isEmpty(genre)) {
        showError(genreInput, 'Genre is required.');
        isValid = false;
    } else {
        clearError(genreInput);
    }

    const boxOffice = boxOfficeInput.value.trim();
    if (!validator.isFloat(boxOffice, { min: 0 })) {
        showError(boxOfficeInput, 'Box Office must be a positive number.');
        isValid = false;
    } else {
        clearError(boxOfficeInput);
    }

    const releaseDate = yearInput.value;
    const currentYear = new Date().getFullYear();
    if (!validator.isDate(releaseDate)) {
        showError(yearInput, 'Release Date must be a valid date.');
        isValid = false;
    } else if (new Date(releaseDate).getFullYear() > currentYear) {
        showError(yearInput, 'Release Date cannot be in the future.');
        isValid = false;
    } else {
        clearError(yearInput);
    }

    return isValid;
}

form.addEventListener('submit', function (event) {
    event.preventDefault();

    if (validateForm()) {
        addNewFilm();
    }
});

async function addNewFilm() {
    const response = await fetch('/api/addFilm', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            [header]: token,
        },
        body: JSON.stringify({
            filmName: titleInput.value,
            year: yearInput.value,
            boxOffice: boxOfficeInput.value,
            genre: genreInput.value,
        }),
    });

    if (response.status === 201) {
        window.location.replace('/films');
        const film = await response.json();
        addFilmToHtmlTable(film);
    } else {
        showError(form, 'Something went wrong!');
    }
}

const filmTableBody = document.getElementById('film_table');

/**
 * @param {{id: number, filmName: string, year: number, boxOffice: number, genre: string }} film
 */
function addFilmToHtmlTable(film) {
    const tableRow = document.createElement('tr');
    tableRow.id = `film_${film.id}`;
    tableRow.innerHTML = `
        <td>
            <a>${film.filmName}</a>
        </td>
    `;
    filmTableBody.appendChild(tableRow);
}
