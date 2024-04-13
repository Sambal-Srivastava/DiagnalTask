package com.diagnal.diagnaltask.ViewsTest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.diagnal.diagnaltask.R
import com.diagnal.diagnaltask.views.MainActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testToolbarTitle() {
        onView(withId(R.id.tvTitle)).check(matches(withText("Romantic Comedy")))
    }

    @Test
    fun testSearchBarVisibility() {
        onView(withId(R.id.etSearch)).check(matches(not(isDisplayed())))
        onView(withId(R.id.ivSearch)).perform(click())
        onView(withId(R.id.etSearch)).check(matches(isDisplayed()))
    }

    // Add more test cases for RecyclerView interactions as needed
}
