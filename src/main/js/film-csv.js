import axios from "axios";


document.getElementById('upload-form').addEventListener('submit', function(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);
    NProgress.start();

    axios.post(form.action, formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        onUploadProgress: function(progressEvent) {
            let percentCompleted = Math.round((progressEvent.loaded * 100) / progressEvent.total);
            NProgress.set(percentCompleted / 100);
        }
    })
        .then(function(response) {
            console.log('File successfully uploaded', response);
            NProgress.done();
        })
        .catch(function(error) {
            console.error('Error uploading file', error);
            NProgress.done();
        });
});
