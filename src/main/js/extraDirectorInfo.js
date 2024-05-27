import {header, token} from "./util/csrf.js";
import moment from 'moment';
import axios from "axios";

const deleteButtons = document.querySelectorAll('button.btn-outline-danger');

for (const deleteButton of deleteButtons) {
    deleteButton.addEventListener("click", handleDeleteDirector);
}

const directorId = document.getElementById("directorId")

async function handleDeleteDirector() {
    console.log("deleting...")
    console.log(directorId);
    const response = await fetch(`/api/extraDirectorInfo/${directorId.value}`, {
        method: "DELETE",
        headers: {
            [header]: token
        }

    })
    if (response.status === 204) {
        console.log("Method delete is successful")
        window.location.replace("/directors")
    }
}

const directorIdInput = document.getElementById("directorId");
const birthText = document.getElementById("birth");
const awardText = document.getElementById("award");

/**
 * @type {HTMLButtonElement}
 */
const updateButton = document.getElementById("updateButtonDirector");

async function changeDirector() {
    console.log(directorIdInput.value)
    const response = await fetch(`/api/extraDirectorInfo/${directorIdInput.value}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            [header]: token
        },
        body: JSON.stringify({
            birth: birthText.value,
            award: awardText.value
        })
    })
    if (response.status === 204) {
        console.log("success")
    } else {
        alert('Something went wrong!');
    }
}

updateButton?.addEventListener("click", changeDirector);

