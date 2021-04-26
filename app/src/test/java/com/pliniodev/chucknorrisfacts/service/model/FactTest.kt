package com.pliniodev.chucknorrisfacts.service.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pliniodev.chucknorrisfacts.data.response.FactDetailsResponse

import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FactTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `Must return true, when text on fact value, is greater then 80 characters`() {
        //Arrange
        val fact = Fact(
            categories = arrayListOf("food"),
            created_at = "2020-01-05 13:42:18.823766",
            icon_url = "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
            id = "MjtEesffSd6AH3Pxbw7_lg",
            updated_at = "2020-01-05 13:42:18.823766",
            url = "https://api.chucknorris.io/jokes/MjtEesffSd6AH3Pxbw7_lg",
            value = "When Chuck Norris played Chopped from Food Network, he finished " +
                    "his food in 1 millisecond, and instantly wins every dish. You " +
                    "didn't see him play because the episode is secret.",
        )

        //act
        val result = fact.isLongText

        //assert
        Assert.assertTrue(result)
    }

    @Test
    fun `Must return false, when text on fact value, is smaller then 80 characters`() {
        //Arrange
        val fact = Fact(
            categories = arrayListOf("food"),
            created_at = "2020-01-05 13:42:18.823766",
            icon_url = "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
            id = "MjtEesffSd6AH3Pxbw7_lg",
            updated_at = "2020-01-05 13:42:18.823766",
            url = "https://api.chucknorris.io/jokes/MjtEesffSd6AH3Pxbw7_lg",
            value = "Chuck Norris can get Mexican food at a Japanese restaurant.",
        )

        //act
        val result = fact.isLongText

        //assert
        Assert.assertFalse(result)
    }

    @Test
    fun `Must return UNCATEGORIZED, when categories on fact, is empty`() {
        //Arrange
        val fact = Fact(
            categories = arrayListOf(),
            created_at = "2020-01-05 13:42:18.823766",
            icon_url = "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
            id = "MjtEesffSd6AH3Pxbw7_lg",
            updated_at = "2020-01-05 13:42:18.823766",
            url = "https://api.chucknorris.io/jokes/MjtEesffSd6AH3Pxbw7_lg",
            value = "Chuck Norris can get Mexican food at a Japanese restaurant.",
        )

        //act
        val result = fact.getCategory

        //assert
        Assert.assertEquals("UNCATEGORIZED",result)
    }

    @Test
    fun `Must return value, when categories on fact, have a value`() {
        //Arrange
        val category = "food"

        val fact = Fact(
            categories = arrayListOf(category),
            created_at = "2020-01-05 13:42:18.823766",
            icon_url = "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
            id = "MjtEesffSd6AH3Pxbw7_lg",
            updated_at = "2020-01-05 13:42:18.823766",
            url = "https://api.chucknorris.io/jokes/MjtEesffSd6AH3Pxbw7_lg",
            value = "Chuck Norris can get Mexican food at a Japanese restaurant.",
        )

        //act
        val result = fact.getCategory

        //assert
        Assert.assertEquals(category,result)
    }


}