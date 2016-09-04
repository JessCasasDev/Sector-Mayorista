
$(document).ready(function () {
    $(".button-collapse").sideNav();
    $(".dropdown-iglesia").dropdown();
    $(".dropdown-i").dropdown();
    $('.slider').slider({full_width: true});
    $('select').material_select();
    console.log("inicio");
}
);

$('.datepicker').pickadate({
    selectMonths: true, // Creates a dropdown to control month
    selectYears: 15 // Creates a dropdown of 15 years to control year
  });