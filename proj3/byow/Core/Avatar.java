package byow.Core;


public class Avatar {
    private Position p;

    public Avatar(World world) {
        for (Room room: world.getRoomList()) {
            if (!world.isWall(room.getC())) {
                this.p = room.getC();
                return;
            }
        }
        this.p = world.getRoomList().get(0).getC();
    }


    public void movePosition(World world, char c) {
        Position to = this.getP();
        if (c == 'W') {
            to = this.getP().shift(0, 1);
        }
        if (c == 'A') {
            to = this.getP().shift(-1, 0);
        }
        if (c == 'S') {
            to = this.getP().shift(0, -1);
        }
        if (c == 'D') {
            to = this.getP().shift(1, 0);
        }
        if (!world.outofbounds(to.getX(), to.getY()) && !world.isWall(to)) {
            this.p = to;
        }
        // System.out.println(this.p);
    }

    public Position getP() {
        return this.p;
    }

}
