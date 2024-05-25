import {getAllRentData} from '../common/apiconstants.js'

document.addEventListener('DOMContentLoaded', function() {


    const tableBody = document.getElementById('rentTableBody');
    const pendingFilter = document.getElementById('pendingFilter');

    function renderTable(data) {
      tableBody.innerHTML = '';

      data.forEach(item => {
        const row = document.createElement('tr');

        row.innerHTML = `
          <td>${item.rentId}</td>
          <td>${item.emailId}</td>
          <td>${item.phoneNumber}</td>
          <td>${item.rentPaid}</td>
          <td>${item.referalsDiscount}</td>
          <td>${item.totalRent}</td>
          <td>${item.createdDate}</td>
          <td>${item.rentDueDate}</td>
          <td>${item.pending}</td>
        `;

        if (item.pending) {
          row.classList.add('highlight');
        }

        tableBody.appendChild(row);
      });
    }

    function filterData(data) {
      if (pendingFilter.checked) {
        const pendingOnly = data.filter(item => item.pending);
        renderTable(pendingOnly);
      } else {
        renderTable(data);
      }
    }


    fetch(`${getAllRentData}`,{
        method:'GET'
    }).then(response =>{
        if(response.status == 200)
        {
            return response.json();
        }
        }).then(data => {
            if(data!=null)
            {
                renderTable(data);
            }
        }).catch(error => {
            console.error(error);
            alert('Error occurred while calling API');
        });


    // Filter event
    pendingFilter.addEventListener('change', function() {
      filterData(data);
    });
  });
