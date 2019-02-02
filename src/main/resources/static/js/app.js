'use strict';

//angular.module('myApp', ['myApp.filters', 'myApp.services', 'myApp.directives', 'ngRoute'])
angular.module('myApp', ['myApp.filters', 'myApp.services', 'ngRoute'])
    .constant("CONST", {
        "DOMAIN": "",
        // "DOMAIN": "http://localhost:7777",
//        "DOMAIN": "http://34.215.102.197:8080",
        "HIVE_COMPOSE_SERVER": ""
        // "HIVE_COMPOSE_SERVER": "http://localhost:7777"
//        "HIVE_COMPOSE_SERVER": "http://34.214.119.140:8080"
    })
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/view1', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'});
        $routeProvider.when('/view2', {templateUrl: 'partials/upload_beat_loops.html', controller: 'BeatLoopsCtrl'});
        $routeProvider.when('/view3', {templateUrl: 'partials/upload_harmonic_loops.html', controller: 'HarmonicLoopsCtrl'});
        $routeProvider.when('/view4', {templateUrl: 'partials/upload_ambience_loops.html', controller: 'AmbienceLoopsCtrl'});
        $routeProvider.when('/view5', {templateUrl: 'partials/upload_soft_synths.html', controller: 'SoftSynthsCtrl'});
        $routeProvider.when('/view6', {templateUrl: 'partials/upload_synth_templates.html', controller: 'SynthTemplateCtrl'});
        $routeProvider.when('/view7', {templateUrl: 'partials/chordeditor.html', controller: 'ChordEditorCtrl'});

        $routeProvider.otherwise({redirectTo: '/view1'});
  }]);




/*
angular.module('app', ['ui.router', 'app.filters', 'app.services', 'app.directives', 'app.controllers'])

  // Gets executed during the provider registrations and configuration type. Only providers and constants can be
  // injected here. This is to prevent accidental instantiation of services before they have been fully configured.
    .config(['$stateProvider', '$locationProvider', function ($stateProvider, $locationProvider) {

      // UI States, URL Routing & Mapping. For more info see: https://github.com/angular-ui/ui-router
      // ------------------------------------------------------------------------------------------------------------

      $stateProvider
          .state('home', {
            url: '/',
            templateUrl: '/views/index',
            controller: 'HomeCtrl'

          })
          .state('synthtemplate', {
            url: '/synthtemplate',
            templateUrl: '/views/synthtemplate',
            controller: 'SynthTemplateCtrl'
          })
          .state('softsynths', {
            url: '/softsynths',
            templateUrl: '/views/softsynths',
            controller: 'SoftSynthsCtrl'
          })
          .state('beatloops', {
            url: '/beatloops',
            templateUrl: '/views/beatloops',
            controller: 'BeatLoopsCtrl'
          })
          .state('ambienceloops', {
            url: '/ambienceloops',
            templateUrl: '/views/ambienceloops',
            controller: 'AmbienceLoopsCtrl'
          })
          .state('harmonicloops', {
            url: '/harmonicloops',
            templateUrl: '/views/harmonicloops',
            controller: 'HarmonicLoopsCtrl'
          })
          .state('chordeditor', {
            url: '/chordeditor',
            templateUrl: '/views/chordeditor',
            controller: 'ChordEditorCtrl'
          })
          .state('otherwise', {
            url: '*path',
            templateUrl: '/views/404',
            controller: 'Error404Ctrl'
          });


      $locationProvider.html5Mode(true);

    }])

  // Gets executed after the injector is created and are used to kickstart the application. Only instances and constants
  // can be injected here. This is to prevent further system configuration during application run time.
    .run(['$templateCache', '$rootScope', '$state', '$stateParams', function ($templateCache, $rootScope, $state, $stateParams) {

        console.log('here!!!!!!!!!!!!');
      // <ui-view> contains a pre-rendered template for the current view
      // caching it will prevent a round-trip to a server at the first page load
      var view = angular.element('#ui-view');
      $templateCache.put(view.data('tmpl-url'), view.html());

      // Allows to retrieve UI Router state information from inside templates
      $rootScope.$state = $state;
      $rootScope.$stateParams = $stateParams;

      $rootScope.$on('$stateChangeSuccess', function (event, toState) {

        // Sets the layout name, which can be used to display different layouts (header, footer etc.)
        // based on which page the user is located
        $rootScope.layout = toState.layout;
      });
    }]);
    */
