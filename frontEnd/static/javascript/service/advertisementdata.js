import {getDynamicAdvertisement} from '../common/apiconstants.js'

document.addEventListener('DOMContentLoaded', function() {

    const tableBody = document.getElementById('rentTableBody');
    const pendingFilter = document.getElementById('pendingFilter');
    let allData = []; // Store the fetched data for filtering

    function renderTable(data) {
      tableBody.innerHTML = '';

      data.forEach(item => {
        const row = document.createElement('tr');

        row.innerHTML = `
          <td><a href="#" class="advertisement-link" data-id="${item.advertisementId}">${item.advertisementId}</a></td>
          <td>${item.roomNo}</td>
          <td>${item.floorNo}</td>
          <td>${item.foodType}</td>
          <td>${item.noOfShares}</td>
          <td>${item.vacancy}</td>
          <td>${item.available}</td>
        `;

        if (item.available) {
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
          localStorage.setItem('advertisementId',advertisementId);
          window.location.href = "updateAdvertisement.html";
          console.log(advertisementId);
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

    fetch(`${getDynamicAdvertisement}`, {
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
