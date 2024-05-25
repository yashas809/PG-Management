import {updateUser} from '../common/apiconstants.js'
import {getUserByLoginName} from '../common/apiconstants.js'
document.addEventListener("DOMContentLoaded", function()
{
    loadData();
    const form = document.getElementById('ownerProfile');
    if(form)
        {
            form.addEventListener('submit', function (event) {
                OwnerUpdate(event);
            });
        }
})


function loadData()
{
   const userData = localStorage.getItem('userData');
   const jsonUserData = JSON.parse(userData);
    setValues(jsonUserData);
}

function setValues(data)
{
    setValueIfElementExists('firstname', data.firstname);
    setValueIfElementExists('lastname', data.lastname);
    setValueIfElementExists('emailId', data.emailId);
    setValueIfElementExists('phoneNumber', data.phoneNumber);
    setValueIfElementExists('loginname', data.loginname);
    setValueIfElementExists('Gender', data.gender);

    if (data.registration !== undefined && data.registration !== null) {
        setValueIfElementExists('referalCode', data.registration.referalCode);
        setValueIfElementExists('adharNumber', data.registration.adharNumber);
        setValueIfElementExists('permanentAddress', data.registration.permanentAddress);
    }

    setValueIfElementExists('createdDate', data.createddate);

    if (data.approvals !== undefined && data.approvals !== null) {
        setValueIfElementExists('approvalStatus', data.approvals.approvalStatus);
    }

    setValueIfElementExists('appuserfk', data.id);
}

function setValueIfElementExists(elementId, value) {
    var element = document.getElementById(elementId);
    if (element !== null && element !== undefined && value !== undefined && value !== null) {
        element.value = value;
    }
}

function OwnerUpdate(event)
{
    event.preventDefault();

    const loginName = document.getElementById('loginname').value;

    const formData = {
        firstname:document.getElementById('firstname').value,
        lastname:document.getElementById('lastname').value,
        emailId:document.getElementById('emailId').value,
        phoneNumber:document.getElementById('phoneNumber').value
    }

    const url = `${updateUser}?loginName=${encodeURIComponent(loginName)}`;
    fetch(url,{
        method:'PUT',
        headers :{
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',
            'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS'
        },
        body : JSON.stringify(formData),
    }).then(response =>{
        if(response.status == 200)
        {
            alert('Updated Successfully');
            updateLocalCache(loginName);
        }
        }).catch(error => {
            console.error(error);
            alert('Error occurred while calling API');
        });
}

function updateLocalCache(loginName)
{

    const url = `${getUserByLoginName}?loginName=${encodeURIComponent(loginName)}`;
    fetch(url,{
        method:'GET'
    }).then(response =>{
        if(response.status == 200)
        {
            return response.json();
        }
        }).then(data => {
            if(data!=null)
            {
                localStorage.removeItem('userData');
                localStorage.setItem('userData', JSON.stringify(data));
                window.location.reload();
            }
        }).catch(error => {
            console.error(error);
            alert('Error occurred while Updating Local Cache, Please log out and Login Again');
        });
}
