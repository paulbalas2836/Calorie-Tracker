class Particle {
    constructor(x, y, size, color, canvasWidth, canvasHeight, imageWidth, imageHeight) {
        this.x = x + canvasWidth / 2 - imageWidth * 2;
        this.y = y + canvasHeight / 2 - imageHeight * 2;
        this.size = 2;
        this.color = color;

        this.baseX = x + canvasWidth / 2 - imageWidth * 2;
        this.baseY = y + canvasHeight / 2 - imageHeight * 2;
        this.density = (Math.random() * 10) + 2;
    }

    draw(context) {
        context.beginPath();
        context.arc(this.x, this.y, this.size, 0, Math.PI * 2);
        context.closePath();
        context.fill();
    }

    update(width, height, context, mouse) {
        context.fillStyle = this.color;

        let dx = mouse.x - this.x;
        let dy = mouse.y - this.y;
        let distance = Math.sqrt(dx * dx + dy * dy);
        let forceDirectionX = dx / distance;
        let forceDirectionY = dy / distance;

        const maxDistance = 100;
        let force = (maxDistance - distance) / maxDistance;
        if (force < 0) force = 0;

        let directionX = (forceDirectionX * force * this.density * 0.6);
        let directionY = (forceDirectionY * force * this.density * 0.6);

        if (distance < mouse.radius + this.size) {
            this.x -= directionX;
            this.y -= directionY;
        } else {
            if (this.x !== this.baseX) {
                let dx = this.x - this.baseX;
                this.x -= dx / 20;
            }
            if (this.y !== this.baseY) {
                let dy = this.y - this.baseY;
                this.y -= dy / 20;
            }
        }
        this.draw(context);
    }
}

export default Particle