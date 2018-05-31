

angular.module('myApp').controller('HarmonicLoopsCtrl', ['$scope', '$location', '$window', '$http', '$rootScope', function ($scope, $location, $window, $http, $rootScope) {

    $scope.$root.title = 'AngularJS SPA | About';

    $scope.genres = [];
    $scope.characteristics = [];
    $scope.instruments = [];

    $scope.selectedstationid = 999;
    $scope.stationids = [];

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

    $http.get('/api/instruments/get').success(function (data, status, headers, config) {

        angular.forEach(data, function (value, key) {
            value["checked"] = false;
        });

        $scope.instruments = data;
    });

    $scope.rootOneOptions = [];
    $scope.rootTwoOptions = [];
    $scope.rootThreeOptions = [];
    $scope.rootFourOptions = [];

    $scope.selectedRootOneOption =-1;
    $scope.selectedRootTwoOption = -1;
    $scope.selectedRootThreeOption = -1;
    $scope.selectedRootFourOption = -1;

    $http.get('/api/chords/get').success(function (data, status, headers, config) {

        angular.forEach(data, function (value, key) {
            value["checked"] = false;
        });

        var filteredChords = [];

        angular.forEach(data, function (value, key) {

            var sstr = value["Chord"].substring(1, 2);

            console.log('sstr', sstr);

            if (sstr != "b") {
                filteredChords.push(value);
            }
        });

        $scope.rootOneOptions = angular.copy(filteredChords);
        $scope.rootTwoOptions = angular.copy(filteredChords);
        $scope.rootThreeOptions = angular.copy(filteredChords);
        $scope.rootFourOptions = angular.copy(filteredChords);

        $scope.selectedRootOneOption = $scope.rootOneOptions[75];
        $scope.selectedRootTwoOption = $scope.rootTwoOptions[75];
        $scope.selectedRootThreeOption = $scope.rootThreeOptions[75];
        $scope.selectedRootFourOption = $scope.rootFourOptions[75];
    });




    $scope.files = [];

    //$scope.genreOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 }];
    $scope.complexityOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 }];
    $scope.mooddarkOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 }];
    $scope.moodlightOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 }];
    $scope.audioLengthOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 },
        { name: 11, id: 11 }, { name: 12, id: 12 }, { name: 13, id: 13 }, { name: 14, id: 14 }, { name: 15, id: 15 }, { name: 16, id: 16 }, { name: 17, id: 17 }, { name: 18, id: 18 }, { name: 19, id: 19 }, { name: 20, id: 20 }];

    $scope.bpmOptions = [{ name: 70, id: 70 },
        { name: 71, id: 71 },
        { name: 72, id: 72 },
        { name: 73, id: 73 },
        { name: 74, id: 74 },
        { name: 75, id: 75 },
        { name: 76, id: 76 },
        { name: 77, id: 77 },
        { name: 78, id: 78 },
        { name: 79, id: 79 },

        { name: 80, id: 80 },
        { name: 81, id: 81 },
        { name: 82, id: 82 },
        { name: 83, id: 83 },
        { name: 84, id: 84 },
        { name: 85, id: 85 },
        { name: 86, id: 86 },
        { name: 87, id: 87 },
        { name: 88, id: 88 },
        { name: 89, id: 89 },

        { name: 90, id: 90 },
        { name: 91, id: 91 },
        { name: 92, id: 92 },
        { name: 93, id: 93 },
        { name: 94, id: 94 },
        { name: 95, id: 95 },
        { name: 96, id: 96 },
        { name: 97, id: 97 },
        { name: 98, id: 98 },
        { name: 99, id: 99 },

        { name: 100, id: 100 },
        { name: 101, id: 101 },
        { name: 102, id: 102 },
        { name: 103, id: 103 },
        { name: 104, id: 104 },
        { name: 105, id: 105 },
        { name: 106, id: 106 },
        { name: 107, id: 107 },
        { name: 108, id: 108 },
        { name: 109, id: 109 },

        { name: 110, id: 110 },
        { name: 111, id: 111 },
        { name: 112, id: 112 },
        { name: 113, id: 113 },
        { name: 114, id: 114 },
        { name: 115, id: 115 },
        { name: 116, id: 116 },
        { name: 117, id: 117 },
        { name: 118, id: 118 },
        { name: 119, id: 119 },

        { name: 120, id: 120 },
        { name: 121, id: 121 },
        { name: 122, id: 122 },
        { name: 123, id: 123 },
        { name: 124, id: 124 },
        { name: 125, id: 125 },
        { name: 126, id: 126 },
        { name: 127, id: 127 },
        { name: 128, id: 128 },
        { name: 129, id: 129 },

        { name: 130, id: 130 },
        { name: 131, id: 131 },
        { name: 132, id: 132 },
        { name: 133, id: 133 },
        { name: 134, id: 134 },
        { name: 135, id: 135 },
        { name: 136, id: 136 },
        { name: 137, id: 137 },
        { name: 138, id: 138 },
        { name: 139, id: 139 },

        { name: 140, id: 140 },
        { name: 141, id: 141 },
        { name: 142, id: 142 },
        { name: 143, id: 143 },
        { name: 144, id: 144 },
        { name: 145, id: 145 },
        { name: 146, id: 146 },
        { name: 147, id: 147 },
        { name: 148, id: 148 },
        { name: 149, id: 149 },

        { name: 150, id: 150 },
        { name: 151, id: 151 },
        { name: 152, id: 152 },
        { name: 153, id: 153 },
        { name: 154, id: 154 },
        { name: 155, id: 155 },
        { name: 156, id: 156 },
        { name: 157, id: 157 },
        { name: 158, id: 158 },
        { name: 159, id: 159 },

        { name: 160, id: 160 },
        { name: 161, id: 161 },
        { name: 162, id: 162 },
        { name: 163, id: 163 },
        { name: 164, id: 164 },
        { name: 165, id: 165 },
        { name: 166, id: 166 },
        { name: 167, id: 167 },
        { name: 168, id: 168 },
        { name: 169, id: 169 }];

    //rootone: <select data-ng-options="o.name for o in rootoneOptions" data-ng-model="selectedRootOneOption"></select> <br />

    //$scope.selectedGenreOption = $scope.genreOptions[0];
    $scope.selectedComplexityOption = $scope.complexityOptions[0];
    $scope.selectedMoodLightOption = $scope.moodlightOptions[0];
    $scope.selectedMoodDarkOption = $scope.mooddarkOptions[0];
    $scope.selectedAudioLengthOption = $scope.audioLengthOptions[0];

    $scope.selectedBpmOption = $scope.bpmOptions[0];

    $scope.$on("fileSelected", function (event, args) {
        $scope.$apply(function () {
            ////console.log('uploading', args.file);
            $scope.files.push(args.file);
        });
    });

    var trackId = -1;
    $scope.recomendedBpm = -1;
    $scope.renderedSample = "";

    function checkForTrackCompletion() {

        $http.get('/api/UploadHarmonicLoops/' + trackId).success(function (data, status, headers, config) {

            if (data.OutputPath.indexOf("error") == -1) {
                //console.log("error", "NOTequal" + data.OutputPath);

                $scope.renderedSample = data.OutputPath;
                //inProgress = false;
                //$scope.showPlayBtn = true;
            } else {
                //console.log("error", "ISequal");
                $scope.audioTrackPath = Date.now();
                setTimeout(checkForTrackCompletion, 1000);
            }
        });
    }

    $scope.updateWavView = function () {

        var bpm = $scope.selectedBpmOption.id;
        //$scope.$broadcast('playAudioLoop', bpm); 
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

            //console.log('char', value);

            if (value.checked) {
                //console.log('checked char id ', value.Id);
                characteristicsArray.push(value.Id);
            }
        });

        return characteristicsArray;
    }

    var createInstrumentList = function () {

        var instrumentArray = [];

        angular.forEach($scope.instruments, function (value, key) {

            //console.log('char', value);

            if (value.checked) {
                //console.log('checked char id ', value.Id);
                instrumentArray.push(value.Id);
            }
        });

        return instrumentArray;
    }

    $scope.save = function () {

        if($scope.selectedstationid === 999){
            alert('SELECT STATION ID !!!')
            return;
        }

        console.log("createInstrumentList()", createInstrumentList());

        $scope.model = {
            stationid: $scope.selectedstationid,
            saveToDatabase: true,
            genres: createGenreList(),
            characteristics: createCharacteristicsList(),
            instruments: createInstrumentList(),
            complexity: $scope.selectedComplexityOption.id,
            moodDark: $scope.selectedMoodDarkOption.id,
            moodLight: $scope.selectedMoodLightOption.id,
            audioLength: $scope.selectedAudioLengthOption.id,

            chordRoots :  selectedChordsIds,
            bpm: $scope.selectedBpmOption.id,
            rhythmicAccents: $scope.accentArray
        };

        $http({
            method: 'POST',
            url: "/api/UploadHarmonicLoops/PostStuff",
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

        }).success(function (data, status, headers, config) {

            if (data && (data.StatusCode != 500)) {

                var response = data.replace(/['"]+/g, '');
                response = response.split("*");

                trackId = response[0];
                $scope.recomendedBpm = response[1];
                $window.location.reload();
                //checkForTrackCompletion();
            } else {
                alert("Sorry, Encountered an error generating a harmonic progression");
            }
        }).
        error(function (data, status, headers, config) {
            alert("failed!");
        });
    };

    $scope.test = function () {
        var bpm = $scope.selectedBpmOption.id;
        $scope.$broadcast('playAudioLoop', bpm);
    }

    $scope.stopAudioLoop = function () {
        $scope.$broadcast('stopAudioLoop', true);
    }

    function uploadProgress(evt) {
        $scope.$apply(function () {
            if (evt.lengthComputable) {
                $scope.progress = Math.round(evt.loaded * 100 / evt.total)
            } else {
                $scope.progress = 'unable to compute'
            }
        })
    }

    $scope.handleFileSelected = function (e) {

        var file = e.files[0];
        var filename = file.name;

        $scope.$broadcast('handleFileSelected', filename);
    }

    //$scope.selectedRootOneOption = 

    var updateSelectedRoots = function () {

        angular.forEach($scope.rootOneOptions, function (value, key) {
            if (value.checked) {
                //console.log('updateSelectedRoots 1', value.Chord);
                $scope.selectedRootOneOption = value.Chord;
            }
        });

        angular.forEach($scope.rootTwoOptions, function (value, key) {
            if (value.checked) {
                //console.log('updateSelectedRoots 2', value.Chord);
                $scope.selectedRootTwoOption = value.Chord;
            }
        });

        angular.forEach($scope.rootThreeOptions, function (value, key) {
            if (value.checked) {
                //console.log('updateSelectedRoots 3', value.Chord);
                $scope.selectedRootThreeOption = value.Chord;
            }
        });

        angular.forEach($scope.rootFourOptions, function (value, key) {
            if (value.checked) {
                //console.log('updateSelectedRoots 4', value.Chord);
                $scope.selectedRootFourOption = value.Chord;
            }
        });
    }

    var selectedChordsIds = [];

    $scope.lockSelections = false;

    $scope.handleChordSelected = function () {

        if ($scope.lockSelections) {
            $scope.selectedRootTwoOption.Chord = $scope.selectedRootOneOption.Chord;
            $scope.selectedRootThreeOption.Chord = $scope.selectedRootOneOption.Chord;
            $scope.selectedRootFourOption.Chord = $scope.selectedRootOneOption.Chord;
            console.log('switching chord',$scope.selectedRootOneOption.Chord)
        }

        updateSelectedRoots();

        var selectedChords = [];

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

    // RYTHM TEMPLATE .... 
    $scope.numberOfAccentBarOptions = [1, 2, 4];
    $scope.selectedNumberOfAccentBars = 1;
    $scope.updateNumberOfAccentBars = function () {
        switch ($scope.selectedNumberOfAccentBars) {
            case 1:
                $scope.accentArray = [false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false];
                break;
            case 2:
                $scope.accentArray = [false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false];
                break;
            case 4:
                $scope.accentArray = [false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false];
                break;
        }
    }

    $scope.accentArray = [false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false];

    $scope.selectAccentCell = function (index) {
        $scope.accentArray[index] = !$scope.accentArray[index];
    }
    // END RYTHM TEMPLATE .... 

}]);