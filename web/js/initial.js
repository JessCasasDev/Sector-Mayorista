(function($){
    $(function(){
        $('.parallax').parallax();
       
       }); // end of document ready
})(jQuery); // end of jQuery name space

          
$('#openLogin').click( function() {$('#login').openModal();} );
$('#openClientRegister').click( function() {$('#clientRegister').openModal();} );
$('#openClientRegisterMenu').click( function() {$('#clientRegister').openModal();} );
$('#openUpdateClient').click( function() {$('#updateClient').openModal();} );