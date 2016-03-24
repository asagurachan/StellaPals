package com.stella.pals.views.login;

import android.support.annotation.IdRes;
import android.support.test.espresso.ViewInteraction;
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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
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
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void clickOnLoginButton_ShowUsernameErrorToast() throws Exception {
        // Click on login button
        onView(withId(R.id.btn_login)).perform(click());

        // Check if username toast is displayed
        onErrorViewWithinTilWithId(R.id.til_username).check(matches(withText(R.string.em_please_enter_your_username_or_email)));
    }

    @Test
    public void clickOnLoginButton_ShowPasswordErrorToast() throws Exception {
        String username = "darjoo";

        // Type username into username field
        onEditTextWithinTilWithId(R.id.til_username).perform(typeText(username));

        // Click on login button
        onView(withId(R.id.btn_login)).perform(click());

        // Check if username toast is displayed
        onErrorViewWithinTilWithId(R.id.til_password).check(matches(withText(R.string.em_please_enter_a_password)));
    }

    @Test
    public void clickOnRegisterButton_ShowRegisterScreen() throws Exception {
        // Click on the register button
        onView(withId(R.id.btn_register)).perform(click());

        // Check if the register screen is displayed
        onView(withId(R.id.til_username)).check(matches(isDisplayed()));
        onView(withId(R.id.til_email)).check(matches(isDisplayed()));
        onView(withId(R.id.til_password)).check(matches(isDisplayed()));

        // Click back button
        onView(withContentDescription("Navigate up")).perform(click());

        // Check if the login screen is displayed
        onEditTextWithinTilWithId(R.id.til_username).check(matches(isDisplayed()));
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
