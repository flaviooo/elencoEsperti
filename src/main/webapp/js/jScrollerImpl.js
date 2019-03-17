$(document).ready(function() {

	// Add Scroller Object
		$jScroller.add("#scroller_container_elettrico", "#scroller_elettrico",
				"up", 1, true);
		$jScroller.add("#scroller_container_gas", "#scroller_gas", "up", 1,
				true);
		$jScroller.add("#scroller_container_news", "#scroller_news", "up", 1,
				true);
		$jScroller.add("#scroller_container_avvisi", "#scroller_avvisi", "up",
				1, true);
		// Start Autoscroller
		$jScroller.start();
	});