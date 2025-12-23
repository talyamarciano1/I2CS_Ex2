public class Index2D implements Pixel2D {
    private int w;
    private int h;

    public Index2D(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public Index2D(Pixel2D other) {
        this.w = other.getX();
        this.h = other.getY();
    }

    @Override
    public int getX() {
        return this.w;
    }

    @Override
    public int getY() {
        return this.h;
    }

    @Override
    public double distance2D(Pixel2D p2) {
        int DeltaX = this.w - p2.getX();
        int DeltaY = this.h - p2.getY();
        int sum = Math.powExact(DeltaX, 2) + Math.powExact(DeltaY, 2);
        double distance = Math.sqrt(sum);

        return distance;
    }

    @Override
    public String toString() {
        String ans = "X: " + this.w;
        ans += "y" + this.h;

        return ans;
    }

    @Override
    public boolean equals(Object p) {
        boolean ans = true;
        if (p instanceof Index2D) {
            Index2D other = (Index2D) p;
            if (this.w == other.getX() && this.h == other.getY()) {
            }
            else {
                ans = false;
            }
        }
        return ans;
    }
}

