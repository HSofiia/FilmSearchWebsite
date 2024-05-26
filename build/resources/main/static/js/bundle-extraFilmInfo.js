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
/*!**************************************!*\
  !*** ./src/main/js/extraFilmInfo.js ***!
  \**************************************/
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _util_csrf_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./util/csrf.js */ "./src/main/js/util/csrf.js");


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
            [_util_csrf_js__WEBPACK_IMPORTED_MODULE_0__.header]: _util_csrf_js__WEBPACK_IMPORTED_MODULE_0__.token
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
            [_util_csrf_js__WEBPACK_IMPORTED_MODULE_0__.header]: _util_csrf_js__WEBPACK_IMPORTED_MODULE_0__.token
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

})();

/******/ })()
;
//# sourceMappingURL=bundle-extraFilmInfo.js.map