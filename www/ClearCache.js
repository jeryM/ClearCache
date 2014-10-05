window.clearAppCache = function (parameters, callback) {
    cordova.exec(function () {
    }, function () {
    }, "ClearCache", "clearAppCache", [ parameters ]);
};
