/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* global google */

$(document).ready(function () {

    $('#lat').change(function () {
        $('#submit-button').removeAttr("disabled");
        var textToMarker = new google.maps.LatLng($('#lat').val(), $('#lon').val());
        currentMarker.setMap(null);
        addMarker(textToMarker);
    });
    $('#lon').change(function () {
        $('#submit-button').removeAttr("disabled");
        var textToMarker = new google.maps.LatLng($('#lat').val(), $('#lon').val());
        currentMarker.setMap(null);
        addMarker(textToMarker);
    });
    $('#deleteLocation').click(function () {
        deleteMarkers();
    });
//    $('#submit-button').click(function () {
//        addNewMarker();
//    });


});

var map;
var currentMarker;
var markers = [];
var options;
var infoWindow = null;
var bounds;
var tempMarker;
var markerId;


window.initMap = function () {
    //console.log(user);
    if (user === "") {
        user = 'default';
    }
    console.log(userId);
    $.ajax({
        type: 'GET',

        url: contextPath + '/loadmap?user=' + user,
        dataType: 'json',
        success: function (location) {

            if (location.length === 0) {

                options = {
                    zoom: 4,
                    center: {lat: 39.8283, lng: -98.5795}
                };

            } else {

                options = {
                    zoom: 15,
                    center: {lat: location[0].lat, lng: location[0].lon}
                };
            }
            ;



            map = new google.maps.Map(document.getElementById('map'), options);
            infoWindow = new google.maps.InfoWindow({
                content: 'placeholder'
            });
            currentMarker = new google.maps.Marker();
            setAllMarkers(location);

            map.addListener('click', function (event) {
                currentMarker.setMap(null);
                addMarker(event.latLng);
            });
            if (user === 'default') {
                $('#submit-button').attr("disabled", "disabled");
                $('#warning-message').text("Please log in to save locations");
            }


        },
        error: function () {
            $('#errorMessages').val('Error Calling Web Service. Please Try Again Later');
        }
    });


};
//adds markers from db to map
function setAllMarkers(location) {
    //loops through locations passed from db and stores them in array after placement on map
    for (var i = 0; i < location.length; i++) {
        var loc = {lat: location[i].lat, lng: location[i].lon};
        var marker = new google.maps.Marker({
            position: loc,
            map: map,
            title: location[i].locationName,
            marker_id: location[i].id
        });
        markers.push(marker);
    }
    ;
    bounds = new google.maps.LatLngBounds();
    for (var i = 0; i < markers.length; i++) {
        bounds.extend(markers[i].getPosition());
    }
    if (location.length > 1) {
        map.fitBounds(bounds);
    }
    ;

    //adds event listener to open an infowindow when an existing marker is clicked
    for (var i = 0; i < markers.length; i++) {

        var marker = markers[i];
        google.maps.event.addListener(marker, 'click', function () {
            infoWindow.setContent("<a href=" + contextPath + "/loadLocation?id=" + this.marker_id + "><h5>" + this.title + "</h5></a>" +
                    "<br>Latitude: " + this.position.lat().toFixed(4) +
                    "<br>Longitude: " + this.position.lng().toFixed(4) +
                    "<br>Tags: " + this.marker_id +
                    "<br><a onClick=\"deleteMarkers()\"id=\"deleteLocation\" href=\"#\">Delete</a>");
            //href=\"/MapIndex/deleteLocation?id=" + (this.marker_id) + "\"
            infoWindow.open(map, this);
            currentMarker.setMap(null);
            //console.log(this.marker_id);
            markerId=this.marker_id;
            //tempMarker = new google.maps.Marker();
            tempMarker = this;
            $('#lat').val(this.position.lat().toFixed(4));
            $('#lon').val(this.position.lng().toFixed(4));
            $('#location-name').val(this.title);
            $('#submit-button').attr("disabled", "disabled");
        });
    }
    ;
}
;
//adds marker on map where clicked -- blue color to differentiate from existing locations
function addMarker(location) {
    currentMarker = new google.maps.Marker({
        position: location,
        map: map,
        icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'

    });
    
    
    
    $('#submit-button').removeAttr("disabled");
    $('#location-name').val(null);
    console.log(location);
    infoWindow.close();
    $('#lat').val(currentMarker.position.lat().toFixed(4));
    $('#lon').val(currentMarker.position.lng().toFixed(4));
    if (user === 'default') {
        $('#submit-button').attr("disabled", "disabled");
    }
}

function addNewMarker() {
    var newMarker = currentMarker;
    actualPath = contextPath + ("/newLocation?user="+user);
    var obj = {
        "location": { 
            "locationName": $('#location-name').val(),
            "lat": $('#lat').val(),
            "lon": $('#lon').val()
        }
    };
    var objJSON = JSON.stringify(obj);
    console.log(objJSON);
    console.log(actualPath);
    $.ajax({
        type: 'POST',
        url: actualPath,
        data: objJSON,
        contentType: 'application/json',
        success: function(){
            alert("success");
        },
        error: function(){
            alert("failure");
        }
    });
    
}

function deleteMarkers() {
var markerToBeDeleted = tempMarker;
    actualPath = contextPath + ("/deleteMarker/"+ markerId);
            $.ajax({
                type:'DELETE',
                url: actualPath,
                //contentType: 'application/json',
                //data: "{}",
                dataType: 'json',
                success: function(location){
                    console.log(markerToBeDeleted);
                    markerToBeDeleted.setMap(null);
                    console.log(location);
                },
                error: function(response) {
                    alert(response.status);
                    alert(XMLHttpRequest.responseText);
                }
            });
    
}

