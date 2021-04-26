package com.pliniodev.chucknorrisfacts.test_utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Classe CorroutineTestRule necessária para testes para evitar este bug abaixo.
 * "java.lang.IllegalStateException: Module with the Main  dispatcher had failed to
 * initialize. For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used"
 */

@ExperimentalCoroutinesApi
class CoroutineTestRule(private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }

}