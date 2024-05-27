import validator from "validator";
import { header, token } from "./util/csrf.js";

const form = document.getElementById("actor-form");
const actorNameInput = document.getElementById("actorName");
const nationalityInput = document.getElementById("nationality");
const genderInput = document.getElementById("gender");
const addButton = document.getElementById("addButton");

// Helper function to show error messages
function showError(input, message) {
    const formGroup = input.parentElement;
    const alertDiv = formGroup.querySelector(".alert.alert-danger");
    if (alertDiv) {
        alertDiv.textContent = message;
        alertDiv.style.display = "block";
    }
}

// Helper function to clear error messages
function clearError(input) {
    const formGroup = input.parentElement;
    const alertDiv = formGroup.querySelector(".alert.alert-danger");
    if (alertDiv) {
        alertDiv.textContent = "";
        alertDiv.style.display = "none";
    }
}

// Custom validation function
function validateForm() {
    let isValid = true;

    // Validate actor name
    const actorName = actorNameInput.value.trim();
    if (validator.isEmpty(actorName)) {
        showError(actorNameInput, "Actor name is required.");
        isValid = false;
    } else {
        clearError(actorNameInput);
    }

    // Validate nationality
    const nationality = nationalityInput.value.trim();
    if (validator.isEmpty(nationality)) {
        showError(nationalityInput, "Nationality is required.");
        isValid = false;
    } else {
        clearError(nationalityInput);
    }

    // Validate gender
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
    event.preventDefault(); // Prevent form submission

    if (validateForm()) {
        addNewActor(); // Call your existing function to add a new actor
    }
});

// Existing function to handle the form submission
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
