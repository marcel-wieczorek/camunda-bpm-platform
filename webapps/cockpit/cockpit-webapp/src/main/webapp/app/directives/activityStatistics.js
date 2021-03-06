'use strict';

ngDefine('cockpit.directives', [ 'angular' ], function(module, angular) {
  
  var Directive = function (ProcessDefinitionResource) {
    return {
      restrict: 'AC',
      require: 'processDiagram', 
      link: function(scope, element, attrs, processDiagram) {
        
        scope.$watch(attrs['processDefinitionId'], function (newValue) {
          processDiagram.annotateWithActivityStatistics(null);
          getActivityStatistics(newValue);
        });
        
        function getActivityStatistics(processDefinitionId) {
          ProcessDefinitionResource
          .queryActivityStatistics(
              {
                id : processDefinitionId
              })
              .$then(function(result) {
                processDiagram.annotateWithActivityStatistics(result.data);
              });
        }
      }
    };
  };
  
  Directive.$inject = [ 'ProcessDefinitionResource' ];
   
  module
    .directive('activityStatistics', Directive);
  
});
