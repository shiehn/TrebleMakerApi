'use strict';

//angular.module('myApp.directives', [])

angular.module('myApp').directive('fileUpload', ['$rootScope', 'CONST', function ($rootScope, CONST) {
      return {
        scope: true,        //create a new scope
        link: function (scope, el, attrs) {
          el.bind('change', function (event) {
            var files = event.target.files;
            //iterate files since 'multiple' may be specified on the element
            for (var i = 0; i < files.length; i++) {
              //emit event upward
              scope.$emit("fileSelected", { file: files[i] });
            }
          });
        }
      };
    }]);

angular.module('myApp').directive('basicAudioPlayer', ['$rootScope', 'CONST', function ($rootScope, CONST) {

      return {
        restrict: 'EA', //E = element, A = attribute, C = class, M = comment
        scope: {
          title: '@'
        },
        template: '<div id="simpleplayer"></div>',

        controller: function ($scope, $attrs) {

          //$scope.$on('handleBroadcast', function() {
          //    console.log('RECIEVE_BROADCAST', mySharedService.message);
          //});
        },
        link: function ($scope) {

          var wavesurfer = null;

          var loadWaveSurfer = function (audioSample) {

            if (wavesurfer == null) {

              wavesurfer = Object.create(WaveSurfer);

              wavesurfer.init({
                container: document.querySelector('#simpleplayer'),
                wavecolor: 'violet',
                progresscolor: 'purple'
              });
            }

            var millisecondsToWait = 2000;
            setTimeout(function () {
              console.log("*LOADING*", audioSample);
              wavesurfer.load(audioSample);
            }, millisecondsToWait);

          };

          //var audioSample = "\\AudioUploadStaging\\a-major-blues-bass-loop-80-bpm_Myae9TNu.wav";

          $scope.$on('loadsimpleplayer', function (event, filePath) {

            //var fullPath = ""; //angular.copy(CONST.DOMAIN) + filePath;

            console.log("FILE LOADED IN PLAYER : " + filePath);

            loadWaveSurfer(filePath);
          });

          $scope.$on('simpleplayerplay', function (event) {
            wavesurfer.play();
          });

          $scope.$on('simpleplayerstop', function (event) {
            wavesurfer.stop();
          });
        }
      };
    }]);

