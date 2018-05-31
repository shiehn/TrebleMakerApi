
angular.module('myApp').controller('BeatLoopsCtrl', ['$scope', '$location', '$window', '$http', 'CONST', function ($scope, $location, $window, $http, CONST) {

    $scope.$root.title = 'AngularJS SPA | About';
    //$scope.$on('$viewContentLoaded', function () {
    //    $window.ga('send', 'pageview', { 'page': $location.path(), 'title': $scope.$root.title });
    //});

    $scope.files = [];

    $scope.selectedstationid = 999;
    $scope.stationids = [];
     
    $scope.complexityOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 }];
    $scope.moodlightOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 }];
    $scope.mooddarkOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 }];
    $scope.barCountOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 }];
    $scope.timeSignatureOptions = [{ name: "3/4", id: "3/4" }, { name: "4/4", id: "4/4" }];
    $scope.tempoOptions = [{ name: 70, id: 70 },
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
     
    $scope.selectedComplexityOption = $scope.complexityOptions[0];
    $scope.selectedMoodLightOption = $scope.moodlightOptions[0];
    $scope.selectedMoodDarkOption = $scope.mooddarkOptions[0];
    $scope.selectedTempoOption = $scope.tempoOptions[0];
    $scope.selectedBarcountOption = $scope.barCountOptions[0];
    $scope.selectedtimeSignatureOption = $scope.timeSignatureOptions[1];

    
    $scope.muteChordState = 'mute';
      
    $scope.$on("fileSelected", function (event, args) {
        $scope.$apply(function () { 
            console.log('uploading', args.file);
            $scope.files.push(args.file);
        });
    });

    $scope.genres = [];
    $scope.characteristics = [];
    $scope.instruments = [];

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
    $scope.selectedRootOneOption = -1;

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
    });

    var updateSelectedRoots = function () {
         
        angular.forEach($scope.rootOneOptions, function (value, key) {
            if (value.checked) { 
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
             
            if (value.checked) { 
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

    //TODO FIX THIS SHIT .. 
    $scope.rootChordId = 3;
     
    var selectedChordsIds = [];

    $scope.handleChordSelected = function () {
         
        updateSelectedRoots();
         
        var selectedChords = []; 

        selectedChords.push($scope.selectedRootOneOption.Chord);
        selectedChords.push($scope.selectedRootOneOption.Chord);
        selectedChords.push($scope.selectedRootOneOption.Chord);
        selectedChords.push($scope.selectedRootOneOption.Chord);

        selectedChordsIds = [];

        if (isInt($scope.selectedRootOneOption.Id)) {
            selectedChordsIds.push($scope.selectedRootOneOption.Id);
            selectedChordsIds.push($scope.selectedRootOneOption.Id);
            selectedChordsIds.push($scope.selectedRootOneOption.Id);
            selectedChordsIds.push($scope.selectedRootOneOption.Id);
        } 

        $scope.$broadcast('setBackingChords', selectedChords);
    }

    $scope.handleFileSelected = function (e) {

        var file = e.files[0];
        var filename = file.name;

        console.log('filename selected',filename);

        $scope.$broadcast('handleFileSelected', filename);
    }

    $scope.test = function () {
        var bpm = $scope.selectedTempoOption.id;
        $scope.$broadcast('playAudioLoop', bpm);
    }

    $scope.stopAudioLoop = function () {
        $scope.$broadcast('stopAudioLoop', true);
    }

    $scope.muteTriads = function () {
        if ($scope.muteChordState == 'mute') {
            $scope.muteChordState = 'unmute';
        } else {
            $scope.muteChordState = 'mute';
        }
         
        $scope.$broadcast('muteTriads');
        $scope.test();
    }
     
    $scope.save = function () {

        if($scope.selectedstationid === 999){
            alert('SELECT STATION ID !!!')
            return;
        }

        console.log('BEAT CONTROLLER');
         
        $scope.model = {
            stationid: $scope.selectedstationid,
            genres: createGenreList(),
            characteristics: createCharacteristicsList(),
            instruments: createInstrumentList(),
            rootChords: selectedChordsIds,
            complexity: $scope.selectedComplexityOption.id,
            moodLight: $scope.selectedMoodLightOption.id,
            moodDark: $scope.selectedMoodDarkOption.id,
            tempo: $scope.selectedTempoOption.id,
            barcount: $scope.selectedBarcountOption.id,
            timeSignature: $scope.selectedtimeSignatureOption.id,
            rhythmicAccents: $scope.accentArray 
        };
          
        $http({
            method: 'POST',
            url: "/api/UploadBeats/PostStuff",
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

    // RYTHM TEMPLATE .... 
    $scope.numberOfAccentBarOptions = [1,2,4];
    $scope.selectedNumberOfAccentBars = 1;
    $scope.updateNumberOfAccentBars = function () {
        switch ($scope.selectedNumberOfAccentBars) {
            case 1:
                $scope.accentArray = [false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false];
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