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
/*!********************************!*\
  !*** ./src/main/js/addFilm.js ***!
  \********************************/
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _util_csrf_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./util/csrf.js */ "./src/main/js/util/csrf.js");


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
            [_util_csrf_js__WEBPACK_IMPORTED_MODULE_0__.header]: _util_csrf_js__WEBPACK_IMPORTED_MODULE_0__.token
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
    }
    else {
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

})();

/******/ })()
;
//# sourceMappingURL=bundle-addFilm.js.map