(function () {
  'use strict';

  angular.module('learnerApp', [])
    .controller('LearnerController', ['$http', function ($http) {
      var vm = this;
      vm.learners = [];
      vm.form = {
        name: '',
        track: ''
      };

      vm.loadLearners = function () {
        $http.get('/api/v1/learners')
          .then(function (response) {
            vm.learners = response.data;
          });
      };

      vm.addLearner = function () {
        $http.post('/api/v1/learners', vm.form)
          .then(function () {
            vm.form = { name: '', track: '' };
            vm.loadLearners();
          });
      };

      vm.loadLearners();
    }]);
})();
