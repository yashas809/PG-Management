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
             messageElement.classList.add('incoming');
         } else if (message.messageDirection === 'O') {

             messageElement.classList.add('outgoing');
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
     if(caseId && caseId!=null)
     {
         if (messageText !== '') {

             const formData = {
                 message:messageText,
                 messageDirection:'O',
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
     }
 }


 function goBack(event)
 {
     event.preventDefault();
     window.location.href = 'viewExistingTicketsOwner.html';
     localStorage.removeItem('caseId');
 }