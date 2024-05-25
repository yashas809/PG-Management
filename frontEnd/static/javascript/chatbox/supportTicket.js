import {checkIfTicketExists,getExistingCases} from '../common/apiconstants.js'

document.addEventListener("DOMContentLoaded", function()
{
    localStorage.removeItem('caseId');
    const submitButton = document.querySelector('#submit-button');

    if(submitButton)
        {
            submitButton.addEventListener('click', checkExists);
        }
        loadCases();


})


function loadCases()
{
    const userData = localStorage.getItem('userData');
    const userDataJson = JSON.parse(userData);

    const url = `${getExistingCases}?appUserFK=${userDataJson.id}`

    fetch(url,{
        method:'GET'
    }).then(response => {
        if (response.status == 200) {
           return response.json();
        }
    }).then(data=>{
        if(data!=null)
            {
                const supportTickets = data.map(item => ({ caseID: item.caseId, description: item.displayValue }));
                displayTickets(supportTickets);
            }
    })
    .catch(error => {
        console.error('Error:', error.stack);
        alert("An error occurred while calling the API.");
    });

}

function displayTickets(supportTickets) {
    const ticketList = document.getElementById('ticketList');

    if(ticketList)
        {
            ticketList.innerHTML = ''; // Clear existing list

            supportTickets.forEach(ticket => {
              const listItem = document.createElement('li');
              listItem.className = 'ticket-item';
              const link = document.createElement('a');

              link.textContent = ticket.caseID;
              link.href = '#'
              link.style.color = '#007bff'; // Add styling to the link text
              link.style.textDecoration = 'none'; // Remove underline from the link text
              link.style.fontWeight = 'bold'; // Make the text bold


              link.textContent = ticket.caseID + ' - ' + ticket.description;
              link.onclick = () => openTicket(ticket); // Call the method when clicked
              listItem.appendChild(link);
              ticketList.appendChild(listItem);
            });
        }

  }

  function openTicket(ticket) {

    localStorage.setItem('caseId',ticket.caseID);
    window.location.href = "chatBox.html";
}

function checkExists()
{
    const userData = localStorage.getItem('userData');
    const userDataJson = JSON.parse(userData);

    const appUserFK = userDataJson.id;

    const reasonCode = document.getElementById('reason').value ;

    localStorage.setItem('reason', reasonCode);

    const url = `${checkIfTicketExists}?appUserFK=${appUserFK}&reasonCode=${reasonCode}`

    fetch(url,{
        method:'GET'
    }).then(response => {
        if (response.status == 200) {
            if(userDataJson.role == 'Prospect')
            {
                window.location.href='viewExistingTicketsProspect.html'
            }else{
                window.location.href='viewExistingTicketsUser.html'
            }

        } else if (response.status == 421) {
          window.location.href='chatBox.html'

        }
    }).catch(error => {
        console.error('Error:', error.stack);
        alert("An error occurred while calling the API.");
    });
}