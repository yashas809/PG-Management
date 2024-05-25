import {getAdvtById, updateAdvertisement} from '../common/apiconstants.js'

document.addEventListener('DOMContentLoaded', function() {
    loadData();
    const form = document.getElementById('AdvertisementForm');
    if(form)
        {
            form.addEventListener('submit', function (event) {
                performAction(event);
            });
        }

})

function loadData()
{
    const id = localStorage.getItem('advertisementId');
    const url = `${getAdvtById}?id=${encodeURIComponent(id)}`;

    fetch(url, {
        method: 'GET'
    }).then(response => {
        if(response.status == 200) {
            return response.json();
        }
    }).then(data => {
        if(data != null) {
            render(data);
        }
    }).catch(error => {
        console.error(error);
        alert('Error occurred while calling API');
    });
}

function render(data)
{
    document.getElementById('roomNo').value = data.roomNo;
    document.getElementById('floorNo').value = data.floorNo;
    document.getElementById('foodType').value = data.foodType;
    document.getElementById('noOfShares').value = data.noOfShares;
    document.getElementById('vacancy').value= data.vacancy;
    document.getElementById('available').value= data.available;
    document.getElementById('available').value= data.available;
    document.getElementById('advertisementId').value = data.advertisementId;
}

function performAction(event)
{
    event.preventDefault();

    const formData = {
        roomNo:document.getElementById('roomNo').value,
        floorNo:document.getElementById('floorNo').value,
        foodType:document.getElementById('foodType').value,
        noOfShares:document.getElementById('noOfShares').value,
        vacancy:document.getElementById('vacancy').value,
        available:document.getElementById('available').value
    }

    const url = `${updateAdvertisement}?id=${encodeURIComponent(document.getElementById('advertisementId').value)}`;


    fetch(url,{
        method:'PUT',
        headers :{
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',
            'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS'
        },
        body : JSON.stringify(formData),
    }).then(response => {
        if (response.status == 200) {
            alert('Updated Successfully');
            window.location.href='OwnerIndex.html'
        } else if (response.status == 422) {
            alert('Cannot Insert, Please change the loginName and try Again');
        }
    }).catch(error => {
        console.error('Error:', error.stack);
        alert("An error occurred while calling the API.");
    });
}