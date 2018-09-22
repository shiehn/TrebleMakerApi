angular.module('myApp').controller('HomeCtrl', ['$scope', '$location', '$window', '$http', 'CONST', function ($scope, $location, $window, $http, CONST) {

    var inProgress = false;
    var trackId = "";

    $scope.buttonLabel = "Generate Audio";
    $scope.audioTrackPath = "";
    $scope.showPlayBtn = false;
    $scope.trainButtonLabel = "Train Hive Compose";
    $scope.audioTrackPath = "";
    $scope.showPlayBtn = false;
    $scope.feedBackModel = [];
    $scope.analytics = "analytics stuff 2"
    $scope.selectedMelody = 0;

    var setAnalyticsFeedback = function(compositionId){
    
        $http.get(CONST.DOMAIN + '/api/Analytics/' + compositionId).success(function (data, status, headers, config) {
            console.log(data);
            $scope.feedBackModel = data;
            $scope.startStopMode = true;
        });
    }

    $scope.showHitRatingBtns = true;
    $scope.showFillRatingBtns = true;

    $scope.setHitRatingAnalytics = function(hitrating){
        console.log('HIT_RATING_ENPOINT= ' + CONST.DOMAIN + '/api/analytics/hitrating/' + trackId + '/' + hitrating + '/');
        $http.get(CONST.DOMAIN + '/api/analytics/hitrating/' + trackId + '/' + hitrating + '/').success(function (data, status, headers, config) {
            console.log('hitRatingResponse : ' + data);
            $scope.showHitRatingBtns = false;
        }).error(function (data) {
            console.log('data error', data);
        });
    }

    $scope.setFillRatingAnalytics = function(fillrating){
        console.log('FILL_RATING_ENDPOINT = ' + CONST.DOMAIN + '/api/analytics/fillrating/' + trackId.value.replace('.wav', '') + '/' + fillrating + '/');
        $http.get(CONST.DOMAIN + '/api/analytics/fillrating/' + trackId + '/' + fillrating + '/').success(function (data, status, headers, config) {
            console.log('fillRatingResponse : ' + data);
            $scope.showFillRatingBtns = false;
        }).error(function (data) {
            console.log('data error', data);
        });
    }

    var getTimeslotAnalytics = function(compositionId){
                $http.get(CONST.DOMAIN + '/api/Analytics/timeslots/' + compositionId).then(function successCallback(data) {
                    $scope.analytics = data.data;
                }, function errorCallback(data) {
                    console.log('ERROR data',data.data.value);
                });
    }

    //TESTING
    //setAnalyticsFeedback('90eadf17-35ab-47b8-80b6-6287995d3ed3');

    function getCompletedTrack(data, melodyVersion){

        var fullTrackId = "";
        if(melodyVersion == 1){
            fullTrackId = CONST.HIVE_COMPOSE_SERVER + "/tmpaudio/" + data.compositionUid + "_0_1.mp3"
        }else if(melodyVersion == 2){
            fullTrackId = CONST.HIVE_COMPOSE_SERVER + "/tmpaudio/" + data.compositionUid + "_0_1_alt_melody.mp3"
        }else{
            console.log("ERROR UNKNOW MELODY VERSION", melodyVersion)
        }

        console.log("getCompletedTrack", fullTrackId);
        $scope.audioTrackPath = fullTrackId;
        $scope.showPlayBtn = true;

        setAnalyticsFeedback(data.compositionUid);

        console.log('about to broadcast : loadsimpleplayer');
        console.log('about to broadcast with : ' + fullTrackId);

        $scope.$broadcast('loadsimpleplayer', fullTrackId);

        inProgress = false;
    }

    function checkForTrackCompletion() {

        console.log('CONST.DOMAIN', CONST.DOMAIN);

        $http.get(CONST.DOMAIN + '/api/Progression/' + trackId).then(function successCallback(data) {

            console.log('/api/Progression/ get data', data)

            if (data.data.value.indexOf("error") == -1) {

                //WAIT A FEW SECONDS BEFORE LOADING SONG ..
                var millisecondsToWait = 8000;
                setTimeout(function () {
                    $scope.audioTrackPath = CONST.HIVE_COMPOSE_SERVER + "/audio/" + data.data.value;
                    $scope.showPlayBtn = true;
                    setAnalyticsFeedback(data.data.value.replace("_0_1.mp3", ""));
                    $scope.$broadcast('loadsimpleplayer', CONST.HIVE_COMPOSE_SERVER + "/audio/" + data.data.value);

                    inProgress = false;
                }, millisecondsToWait);

            } else {
                $scope.audioTrackPath = Date.now();
                setTimeout(checkForTrackCompletion, 1000);
            }
        }, function errorCallback(data) {
            console.log('ERROR data',data.data.value);
        });
    }

    var composeChordProgression = function () {

        $http.post(CONST.DOMAIN + '/api/Progression/?testid=-1').success(function (data) {
            if (data && (data.StatusCode != 500)) {
                console.log('/api/Progression/ data=', data);
                trackId = data;
                checkForTrackCompletion();
                console.log("checkForTrackCompletion() complete");
            } else {
                console.log("ERROR DATA", data);
                alert("Sorry, Encountered an error generating a harmonic progression");
            }
        }).error(function (data) {
            console.log('data error', data);
        });
    }

    var trainHiveCompose = function (melodyVersion) {
            $http.get(CONST.DOMAIN + '/api/CompositionStatus/findUnRated').success(function (data) {
                if (data && (data.StatusCode != 500)) {
                    if(melodyVersion === 1){
                        console.log('/api/CompositionStatus/findUnRated/ data=', data.compositionUid + "_0_1.mp3");
                    }else if(melodyVersion === 2){
                        console.log('/api/CompositionStatus/findUnRated/ data=', data.compositionUid + "_0_1_alt_melody.mp3");
                    }
                    trackId = data.compositionUid;
                    getTimeslotAnalytics(trackId);
                    getCompletedTrack(data, melodyVersion);
                } else {
                    console.log("ERROR DATA", data.value);
                    alert("Sorry, Encountered an error generating a harmonic progression");
                }
            }).error(function (data) {
                console.log('data error', data);
            });
        }

    $scope.setStateGood = function (timeSlot) {
        timeSlot.Rating = 2;
    }
     
    $scope.setStateOk = function (timeSlot) {
        timeSlot.Rating = 1;
    }

    $scope.setStateBad = function (timeSlot) {
        timeSlot.Rating = 0;
    }

    $scope.setEqStateGood = function (timeSlot) {
        timeSlot.EqRating = 2;
    }

    $scope.setEqStateOk = function (timeSlot) {
        timeSlot.EqRating = 1;
    }

    $scope.setEqStateBad = function (timeSlot) {
        timeSlot.EqRating = 0;
    }

    //LEVELS

    $scope.setLevelsStateGood = function (timeSlot) {
        timeSlot.LevelsRating = 2;
    }

    $scope.setLevelsStateOk = function (timeSlot) {
        timeSlot.LevelsRating = 1;
    }

    $scope.setLevelsStateBad = function (timeSlot) {
        timeSlot.LevelsRating = 0;
    }

    //PANNING

    $scope.setPanningStateGood = function (timeSlot) {
        timeSlot.PanningRating = 2;
    }

    $scope.setPanningStateOk = function (timeSlot) {
        timeSlot.PanningRating = 1;
    }

    $scope.setPanningStateBad = function (timeSlot) {
        timeSlot.PanningRating = 0;
    }



    $scope.setArpeggioStateGood = function (timeSlot) {
        timeSlot.ArpeggioRating = 2;
    }

    $scope.setArpeggioStateOk = function (timeSlot) {
        timeSlot.ArpeggioRating = 1;
    }

    $scope.setArpeggioStateBad = function (timeSlot) {
        timeSlot.ArpeggioRating = 0;
    }

    //COMP

        $scope.setCompStateGood = function (timeSlot) {
            timeSlot.CompRating = 2;
        }

        $scope.setCompStateOk = function (timeSlot) {
            timeSlot.CompRating = 1;
        }

        $scope.setCompStateBad = function (timeSlot) {
            timeSlot.CompRating = 0;
        }


    $scope.setBasslineStateGood = function (timeSlot) {
        timeSlot.BasslineRating = 2;
    }

    $scope.setBasslineStateOk = function (timeSlot) {
        timeSlot.BasslineRating = 1;
    }

    $scope.setBasslineStateBad = function (timeSlot) {
        timeSlot.BasslineRating = 0;
    }

        $scope.setKickStateGood = function (timeSlot) {
            timeSlot.KickRating = 2;
        }

        $scope.setKickStateOk = function (timeSlot) {
            timeSlot.KickRating = 1;
        }

        $scope.setKickStateBad = function (timeSlot) {
            timeSlot.KickRating = 0;
        }

        $scope.setHatStateGood = function (timeSlot) {
            timeSlot.HatRating = 2;
        }

        $scope.setHatStateOk = function (timeSlot) {
            timeSlot.HatRating = 1;
        }

        $scope.setHatStateBad = function (timeSlot) {
            timeSlot.HatRating = 0;
        }

        $scope.setSnareStateGood = function (timeSlot) {
            timeSlot.SnareRating = 2;
        }

        $scope.setSnareStateOk = function (timeSlot) {
            timeSlot.SnareRating = 1;
        }

        $scope.setSnareStateBad = function (timeSlot) {
            timeSlot.SnareRating = 0;
        }
     
    $scope.composeButtonClick = function () {
        if (!inProgress) {
            inProgress = true;
            $scope.buttonLabel = "hold on .."
            composeChordProgression();
        }
    }

     $scope.trainButtonClick = function (melodyVersion) {

            $scope.selectedMelody = melodyVersion;

            console.log("trainButtonClick");
            console.log("TRAIN WITH MELODY", $scope.selectedMelody)

             if (!inProgress) {
                 inProgress = true;
                 $scope.buttonLabel = "hold on .."
                 $scope.trainButtonLabel = "fetching audio .."
                 trainHiveCompose(melodyVersion);
             }
         }

    $scope.play = function () {

        console.log("inProgress state = " + inProgress);

        if (!inProgress) {
            $scope.$broadcast('simpleplayerplay');
        }
    };

    $scope.stop = function () {
        if (!inProgress) {
            $scope.$broadcast('simpleplayerstop');
        }
    };

    $scope.startStopMode = true;

    $scope.setHorizontalGroup = function(timeSlot){

        if($scope.startStopMode){

            console.log('$scope.startStopMode',$scope.startStopMode);
            timeSlot.HorizontalStart = 1;
            timeSlot.HorizontalStop = 0;
            $scope.startStopMode = false;
        }else{

            //STOPMODE
            console.log('$scope.startStopMode',$scope.startStopMode);
            timeSlot.HorizontalStart = 0;
            timeSlot.HorizontalStop = 1;
            $scope.startStopMode = true;

            reDrawGroups();
        }
    };

    function generateUUID() {
        var d = new Date().getTime();
        var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = (d + Math.random()*16)%16 | 0;
            d = Math.floor(d/16);
            return (c=='x' ? r : (r&0x3|0x8)).toString(16);
        });
        return uuid;
    };

    var reDrawGroups = function(){

        var groupOn = false;
        var horizontalId = "";

        for (var i = 0; i < $scope.feedBackModel.length; i++) {

            if($scope.feedBackModel[i].HorizontalId == undefined || $scope.feedBackModel[i].HorizontalId == "") {

                $scope.feedBackModel[i].HorizontalRating = -1;

                if ($scope.feedBackModel[i].HorizontalStart == 1) {

                    horizontalId = generateUUID();
                    groupOn = true;
                } else if ($scope.feedBackModel[i].HorizontalStop == 1) {

                    groupOn = false;
                }

                $scope.feedBackModel[i].HorizontalId = angular.copy(horizontalId);

                if(!groupOn){
                    horizontalId = "";
                }

                console.log('groupOn',groupOn);

                if(groupOn){
                    $scope.feedBackModel[i].InGroup = 1;
                }else{
                    $scope.feedBackModel[i].InGroup = 0;
                }
            }
        }
    }

    $scope.setHorizontalRatingGood = function(timeSlot){

        if(timeSlot.HorizontalId == undefined || timeSlot.HorizontalId == ""){
            return;
        }

        for (var i = 0; i < $scope.feedBackModel.length; i++) {
            if($scope.feedBackModel[i].HorizontalId == timeSlot.HorizontalId){
                $scope.feedBackModel[i].HorizontalRating = 2;
            }
        }
    }

    $scope.setHorizontalRatingOk = function(timeSlot){

        if(timeSlot.HorizontalId == undefined || timeSlot.HorizontalId == ""){
            return;
        }

        for (var i = 0; i < $scope.feedBackModel.length; i++) {
            if($scope.feedBackModel[i].HorizontalId == timeSlot.HorizontalId){
                $scope.feedBackModel[i].HorizontalRating = 1;
            }
        }
    }

    $scope.setHorizontalRatingBad = function(timeSlot){

        if(timeSlot.HorizontalId == undefined || timeSlot.HorizontalId == ""){
            return;
        }

        for (var i = 0; i < $scope.feedBackModel.length; i++) {
            if($scope.feedBackModel[i].HorizontalId == timeSlot.HorizontalId){
                $scope.feedBackModel[i].HorizontalRating = 0;
            }
        }
    }

    //CATEGORY STUFF
    //CATEGORY STUFF
    //CATEGORY STUFF

    $scope.setCategoryHarmonic = function(timeSlot){

        if(timeSlot.HorizontalId == undefined || timeSlot.HorizontalId == ""){
            return;
        }

        for (var i = 0; i < $scope.feedBackModel.length; i++) {
            if($scope.feedBackModel[i].HorizontalId == timeSlot.HorizontalId){
                $scope.feedBackModel[i].HorizontalCategoryHarmonic = ($scope.feedBackModel[i].HorizontalCategoryHarmonic == 1) ? 0 : 1;

            }
        }
    }

    $scope.setCategoryRhythmic = function(timeSlot){

        if(timeSlot.HorizontalId == undefined || timeSlot.HorizontalId == ""){
            return;
        }

        for (var i = 0; i < $scope.feedBackModel.length; i++) {
            if($scope.feedBackModel[i].HorizontalId == timeSlot.HorizontalId){
                $scope.feedBackModel[i].HorizontalCategoryRhythmic = ($scope.feedBackModel[i].HorizontalCategoryRhythmic == 1) ? 0 : 1;
            }
        }
    }

    $scope.setCategoryAmbience = function(timeSlot){

        if(timeSlot.HorizontalId == undefined || timeSlot.HorizontalId == ""){
            return;
        }

        for (var i = 0; i < $scope.feedBackModel.length; i++) {
            if($scope.feedBackModel[i].HorizontalId == timeSlot.HorizontalId){
                $scope.feedBackModel[i].HorizontalCategoryAmbience = ($scope.feedBackModel[i].HorizontalCategoryAmbience == 1) ? 0 : 1;
            }
        }
    }

    $scope.setCategoryFill = function(timeSlot){

        if(timeSlot.HorizontalId == undefined || timeSlot.HorizontalId == ""){
            return;
        }

        for (var i = 0; i < $scope.feedBackModel.length; i++) {
            if($scope.feedBackModel[i].HorizontalId == timeSlot.HorizontalId){
                $scope.feedBackModel[i].HorizontalCategoryFill = ($scope.feedBackModel[i].HorizontalCategoryFill == 1) ? 0 : 1;
            }
        }
    }


    /*
    $scope.$root.title = 'AngularJS SPA Template for Visual Studio';
    $scope.$on('$viewContentLoaded', function () {
        $window.ga('send', 'pageview', { 'page': $location.path(), 'title': $scope.$root.title });
    });
*/

    $scope.addToStation = function(){
        $http.get(CONST.DOMAIN + '/api/analytics/addtostation/' + trackId).success(function (data, status, headers, config) {
            console.log(data);
        });
    }

    $scope.clearAll = function(){

        console.log('SCOPE', $scope);

        for (var i = 0; i < $scope.feedBackModel.length; i++) {
            $scope.feedBackModel[i].HorizontalCategoryAmbience = 0;
            $scope.feedBackModel[i].HorizontalCategoryFill = 0;
            $scope.feedBackModel[i].HorizontalCategoryHarmonic = 0;
            $scope.feedBackModel[i].HorizontalCategoryRhythmic = 0;
            $scope.feedBackModel[i].HorizontalId = 0;
            $scope.feedBackModel[i].HorizontalRating = -1;
            $scope.feedBackModel[i].HorizontalStart = false;
            $scope.feedBackModel[i].HorizontalStop = false;
            $scope.feedBackModel[i].InGroup = false;
            $scope.feedBackModel[i].Rating = 1;
            $scope.feedBackModel[i].EqRating = 1;
            $scope.feedBackModel[i].horizontalRating = -1;
            $scope.feedBackModel[i].horizontalStart = false;
            $scope.feedBackModel[i].horizontalStop = false;
            $scope.feedBackModel[i].inGroup = false;
            $scope.feedBackModel[i].selectedMelody = 0;
        }
    }

    $scope.setRow = function(row, value){
        for (var i = 0; i < $scope.feedBackModel.length; i++) {

            if(row === 'Rating'){
                $scope.feedBackModel[i].Rating = value;
            }
            if(row === 'EqRating'){
                $scope.feedBackModel[i].EqRating = value;
            }
            if(row === 'LevelsRating'){
                $scope.feedBackModel[i].LevelsRating = value;
            }
            if(row === 'PanningRating'){
                $scope.feedBackModel[i].PanningRating = value;
            }
            if(row === 'ArpeggioRating'){
                $scope.feedBackModel[i].ArpeggioRating = value;
            }
            if(row === 'CompRating'){
                $scope.feedBackModel[i].CompRating = value;
            }
            if(row === 'BasslineRating'){
                $scope.feedBackModel[i].BasslineRating = value;
            }
            if(row === 'KickRating'){
                $scope.feedBackModel[i].KickRating = value;
            }
            if(row === 'HatRating'){
                $scope.feedBackModel[i].HatRating = value;
            }
            if(row === 'SnareRating'){
                $scope.feedBackModel[i].SnareRating = value;
            }
        }
    }


    $scope.save = function () {

        var timeSlots = [];

        for (var i = 0; i < $scope.feedBackModel.length; i++) {

            var timeSlot = {
                "HorizontalCategoryHarmonic":Boolean($scope.feedBackModel[i].HorizontalCategoryHarmonic),
                "HorizontalCategoryAmbience":Boolean($scope.feedBackModel[i].HorizontalCategoryAmbience),
                "HorizontalCategoryRhythmic":Boolean($scope.feedBackModel[i].HorizontalCategoryRhythmic),
                "HorizontalCategoryFill":Boolean($scope.feedBackModel[i].HorizontalCategoryFill),
                "Id" : $scope.feedBackModel[i].Id,
                "Rating":$scope.feedBackModel[i].Rating,
                "EqRating":$scope.feedBackModel[i].EqRating,
                "LevelsRating":$scope.feedBackModel[i].LevelsRating,
                "PanningRating":$scope.feedBackModel[i].PanningRating,
                "ArpeggioRating":$scope.feedBackModel[i].ArpeggioRating,
                "CompRating":$scope.feedBackModel[i].CompRating,
                "BasslineRating":$scope.feedBackModel[i].BasslineRating,
                "KickRating":$scope.feedBackModel[i].KickRating,
                "HatRating":$scope.feedBackModel[i].HatRating,
                "SnareRating":$scope.feedBackModel[i].SnareRating,
                "StartTime":$scope.feedBackModel[i].StartTime,
                "HorizontalStart":Boolean($scope.feedBackModel[i].HorizontalStart),
                "HorizontalStop":Boolean($scope.feedBackModel[i].HorizontalStop),
                "HorizontalRating":$scope.feedBackModel[i].HorizontalRating,
                "HorizontalId":$scope.feedBackModel[i].HorizontalId,
                "InGroup":$scope.feedBackModel[i].InGroup,
                "selectedMelody": $scope.selectedMelody
            };

            timeSlots.push(timeSlot);
        }


        console.log("POST PAYLOAD:", timeSlots);


        $http({
            method: 'POST',
            url: '/api/analytics',
            data: timeSlots,
            headers: {
                'Content-Type': 'application/json'
            }
        }).
        success(function (data, status, headers, config) {
            alert("success!");
            $window.location.reload();
        }).
        error(function (data, status, headers, config) {
            alert("failed!");
        });

    };

}]);