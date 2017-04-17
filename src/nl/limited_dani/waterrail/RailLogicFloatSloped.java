package nl.limited_dani.waterrail;

import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import com.bergerkiller.bukkit.common.bases.IntVector3;
import com.bergerkiller.bukkit.common.entity.type.CommonMinecart;
import com.bergerkiller.bukkit.common.utils.FaceUtil;
import com.bergerkiller.bukkit.tc.rails.logic.RailLogicSloped;

public class RailLogicFloatSloped extends RailLogicSloped {
	private static final RailLogicFloatSloped[] values = new RailLogicFloatSloped[4];
	static {
		for (int i = 0; i < 4; i++) {
			values[i] = new RailLogicFloatSloped(FaceUtil.notchToFace(i << 1));
		}
	}

	protected RailLogicFloatSloped(BlockFace direction) {
		super(direction);
	}

	@Override
	public Vector getFixedPosition(CommonMinecart<?> entity, double x, double y, double z, IntVector3 railPos) {
		Vector pos = super.getFixedPosition(entity, x, y, z, railPos);
		pos.setY(pos.getY() + 3.0 - 1.0);
		return pos;
	}

	/**
	 * Gets the sloped hanging rail logic to go into the direction specified
	 * 
	 * @param direction to go to
	 * @return Sloped hanging rail logic for that direction
	 */
	public static RailLogicFloatSloped get(BlockFace direction) {
		return values[FaceUtil.faceToNotch(direction) >> 1];
	}
}
