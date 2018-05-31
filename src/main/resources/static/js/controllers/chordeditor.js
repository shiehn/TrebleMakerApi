
angular.module('myApp').controller('ChordEditorCtrl', ['$scope', '$location', '$window', '$http', function ($scope, $location, $window, $http) {
     
    $scope.song_name = "";
    $scope.timesignature = "4/4";
    $scope.key = "";
     
    $scope.$root.title = 'AngularJS SPA | ChordEditor';
     
    $scope.rootOneOptions = new Array(270);

    $scope.rootOneSelection = [];

    $scope.handleChordSelected = function () {
        console.log('UPDATED');
        console.log($scope.rootOneSelection[0]);
        console.log($scope.rootOneSelection[59]);
    }

    $http.get('/api/chords/get').success(function (data, status, headers, config) {

        console.log('value=', $scope.rootOneOptions[0]);
         
        var emptyValue = { Chord: "empty", Id: -1 };

        $scope.rootOneOptions[0] = emptyValue;
        $scope.rootOneSelection[0] = emptyValue;

        var filteredChords = [];

        angular.forEach(data, function (value, key) {

            var sstr = value["Chord"].substring(1, 2);

            console.log('sstr', sstr);
             
            if (sstr != "b") {
                filteredChords.push(value);
            }
        });

        var populateCount = 1;
        angular.forEach(filteredChords, function (value, key) {
            if (populateCount < $scope.rootOneOptions.length) { 
                $scope.rootOneOptions[populateCount] = angular.copy(value);
                $scope.rootOneSelection[populateCount] = emptyValue;
                populateCount++;
            }
        });

        console.log('root_one_options', $scope.rootOneOptions);
    });

    $scope.genres = [];

    $http.get('/api/genre/get').success(function (data, status, headers, config) {

        angular.forEach(data, function (value, key) {
            value["checked"] = false;
        });

        $scope.genres = data;
    });

    var createGenreList = function () {

        var genresArray = [];

        angular.forEach($scope.genres, function (value, key) {
            if (value.checked) {
                genresArray.push(value.Id);
            }
        });

        return genresArray;
    }

    /*
        $scope.song_name = "";
        $scope.timesignature = "4/4";
        $scope.key = "";
    */

    var chordIds = [];

    var collectChordSelections = function(){
        angular.forEach($scope.rootOneSelection, function (value, key) {
            if (value.Chord != "" && value.Chord != undefined && value.Chord != null && value.Chord != "empty") { 
                chordIds.push(value.Id);
            }
        });
    }
     
    $scope.save = function () {

        collectChordSelections();

        if ($scope.song_name == "" || (chordIds.length == 0)) {
            alert('error');
        }
         
        $scope.SongUpload = {
            genres: createGenreList(),
            songname: $scope.song_name,
            timesignature: $scope.timesignature,
            key: $scope.key,
            chords: chordIds
        };

        $http.post("/api/Chords/Post", $scope.SongUpload).
        success(function (data, status, headers, config) {
            $window.location.reload();
            /*alert("Success");*/
        }).
        error(function (data, status, headers, config) {
            $window.location.reload();
            /*alert("ERROR!");*/
        });


    };
    //SAVE ...
    //genres: createGenreList(),

}]);