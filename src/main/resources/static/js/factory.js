(function () {


    function CollectionService($resource, $http) {
        var CollectionService = {};
        CollectionService.list = $resource('/api/list/collections', {}, {isArray: true});
        CollectionService.run = $resource('/api/run?collectionId=:collectionId');
        CollectionService.stopOne = $resource('/api/stop?collectionId=:collectionId');
        CollectionService.stopAll = $http.get('/api/stop');
        CollectionService.delete = $resource('/api/delete?collectionId=:collectionId');
        CollectionService.add = $resource('/api/new?keywords=:keywords&desc=:desc', {
                keywords: '@keywords',
                desc: '@desc'
            },
            {
                'update': {method: 'PUT'}
            });

        return CollectionService;
    }

    function StatsService($resource) {
        var StatsService = {};
        StatsService.wordCount = $resource('/api/stats/wordcount?collectionId=:collectionId', {}, {isArray: true});

        return StatsService;
    }


    angular
        .module('twitterVisualizer')
        .factory('CollectionService', CollectionService)
        .factory('StatsService', StatsService);

})()