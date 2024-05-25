import {registrationUrl} from '../common/apiconstants.js';

document.addEventListener("DOMContentLoaded", function()
{
    const form = document.getElementById('registrationForm');
    if(form)
        {
            form.addEventListener('submit', function (event) {
                performAction(event);
            });
        }
})

function performAction(event)
{
    event.preventDefault();

    const formData = {
        firstname:document.getElementById('firstname').value,
        lastname :document.getElementById('lastname').value,
        emailId :document.getElementById('emailId').value,
        phoneNumber:document.getElementById('phoneNumber').value,
        loginname:document.getElementById('loginname').value,
        password:document.getElementById('password').value,
        roleName:'NewUser',
        gender:document.getElementById('Gender').value,
        registration:{
            referalCode:document.getElementById('referalCode').value,
            adharNumber:document.getElementById('adharNumber').value,
            permanentAddress:document.getElementById('permanentAddress').value
        },
        advertisementInfo:{
            advertisementId:document.getElementById('dynamicAdvertisement').value
        }

    }

    fetch(`${registrationUrl}`,{
        method:'POST',
        headers :{
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',
            'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS'
        },
        body : JSON.stringify(formData),
    }).then(response => {
        if (response.status == 200) {
            alert('Added Successfully');
            window.location.href='login.html'
        } else if (response.status == 422) {
            alert('Cannot Insert, Please change the loginName and try Again');
        }
    }).catch(error => {
        console.error('Error:', error.stack);
        alert("An error occurred while calling the API.");
    });
}