package com.jotte.core.usecase

import com.jotte.core.di.emailRegex
import com.jotte.core.di.phoneRegex
import com.jotte.core.di.provideCoreModule
import com.jotte.core.di.urlRegex
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class LinkValidationTest : KoinTest {

    val links = "www.google.com"

    val phones =
        listOf(
            "6098675309",
            "(609)8675309",
            "(609) 8675309",
            "609-867-5309",
            "609 867-5309",
            "609 867 5309",
            "1 609 867 5309",
            "+1 609 867 5309",
        )

    val emails =
        listOf(
            "example@email.com",
            "123@email.com",
        )

    val linkPattern: String by inject(urlRegex())
    val phonePattern: String by inject(phoneRegex())
    val emailPattern: String by inject(emailRegex())

    @Before
    fun setup() {
        startKoin { modules(provideCoreModule()) }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `matches links`() {
        val isMatch = linkPattern.toRegex().matches(links)
        assert(isMatch)
    }

    @Test
    fun `matches phone numbers`() {
        phones.forEach { phoneNumber ->
            val isMatch = phonePattern.toRegex().matches(phoneNumber)
            assert(isMatch)
        }
    }

    @Test
    fun `matches emails`() {
        emails.forEach { email ->
            val isMatch = emailPattern.toRegex().matches(email)
            assert(isMatch)
        }
    }
}
