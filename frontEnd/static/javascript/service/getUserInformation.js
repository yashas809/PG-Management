import {getAllusers,deleteUser} from '../common/apiconstants.js'

document.addEventListener('DOMContentLoaded', function() {

  const modal = document.getElementById('deleteModal');
  const closeButton = document.querySelector('.close-button');
  const confirmButton = document.getElementById('confirmButton');
  const cancelButton = document.getElementById('cancelButton');
  let currentAdvertisementId = null;


    const tableBody = document.getElementById('rentTableBody');
    const pendingFilter = document.getElementById('pendingFilter');
    let allData = []; // Store the fetched data for filtering

    function openModal(advertisementId) {
      if(advertisementId)
        {
          console.log(advertisementId);
          currentAdvertisementId = advertisementId;
          modal.style.display = 'block';
        }

  }

  function closeModal() {
      modal.style.display = 'none';
      currentAdvertisementId = null;
  }

  closeButton.addEventListener('click', closeModal);
  cancelButton.addEventListener('click', closeModal);

  confirmButton.addEventListener('click', function() {
      if (currentAdvertisementId !== null) {
          deleteUserss(currentAdvertisementId);
          closeModal();
      }
  });


    function renderTable(data) {
      tableBody.innerHTML = '';

      data.forEach(item => {
        const row = document.createElement('tr');

        let roomNo = 0;
        let floorNo = 0;
        let referalCode = null;

        if(item.advertisementInfo)
        {
          if(item.advertisementInfo.roomNo)
            {
                roomNo = item.advertisementInfo.roomNo;
            }
          if(item.advertisementInfo.floorNo)
            {
                floorNo = item.advertisementInfo.floorNo;
            }
        }

        if(item.referalCodes)
        {
          if(item.referalCodes.referalCode)
            {
              referalCode = item.referalCodes.referalCode;
            }
        }

        let approvalStatus = null;
        if(item.approvals.approvalStatus)
          {
            approvalStatus = item.approvals.approvalStatus;
          }

        row.innerHTML = `
          <td><a href="#" class="advertisement-link" data-id="${item.id}">${item.id}</a></td>
          <td>${item.emailId}</td>
          <td>${item.createddate}</td>
          <td>${roomNo}</td>
          <td>${floorNo}</td>
          <td>${referalCode}</td>
          <td>${item.phoneNumber}</td>
          <td>${item.roleName}</td>
        `;

        if (item.roleName === 'Deleted') {
          row.classList.add('highlight');
        }

        tableBody.appendChild(row);
      });

      // Add event listeners to all advertisement links
      const links = document.querySelectorAll('.advertisement-link');
      links.forEach(link => {
        link.addEventListener('click', function(event) {
          event.preventDefault(); // Prevent default anchor click behavior
          const advertisementId = this.getAttribute('data-id');
          openModal(advertisementId);
        });
      });
    }

    function filterData(data) {
      if (pendingFilter.checked) {
        const pendingOnly = data.filter(item => item.available);
        renderTable(pendingOnly);
      } else {
        renderTable(data);
      }
    }

    fetch(`${getAllusers}`, {
        method: 'GET'
    }).then(response => {
        if(response.status == 200) {
            return response.json();
        }
    }).then(data => {
        if(data != null) {
            allData = data; // Store the fetched data for filtering
            renderTable(data);
        }
    }).catch(error => {
        console.error(error);
        alert('Error occurred while calling API');
    });


});



function deleteUserss(id)
{
    const url = `${deleteUser}?appUserFK=${encodeURIComponent(id)}`;
    fetch(url,{
        method:'PUT',
        headers :{
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',
            'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS'
        }
    }).then(response =>{
        if(response.status == 200)
        {
            alert('Deleted Successfully');
            window.location.reload();
        }
        }).catch(error => {
            console.error(error);
            alert('Error occurred while calling API');
        });
}