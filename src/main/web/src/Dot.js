import gsap from "gsap";
const DOT_RADIUS = 10; // Radius of the dots

class Dot {
    constructor(width, height, perspective, projection_center_x, projection_center_y, context) {
        this.width = width
        this.theta = Math.random() * 2 * Math.PI; // Random value between [0, 2Pi]
        this.phi = Math.acos((Math.random() * 2) - 1); // Random value between [0, Pi]
        this.x = (Math.random() - 0.5) * width;
        this.y = (Math.random() - 0.5) * height/4;
        this.z = Math.random() * width;
        this.radius = 10;
        this.perspective = perspective
        this.projection_center_x  = projection_center_x
        this.projection_center_y = projection_center_y
        this.GLOBE_RADIUS = width/8
        this.context = context

        this.xProjected = 0;
        this.yProjected = 0;
        this.scaleProjected = 0;

        gsap.to(this, {duration :Math.random() * 10 + 15,
            z: width,
            repeat: -1,
            theta: this.theta + Math.PI *2,
            ease: "power0.easeNone",
        });
    }

    project() {
        this.x = this.GLOBE_RADIUS * Math.sin(this.phi) * Math.cos(this.theta);
        this.y = this.GLOBE_RADIUS * Math.cos(this.phi);
        this.z = this.GLOBE_RADIUS * Math.sin(this.phi) * Math.sin(this.theta) + this.GLOBE_RADIUS;

        this.scaleProjected = this.perspective / (this.perspective + this.z);
        this.xProjected = (this.x * this.scaleProjected) + this.projection_center_x;
        this.yProjected = (this.y * this.scaleProjected) + this.projection_center_y;
    }
    draw() {
        this.project();
        this.context.globalAlpha = Math.abs(1 - this.z / this.width);
        this.context.beginPath();
        this.context.arc(this.xProjected, this.yProjected, DOT_RADIUS * this.scaleProjected, 0, Math.PI * 2);
        this.context.fillStyle = '#8B0000'
        // context.fillRect(this.xProjected - this.radius, this.yProjected - this.radius, this.radius * 2 * this.scaleProjected, this.radius * 2 * this.scaleProjected);
        this.context.fill()
    }
}

export default Dot