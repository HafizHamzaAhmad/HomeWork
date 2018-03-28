function init() {

    fillCategoryDropDown();

    jQuery.ajax({
        url: "/api/book"
    }).then(function (data) {
        displayBooks(data)
    }).fail(function (data) {
        console.log(data);
    });
}

function updateBooks() {
    var x = document.getElementById("dropdown").value;
    var url;
    if(x == "All")
        url = "/api/book";
    else
        url = "/api/category/"+ x +"/books";

    jQuery.ajax({
        url: url
    }).then(function (data) {
        displayBooks(data)
    }).fail(function (data) {
        console.log(data);
    });
}

function displayBooks(data) {
    $('#showBooks').html("");
    data.forEach(function (element) {
        console.log(element);
        $('#showBooks').append(
            '<div class="item  col-xs-4 col-lg-4">' +
                '<div class="thumbnail">' +
                    '<img class="group grid-group-image" src="' + element.thumbnailUrl + '" alt="" />' +
                    '<div class="caption">' +
                        '<h4 class="group inner grid-group-item-heading">' + element.title + '</h4>' +
                        '<div class="row">' +
                            '<div class="col-xs-12 col-md-6">' +
                                '<h6 class="group inner grid-group-item-heading">Rating: </h6>' +
                                '<p class="lead">'+ element.averageRating +'</p>' +
                            '</div>' +
                            '<div class="col-xs-12 col-md-6">' +
                                '<h6 class="group inner grid-group-item-heading">Publication Date: </h6>' +
                                '<p class="lead">'+ formatDate(element.publishedDate) +'</p>' +
                            '</div>' +
                        '</div>' +
                        '<a class="btn btn-success" href="'+ element.previewLink +'"> Preview </a>' +
                    '</div>' +
                '</div>' +
            '</div>');
    })


}

function formatDate(unixDate) {
    if(unixDate == null)
        return 'Not Available';
    var date = new Date(unixDate*1000);
// Hours part from the timestamp
    var yyyy = date.getFullYear();
// Minutes part from the timestamp
    var mm = date.getMonth() + 1;
// Seconds part from the timestamp
    var dd = date.getDate();

    return yyyy + '-' + mm + '-' + dd;

// Will display time in 10:30:23 format
    //return formattedTime = hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
}

function fillCategoryDropDown() {
    jQuery.ajax({
        url: "/api/categories"
    }).then(function (data) {
        // Remove current options
        $('#dropdown').html('');
        // Add the empty option with the empty message
        $('#dropdown').append('<option value="All">' + 'All' + '</option>');
        // Check result isnt empty
        if(data != '')
        {
            // Loop through each of the results and append the option to the dropdown
            $.each(data, function(k, v) {
                $('#dropdown').append('<option value="' + v.name + '">' + v.name + '</option>');
            });
        }

    }).fail(function (data) {
        console.log(data);
    });
}