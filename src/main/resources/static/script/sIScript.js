const userName = document.getElementById("userName");
const userPwd = document.getElementById("password");
const checkbox = document.getElementById('remember-me');
const form = document.querySelector('form');
const submitBu = document.getElementById("loginBu");


submitBu.addEventListener('click', function(event) {
    setLocalStorage()
})

    function setLocalStorage() {
        if (checkbox.checked) {
            console.log('La checkbox est cochée, on fait quelque chose !');
            const currentUser = {
                name: userName.value,
                pwd: userPwd.value
            };
            const currentUserData = JSON.stringify(currentUser);
            localStorage.setItem("currentUserDatas", currentUserData);
        } else {
            console.log('La checkbox N\'est PAS cochée.');
            // Optionnel : tu peux supprimer l'ancien localStorage si tu veux
            localStorage.removeItem("currentUserDatas");
        }

        // ✅ Soumettre après avoir fait le localStorage
        // (empêcher l'envoi immédiat et renvoyer)
        event.preventDefault();
        form.submit();
    }

