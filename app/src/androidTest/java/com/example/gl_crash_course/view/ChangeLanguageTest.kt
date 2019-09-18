package com.example.gl_crash_course.splash.view


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.gl_crash_course.R
import com.example.gl_crash_course.view.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ChangeLanguageTest {

    // TODO: set locale at startup, or detect language

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun changeLanguageTest() {

        val textView = onView(
            allOf(
                withId(R.id.textView), withText("First activity"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("First activity")))

        val extendedFloatingActionButton = onView(
            allOf(
                withId(R.id.btnStartSecondActivity), withText("Enter"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        extendedFloatingActionButton.perform(click())

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Open navigation drawer"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val navigationMenuItemView = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.design_navigation_view),
                        childAtPosition(
                            withId(R.id.nav_view),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        navigationMenuItemView.perform(click())

        val appCompatSpinner = onView(
            allOf(
                withId(R.id.spinnerLanguage),
                childAtPosition(
                    allOf(
                        withId(R.id.layout3),
                        childAtPosition(
                            withId(R.id.fragment_container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatSpinner.perform(click())

        onData(anything()).atPosition(1).perform(click()) // polish

        val extendedFloatingActionButton2 = onView(
            allOf(
                withId(R.id.btnSave), withText("Save"),
                childAtPosition(
                    allOf(
                        withId(R.id.layout3),
                        childAtPosition(
                            withId(R.id.fragment_container),
                            0
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        extendedFloatingActionButton2.perform(click())

        val appCompatImageButton2 = onView(
            allOf(
                withContentDescription("Otwórz panel nawigacji"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        val checkedTextView = onView(
            allOf(
                withId(R.id.design_menu_item_text),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.design_navigation_view),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )

        checkedTextView.check(matches(withText("List miast")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
