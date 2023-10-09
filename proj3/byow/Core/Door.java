package byow.Core;

public class Door {
    private Position p;

    public Door(World world) {
        for (int x = world.getRoomList().size() - 1; x > 0; x--) {
            if (!world.isWall(world.getRoomList().get(x).getC())) {
                this.p = world.getRoomList().get(x).getC();
                return;
            }
        }
        this.p = world.getRoomList().get(world.getRoomList().size() - 1).getC();
    }

    public Position getP() {
        return this.p;
    }
}

