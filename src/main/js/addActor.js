import validator from "validator";
import { header, token } from "./util/csrf.js";

const form = document.getElementById("actor-form");
const actorNameInput = document.getElementById("actorName");
const nationalityInput = document.getElementById("nationality");
const genderInput = document.getElementById("gender");
const addButton = document.getElementById("addButton");

function showError(input, message) {
    const formGroup = input.parentElement;
    const alertDiv = formGroup.querySelector(".alert.alert-danger");
    if (alertDiv) {
        alertDiv.textContent = message;
        alertDiv.style.display = "block";
    }
}

function clearError(input) {
    const formGroup = input.parentElement;
    const alertDiv = formGroup.querySelector(".alert.alert-danger");
    if (alertDiv) {
        alertDiv.textContent = "";
        alertDiv.style.display = "none";
    }
}

function validateForm() {
    let isValid = true;

    const actorName = actorNameInput.value.trim();
    if (validator.isEmpty(actorName)) {
        showError(actorNameInput, "Actor name is required.");
        isValid = false;
    } else {
        clearError(actorNameInput);
    }

    const nationality = nationalityInput.value.trim();
    if (validator.isEmpty(nationality)) {
        showError(nationalityInput, "Nationality is required.");
        isValid = false;
    } else {
        clearError(nationalityInput);
    }

    const gender = genderInput.value;
    if (!validator.isIn(gender, ["M", "F", "N"])) {
        showError(genderInput, "Please select a valid gender.");
        isValid = false;
    } else {
        clearError(genderInput);
    }

    return isValid;
}

form.addEventListener("submit", function (event) {
    event.preventDefault();

    if (validateForm()) {
        addNewActor();
    }
});

async function addNewActor() {
    const xmlData = `
        <actor>
            <actorName>${actorNameInput.value}</actorName>
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
    } else {
        alert("Something went wrong!");
    }
}
