angular
		.module('app')
		.controller(
				'HomeController',
		[
						'$scope',
						'$state',
						//'$cordovaContacts',
						function($scope, $state) {

							$scope.company = {};

							$scope.contacts = [];
							
							$scope.searchTerm = '';
							
							for(var i =0; i<20;i++){
								var contact = {};								
								contact.name ='name-' + i;
								contact.displayName ='Siva Kalidasan-' + i;
								contact.number ='+91' + '988489228' + i;
								
								$scope.contacts.push(contact);
							}
							
							var storage = window.localStorage;
														
							var companyname = storage.getItem("companyname");
							
							var address = storage.getItem("address");
							
							if (companyname) {
								$scope.company.name = companyname;
							}
							
							if (address) {
								$scope.company.address = address;
							}
							
							
							/*
							 $scope.getAllContacts = function() {
								    contactObject.find().then(function(allContacts) { //omitting parameter to .find() causes all contacts to be returned
								      $scope.contacts = allContacts;
								      console.log($scope.contacts);
								    }
								  };
*/
						} 
	]);