angular.module('myApp').directive('myDomDirective', ['$rootScope', 'CONST', function ($rootScope, CONST) {
      return {
        restrict: 'EA', //E = element, A = attribute, C = class, M = comment
        scope: {
          //@ reads the attribute value, = provides two-way binding, & works with functions
          title: '@'
        },
        template: '<div id="wave"></div>',
        //templateUrl: 'mytemplate.html',
        controller: function ($scope, $attrs) {
          //$scope.$on('handleBroadcast', function() {
          //    console.log('RECIEVE_BROADCAST', mySharedService.message);
          //});
        },
        link: function ($scope, element, attrs) {

          var wavesurfer = null;

          var loadWaveSurfer = function (audioSample) {

            if (wavesurfer == null) {

              wavesurfer = Object.create(WaveSurfer);

              wavesurfer.init({
                container: document.querySelector('#wave'),
                wavecolor: 'violet',
                progresscolor: 'purple'
              });
            }

            wavesurfer.load(audioSample);
          }

          var milliSecondsInBar = function (bpm) {

            var secondsPerBeat = 60 / bpm;
            var milliSecondsPerBeat = secondsPerBeat * 1000;
            var milliSecondsInBar = milliSecondsPerBeat * 4;

            return milliSecondsInBar;
          }

          var numOfBarsInLoop = function (sampleLengthInMilliSeconds, bpm) {

            var barsInLoop = sampleLengthInMilliSeconds / milliSecondsInBar(bpm);

            return Math.round(barsInLoop);
          }

          var audioSourceBuffers = [];

          var addBeatDivisions = function (bpm) {

            var secondsPerBeat = 60 / bpm;

            wavesurfer.clearRegions()

            wavesurfer.addRegion({ 'id': 0, 'start': 0, 'end': 0.01, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
            wavesurfer.addRegion({ 'id': 1, 'start': secondsPerBeat * 1, 'end': secondsPerBeat * 1 + 0.025, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
            wavesurfer.addRegion({ 'id': 2, 'start': secondsPerBeat * 2, 'end': secondsPerBeat * 2 + 0.025, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
            wavesurfer.addRegion({ 'id': 3, 'start': secondsPerBeat * 3, 'end': secondsPerBeat * 3 + 0.025, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
            wavesurfer.addRegion({ 'id': 4, 'start': secondsPerBeat * 4, 'end': secondsPerBeat * 4 + 0.025, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
            wavesurfer.addRegion({ 'id': 5, 'start': secondsPerBeat * 5, 'end': secondsPerBeat * 5 + 0.025, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
            wavesurfer.addRegion({ 'id': 6, 'start': secondsPerBeat * 6, 'end': secondsPerBeat * 6 + 0.025, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
            wavesurfer.addRegion({ 'id': 7, 'start': secondsPerBeat * 7, 'end': secondsPerBeat * 7 + 0.025, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
            wavesurfer.addRegion({ 'id': 8, 'start': secondsPerBeat * 8, 'end': secondsPerBeat * 8 + 0.025, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
            wavesurfer.addRegion({ 'id': 9, 'start': secondsPerBeat * 9, 'end': secondsPerBeat * 9 + 0.025, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
            wavesurfer.addRegion({ 'id': 10, 'start': secondsPerBeat * 10, 'end': secondsPerBeat * 10 + 0.025, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
            wavesurfer.addRegion({ 'id': 11, 'start': secondsPerBeat * 11, 'end': secondsPerBeat * 11 + 0.025, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
            wavesurfer.addRegion({ 'id': 12, 'start': secondsPerBeat * 12, 'end': secondsPerBeat * 12 + 0.025, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
            wavesurfer.addRegion({ 'id': 13, 'start': secondsPerBeat * 13, 'end': secondsPerBeat * 13 + 0.025, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
            wavesurfer.addRegion({ 'id': 14, 'start': secondsPerBeat * 14, 'end': secondsPerBeat * 14 + 0.025, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
            wavesurfer.addRegion({ 'id': 15, 'start': secondsPerBeat * 15, 'end': secondsPerBeat * 15 + 0.025, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
            wavesurfer.addRegion({ 'id': 16, 'start': secondsPerBeat * 16, 'end': secondsPerBeat * 16 + 0.025, 'drag': false, 'resize': false, 'color': "rgba(217, 13, 13, 1)" });
          };


          /* BUFFER LOADER */
          /* BUFFER LOADER */
          /* BUFFER LOADER */
          /* BUFFER LOADER */

          function BufferLoader(context, urlList, callback) {
            this.context = context;
            this.urlList = urlList;
            this.onload = callback;
            this.bufferList = new Array();
            this.loadCount = 0;
          }

          BufferLoader.prototype.loadBuffer = function (url, index) {
            var request = new XMLHttpRequest();
            request.open("GET", url, true);
            request.responseType = "arraybuffer";

            var loader = this;

            request.onload = function () {
              loader.context.decodeAudioData(
                  request.response,
                  function (buffer) {
                    if (!buffer) {
                      alert('error decoding file data: ' + url);
                      return;
                    }
                    loader.bufferList[index] = buffer;
                    if (++loader.loadCount == loader.urlList.length)
                      loader.onload(loader.bufferList);
                  }
              );
            }

            request.onerror = function () {
              alert('BufferLoader: XHR error');
            }

            request.send();
          }

          BufferLoader.prototype.load = function () {
            for (var i = 0; i < this.urlList.length; ++i)
              this.loadBuffer(this.urlList[i], i);
          }

          /* END BUFFER LOADER */
          /* END BUFFER LOADER */
          /* END BUFFER LOADER */
          /* END BUFFER LOADER */

          /* MIDI SHIT */
          /* MIDI SHIT */
          /* MIDI SHIT */
          /* MIDI SHIT */
          /* MIDI SHIT */

          //window.onload = init;

          var context;
          var bufferLoaderA;
          var bufferList;

          function bufferLoadCompleted(buffers) {
            bufferList = buffers;
          }

          function loadAudioBuffers(audioFile) {

            try {
              context = new AudioContext();
            }
            catch (e) {
              alert("Web Audio API is not supported in this browser");
            }

            console.log('*** CONST.DOMAIN ****', CONST.DOMAIN);

            // Start loading the drum kit 2.
            bufferLoaderA = null;
            bufferLoaderA = new BufferLoader(
                context,
                [
                  CONST.DOMAIN + '/audiofiles/kick.wav',
                  CONST.DOMAIN + '/audiofiles/snare.wav',
                  audioFile,

                  //3
                  CONST.DOMAIN + '/audiofiles/asharp_maj7-.wav',
                  CONST.DOMAIN + '/audiofiles/asharp_dom7-.wav',
                  CONST.DOMAIN + '/audiofiles/asharp_min7-.wav',
                  CONST.DOMAIN + '/audiofiles/asharp_min7b5-.wav',
                  CONST.DOMAIN + '/audiofiles/asharp_sus-.wav',
                  CONST.DOMAIN + '/audiofiles/asharp_aug-.wav',

                  //9
                  CONST.DOMAIN + '/audiofiles/a_maj7-.wav',
                  CONST.DOMAIN + '/audiofiles/a_dom7-.wav',
                  CONST.DOMAIN + '/audiofiles/a_min7-.wav',
                  CONST.DOMAIN + '/audiofiles/a_min7b5-.wav',
                  CONST.DOMAIN + '/audiofiles/a_sus-.wav',
                  CONST.DOMAIN + '/audiofiles/a_sus-.wav',

                  //15
                  CONST.DOMAIN + '/audiofiles/b_maj7-.wav',
                  CONST.DOMAIN + '/audiofiles/b_dom7-.wav',
                  CONST.DOMAIN + '/audiofiles/b_min7-.wav',
                  CONST.DOMAIN + '/audiofiles/b_min7b5-.wav',
                  CONST.DOMAIN + '/audiofiles/b_sus-.wav',
                  CONST.DOMAIN + '/audiofiles/b_aug-.wav',

                  //21
                  CONST.DOMAIN + '/audiofiles/csharp_maj7-.wav',
                  CONST.DOMAIN + '/audiofiles/csharp_dom7-.wav',
                  CONST.DOMAIN + '/audiofiles/csharp_min7-.wav',
                  CONST.DOMAIN + '/audiofiles/csharp_min7b5-.wav',
                  CONST.DOMAIN + '/audiofiles/csharp_sus-.wav',
                  CONST.DOMAIN + '/audiofiles/csharp_aug-.wav',

                  //27
                  CONST.DOMAIN + '/audiofiles/c_maj7-.wav',
                  CONST.DOMAIN + '/audiofiles/c_dom7-.wav',
                  CONST.DOMAIN + '/audiofiles/c_min7-.wav',
                  CONST.DOMAIN + '/audiofiles/c_min7b5-.wav',
                  CONST.DOMAIN + '/audiofiles/c_sus-.wav',
                  CONST.DOMAIN + '/audiofiles/c_aug-.wav',

                  //33
                  CONST.DOMAIN + '/audiofiles/dsharp_maj7-.wav',
                  CONST.DOMAIN + '/audiofiles/dsharp_dom7-.wav',
                  CONST.DOMAIN + '/audiofiles/dsharp_min7-.wav',
                  CONST.DOMAIN + '/audiofiles/dsharp_min7b5-.wav',
                  CONST.DOMAIN + '/audiofiles/dsharp_sus-.wav',
                  CONST.DOMAIN + '/audiofiles/dsharp_aug-.wav',

                  //39
                  CONST.DOMAIN + '/audiofiles/d_maj7-.wav',
                  CONST.DOMAIN + '/audiofiles/d_dom7-.wav',
                  CONST.DOMAIN + '/audiofiles/d_min7-.wav',
                  CONST.DOMAIN + '/audiofiles/d_min7b5-.wav',
                  CONST.DOMAIN + '/audiofiles/d_sus-.wav',
                  CONST.DOMAIN + '/audiofiles/d_aug-.wav',

                  //45
                  CONST.DOMAIN + '/audiofiles/e_maj7-.wav',
                  CONST.DOMAIN + '/audiofiles/e_dom7-.wav',
                  CONST.DOMAIN + '/audiofiles/e_min7-.wav',
                  CONST.DOMAIN + '/audiofiles/e_min7b5-.wav',
                  CONST.DOMAIN + '/audiofiles/e_sus-.wav',
                  CONST.DOMAIN + '/audiofiles/e_aug-.wav',

                  //51
                  CONST.DOMAIN + '/audiofiles/fsharp_maj7-.wav',
                  CONST.DOMAIN + '/audiofiles/fsharp_dom7-.wav',
                  CONST.DOMAIN + '/audiofiles/fsharp_min7-.wav',
                  CONST.DOMAIN + '/audiofiles/fsharp_min7b5-.wav',
                  CONST.DOMAIN + '/audiofiles/fsharp_sus-.wav',
                  CONST.DOMAIN + '/audiofiles/fsharp_aug-.wav',

                  //57
                  CONST.DOMAIN + '/audiofiles/f_maj7-.wav',
                  CONST.DOMAIN + '/audiofiles/f_dom7-.wav',
                  CONST.DOMAIN + '/audiofiles/f_min7-.wav',
                  CONST.DOMAIN + '/audiofiles/f_min7b5-.wav',
                  CONST.DOMAIN + '/audiofiles/f_sus-.wav',
                  CONST.DOMAIN + '/audiofiles/f_aug-.wav',

                  //63
                  CONST.DOMAIN + '/audiofiles/gsharp_maj7-.wav',
                  CONST.DOMAIN + '/audiofiles/gsharp_dom7-.wav',
                  CONST.DOMAIN + '/audiofiles/gsharp_min7-.wav',
                  CONST.DOMAIN + '/audiofiles/gsharp_min7b5-.wav',
                  CONST.DOMAIN + '/audiofiles/gsharp_sus-.wav',
                  CONST.DOMAIN + '/audiofiles/gsharp_aug-.wav',

                  //69
                  CONST.DOMAIN + '/audiofiles/g_maj7-.wav',
                  CONST.DOMAIN + '/audiofiles/g_dom7-.wav',
                  CONST.DOMAIN + '/audiofiles/g_min7-.wav',
                  CONST.DOMAIN + '/audiofiles/g_min7b5-.wav',
                  CONST.DOMAIN + '/audiofiles/g_sus-.wav',
                  CONST.DOMAIN + '/audiofiles/g_aug-.wav'
                ],
                bufferLoadCompleted
            );

            bufferLoaderA.load();
          }

          function stopSound() {

            clearTimeout(repeatTimeout);

            for (var i = 0; i < audioSourceBuffers.length; i++) {
              audioSourceBuffers[i].stop();
            }

            audioSourceBuffers = null;
            audioSourceBuffers = [];
          }

          function playSound(buffer, time) {

            var source = context.createBufferSource();
            source.buffer = buffer;
            source.connect(context.destination);
            source.start(time);

            audioSourceBuffers.push(source);

          }

          var repeatTimeout = null;

          //var normalizeToSharps = function (chord) {

          //    if (chord == undefined) {
          //        return chord;
          //    }

          //    var indexOfUnderScore = chord.indexOf("_");
          //    var indexOfFlat = chord.indexOf("_");

          //    if (indexOfUnderScore == 2 && indexOfFlat == 1) {

          //        var res = str.replace("ab_", "g#_");
          //    }
          //};


          var getTriadFromBuffer = function (triad, bufferList) {

            console.log('triad', triad);

            //bufferList

            switch (triad) {

              //a,9
              case 'amaj':
              case 'amaj6':
              case 'amaj7':
              case 'amaj9':
                return bufferList[9];
              case 'adom7':
              case 'adom9':
                return bufferList[10];
              case 'amin':
              case 'amin7':
              case 'amin9':
                return bufferList[11];
              case 'adim':
              case 'amin7b5':
                return bufferList[12];
              case 'asus4':
              case 'asus2':
                return bufferList[13];
              case 'aaug':
                return bufferList[14];

              //a#,bb,9

              case 'a#maj':
              case 'a#maj6':
              case 'a#maj7':
              case 'a#maj9':
                return bufferList[3];
              case 'a#dom7':
              case 'a#dom9':
                return bufferList[4];
              case 'a#min':
              case 'a#min7':
              case 'a#min9':
                return bufferList[5];
              case 'a#dim':
              case 'a#min7b5':
                return bufferList[6];
              case 'a#sus4':
              case 'a#sus2':
                return bufferList[7];
              case 'a#aug':
                return bufferList[8];
              case 'bbmaj':
              case 'bbmaj6':
              case 'bbmaj7':
              case 'bbmaj9':
                return bufferList[3];
              case 'bbdom7':
              case 'bbdom9':
                return bufferList[4];
              case 'bbmin':
              case 'bbmin7':
              case 'bbmin9':
                return bufferList[5];
              case 'bbdim':
              case 'bbmin7b5':
                return bufferList[6];
              case 'bbsus4':
              case 'bbsus2':
                return bufferList[7];
              case 'bbaug':
                return bufferList[8];



              //b,15

              case 'bmaj':
              case 'bmaj6':
              case 'bmaj7':
              case 'bmaj9':
                return bufferList[15];
              case 'bdom7':
              case 'bdom9':
                return bufferList[16];
              case 'bmin':
              case 'bmin7':
              case 'bmin9':
                return bufferList[17];
              case 'bdim':
              case 'bmin7b5':
                return bufferList[18];
              case 'bsus4':
              case 'bsus2':
                return bufferList[19];
              case 'baug':
                return bufferList[20];


              //c,27

              case 'cmaj':
              case 'cmaj6':
              case 'cmaj7':
              case 'cmaj9':
                return bufferList[27];
              case 'cdom7':
              case 'cdom9':
                return bufferList[28];
              case 'cmin':
              case 'cmin7':
              case 'cmin9':
                return bufferList[29];
              case 'cdim':
              case 'cmin7b5':
                return bufferList[30];
              case 'csus4':
              case 'csus2':
                return bufferList[31];
              case 'caug':
                return bufferList[32];


              //c#,db,21


              case 'c#maj':
              case 'c#maj6':
              case 'c#maj7':
              case 'c#maj9':
                return bufferList[21];
              case 'c#dom7':
              case 'c#dom9':
                return bufferList[22];
              case 'c#min':
              case 'c#min7':
              case 'c#min9':
                return bufferList[23];
              case 'c#dim':
              case 'c#min7b5':
                return bufferList[24];
              case 'c#sus4':
              case 'c#sus2':
                return bufferList[25];
              case 'c#aug':
                return bufferList[26];

              case 'dbmaj':
              case 'dbmaj6':
              case 'dbmaj7':
              case 'dbmaj9':
                return bufferList[21];
              case 'dbdom7':
              case 'dbdom9':
                return bufferList[22];
              case 'dbmin':
              case 'dbmin7':
              case 'dbmin9':
                return bufferList[23];
              case 'dbdim':
              case 'dbmin7b5':
                return bufferList[24];
              case 'dbsus4':
              case 'dbsus2':
                return bufferList[25];
              case 'dbaug':
                return bufferList[26];



              //d,39

              case 'dmaj':
              case 'dmaj6':
              case 'dmaj7':
              case 'dmaj9':
                return bufferList[39];
              case 'ddom7':
              case 'ddom9':
                return bufferList[40];
              case 'dmin':
              case 'dmin7':
              case 'dmin9':
                return bufferList[41];
              case 'ddim':
              case 'dmin7b5':
                return bufferList[42];
              case 'dsus4':
              case 'dsus2':
                return bufferList[43];
              case 'daug':
                return bufferList[44];




              //d#,eb,33

              case 'd#maj':
              case 'd#maj6':
              case 'd#maj7':
              case 'd#maj9':
                return bufferList[33];
              case 'd#dom7':
              case 'd#dom9':
                return bufferList[34];
              case 'd#min':
              case 'd#min7':
              case 'd#min9':
                return bufferList[35];
              case 'd#dim':
              case 'd#min7b5':
                return bufferList[36];
              case 'd#sus4':
              case 'd#sus2':
                return bufferList[37];
              case 'd#aug':
                return bufferList[38];

              case 'ebmaj':
              case 'ebmaj6':
              case 'ebmaj7':
              case 'ebmaj9':
                return bufferList[33];
              case 'ebdom7':
              case 'ebdom9':
                return bufferList[34];
              case 'ebmin':
              case 'ebmin7':
              case 'ebmin9':
                return bufferList[35];
              case 'ebdim':
              case 'ebmin7b5':
                return bufferList[36];
              case 'ebsus4':
              case 'ebsus2':
                return bufferList[37];
              case 'ebaug':
                return bufferList[38];







              //e,fb,45

              case 'emaj':
              case 'emaj6':
              case 'emaj7':
              case 'emaj9':
                return bufferList[45];
              case 'edom7':
              case 'edom9':
                return bufferList[46];
              case 'emin':
              case 'emin7':
              case 'emin9':
                return bufferList[47];
              case 'edim':
              case 'emin7b5':
                return bufferList[48];
              case 'esus4':
              case 'esus2':
                return bufferList[49];
              case 'eaug':
                return bufferList[50];

              case 'fbmaj':
              case 'fbmaj6':
              case 'fbmaj7':
              case 'fbmaj9':
                return bufferList[45];
              case 'fbdom7':
              case 'fbdom9':
                return bufferList[46];
              case 'fbmin':
              case 'fbmin7':
              case 'fbmin9':
                return bufferList[47];
              case 'fbdim':
              case 'fbmin7b5':
                return bufferList[48];
              case 'fbsus4':
              case 'fbsus2':
                return bufferList[49];
              case 'fbaug':
                return bufferList[50];


              //f,51

              case 'fmaj':
              case 'fmaj6':
              case 'fmaj7':
              case 'fmaj9':
                return bufferList[57];
              case 'fdom7':
              case 'fdom9':
                return bufferList[58];
              case 'fmin':
              case 'fmin7':
              case 'fmin9':
                return bufferList[59];
              case 'fdim':
              case 'fmin7b5':
                return bufferList[60];
              case 'fsus4':
              case 'fsus2':
                return bufferList[61];
              case 'faug':
                return bufferList[62];




              //f#,gb,57

              case 'f#maj':
              case 'f#maj6':
              case 'f#maj7':
              case 'f#maj9':
                return bufferList[51];
              case 'f#dom7':
              case 'f#dom9':
                return bufferList[52];
              case 'f#min':
              case 'f#min7':
              case 'f#min9':
                return bufferList[53];
              case 'f#dim':
              case 'f#min7b5':
                return bufferList[54];
              case 'f#sus4':
              case 'f#sus2':
                return bufferList[55];
              case 'f#aug':
                return bufferList[56];

              case 'gbmaj':
              case 'gbmaj6':
              case 'gbmaj7':
              case 'gbmaj9':
                return bufferList[51];
              case 'gbdom7':
              case 'gbdom9':
                return bufferList[52];
              case 'gbmin':
              case 'gbmin7':
              case 'gbmin9':
                return bufferList[53];
              case 'gbdim':
              case 'gbmin7b5':
                return bufferList[54];
              case 'gbsus4':
              case 'gbsus2':
                return bufferList[55];
              case 'gbaug':
                return bufferList[56];


              //g,63

              case 'gmaj':
              case 'gmaj6':
              case 'gmaj7':
              case 'gmaj9':
                return bufferList[69];
              case 'gdom7':
              case 'gdom9':
                return bufferList[70];
              case 'gmin':
              case 'gmin7':
              case 'gmin9':
                return bufferList[71];
              case 'gdim':
              case 'gmin7b5':
                return bufferList[72];
              case 'gsus4':
              case 'gsus2':
                return bufferList[73];
              case 'gaug':
                return bufferList[74];


              //g#,ab,63

              case 'g#maj':
              case 'g#maj6':
              case 'g#maj7':
              case 'g#maj9':
                return bufferList[63];
              case 'g#dom7':
              case 'g#dom9':
                return bufferList[64];
              case 'g#min':
              case 'g#min7':
              case 'g#min9':
                return bufferList[65];
              case 'g#dim':
              case 'g#min7b5':
                return bufferList[66];
              case 'g#sus4':
              case 'g#sus2':
                return bufferList[67];
              case 'g#aug':
                return bufferList[68];


              case 'abmaj':
              case 'abmaj6':
              case 'abmaj7':
              case 'abmaj9':
                return bufferList[63];
              case 'abdom7':
              case 'abdom9':
                return bufferList[64];
              case 'abmin':
              case 'abmin7':
              case 'abmin9':
                return bufferList[65];
              case 'abdim':
              case 'abmin7b5':
                return bufferList[66];
              case 'absus4':
              case 'absus2':
                return bufferList[67];
              case 'abaug':
                return bufferList[68];



              default: //TODO DEFAULT TO case 'c_maj':
                console.log('DEFAULT TO case!!!', 'c_maj');
                return bufferList[21];
            }
          }

          var harmonizedChords = ['c_maj', 'c_maj', 'c_maj', 'c_maj'];


          var startPlayingRhythm = function (bufferList, disableClick) {

            var kick = bufferList[0];
            var snare = bufferList[1];
            var audioLoop = bufferList[2];

            // We'll start playing the rhythm 100 milliseconds from "now"
            var startTime = context.currentTime + 0.100;

            var quarterNoteTime = 60 / tempo;

            /* PLAY LOOP SAMPLE */
            playSound(audioLoop, startTime);

            /* PLAY BEAT MATCH SAMPLE */
            if (!disableClick) {
              playSound(snare, startTime);
              playSound(snare, startTime + 1 * quarterNoteTime);
              playSound(snare, startTime + 2 * quarterNoteTime);
              playSound(snare, startTime + 3 * quarterNoteTime);
            }

            var barCount = numOfBarsInLoop(audioSourceBuffers[0].buffer.duration * 1000, tempo);
            var loopCount = 1;

            if (loopCount < barCount) {
              if (!disableClick) {
                playSound(snare, startTime + 4 * quarterNoteTime);
                playSound(snare, startTime + 5 * quarterNoteTime);
                playSound(snare, startTime + 6 * quarterNoteTime);
                playSound(snare, startTime + 7 * quarterNoteTime);
              }

              loopCount++;
            }

            if (loopCount < barCount) {
              if (!disableClick) {
                playSound(snare, startTime + 8 * quarterNoteTime);
                playSound(snare, startTime + 9 * quarterNoteTime);
                playSound(snare, startTime + 10 * quarterNoteTime);
                playSound(snare, startTime + 11 * quarterNoteTime);
              }

              loopCount++;
            }

            if (loopCount < barCount) {
              if (!disableClick) {
                playSound(snare, startTime + 12 * quarterNoteTime);
                playSound(snare, startTime + 13 * quarterNoteTime);
                playSound(snare, startTime + 14 * quarterNoteTime);
                playSound(snare, startTime + 15 * quarterNoteTime);
              }

              loopCount++;
            }


            ///* HARMONIZE TRAIDS */
            ///* HARMONIZE TRAIDS */

            if (muteTriads) {
              playSound(getTriadFromBuffer(harmonizedChords[0], bufferList), startTime);
              playSound(getTriadFromBuffer(harmonizedChords[1], bufferList), startTime + 1 * quarterNoteTime);
              playSound(getTriadFromBuffer(harmonizedChords[2], bufferList), startTime + 2 * quarterNoteTime);
              playSound(getTriadFromBuffer(harmonizedChords[3], bufferList), startTime + 3 * quarterNoteTime);
            }

            barCount = numOfBarsInLoop(audioSourceBuffers[0].buffer.duration * 1000, tempo);
            loopCount = 1;

            if (muteTriads) {
              if (loopCount < barCount) {
                playSound(getTriadFromBuffer(harmonizedChords[0], bufferList), startTime + 4 * quarterNoteTime);
                playSound(getTriadFromBuffer(harmonizedChords[1], bufferList), startTime + 5 * quarterNoteTime);
                playSound(getTriadFromBuffer(harmonizedChords[2], bufferList), startTime + 6 * quarterNoteTime);
                playSound(getTriadFromBuffer(harmonizedChords[3], bufferList), startTime + 7 * quarterNoteTime);

                loopCount++;
              }

              if (loopCount < barCount) {
                playSound(getTriadFromBuffer(harmonizedChords[0], bufferList), startTime + 8 * quarterNoteTime);
                playSound(getTriadFromBuffer(harmonizedChords[1], bufferList), startTime + 9 * quarterNoteTime);
                playSound(getTriadFromBuffer(harmonizedChords[2], bufferList), startTime + 10 * quarterNoteTime);
                playSound(getTriadFromBuffer(harmonizedChords[3], bufferList), startTime + 11 * quarterNoteTime);

                loopCount++;
              }

              if (loopCount < barCount) {
                playSound(getTriadFromBuffer(harmonizedChords[0], bufferList), startTime + 12 * quarterNoteTime);
                playSound(getTriadFromBuffer(harmonizedChords[1], bufferList), startTime + 13 * quarterNoteTime);
                playSound(getTriadFromBuffer(harmonizedChords[2], bufferList), startTime + 14 * quarterNoteTime);
                playSound(getTriadFromBuffer(harmonizedChords[3], bufferList), startTime + 15 * quarterNoteTime);

                loopCount++;
              }
            }

            repeatTimeout = setTimeout(function () {
              startPlayingRhythm(bufferList);
            }, ((quarterNoteTime * 1000) * 4) * barCount);
          }

          var muteTriads = true;

          var tempo = 50;

          var stopAudioLoop = function () {
            stopSound();
          }

          var playAudioLoop = function (bpm, disableClick) {

            stopSound();
            tempo = bpm;

            var quarterNoteTime = 60 / bpm;

            startPlayingRhythm(bufferList, disableClick);

            addBeatDivisions(bpm);
          };

          var showFilePath = function (filename) {

            filename = CONST.HIVE_COMPOSE_SERVER + '/audiouploadstaging/' +  filename;

            //filename = CONST.HIVE_COMPOSE_SERVER + '/audiouploadstaging/080_too-much-pot-groove.wav';

            console.log("audiouploadstaging : " + filename);

            loadWaveSurfer(filename);

            loadAudioBuffers(filename);
          }

          $scope.$on('handleFileSelected', function (event, filename) {
            showFilePath(filename);
          });

          $scope.$on('playAudioLoop', function (event, bpm, disableClick) {
            playAudioLoop(bpm, disableClick);
          });

          $scope.$on('setBackingChords', function (event, chordArray) {
            console.log('setBackingChords', chordArray)
            harmonizedChords = chordArray;
          });

          $scope.$on('stopAudioLoop', function (event, param) {
            stopAudioLoop();
          });

          $scope.$on('muteTriads', function (event) {
            muteTriads = !muteTriads;
          });
        }
      };
    }]);