/*
 * Project: YourProjectName
 * Created Date: Friday, June 9th 2023, 10:14:08 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */

package Views;

import Models.Towers.AbstractTower;
import Models.Towers.LongTower;
import Models.Towers.RoundTower;

public class TowerViewCreator {
	public static AbstractTowerView getTowerView(AbstractTower tower) {
		if (tower instanceof RoundTower) {
			return new RoundTowerView((RoundTower) tower);
		} else if (tower instanceof LongTower) {
			return new LongTowerView((LongTower) tower);
		}

		return null;
	} 
}
