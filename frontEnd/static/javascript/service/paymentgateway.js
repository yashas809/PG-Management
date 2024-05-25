import { payrent } from '../common/apiconstants.js';

document.addEventListener("DOMContentLoaded", function() {
    const paymentForm = document.getElementById("paymentForm");
    const paymentStatus = document.getElementById("paymentStatus");

    const amount = localStorage.getItem('amount');
    const amountElement = document.getElementById('amount');

    if (amountElement) {
        amountElement.value = amount;
    }

    paymentForm.addEventListener("submit", async function(event) {
        event.preventDefault();

        // Simulate payment processing
        paymentStatus.textContent = "Processing payment...";

        try {
            const response = await updateDatabase(event);

            if (response) {
                paymentStatus.textContent = "Payment successful!";
                paymentStatus.style.color = "green";
            } else {
                paymentStatus.textContent = "Payment failed. Please try again.";
                paymentStatus.style.color = "red";
            }
        } catch (error) {
            paymentStatus.textContent = "An error occurred. Please try again.";
            paymentStatus.style.color = "red";
        }
    });
});

async function updateDatabase(event) {
    event.preventDefault();

    const paidInfo = localStorage.getItem('paidInformation');
    if (!paidInfo) {
        console.error('No paid information found in local storage');
        alert('No paid information found in local storage');
        return false;
    }

    let paidJsonInfo;
    try {
        paidJsonInfo = JSON.parse(paidInfo);
    } catch (e) {
        console.error('Failed to parse paid information:', e);
        alert('Invalid paid information format');
        return false;
    }

    const referalsDiscount = paidJsonInfo.referalsDiscount || 0;
    const settledAgainst = paidJsonInfo.settledAgainst || 0;

    const formData = {
        isPending: false,
        rentPaid: paidJsonInfo.rentPaid,
        referalsDiscount: referalsDiscount,
        settledAgainst: settledAgainst,
        rentId: paidJsonInfo.rentId
    };

    try {
        const response = await fetch(payrent, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData),
        });

        if (response.status === 200) {
            return true;
        } else {
            console.error('Failed to update the database. Status:', response.status);
            alert('Failed to update the database.');
            return false;
        }
    } catch (error) {
        console.error('Error:', error.stack);
        alert('An error occurred while calling the API.');
        return false;
    }
}
