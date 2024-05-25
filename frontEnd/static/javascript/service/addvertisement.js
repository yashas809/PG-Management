import {addAdvertisement} from '../common/apiconstants.js'

document.addEventListener("DOMContentLoaded", function()
{
    const form = document.getElementById('AdvertisementForm');
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
        roomNo:document.getElementById('roomNo').value,
        floorNo:document.getElementById('floorNo').value,
        foodType:document.getElementById('foodType').value,
        noOfShares:document.getElementById('noOfShares').value,
        vacancy:document.getElementById('vacancy').value,
        available:true
    }

    fetch(`${addAdvertisement}`,{
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
            window.location.href='OwnerIndex.html'
        }
    }).catch(error => {
        console.error('Error:', error.stack);
        alert("An error occurred while calling the API.");
    });
}