document.addEventListener("DOMContentLoaded", function() {
  const paymentForm = document.getElementById("paymentForm");
  const paymentStatus = document.getElementById("paymentStatus");

  paymentForm.addEventListener("submit", function(event) {
      event.preventDefault();

      // Simulate payment processing
      paymentStatus.textContent = "Processing payment...";
      setTimeout(() => {
          // Simulate successful payment
          const success = Math.random() > 0.2; // 80% chance of success
          if (success) {
              paymentStatus.textContent = "Payment successful!";
              paymentStatus.style.color = "green";
          } else {
              paymentStatus.textContent = "Payment failed. Please try again.";
              paymentStatus.style.color = "red";
          }
      }, 2000);
  });
});
