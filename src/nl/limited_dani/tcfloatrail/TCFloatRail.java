package nl.limited_dani.tcfloatrail;

import org.bukkit.plugin.java.JavaPlugin;

import com.bergerkiller.bukkit.tc.rails.type.RailType;

public class TCFloatRail extends JavaPlugin {
	private final RailTypeFloat floatType = new RailTypeFloat();
	@Override
	public void onEnable() {
		RailType.register(floatType, false);
	}

	@Override
	public void onDisable() {
		RailType.unregister(floatType);
	}
}
