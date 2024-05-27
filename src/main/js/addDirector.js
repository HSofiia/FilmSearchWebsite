import validator from 'validator';
import { header, token } from './util/csrf.js';

const form = document.getElementById('director-form');
const nameInput = document.getElementById('directorName');
const birthInput = document.getElementById('birth');
const awardInput = document.getElementById('award');
const addButton = document.getElementById("addButton");

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

    const directorName = nameInput.value.trim();
    if (validator.isEmpty(directorName)) {
        showError(nameInput, 'Director name is required.');
        isValid = false;
    } else {
        clearError(nameInput);
    }

    const award = awardInput.value.trim();
    if (validator.isEmpty(award)) {
        showError(awardInput, 'Award is required.');
        isValid = false;
    } else {
        clearError(awardInput);
    }

    const birth = birthInput.value.trim();
    if (!validator.isInt(birth, { min: 0 })) {
        showError(birthInput, 'The birth year must be positive.');
        isValid = false;
    } else {
        clearError(birthInput);
    }

    return isValid;
}

form.addEventListener('submit', function (event) {
    event.preventDefault();

    if (validateForm()) {
        addNewDirector();
    }
});

async function addNewDirector() {
    const response = await fetch('/api/addDirector', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            [header]: token,
        },
        body: JSON.stringify({
            directorName: nameInput.value,
            birth: birthInput.value,
            award: awardInput.value,
        }),
    });

    if (response.status === 201) {
        window.location.replace('/directors');
        const director = await response.json();
        addDirectorToHtmlTable(director);
    } else {
        showError(form, 'Something went wrong!');
    }
}

const directorTableBody = document.getElementById('director_table');

/**
 * @param {{id: number, directorName: string, birth: number, award: string }} director
 */
function addDirectorToHtmlTable(director) {
    const tableRow = document.createElement('tr');
    tableRow.id = `director_${director.id}`;
    tableRow.innerHTML = `
        <td>
            <a>${director.directorName}</a>
        </td>
    `;
    directorTableBody.appendChild(tableRow);
}
