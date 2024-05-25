import {getExistingCasesOwner} from '../common/apiconstants.js'

document.addEventListener("DOMContentLoaded", function()
{
      loadCases();
      localStorage.removeItem('caseId');

})


function loadCases()
{
    const userData = localStorage.getItem('userData');
    const userDataJson = JSON.parse(userData);

    const url = `${getExistingCasesOwner}`

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

  function openTicket(ticket) {

    localStorage.setItem('caseId',ticket.caseID);
    window.location.href = "chatBoxOwner.html";
}

