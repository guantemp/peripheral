/*
 * @(#}CustomerDisplayAdapter.java
 *
 * Copyright 2013 www.pos4j.com All rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cc.foxtail.peripheral.customerdisplay;

import java.util.EnumSet;
import java.util.Set;

import cc.foxtail.peripheral.util.Align;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @since JDK7.0
 * @version 0.0.1 2014年10月4日
 */
public abstract class CustomerDisplayAdapter implements CustomerDisplay {
	@Override
	public void adjustBrightness(int level) {
		// TODO Auto-generated method stub
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bulepos.peripheral.customerdisplay.CustomerDisplay#showInfo(int,
	 * java.util.Set, java.lang.String)
	 */
	@Override
	public void showInfo(int x, Set<Y_axis> y_axis, String info) {
		// TODO Auto-generated method stub

	}

	@Override
	public void demo() {
		try {
			if (isSupportShowInfo()) {
				sleep(1500);
				showInfo(0, EnumSet.of(Y_axis.ONE, Y_axis.TWO), Align.CENTER,
						"德芙黑浓巧克力");
				showInfo(0, EnumSet.of(Y_axis.THREE, Y_axis.FOUR), "数量：1.52");
				showInfo(125, EnumSet.of(Y_axis.THREE, Y_axis.FOUR),
						"单价：115.73");
			} else {
				sleep(1500);
				showUnitPrice(7.45);
			}
			sleep(1500);
			showTotal(51.57);
			sleep(1500);
			showPaid(60.00);
			sleep(1500);
			showChange(8.43);
		} finally {
			close();
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void openCashBox() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showChange(double chang) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showInfo(int x, Set<Y_axis> y, Align align, String info) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showPaid(double paid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showQuantity(double quantity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showTotal(double total) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showUnitPrice(double price) {
		// TODO Auto-generated method stub

	}

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean isSupportShowInfo() {
		return false;
	}
}
