import anime from 'animejs';

document.addEventListener("DOMContentLoaded", function() {
    var heading = document.querySelector(".display-4");

    var colors = ['#ff0000', '#ff7f00', '#ffff00', '#00ff00', '#0000ff', '#4b0082', '#9400d3'];

    var slideAnimation = anime.timeline({
        easing: 'easeOutExpo',
        duration: 1000,
        delay: 500
    });

    slideAnimation.add({
        targets: heading,
        translateY: [-50, 0],
        opacity: [0, 1]
    });

    var colorAnimation = anime.timeline({
        easing: 'linear',
        duration: 1500,
        loop: true
    });

    for (var i = 0; i < colors.length; i++) {
        colorAnimation.add({
            targets: heading,
            color: colors[i],
            offset: i * 100
        });
    }
});
