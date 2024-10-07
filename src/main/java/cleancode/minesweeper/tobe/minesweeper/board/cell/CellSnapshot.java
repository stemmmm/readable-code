package cleancode.minesweeper.tobe.minesweeper.board.cell;

import java.util.Objects;

public class CellSnapshot {

    private final CellSnapshotStatus status;
    private final int nearbyLandMindCount;

    private CellSnapshot(CellSnapshotStatus status, int nearbyLandMindCount) {
        this.status = status;
        this.nearbyLandMindCount = nearbyLandMindCount;
    }

    public static CellSnapshot of(CellSnapshotStatus status, int nearbyLandMindCount) {
        return new CellSnapshot(status, nearbyLandMindCount);
    }

    public static CellSnapshot ofEmpty() {
        return of(CellSnapshotStatus.EMPTY, 0);
    }

    public static CellSnapshot ofFlag() {
        return of(CellSnapshotStatus.FLAG, 0);
    }

    public static CellSnapshot ofLandMine() {
        return of(CellSnapshotStatus.LAND_MINE, 0);
    }

    public static CellSnapshot ofNumber(int nearbyLandMindCount) {
        return of(CellSnapshotStatus.NUMBER, nearbyLandMindCount);
    }

    public static CellSnapshot ofUnchecked() {
        return of(CellSnapshotStatus.UNCHECKED, 0);
    }

    public boolean isSameStatus(CellSnapshotStatus cellSnapshotStatus) {
        return status == cellSnapshotStatus;
    }

    public CellSnapshotStatus getStatus() {
        return status;
    }

    public int getNearbyLandMindCount() {
        return nearbyLandMindCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CellSnapshot snapshot = (CellSnapshot) o;
        return getNearbyLandMindCount() == snapshot.getNearbyLandMindCount() && getStatus() == snapshot.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getNearbyLandMindCount());
    }
}
