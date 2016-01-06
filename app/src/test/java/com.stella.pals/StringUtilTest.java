package com.stella.pals;

import com.stella.pals.utils.StringUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by DJ on 6/12/15.
 * Project: Stella Pals
 */
@RunWith(PowerMockRunner.class)
public class StringUtilTest {

    @Test
    public void testIsEmptyPass() {
        assertTrue(StringUtil.isEmpty(""));
        assertTrue(StringUtil.isEmpty(" "));
        assertTrue(StringUtil.isEmpty(null));
        assertFalse(StringUtil.isEmpty("Hello"));
        assertFalse(StringUtil.isEmpty("    ."));
        assertFalse(StringUtil.isEmpty(".    "));
    }

    @Test
    public void testIsEmptyFail() {
        assertNotEquals(StringUtil.isEmpty(""), false);
        assertNotEquals(StringUtil.isEmpty(" "), false);
        assertNotEquals(StringUtil.isEmpty(null), false);
        assertNotEquals(StringUtil.isEmpty("Hello"), true);
        assertNotEquals(StringUtil.isEmpty("    ."), true);
        assertNotEquals(StringUtil.isEmpty(".    "), true);
    }

    @Test
     public void testIsNotEmptyPass() {
        assertFalse(StringUtil.isNotEmpty(""));
        assertFalse(StringUtil.isNotEmpty(" "));
        assertFalse(StringUtil.isNotEmpty(null));
        assertTrue(StringUtil.isNotEmpty("Hello"));
        assertTrue(StringUtil.isNotEmpty("    ."));
        assertTrue(StringUtil.isNotEmpty(".    "));
    }

    @Test
    public void testIsNotEmptyFail() {
        assertNotEquals(StringUtil.isNotEmpty(""), true);
        assertNotEquals(StringUtil.isNotEmpty(" "), true);
        assertNotEquals(StringUtil.isNotEmpty(null), true);
        assertNotEquals(StringUtil.isNotEmpty("Hello"), false);
        assertNotEquals(StringUtil.isNotEmpty("    ."), false);
        assertNotEquals(StringUtil.isNotEmpty(".    "), false);
    }

    @Test
    public void testRemoveEmailProtectionPass() {
        //TODO: Test Remove Email Protection
    }

}
