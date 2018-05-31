

angular.module('myApp').controller('AmbienceLoopsCtrl', ['$scope', '$location', '$window', '$http', function ($scope, $location, $window, $http) {

    $scope.$root.title = 'AngularJS SPA | About';

    $scope.files = [];

    $scope.selectedstationid = 999;
    $scope.stationids = [];

    $scope.genreOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 }];
    $scope.complexityOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 }];
    $scope.mooddarkOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 }];
    $scope.moodlightOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 }];
    $scope.audioLengthOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 },
        { name: 11, id: 11 }, { name: 12, id: 12 }, { name: 13, id: 13 }, { name: 14, id: 14 }, { name: 15, id: 15 }, { name: 16, id: 16 }, { name: 17, id: 17 }, { name: 18, id: 18 }, { name: 19, id: 19 }, { name: 20, id: 20 }];
     
    $scope.selectedGenreOption = $scope.genreOptions[0];
    $scope.selectedComplexityOption = $scope.complexityOptions[0];
    $scope.selectedMoodLightOption = $scope.moodlightOptions[0];
    $scope.selectedMoodDarkOption = $scope.mooddarkOptions[0];
    $scope.selectedAudioLengthOption = $scope.audioLengthOptions[0];
     
    $scope.$on("fileSelected", function (event, args) {
        $scope.$apply(function () { 
            console.log('uploading', args.file);
            $scope.files.push(args.file);
        });
    });

    $scope.genres = [];
    $scope.characteristics = [];

    $http.get('/api/genre/get').success(function (data, status, headers, config) {

        angular.forEach(data, function (value, key) {
            value["checked"] = false;
        });

        $scope.genres = data;
    });

    $http.get('/api/stationids/get').success(function (data, status, headers, config) {
        $scope.stationids = data;
    });

    $http.get('/api/characteristics/get').success(function (data, status, headers, config) {

        angular.forEach(data, function (value, key) {
            value["checked"] = false;
        });

        $scope.characteristics = data;
    });

    $scope.rootOneOptions = [];
    $scope.rootTwoOptions = [];
    $scope.rootThreeOptions = [];
    $scope.rootFourOptions = [];

    $scope.selectedRootOneOption = -1;
    $scope.selectedRootTwoOption = -1;
    $scope.selectedRootThreeOption = -1;
    $scope.selectedRootFourOption = -1;

    $http.get('/api/chords/get').success(function (data, status, headers, config) {

        angular.forEach(data, function (value, key) {
            value["checked"] = false;
        });

        $scope.rootOneOptions = angular.copy(data);
        $scope.rootTwoOptions = angular.copy(data);
        $scope.rootThreeOptions = angular.copy(data);
        $scope.rootFourOptions = angular.copy(data);

        console.log('$scope.rootOneOptions', $scope.rootOneOptions);
    });

    var updateSelectedRoots = function () {

        angular.forEach($scope.rootOneOptions, function (value, key) {
            if (value.checked) {
                console.log('updateSelectedRoots 1', value.Chord);
                $scope.selectedRootOneOption = value.Chord;
            }
        });
    }

    var createGenreList = function () {

        var genresArray = [];

        angular.forEach($scope.genres, function (value, key) {
            if (value.checked) {
                genresArray.push(value.Id);
            }
        });

        return genresArray;
    }

    var createCharacteristicsList = function () {

        var characteristicsArray = [];

        angular.forEach($scope.characteristics, function (value, key) {

            console.log('char', value);

            if (value.checked) {
                console.log('checked char id ', value.Id);
                characteristicsArray.push(value.Id);
            }
        });

        return characteristicsArray;
    }

    //TODO FIX THIS SHIT .. 
    $scope.rootChordId = 3;

    var selectedChordsIds = [];

    $scope.handleChordSelected = function () {

        console.log('$scope.selectedRootOneOption', $scope.selectedRootOneOption);

        updateSelectedRoots();

        var selectedChords = [];

        console.log('pre-break', $scope.selectedRootOneOption.Id);
        console.log('pre-break', $scope.selectedRootTwoOption.Id);

        selectedChords.push($scope.selectedRootOneOption.Chord);
        selectedChords.push($scope.selectedRootTwoOption.Chord);
        selectedChords.push($scope.selectedRootThreeOption.Chord);
        selectedChords.push($scope.selectedRootFourOption.Chord);

        selectedChordsIds = [];

        if (isInt($scope.selectedRootOneOption.Id)) {
            selectedChordsIds.push($scope.selectedRootOneOption.Id);
        }

        if (isInt($scope.selectedRootTwoOption.Id)) {
            selectedChordsIds.push($scope.selectedRootTwoOption.Id);
        }

        if (isInt($scope.selectedRootThreeOption.Id)) {
            selectedChordsIds.push($scope.selectedRootThreeOption.Id);
        }

        if (isInt($scope.selectedRootFourOption.Id)) {
            selectedChordsIds.push($scope.selectedRootFourOption.Id);
        }

        $scope.$broadcast('setBackingChords', selectedChords);
    }

    $scope.handleFileSelected = function (e) {

        var file = e.files[0];
        var filename = file.name;

        $scope.$broadcast('handleFileSelected', filename);
    }

    $scope.test = function () {

        //TODO ambience loops are not supposed to have a tempo!!
        var bpm = 50;
        $scope.$broadcast('playAudioLoop', bpm, true);
    }

    $scope.stopAudioLoop = function () {
        $scope.$broadcast('stopAudioLoop', true);
    }
     
    $scope.save = function () {

        if($scope.selectedstationid === 999){
            alert('SELECT STATION ID !!!')
            return;
        }
         
        $scope.model = {
            stationid: $scope.selectedstationid,
            genres: createGenreList(),
            characteristics: createCharacteristicsList(),
            rootChords: selectedChordsIds,
            complexity: $scope.selectedComplexityOption.id,
            moodDark: $scope.selectedMoodDarkOption.id,
            moodLight: $scope.selectedMoodLightOption.id,
            audioLength: $scope.selectedAudioLengthOption.id
        }; 
         
        $http({
            method: 'POST',
            url: "/api/UploadAmbience/PostStuff",
            headers: { 'Content-Type': undefined },
            transformRequest: function (data) {

                var formData = new FormData();
                formData.append("model", angular.toJson(data.model));
                for (var i = 0; i < data.files.length; i++) {
                    formData.append("file" + i, data.files[i]);
                }

                return formData;
            },
            data: { model: $scope.model, files: $scope.files }
        }).
        success(function (data, status, headers, config) {
            alert("success!");
            $window.location.reload();
        }).
        error(function (data, status, headers, config) {
            alert("failed!");
        });
    };
     
    function uploadProgress(evt) {
        $scope.$apply(function () {
            if (evt.lengthComputable) {
                $scope.progress = Math.round(evt.loaded * 100 / evt.total)
            } else {
                $scope.progress = 'unable to compute'
            }
        })
    }

    function uploadComplete(evt) {
        alert(evt.target.responseText)
    }

    function uploadFailed(evt) {
        alert("There was an error attempting to upload the file.")
    }

    function uploadCanceled(evt) {
        scope.$apply(function () {
            scope.progressVisible = false
        })
        alert("The upload has been canceled by the user or the browser dropped the connection.")
    }
}]);