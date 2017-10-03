package com.deco2800.hcg.entities;

import org.junit.*;

public class ShopKeeperTest {
	private ShopKeeper shopKeeper;

	@Before
	public void setUp() {
		shopKeeper = new ShopKeeper();
	}

	@Test
	public void isSelectedTest() {
		Assert.assertFalse("ShopKeeper is selected is not false",
				shopKeeper.isSelected());
	}

	@Test
	public void getButtonTest() {
		Assert.assertEquals("Shopkeeper get button is not null",
				shopKeeper.getButton(), null);
	}

	@Test
	public void buttonWasPressedTest() {
		try {
			shopKeeper.buttonWasPressed();
		} catch (UnsupportedOperationException e) {
			Assert.assertTrue(true);
			return;
		}

		Assert.fail("button was pressed not throwing error");
	}

	@Test
	public void deselectTest() {
		try {
			shopKeeper.deselect();
		} catch (UnsupportedOperationException e) {
			Assert.assertTrue(true);
			return;
		}

		Assert.fail("deselect not throwing error");
	}
}
