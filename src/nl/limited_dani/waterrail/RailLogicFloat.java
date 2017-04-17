package nl.limited_dani.waterrail;

import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import com.bergerkiller.bukkit.common.bases.IntVector3;
import com.bergerkiller.bukkit.common.entity.type.CommonMinecart;
import com.bergerkiller.bukkit.common.utils.FaceUtil;
import com.bergerkiller.bukkit.tc.rails.logic.RailLogicHorizontal;

public class RailLogicFloat extends RailLogicHorizontal {
	private static final RailLogicFloat[] values = new RailLogicFloat[8];
	static {
		for (int i = 0; i < 8; i++) {
			values[i] = new RailLogicFloat(FaceUtil.notchToFace(i));
		}
	}

	protected RailLogicFloat(BlockFace direction) {
		super(direction);
	}

	@Override
	public Vector getFixedPosition(CommonMinecart<?> entity, double x, double y, double z, IntVector3 railPos) {
		Vector pos = super.getFixedPosition(entity, x, y, z, railPos);
		pos.setY(pos.getY() + 3.0);
		return pos;
	}
	public static RailLogicFloat get(BlockFace direction) {
		return values[FaceUtil.faceToNotch(direction)];
	}
}
