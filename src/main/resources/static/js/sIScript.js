document.addEventListener('DOMContentLoaded', function() {
    const userName = document.getElementById("userName");
    const userPwd = document.getElementById("password");
    const checkbox = document.getElementById('remember-me');
    const form = document.querySelector('form');
    const submitBu = document.getElementById("loginBu");

    window.addEventListener('load', function() {
        loadSavedCredentials();
    });

    submitBu.addEventListener('click', function(event) {
        event.preventDefault(); // Prevent default form submission
        checkF();
        setLocalStorage();
        form.submit(); // Submit after localStorage operations
    });

    function loadSavedCredentials() {
        const savedData = localStorage.getItem("currentUserDatas");
        if (savedData) {
            const userData = JSON.parse(savedData);
            userName.value = userData.name;
            userPwd.value = userData.pwd;
            checkbox.checked = true;
            console.log('Identifiants chargés depuis la sauvegarde');
        }
    }

    function checkF(){
        console.log("okidoki");
    }

    function setLocalStorage() {
        if (checkbox.checked) {
            console.log('La checkbox est cochée, sauvegarde des données !');
            const currentUser = {
                name: userName.value,
                pwd: userPwd.value
            };
            const currentUserData = JSON.stringify(currentUser);
            localStorage.setItem("currentUserDatas", currentUserData);
        } else {
            console.log('La checkbox N\'est PAS cochée, suppression des données.');
            localStorage.removeItem("currentUserDatas");
        }
    }
});
