import anime from 'animejs';

document.addEventListener("DOMContentLoaded", function() {
    var heading = document.querySelector(".display-4");

    // Define rainbow colors
    var colors = ['#ff0000', '#ff7f00', '#ffff00', '#00ff00', '#0000ff', '#4b0082', '#9400d3'];

    // Create slide animation
    var slideAnimation = anime.timeline({
        easing: 'easeOutExpo',
        duration: 1000,
        delay: 500
    });

    // Add slide animation to the timeline
    slideAnimation.add({
        targets: heading,
        translateY: [-50, 0],
        opacity: [0, 1]
    });

    // Create rainbow color change animation
    var colorAnimation = anime.timeline({
        easing: 'linear',
        duration: 1500,
        loop: true // Loop the animation
    });

    // Add color change animation to the timeline
    for (var i = 0; i < colors.length; i++) {
        colorAnimation.add({
            targets: heading,
            color: colors[i],
            offset: i * 100 // Delay each color change
        });
    }
});
