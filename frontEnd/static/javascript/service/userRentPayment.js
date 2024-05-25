document.addEventListener("DOMContentLoaded", function() {
    const paymentData = localStorage.getItem('paymentData');
    const jsonData = JSON.parse(paymentData);

    if (jsonData.rentId) {
        document.getElementById('rentId').value = jsonData.rentId;
    }
    if (jsonData.totalRent) {
        document.getElementById('totalRent').value = jsonData.totalRent;
    }
    if (jsonData.createdDate) {
        document.getElementById('createdDate').value = jsonData.createdDate;
    }
    if (jsonData.emailId) {
        document.getElementById('emailId').value = jsonData.emailId;
    }
    if (jsonData.phoneNumber) {
        document.getElementById('phoneNumber').value = jsonData.phoneNumber;
    }
    if (jsonData.rentPaid) {
        document.getElementById('rentPaid').value = jsonData.rentPaid;
    }

    if (jsonData.referalsDiscount) {
        document.getElementById('referalDiscount').value = jsonData.referalsDiscount;
    }

    var isPending = jsonData.pending;

    if (!jsonData.pending) {
        document.getElementById('approveBtn').style.display = 'none';
    }
    document.getElementById('rentPaid').value = jsonData.totalRent;

    const userData = localStorage.getItem('userData');
    const UserjsonData = JSON.parse(userData);
    loadData(UserjsonData.referalBonus);

    const form = document.getElementById('payment');
    if(form)
        {
            form.addEventListener('submit', function (event) {
                performAction(event);
            });
        }
});

function performAction(event)
{
    event.preventDefault();
    const buttonId = event.submitter.id;
    if(buttonId === 'approveBtn')
    {
            const paidInformation = {
                rentId : document.getElementById('rentId').value,
                rentPaid : document.getElementById('rentPaid').value,
                settledAgainst : document.getElementById('dynamicReferalBonus').value,
                referalsDiscount : document.getElementById('referalDiscount').value,
            }
            console.log(paidInformation);
            localStorage.setItem('paidInformation',JSON.stringify(paidInformation));
            localStorage.setItem('amount',document.getElementById('rentPaid').value);
            window.location.href = 'paymentgateway.html';
    }else{
        window.location.href = 'UserIndex.html';
    }

}

function loadData(optionsData) {
    const dropdown = document.getElementById("dynamicReferalBonus");

    console.log(optionsData);
    if (dropdown) {
        // Clear existing options
        dropdown.innerHTML = "";
        const defaultOption = document.createElement("option");
        defaultOption.value = ""; // Default empty value
        defaultOption.text = "Select any Referal Bonus";
        dropdown.appendChild(defaultOption);

        // Add new options
        optionsData.forEach(option => {
            const optionElement = document.createElement("option");
            optionElement.value = option.referalBonusId; // Assuming referalBonusId is the property for value
            optionElement.text = "ReferedTo - " + option.referedTo;
            dropdown.appendChild(optionElement);
        });

        // Add change event listener to display selected info
        dropdown.addEventListener("change", function () {
            const selectedOption = dropdown.options[dropdown.selectedIndex];
            if (selectedOption.value !== "") {
                document.getElementById('referalDiscount').value = 100;
                document.getElementById('rentPaid').value = document.getElementById('rentPaid').value - 100;
            } else {
                document.getElementById('referalDiscount').value = '';
            }
        });
    }
}