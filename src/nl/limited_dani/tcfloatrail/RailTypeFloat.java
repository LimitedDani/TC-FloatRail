package nl.limited_dani.tcfloatrail;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.bergerkiller.bukkit.common.bases.IntVector3;
import com.bergerkiller.bukkit.common.utils.FaceUtil;
import com.bergerkiller.bukkit.common.wrappers.BlockData;
import com.bergerkiller.bukkit.tc.controller.MinecartMember;
import com.bergerkiller.bukkit.tc.rails.logic.RailLogic;
import com.bergerkiller.bukkit.tc.rails.type.RailTypeHorizontal;
import com.bergerkiller.bukkit.tc.rails.type.RailTypeRegular;

public class RailTypeFloat extends RailTypeHorizontal {

	@Override
	public boolean isRail(BlockData block) {
		return block.getType() == Material.WOOL;
	}
	@Override
    public Block findRail(Block pos) {
        Block rail;
        if (this.isRail(pos.getWorld(), pos.getX(), pos.getY() - 3, pos.getZ())) {
            return pos.getRelative(0, - 3, 0);
        }
        if (this.isRail(pos.getWorld(), pos.getX(), pos.getY() - 1 - 3, pos.getZ()) && this.findSlope(rail = pos.getRelative(0, - 3 + 1, 0)) != null) {
            return rail;
        }
        return null;
    }
    @SuppressWarnings("rawtypes")
	public IntVector3 findRail(MinecartMember member, World world, IntVector3 pos) {
        IntVector3 railPos;
        if (this.isRail(world, pos.x, pos.y - 3, pos.z)) {
            return pos.add(BlockFace.UP, - 3);
        }
        if (this.isRail(world, pos.x, pos.y - 3 + 1, pos.z) && this.findSlope((railPos = pos.add(BlockFace.UP, - 3 + 1)).toBlock(world)) != null) {
            return railPos;
        }
        return null;
    }
    public Block findMinecartPos(Block trackBlock) {
        if (this.findSlope(trackBlock) == null) {
            return trackBlock.getRelative(0, 3, 0);
        }
        return trackBlock.getRelative(0, 3 - 1, 0);
    }
    
    public Block getNextPos(Block currentTrack, BlockFace currentDirection) {
        BlockFace sloped = this.findSlope(currentTrack);
        if (sloped != null) {
            return RailTypeRegular.getNextPos((Block)currentTrack.getRelative(0, 3 - 1, 0), (BlockFace)currentDirection, (BlockFace)sloped, (boolean)true);
        }
        BlockFace dir = this.getHorizontalDirection(currentTrack);
        if (dir == BlockFace.SELF) {
            dir = currentDirection;
        }
        return RailTypeRegular.getNextPos((Block)currentTrack.getRelative(0, 3, 0), (BlockFace)currentDirection, (BlockFace)dir, (boolean)false);
    }

    public BlockFace[] getPossibleDirections(Block trackBlock) {
        return RailTypeRegular.getPossibleDirections((BlockFace)this.getDirection(trackBlock));
    }

	@Override
	public BlockFace getSignColumnDirection(Block railsBlock) {
		return BlockFace.DOWN;
	}

	@Override
	public BlockFace getDirection(Block railsBlock) {
		BlockFace sloped = findSlope(railsBlock);
		return sloped == null ? getHorizontalDirection(railsBlock) : sloped;
	}

	@Override
	public RailLogic getLogic(MinecartMember<?> member, Block railsBlock) {
		BlockFace sloped = findSlope(railsBlock);
		if (sloped != null) {
			return RailLogicFloatSloped.get(sloped);
		}
		// Check what sides have a connecting hanging rail
		BlockFace dir = getHorizontalDirection(railsBlock);
		if (dir == BlockFace.SELF) {
			// Use the Minecart direction to figure this one out
			// This is similar to the Crossing rail type
			dir = FaceUtil.toRailsDirection(member.getDirectionTo());
		}
		return RailLogicFloat.get(dir);
	}
    private BlockFace findSlope(Block railsBlock) {
        Block below = railsBlock.getRelative(BlockFace.DOWN);
        for (BlockFace face : FaceUtil.AXIS) {
            if (!this.isRail(below, face) || this.isRail(below.getRelative(face.getModX(), -1, face.getModZ()))) continue;
            return face.getOppositeFace();
        }
        return null;
    }

    private BlockFace getHorizontalDirection(Block railsBlock) {
        boolean north = this.isRail(railsBlock, BlockFace.NORTH);
        boolean east = this.isRail(railsBlock, BlockFace.EAST);
        boolean south = this.isRail(railsBlock, BlockFace.SOUTH);
        boolean west = this.isRail(railsBlock, BlockFace.WEST);
        if (north && south && east && west) {
            return BlockFace.SELF;
        }
        if (north && south) {
            return BlockFace.NORTH;
        }
        if (east && west) {
            return BlockFace.EAST;
        }
        if (north && east) {
            return BlockFace.SOUTH_WEST;
        }
        if (north && west) {
            return BlockFace.SOUTH_EAST;
        }
        if (south && east) {
            return BlockFace.NORTH_WEST;
        }
        if (south && west) {
            return BlockFace.NORTH_EAST;
        }
        if (north || south) {
            return BlockFace.NORTH;
        }
        if (east || west) {
            return BlockFace.EAST;
        }
        return BlockFace.SELF;
    }
}
