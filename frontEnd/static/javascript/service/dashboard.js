import {getAllusers} from '../common/apiconstants.js'
import {getCountofUsers} from '../service/getCounts.js'

// Sample array of dynamic values
  const data = [
    { label: "Active", value: 50 },
    { label: "Deleted", value: 75 },
    { label: "Pending", value: 30 }
];

// Function to update the pie chart with new data
function updatePieChart(data) {
    const labels = data.map(item => item.roleName);
    const values = data.map(item => item.count);

    const ctx = document.getElementById("pieChart").getContext("2d");
    const myChart = new Chart(ctx, {
        type: "pie",
        data: {
            labels: labels,
            datasets: [{
                data: values,
                backgroundColor: [
                    "rgba(255, 99, 132, 0.5)",
                    "rgba(54, 162, 235, 0.5)",
                    "rgba(255, 206, 86, 0.5)",
                    "rgba(75, 192, 192, 0.5)",
                ],
                borderColor: [
                    "rgba(255, 99, 132, 1)",
                    "rgba(54, 162, 235, 1)",
                    "rgba(255, 206, 86, 1)",
                    "rgba(75, 192, 192, 1)",
                ],
                borderWidth: 1,
            }],
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
        },
    });
}


document.addEventListener("DOMContentLoaded",function()
{
    getUserData();

})

function getUserData()
{

    fetch(`${getAllusers}`,{
        method:'GET'
    }).then(response =>{
        if(response.status == 200)
        {
            return response.json();
        }
        }).then(data => {
            if(data!=null)
            {
                const roleCounts = getCountofUsers(data);
                console.log(roleCounts);
                updatePieChart(roleCounts);
            }
        }).catch(error => {
            console.error(error);
            alert('Error occurred while calling API');
        });
}
