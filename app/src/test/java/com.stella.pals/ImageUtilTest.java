package com.stella.pals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by DJ on 6/12/15.
 * Project: Stella Pals
 */
@RunWith(PowerMockRunner.class)
public class ImageUtilTest {

    @Test
    public void testFemalePhotoOptionsPass() {
        assertTrue(ImageUtil.displayFemalePhotoOptions.isCacheOnDisk());
        assertTrue(ImageUtil.displayFemalePhotoOptions.isConsiderExifParams());
    }

    @Test
    public void testFemalePhotoOptionsFail() {
        assertNotEquals(ImageUtil.displayFemalePhotoOptions.isCacheOnDisk(), false);
        assertNotEquals(ImageUtil.displayFemalePhotoOptions.isConsiderExifParams(), false);
    }

}
