package com.androidsphere.aditya.egyaan;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.androidsphere.aditya.egyaan.ui.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public final ActivityTestRule<LoginActivity> loginActivityActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void ifEverythingLeftBlank() {
        onView(withId(R.id.floating_action_button)).perform(click());
    }

    @Test
    public void ifWrongDetailsEntered() {
        onView(withId(R.id.email_activity_login)).perform(typeText("bk@gmail.com"));
        onView(withId(R.id.password_activity_login)).perform(typeText("bk141"));
        onView(withId(R.id.floating_action_button)).perform(click());
    }

    @Test
    public void ifRightDetailsEntered() {
        onView(withId(R.id.email_activity_login)).perform(typeText("bk@gmail.com"));
        onView(withId(R.id.password_activity_login)).perform(typeText("bk1411"));
        onView(withId(R.id.floating_action_button)).perform(click());
    }
}
