import {getPendingUsers} from '../common/apiconstants.js';


document.addEventListener("DOMContentLoaded",function()
{
    invokeApi();
})

function invokeApi()
{
    fetch(`${getPendingUsers}`,{
        method:'GET'
    }).then(response =>{
        if(response.status == 200)
        {
            return response.json();
        }
        }).then(data => {
            if(data!=null)
            {
                const roleCounts = displayPendingUsers(data);
            }
        }).catch(error => {
            console.error(error);
            alert('Error occurred while calling API');
        });
}


function saveloginName(loginname)
{
   localStorage.setItem('selectedUser',loginname);
   window.location.href='ApproveOrReject.html';
}


function displayPendingUsers(apidata) {
    const appContainer = document.getElementById('app');
    const table = document.createElement('table');
    table.innerHTML = `
        <tr>
            <th>First Name</th>
            <th>Email</th>
            <th>Phone Number</th>
            <th>Approval Status</th>
            <th>ID</th>
        </tr>
    `;

    if (apidata) {
        apidata.forEach(user => {
            const email = user.emailId;
            const approvalStatus = user.approvals.approvalStatus;
            const userId = `<span class="id-link">${user.id}</span>`;
            const phoneNumber = user.phoneNumber;
            const firstname = user.firstname;
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${firstname}</td>
                <td>${email}</td>
                <td>${phoneNumber}</td>
                <td>${approvalStatus}</td>
                <td>${userId}</td>
            `;
            // Attach click event listener to each ID span
            const idSpan = row.querySelector('.id-link');
            idSpan.addEventListener('click', saveloginName.bind(null, user.loginname));
            table.appendChild(row);
        });
        appContainer.appendChild(table);
    }
}


document.addEventListener("DOMContentLoaded",function(){
    displayPendingUsers();
})