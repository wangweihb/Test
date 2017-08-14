jQuery(document).ready(function(event){
	var projectsContainer = $('.cd-projects-container'),
		navigation = $('.cd-primary-nav'),
		triggerNav = $('.cd-nav-trigger'),
		logo = $('.cd-logo');
	
	triggerNav.on('click', function(){
		if( triggerNav.hasClass('project-open') ) {
			//close project
			projectsContainer.removeClass('project-open').find('.selected').removeClass('selected').one('webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend', function(){
				$(this).children('.cd-project-info').scrollTop(0).removeClass('has-boxshadow');

			});
			triggerNav.add(logo).removeClass('project-open');
		} else {
			//trigger navigation visibility
			triggerNav.add(projectsContainer).add(navigation).toggleClass('nav-open');
		}
	});

	projectsContainer.on('click', '.single-project', function(){
		var selectedProject = $(this);
		if( projectsContainer.hasClass('nav-open') ) {
			//close navigation
			triggerNav.add(projectsContainer).add(navigation).removeClass('nav-open');
		} else {
			//open project
			selectedProject.addClass('selected');
			projectsContainer.add(triggerNav).add(logo).addClass('project-open');
		}
	});

	projectsContainer.on('click', '.cd-scroll', function(){
		//scroll down when clicking on the .cd-scroll arrow
		var visibleProjectContent =  projectsContainer.find('.selected').children('.cd-project-info'),
			windowHeight = $(window).height();

		visibleProjectContent.animate({'scrollTop': windowHeight}, 300); 
	});

});