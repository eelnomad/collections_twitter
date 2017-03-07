(function () {


    function router($stateProvider, $urlRouterProvider, $locationProvider) {
        $locationProvider.html5Mode({
            enabled: true,
            requireBase: false
        });
        $urlRouterProvider.otherwise("/");

        // I udnerstand it works like this, but why is this beneficial? Seems to be something about header/footer being swappable based on content? How does that work?
        $stateProvider.state('myapp', {
                views: {
                    'header': {
                        templateUrl: "partials/header.html",
                        controller: 'HeaderController'
                    },
                    'content': {
                        template: '<div ui-view></div>'
                    },
                    'footer': {
                        templateUrl: "partials/footer.html"
                    }
                }
            })
            .state("myapp.collections", {
                url: "/collections",
                templateUrl: "partials/collections.html",
                controller: 'CollectionsController'
            })
            .state("myapp.visual1", {
                url: "/",
                templateUrl: "partials/visual1.html",
                controller: 'Visual1Controller'
            })
            .state("myapp.visual2", {
                url: "/visual2",
                templateUrl: "partials/visual2.html"
            })
            .state("myapp.visual3", {
                url: "/visual3",
                templateUrl: "partials/visual3.html"
            })

    }

    angular
        .module("twitterVisualizer")
        .config(router);

})();