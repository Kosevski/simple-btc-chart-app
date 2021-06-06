package com.example.mk.wsahomeproject

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
  public  class MainActivityTest {

    @Rule @JvmField
    public val mActivityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun clickUpdateButton(){
        onView(withId(R.id.btn_update_chart))
            .perform(click())
    }

    @Test
    fun checkIfChartIsDisplayed(){
        onView(withId(R.id.chart))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkIfErrorUIIsHidden(){
        onView(withId(R.id.onErrorUI))
            .check(matches(not(isDisplayed())))
    }
}