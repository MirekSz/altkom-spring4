$(document).ready(function() {
	$("input[name='file']").change(function(){
	    showPreview(this);
	});
})


function showPreview(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#preview').hide().width(300).attr('src', e.target.result).fadeIn(1000);
        }

        reader.readAsDataURL(input.files[0]);
    }
}