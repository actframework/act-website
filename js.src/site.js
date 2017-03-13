$(document).ready(function() {

    // Variables
    var $codeSnippets = $('.code-example-body'),
        $nav = $('.navbar'),
        $body = $('body'),
        $window = $(window),
        $popoverLink = $('[data-popover]'),
        navOffsetTop = $nav.offset().top,
        $document = $(document),
        entityMap = {
            "&": "&amp;",
            "<": "&lt;",
            ">": "&gt;",
            '"': '&quot;',
            "'": '&#39;',
            "/": '&#x2F;'
        }

    function init() {
        $popoverLink.on('click', openPopover)
        $document.on('click', closePopover)
        buildSnippets();
    }

    function openPopover(e) {
        e.preventDefault()
        closePopover();
        var popover = $($(this).data('popover'));
        popover.toggleClass('open')
        e.stopImmediatePropagation();
    }

    function closePopover(e) {
        if($('.popover.open').length > 0) {
            $('.popover').removeClass('open')
        }
    }

    $("#button").click(function() {
        $('html, body').animate({
            scrollTop: $("#elementtoScrollToID").offset().top
        }, 2000);
    });


    function escapeHtml(string) {
        return String(string).replace(/[&<>"'\/]/g, function (s) {
            return entityMap[s];
        });
    }

    function buildSnippets() {
        $codeSnippets.each(function() {
            var newContent = escapeHtml($(this).html())
            $(this).html(newContent)
        })
    }


    init();

});


