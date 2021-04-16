package com.pliniodev.chucknorrisfacts.service.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class FactModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `when text on fact value is greater then 80 characters`() {
        //Arrange
        val factTest: FactModel = FactModel(
            arrayListOf("food"),
            "2020-01-05 13:42:18.823766",
            "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
            "MjtEesffSd6AH3Pxbw7_lg",
            "2020-01-05 13:42:18.823766",
            "https://api.chucknorris.io/jokes/MjtEesffSd6AH3Pxbw7_lg",
            "When Chuck Norris played Chopped from Food Network, he finished " +
                    "his food in 1 millisecond, and instantly wins every dish. You " +
                    "didn't see him play because the episode is secret.",
        )

        //act
        var result = factTest.isLongText

        //assert
        Assert.assertTrue(result)
    }

    @Test
    fun `when text on fact value is smaller then 80 characters`() {
        //Arrange
        val factTest: FactModel = FactModel(
            arrayListOf("food"),
            "2020-01-05 13:42:18.823766",
            "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
            "MjtEesffSd6AH3Pxbw7_lg",
            "2020-01-05 13:42:18.823766",
            "https://api.chucknorris.io/jokes/MjtEesffSd6AH3Pxbw7_lg",
            "Chuck Norris can get Mexican food at a Japanese restaurant.",
        )

        //act
        val result = factTest.isLongText

        //assert
        Assert.assertFalse(result)
    }

    @Test
    fun `when category is empty categorie's text is UNCATEGORIZED`() {
        //Arrange
        val factTest: FactModel = FactModel(
            arrayListOf(),
            "2020-01-05 13:42:18.823766",
            "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
            "MjtEesffSd6AH3Pxbw7_lg",
            "2020-01-05 13:42:18.823766",
            "https://api.chucknorris.io/jokes/MjtEesffSd6AH3Pxbw7_lg",
            "Chuck Norris can get Mexican food at a Japanese restaurant.",
        )

        //act
        val result = factTest.getCategory

        //assert
        Assert.assertEquals("UNCATEGORIZED",result)
    }

    @Test
    fun `when category have a value, categorie's text show value`() {
        //Arrange
        val category = "food"

        val factTest: FactModel = FactModel(
            arrayListOf(category),
            "2020-01-05 13:42:18.823766",
            "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
            "MjtEesffSd6AH3Pxbw7_lg",
            "2020-01-05 13:42:18.823766",
            "https://api.chucknorris.io/jokes/MjtEesffSd6AH3Pxbw7_lg",
            "Chuck Norris can get Mexican food at a Japanese restaurant.",
        )

        //act
        val result = factTest.getCategory

        //assert
        Assert.assertEquals(category,result)
    }
}