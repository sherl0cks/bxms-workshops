$(document).ready(function() {
	$('#vacation-request-form').submit(function() {
		var url = "/rest/requests/"; 
		$.ajax({
			type : "POST",
			url : url,
			data : $("#vacation-request-form").serializeArray(),
			success : function(data) {
				alert( "request submitted successfully" );
			},
			 error: function (data) {
				 alert("something broke in the ajax. go to the contact us section and send us a bug!"); // show response from the php script.
			 }
			
		});

		return false; // avoid to execute the actual submit of the form.
	});
});