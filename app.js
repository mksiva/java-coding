angular
        .module('app', [
            'ui.router',          
            'ngMaterial',
            'lbServices',
            'ngResource',
            'ngMdIcons',
            'ngAria',
            'ngCordova'
        ])
        .controller('EntryController', ['$scope', '$rootScope', '$mdSidenav',
            function ($scope, $rootScope, $mdSidenav) {
                $scope.apptitle = "Timed Contacts";
                var storage = window.localStorage;
                var whereami = storage.getItem("whereami"); // Pass a key name to get its value.
                var route = storage.getItem("route");
                var userName = storage.getItem("user");
                
              
                $scope.toggleMenu = function () {
                    $mdSidenav('left').toggle();
                }

                $scope.openSideNavPanel = function () {
                    $mdSidenav('left').open();
                };

                $scope.closeSideNavPanel = function () {
                    $mdSidenav('left').close();
                };
                
                var vm = this;
                vm.isOpen = isOpen;
                vm.toggleOpen = toggleOpen;
                vm.togg = togg;
                vm.isSectionSelected = isSectionSelected;

                vm.autoFocusContent = false;
                
                function isSectionSelected(section) {
                    //console.log('selected');
                    return dmenu.isSectionSelected(section);
                }

                function isOpen(section) {
                    return dmenu.isSectionSelected(section);
                }

                function togg() {
                    $mdSidenav('left').toggle();
                }

                function toggleOpen(section) {
                    dmenu.toggleSelectSection(section);
                }
            }])

        .config(function ($mdThemingProvider, $mdIconProvider) {
            $mdIconProvider
                    .defaultIconSet("svg/avatars.svg", 128)
                    .icon("menu", "/img/svg/menu.svg", 24)
                    .fontSet('md', 'material-icons');

            $mdThemingProvider.theme('default')
                    .primaryPalette('teal')
                    .accentPalette('green');

            $mdThemingProvider.theme('custom')
                    .primaryPalette('green')
                    .accentPalette('yellow');

            $mdThemingProvider.theme('pink')
                    .primaryPalette('pink')
                    .accentPalette('yellow');
        })
        
              //take all whitespace out of string
        .filter('nospace', function () {
            return function (value) {
                return (!value) ? '' : value.replace(/ /g, '');
            };
        })

        //replace uppercase to regular case
        .filter('humanizeDoc', function () {
            return function (doc) {
                if (!doc)
                    return;
                if (doc.type === 'directive') {
                    return doc.name.replace(/([A-Z])/g, function ($1) {
                        return '-' + $1.toLowerCase();
                    });
                }

                return doc.label || doc.name;
            };
        })
        
        .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider,
                    $urlRouterProvider) {
                $stateProvider
                        .state('home', {
                            url: '/home',
                            templateUrl: 'app/main/home.html',
                            controller:'HomeController'

                        })                       
                        .state('whereami', {
                            url: '/whereami',
                            templateUrl: 'app/whereami/whereami.html',
                            controller: 'WhereAmIController'
                        })
                        .state('profile', {
                            url: '/profile',
                            templateUrl: 'app/profile/profile.html',
                            controller: 'ProfileController'
                        })
                $urlRouterProvider.otherwise('home');
            }]);
