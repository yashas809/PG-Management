import {getDynamicAdvertisement} from './apiconstants.js';

document.addEventListener("DOMContentLoaded",function(){

    invokeAPI();
})

function invokeAPI()
{
    fetch(`${getDynamicAdvertisement}`,{
        method:'GET'
    }).then(response =>{
        if(response.status == 200)
        {
            return response.json();
        }
        }).then(data => {
            if(data!=null)
            {
                console.log(data);
                loadData(data)
            }
        }).catch(error => {
            console.error(error);
            alert('Error occurred while calling API');
        });
}

function loadData(optionsData)
{
    const dropdown = document.getElementById("dynamicAdvertisement");

    if(dropdown)
    {
        // Clear existing options
        dropdown.innerHTML = "";
        const optionElement = document.createElement("option");
          optionElement.value = ""; // Assuming departmentId is the property for value
          optionElement.text = "Select Advertisment";
          dropdown.appendChild(optionElement);

        // Add new options
        optionsData.forEach(option => {
          const optionElement = document.createElement("option");
          optionElement.value = option.advertisementId; // Assuming departmentId is the property for value
          optionElement.text = "FoodType -"+option.foodType + " TotalShares -" +option.noOfShares +
           " Vacancy -"+ option.vacancy +"  RoomNo -"+option.roomNo + " FloorNo -"+option.floorNo;
          dropdown.appendChild(optionElement);
        });
    }
}