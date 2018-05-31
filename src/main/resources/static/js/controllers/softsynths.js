
angular.module('myApp').controller('SoftSynthsCtrl', ['$scope', '$location', '$window', '$http', function ($scope, $location, $window, $http) {

    $scope.$root.title = 'AngularJS SPA | SoftSynth';

    $scope.files = [];

    $scope.synthName = "";
    $scope.synthFilePath = "";

    $scope.sixtyFourBitOptions = [{ name: "64bit", id: 1 }, { name: "32bit", id: 0 }];
     
    $scope.sixtyFourBitSelection = $scope.sixtyFourBitOptions[0];
      
    $scope.$on("fileSelected", function (event, args) {
        $scope.$apply(function () {
            console.log('uploading', args.file);
            $scope.files.push(args.file);
        });
    });
     
    $scope.save = function () {

        $scope.model = {
            synthName: $scope.synthName,
            synthFilePath: $scope.synthFilePath, 
            sixtyFourBit: $scope.sixtyFourBitSelection.id
        };

        $http({
            method: 'POST',
            url: "/api/SoftSynths/PostStuff",
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