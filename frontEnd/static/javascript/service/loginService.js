import {loginUrl } from '../common/apiconstants.js';

document.addEventListener("DOMContentLoaded", function() {
    localStorage.clear();
    const loginForm = document.getElementById('registrationForm');
    if(loginForm)
        {
            loginForm.addEventListener('submit', login);
        }

});

function login(event)
{
    event.preventDefault();
    const loginName = document.getElementById('loginName').value;
    const password = document.getElementById('password').value;

    const url = `${loginUrl}?loginName=${encodeURIComponent(loginName)}&password=${encodeURIComponent(password)}`;

    fetch(url,{
        method:'GET'
    }).then(response =>{
        if(response.status == 200)
        {
            return response.json();
        }else if(response.status == 422){
            alert("Invalid Credentials");
        }
        }).then(data => {
            if(data!=null)
            {
                console.log(data);
                localStorage.setItem('userData', JSON.stringify(data));
                if(data.roleName === 'Owner')
                    {
                        window.location.href = 'OwnerIndex.html';
                    }else if(data.roleName === 'Tenant'){
                        window.location.href = 'UserIndex.html';
                    }else{
                        window.location.href = 'prospectIndex.html';
                    }
            }
        }).catch(error => {
            console.error(error);
            alert('Error occurred while calling API');
        });
}
