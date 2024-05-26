/******/ (() => { // webpackBootstrap
/******/ 	"use strict";
/******/ 	var __webpack_modules__ = ({

/***/ "./src/main/js/util/csrf.js":
/*!**********************************!*\
  !*** ./src/main/js/util/csrf.js ***!
  \**********************************/
/***/ ((__unused_webpack___webpack_module__, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   header: () => (/* binding */ header),
/* harmony export */   token: () => (/* binding */ token)
/* harmony export */ });
const header = document.querySelector("meta[name=\"_csrf_header\"]").content;
const token = document.querySelector("meta[name=\"_csrf\"]").content;


/***/ })

/******/ 	});
/************************************************************************/
/******/ 	// The module cache
/******/ 	var __webpack_module_cache__ = {};
/******/ 	
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/ 		// Check if module is in cache
/******/ 		var cachedModule = __webpack_module_cache__[moduleId];
/******/ 		if (cachedModule !== undefined) {
/******/ 			return cachedModule.exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = __webpack_module_cache__[moduleId] = {
/******/ 			// no module.id needed
/******/ 			// no module.loaded needed
/******/ 			exports: {}
/******/ 		};
/******/ 	
/******/ 		// Execute the module function
/******/ 		__webpack_modules__[moduleId](module, module.exports, __webpack_require__);
/******/ 	
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/ 	
/************************************************************************/
/******/ 	/* webpack/runtime/define property getters */
/******/ 	(() => {
/******/ 		// define getter functions for harmony exports
/******/ 		__webpack_require__.d = (exports, definition) => {
/******/ 			for(var key in definition) {
/******/ 				if(__webpack_require__.o(definition, key) && !__webpack_require__.o(exports, key)) {
/******/ 					Object.defineProperty(exports, key, { enumerable: true, get: definition[key] });
/******/ 				}
/******/ 			}
/******/ 		};
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/hasOwnProperty shorthand */
/******/ 	(() => {
/******/ 		__webpack_require__.o = (obj, prop) => (Object.prototype.hasOwnProperty.call(obj, prop))
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/make namespace object */
/******/ 	(() => {
/******/ 		// define __esModule on exports
/******/ 		__webpack_require__.r = (exports) => {
/******/ 			if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 				Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 			}
/******/ 			Object.defineProperty(exports, '__esModule', { value: true });
/******/ 		};
/******/ 	})();
/******/ 	
/************************************************************************/
var __webpack_exports__ = {};
// This entry need to be wrapped in an IIFE because it need to be isolated against other modules in the chunk.
(() => {
/*!***************************************!*\
  !*** ./src/main/js/extraActorInfo.js ***!
  \***************************************/
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _util_csrf_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./util/csrf.js */ "./src/main/js/util/csrf.js");


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
            [_util_csrf_js__WEBPACK_IMPORTED_MODULE_0__.header]: _util_csrf_js__WEBPACK_IMPORTED_MODULE_0__.token
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
    if (filmsTable.style.display === "table") {
        hideActorsTable();
    } else {
        const response = await fetch(`/api/extraActorInfo/${actorIdInput.value}/films`,
            { headers: { "Accept": "application/json" } });
        if (response.status === 200) {
            const films = await response.json();
            tableBody.innerHTML = '';
            for (const film of films) {
                tableBody.innerHTML += `
                    <tr>
                        <td>${film.filmName}</td>
                        <td>${film.boxOffice}</td>
                        <td>${film.genre}</td>
                        <td>${film.year}</td>
                    </tr>
                `;
            }
            showActorsTable();
        }
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
            [_util_csrf_js__WEBPACK_IMPORTED_MODULE_0__.header]: _util_csrf_js__WEBPACK_IMPORTED_MODULE_0__.token
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


})();

/******/ })()
;
//# sourceMappingURL=bundle-extraActorInfo.js.map