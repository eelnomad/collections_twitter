(function () {


    function theme($mdThemingProvider) {
        $mdThemingProvider
            .theme('default')
            .primaryPalette('pink') // specify primary color, all
                                    // other color intentions will be inherited
                                    // from default
        $mdThemingProvider.theme('docs-dark').dark()
    }

    angular
        .module("twitterVisualizer")
        .config(theme);

})();