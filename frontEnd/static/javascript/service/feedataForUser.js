import {feeDataUser} from '../common/apiconstants.js'

document.addEventListener('DOMContentLoaded', function() {



    const tableBody = document.getElementById('rentTableBody');
    const pendingFilter = document.getElementById('pendingFilter');

    function renderTable(data) {

      if(tableBody)
        {
          tableBody.innerHTML = '';

          data.forEach(item => {
            const row = document.createElement('tr');

            console.log("Item data:", JSON.stringify(item));
            row.innerHTML = `
            <td><a href="#" class="advertisement-link" data-item='${JSON.stringify(item)}'>${item.rentId}</a></td>
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


         // Add event listeners to all advertisement links
         const links = document.querySelectorAll('.advertisement-link');
         links.forEach(link => {
           link.addEventListener('click', function(event) {
             event.preventDefault(); // Prevent default anchor click behavior
             console.log("Data-item attribute value:", this.getAttribute('data-item'));
             const itemData = JSON.parse(this.getAttribute('data-item'));
             localStorage.setItem('paymentData', JSON.stringify(itemData));
             window.location.href = 'UserRentPayment.html';
             console.log(itemData);
           });
         });
        }

    }


    function filterData(data) {
      if (pendingFilter.checked) {
        const pendingOnly = data.filter(item => item.pending);
        renderTable(pendingOnly);
      } else {
        renderTable(data);
      }
    }

    const userData = localStorage.getItem('userData');
    const jsonData = JSON.parse(userData);

    const url = `${feeDataUser}?appUserFK=${encodeURIComponent(jsonData.id)}`;


    // Later, when you want to fill its inner HTML:
    const contentToFill = jsonData.referalCodes.referalCode;
    document.getElementById('myReferralCode').value = contentToFill;

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
                renderTable(data);
            }
        }).catch(error => {
            console.error(error);
            alert('Error occurred while calling API');
        });

  });

