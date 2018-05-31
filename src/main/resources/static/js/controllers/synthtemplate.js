
angular.module('myApp').controller('SynthTemplateCtrl', ['$scope', '$location', '$window', '$http', function ($scope, $location, $window, $http) {

    $scope.MoodDarkLevelOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 }];
    $scope.MoodLightLevelOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 }];

    $scope.complexityOptions = [{ name: 1, id: 1 }, { name: 2, id: 2 }, { name: 3, id: 3 }, { name: 4, id: 4 }, { name: 5, id: 5 }, { name: 6, id: 6 }, { name: 7, id: 7 }, { name: 8, id: 8 }, { name: 9, id: 9 }, { name: 10, id: 10 }];

    $scope.HiQuarterNoteCompatibilityOptions = [{ name: 0, id: 0 }, { name: 1, id: 1 }];
    $scope.HiEigthNoteCompatibilityOptions = [{ name: 0, id: 0 }, { name: 1, id: 1 }];
    $scope.HiSixteenthNoteCompatibilityOptions = [{ name: 0, id: 0 }, { name: 1, id: 1 }];

    $scope.HiQuarterNoteCompatibilityAltOptions = [{ name: 0, id: 0 }, { name: 1, id: 1 }];
    $scope.HiEigthNoteCompatibilityAltOptions = [{ name: 0, id: 0 }, { name: 1, id: 1 }];
    $scope.HiSixteenthNoteCompatibilityAltOptions = [{ name: 0, id: 0 }, { name: 1, id: 1 }];

    $scope.selectedMoodDarkLevelOptions = $scope.MoodDarkLevelOptions[0];
    $scope.selectedMoodLightLevelOptions = $scope.MoodLightLevelOptions[0];
    $scope.selectedComplexityOption = $scope.complexityOptions[0];
     
    $scope.selectedHiQuarterNoteCompatibilityOptions = $scope.HiQuarterNoteCompatibilityOptions[0];
    $scope.selectedHiEigthNoteCompatibilityOptions = $scope.HiEigthNoteCompatibilityOptions[0];
    $scope.selectedHiSixteenthNoteCompatibilityOptions = $scope.HiSixteenthNoteCompatibilityOptions[0];

    $scope.selectedHiQuarterNoteCompatibilityAltOptions = $scope.HiQuarterNoteCompatibilityAltOptions[0];
    $scope.selectedHiEigthNoteCompatibilityAltOptions = $scope.HiEigthNoteCompatibilityAltOptions[0];
    $scope.selectedHiSixteenthNoteCompatibilityAltOptions = $scope.HiSixteenthNoteCompatibilityAltOptions[0];

    $scope.savePayload = {}; 
     
    $http.get('/api/softsynths/get').success(function (data, status, headers, config) {

        $scope.hiSynthsOptions = [];
        $scope.hiSynthsAltOptions = [];
        $scope.midSynthsOptions = [];
        $scope.midSynthsAltOptions = [];
        $scope.lowSynthsOptions = [];
        $scope.lowSynthsAltOptions = [];

        angular.forEach(data, function (synthId, synthName) {

            $scope.hiSynthsOptions.push({ name: synthName, id: synthId });
            $scope.hiSynthsAltOptions.push({ name: synthName, id: synthId });
            $scope.midSynthsOptions.push({ name: synthName, id: synthId });
            $scope.midSynthsAltOptions.push({ name: synthName, id: synthId });
            $scope.lowSynthsOptions.push({ name: synthName, id: synthId });
            $scope.lowSynthsAltOptions.push({ name: synthName, id: synthId });

        }, this);

        $scope.hiSynthsSelection = $scope.hiSynthsOptions[0];
        $scope.hiSynthsAltSelection = $scope.hiSynthsAltOptions[0];
        $scope.midSynthsSelection = $scope.midSynthsOptions[0];
        $scope.midSynthsAltSelection = $scope.midSynthsAltOptions[0];
        $scope.lowSynthsSelection = $scope.lowSynthsOptions[0];
        $scope.lowSynthsAltSelection = $scope.lowSynthsAltOptions[0];
         
    }).error(function (data, status, headers, config) {
        // log error
    });

    $scope.hiPreset = "";
    $scope.hiPresetAlt = "";
    $scope.midPreset = "";
    $scope.midPresetAlt = "";
    $scope.lowPreset = "";
    $scope.lowPresetAlt = "";

    var trackId = "";
    var templateid = 0;

    function checkForTrackCompletion() {

        console.log('ENTERED heckForTrackCompletion();');

        $http.get('/api/Progression/' + trackId).success(function (data, status, headers, config) {

            console.log('data.OutputPath', data.OutputPath);

            if (data.OutputPath.indexOf("error") == -1) {
                console.log("error", "NOTequal" + data.OutputPath);
                $scope.audioTrackPath = data.OutputPath;
                
            } else {
                console.log("error", "ISequal");
                $scope.audioTrackPath = Date.now();
                setTimeout(checkForTrackCompletion, 1000);
            }
        });
    }

    var composeChordProgression = function (testid) {

        $http.post('/api/Progression/?testid=' + testid).success(function (data, status, headers, config) {
            console.log('data', data);

            if (data) {
                trackId = data;
                checkForTrackCompletion();
                console.log('checkForTrackCompletion();');
            }

            // this callback will be called asynchronously
            // when the response is available
        }).error(function (data, status, headers, config) {
            console.log('data error', data);
            // called asynchronously if an error occurs
            // or server returns response with an error status.
        });
    }
     
    $scope.save = function () {

        $scope.savePayload = {

            "MoodDarkLevel": $scope.selectedMoodDarkLevelOptions.id,
            "MoodLightLevel": $scope.selectedMoodLightLevelOptions.id,
            "Complexity": $scope.selectedComplexityOption.id,

            "HiSynthId": $scope.hiSynthsSelection.id,
            "HiPreset": $scope.hiPreset.replace(" ", "%20"),
            "HiSynthName": $scope.hiSynthsSelection.name,

            "HiSynthIdAlt": $scope.hiSynthsAltSelection.id,
            "HiPresetAlt": $scope.hiPresetAlt.replace(" ", "%20"),
            "HiSynthNameAlt": $scope.hiSynthsAltSelection.name,

            "MidSynthId": $scope.midSynthsSelection.id,
            "MidPreset": $scope.midPreset.replace(" ", "%20"),
            "MidSynthName": $scope.midSynthsSelection.name,

            "MidSynthIdAlt": $scope.midSynthsAltSelection.id,
            "MidPresetAlt": $scope.midPresetAlt.replace(" ", "%20"),
            "MidSynthNameAlt": $scope.midSynthsAltSelection.name,

            "LowSynthId": $scope.lowSynthsSelection.id,
            "LowPreset": $scope.lowPreset.replace(" ", "%20"),
            "LowSynthName": $scope.lowSynthsSelection.name,

            "LowSynthIdAlt": $scope.lowSynthsAltSelection.id,
            "LowPresetAlt": $scope.lowPresetAlt.replace(" ", "%20"),
            "LowSynthNameAlt": $scope.lowSynthsAltSelection.name,

            "HiQuarterNoteCompatibility": $scope.selectedHiQuarterNoteCompatibilityOptions.id,
            "HiEigthNoteCompatibility": $scope.selectedHiEigthNoteCompatibilityOptions.id,
            "HiSixteenthNoteCompatibility": $scope.selectedHiSixteenthNoteCompatibilityOptions.id,

            "HiQuarterNoteCompatibilityAlt": $scope.selectedHiQuarterNoteCompatibilityAltOptions.id,
            "HiEigthNoteCompatibilityAlt": $scope.selectedHiEigthNoteCompatibilityAltOptions.id,
            "HiSixteenthNoteCompatibilityAlt": $scope.selectedHiSixteenthNoteCompatibilityAltOptions.id 
        };

        console.log('post payload', $scope.savePayload);

        $http({
            url: '/api/SynthTemplate/post',
            method: "POST",
            data: JSON.stringify($scope.savePayload),
            headers: { 'Content-Type': 'application/json' }
        }).success(function (data, status, headers, config) {

            alert('COMPLETE!!');
            $window.location.reload();

            //alert('success');
        }).error(function (data, status, headers, config) {
            alert('ERROR');
        });
    }



    $scope.$root.title = 'AngularJS SPA | SynthTemplate';
}]);


