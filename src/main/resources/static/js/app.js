(function () {


    angular.module("twitterVisualizer", ['ui.router', 'ngMaterial', 'ngMdIcons', 'ngResource', 'ngAnimate']);

    function HeaderController() {
    }

    function CollectionsController($rootScope, $scope, $log, CollectionService, $mdDialog, $mdMedia) {
        $scope.collectionList = CollectionService.list.query();

        $scope.showAddCollection = function () {
            var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
            $mdDialog.show({
                    controller: 'CollectionAddingController',
                    templateUrl: 'partials/addCollection.html',
                    parent: angular.element(document.body),
                    clickOutsideToClose: true,
                    escapeToClose: true,
                    fullscreen: useFullScreen
                })
                .then(function () {
                    $scope.collectionList = CollectionService.list.query();
                }, function () {

                });

            $scope.$watch(function () {
                return $mdMedia('xs') || $mdMedia('sm');

            }, function (wantsFullScreen) {
                $scope.customFullscreen = (wantsFullScreen === true);
            });
        };

        $scope.runCollection = function (collectionId) {
            CollectionService.run.get({collectionId: collectionId}).$promise.then(
                function () {
                    $log.debug("Ran collection: " + collectionId)
                    $scope.collectionList = CollectionService.list.query();
                }
            );
        };

        $scope.stopCollection = function (collectionId) {
            CollectionService.stopOne.get({collectionId: collectionId}).$promise.then(
                function () {
                    $log.debug("Stopped collection: " + collectionId)
                    $scope.collectionList = CollectionService.list.query();
                }
            );
        };

        $scope.deleteCollection = function (collectionId) {
            CollectionService.delete.get({collectionId: collectionId}).$promise.then(
                function () {
                    $log.debug("Deleted collection: " + collectionId)
                    $scope.collectionList = CollectionService.list.query();
                }
            );
            if ($rootScope.activeCollection == collectionId)
                $rootScope.activeCollection = '';
        };

        $scope.setActiveCollection = function (collectionId) {
            $rootScope.activeCollection = collectionId;
        };

    }

    function Visual1Controller(StatsService, $rootScope) {
        console.log(StatsService.wordCount.query({collectionId: $rootScope.activeCollection['_id']}));
        WordCloud(document.getElementById('html-canvas'), {list: StatsService.wordCount.query({collectionId: $rootScope.activeCollection['_id']})});
        console.log(WordCloud.settings);
    }

    function CollectionAddingController($scope, $mdDialog, CollectionService) {
        $scope.newCollection = {
            desc: "",
            keywords: []
        };
        $scope.submit = function () {
            console.log($scope.newCollection);
            CollectionService.add.update($scope.newCollection);
            $mdDialog.hide();

        };
        $scope.close = function () {
            $mdDialog.cancel();
        };

    }

    angular
        .module("twitterVisualizer")
        .controller("HeaderController", HeaderController)
        .controller("CollectionsController", CollectionsController)
        .controller("CollectionAddingController", CollectionAddingController)
        .controller("Visual1Controller", Visual1Controller);

})();