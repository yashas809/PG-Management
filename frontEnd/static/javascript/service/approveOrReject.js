import {getUserByLoginName} from '../common/apiconstants.js';
import {apporverAction} from '../common/apiconstants.js';

document.addEventListener("DOMContentLoaded",function()
{
    InvokeAPI();
    const form = document.getElementById('approveOrReject');
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
    const buttonId = event.submitter.id;
    const appUserFK = document.getElementById('appuserfk').value;
    var status ;

    if (buttonId === 'approveBtn') {

        status = 'approved';

    } else if (buttonId === 'rejectBtn') {

        status = 'rejected';
    }

    const url = `${apporverAction}?appUserFK=${encodeURIComponent(appUserFK)}&status=${encodeURIComponent(status)}`;

    fetch(url,{
        method: 'put',
        headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS'
        }
    }).then(response =>{
        if(response.status == 200)
        {
            alert("Action Successful");
            window.location.href = 'OwnerIndex.html';
        }else if(response.status == 422){
            alert("Invalid Entry");
            window.location.href = 'OwnerIndex.html';
        }
        }).catch(error => {
            console.error(error);
            alert('Error occurred while calling API');
        });
}


function InvokeAPI()
{
    const loginName = localStorage.getItem('selectedUser');

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
                setValues(data);
            }
        }).catch(error => {
            console.error(error);
            alert('Error occurred while calling API');
        });
}

function setValues(data)
{
    document.getElementById('firstname').value = data.firstname;
    document.getElementById('lastname').value = data.lastname;
    document.getElementById('emailId').value= data.emailId;
    document.getElementById('phoneNumber').value = data.phoneNumber;
    document.getElementById('loginname').value = data.loginname;
    document.getElementById('Gender').value = data.gender;
    document.getElementById('referalCode').value = data.registration.referalCode;
    document.getElementById('adharNumber').value = data.registration.adharNumber;
    document.getElementById('permanentAddress').value = data.registration.permanentAddress;
    document.getElementById('createdDate').value = data.createddate;
    document.getElementById('approvalStatus').value = data.approvals.approvalStatus;
    document.getElementById('appuserfk').value = data.id;

}