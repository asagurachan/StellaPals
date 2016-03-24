package com.stella.pals.views.register;

import android.support.annotation.IdRes;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.EditText;
import android.widget.TextView;

import com.stella.pals.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by DJ on 6/12/15.
 * Project: Stella Pals
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<RegisterActivity> mActivityTestRule = new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void clickOnRegisterButton_ShowUsernameError() throws Exception {
        // Click on register button
        onView(withId(R.id.btn_register)).perform(click(), closeSoftKeyboard());

        // Check if username empty error is displayed
        onErrorViewWithinTilWithId(R.id.til_username).check(matches(withText(R.string.em_please_enter_a_username)));
    }

    @Test
    public void clickOnRegisterButton_ShowEmailError() throws Exception {
        // Click on register button
        onView(withId(R.id.btn_register)).perform(click(), closeSoftKeyboard());

        // Check if email empty error is displayed
        onErrorViewWithinTilWithId(R.id.til_email).check(matches(withText(R.string.em_please_enter_a_email)));

        // Test invalid email
        String invalidEmail = "hello";
        onEditTextWithinTilWithId(R.id.til_email).perform(typeText(invalidEmail));
        onView(withId(R.id.btn_register)).perform(click(), closeSoftKeyboard());
        onErrorViewWithinTilWithId(R.id.til_email).check(matches(withText(R.string.em_please_enter_a_valid_email)));

        invalidEmail = "hello@email";
        onEditTextWithinTilWithId(R.id.til_email).perform(typeText(invalidEmail));
        onView(withId(R.id.btn_register)).perform(click(), closeSoftKeyboard());
        onErrorViewWithinTilWithId(R.id.til_email).check(matches(withText(R.string.em_please_enter_a_valid_email)));

        invalidEmail = "hello+123@email.";
        onEditTextWithinTilWithId(R.id.til_email).perform(typeText(invalidEmail));
        onView(withId(R.id.btn_register)).perform(click(), closeSoftKeyboard());
        onErrorViewWithinTilWithId(R.id.til_email).check(matches(withText(R.string.em_please_enter_a_valid_email)));

        invalidEmail = "hello@123.123";
        onEditTextWithinTilWithId(R.id.til_email).perform(typeText(invalidEmail));
        onView(withId(R.id.btn_register)).perform(click(), closeSoftKeyboard());
        onErrorViewWithinTilWithId(R.id.til_email).check(matches(withText(R.string.em_please_enter_a_valid_email)));

        // Test valid email
        String validEmail = "hello@gmail.com";
        onEditTextWithinTilWithId(R.id.til_email).perform(typeText(validEmail));
        onView(withId(R.id.btn_register)).perform(click(), closeSoftKeyboard());
        onErrorViewWithinTilWithId(R.id.til_email).check((matches(not(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))));

        validEmail = "hello+123@gmail.com";
        onEditTextWithinTilWithId(R.id.til_email).perform(typeText(validEmail));
        onView(withId(R.id.btn_register)).perform(click(), closeSoftKeyboard());
        onErrorViewWithinTilWithId(R.id.til_email).check((matches(not(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))));
    }

    /*
       * Use this method to find the EditText within the TextInputLayout. Useful for typing into the TextInputLayout
       */
    public static ViewInteraction onEditTextWithinTilWithId(@IdRes int textInputLayoutId) {
        //Note, if you have specified an ID for the EditText that you place inside
        //the TextInputLayout, use that instead - i.e, onView(withId(R.id.my_edit_text));
        return onView(allOf(isDescendantOfA(withId(textInputLayoutId)), isAssignableFrom(EditText.class)));
    }

    /*
     * Use this method to find the error view within the TextInputLayout. Useful for asseting that certain errors are displayed to the user
     */
    public static ViewInteraction onErrorViewWithinTilWithId(@IdRes int textInputLayoutId) {
        return onView(allOf(isDescendantOfA(withId(textInputLayoutId)), not(isAssignableFrom(EditText.class)), isAssignableFrom(TextView.class)));
    }
}
