document.addEventListener("DOMContentLoaded", function(){
    var caseId = localStorage.getItem('caseId');
     if(caseId && caseId!=null)
     {
         getConversation(caseId)
     }

 })

 function getConversation(caseId)
 {
     fetch(`http://localhost:8080/chatbox/getConversation?caseId=${caseId}`,{
         method:"GET",
     }).then(response => {
         return response.json();
       }).then(data => {
         if(data!=null)
         {
             displayMessagesForClient(data);

         }
     })
 }


 // Function to display messages
 function displayMessagesForClient(data) {
     var container = document.getElementById('message-container');
     container.innerHTML = ''; // Clear previous messages

     data.forEach(function(message) {
         var messageElement = document.createElement('div');
         messageElement.textContent = message.message;
         messageElement.classList.add('message');

         if (message.messageDirection === 'I') {
             messageElement.classList.add('outgoing');
         } else if (message.messageDirection === 'O') {

             messageElement.classList.add('incoming');
         }

         container.appendChild(messageElement);
     });
 }

 // Display messages/
 //displayMessages(jsonData);

 // Send message function

 function sendClientMessage(event)
 {
     event.preventDefault();
     var messageInput = document.getElementById('message-input');
     var messageText = messageInput.value.trim();


     var caseId = localStorage.getItem('caseId');
     if(caseId>0)
     {
         if (messageText !== '') {

             const formData = {
                 message:messageText,
                 messageDirection:'I',
             }
             console.log(formData);

             fetch(`http://localhost:8080/chatbox/sendMessage?caseId=${localStorage.getItem('caseId')}`,{
                 method:"POST",  headers: {
                     "Content-Type": "application/json"
                   },
                 body:JSON.stringify(formData)
             }).then(response => {
                 if(response.status == 200)
                     {
                         window.location.reload();
                     }
               })
         }
     }else{
         if (messageText !== '') {
             var reasonCode = localStorage.getItem('reason');
             var userName = localStorage.getItem('userData');
             var userDataJson = JSON.parse(userName);

             const formData = {
                 reasonCode:reasonCode,
                 userName:userDataJson.loginname,
                 message:messageText,
                 messageDirection:'I',
             }
             console.log(formData);

             fetch(`http://localhost:8080/chatbox/createCase`,{
                 method:"POST",  headers: {
                     "Content-Type": "application/json"
                   },
                 body:JSON.stringify(formData)
             }).then(response => {
                 return response.json();
               }).then(data => {
                 if(data!=null)
                 {
                     localStorage.setItem('caseId', data.caseId);
                     window.location.reload();
                 }
             })
         }
     }


 }

 function goBack(event)
 {
    var userName = localStorage.getItem('userData');
    var userDataJson = JSON.parse(userName);

     event.preventDefault();
     if(userDataJson.roleName == 'NewUser')
        {
            window.location.href='viewExistingTicketsProspect.html'
        }else{
            window.location.href='viewExistingTicketsUser.html'
        }
        localStorage.removeItem('caseId');
 }