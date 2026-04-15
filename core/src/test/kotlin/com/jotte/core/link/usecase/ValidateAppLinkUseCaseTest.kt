package com.jotte.core.link.usecase

import com.jotte.core.link.di.provideLinkModule
import com.jotte.core.link.model.AppLink
import com.jotte.core.link.model.AppLinkScheme
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertFalse

class ValidateAppLinkUseCaseTest : KoinTest {

    val validateAppLinkUseCase: ValidateAppLinkUseCase by inject<ValidateAppLinkUseCase>()

    val urls =
        listOf(
            "www.jotte.net",
            "example.com/api/v1/users",
            "test.com/products/123",
            "sample.org/search?q=hello&page=1",
            "demo.net/auth/login"
        )

    val malformedUrls =
        listOf(
            "www.jotte-net",
            "example .com/api/v1/users",
            "test-com/products/123",
            "sample.org/search?",
            "demo.net/auth/login//"
        )

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
            "jottetest@yopmail.com"
        )

    @Before
    fun setup() {
        startKoin { modules(provideLinkModule()) }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `matches links`() {
        urls.forEach { url ->
            val appLink =
                AppLink(
                    name = null,
                    scheme = AppLinkScheme.Web,
                    link = url
                )

            val isMatch = validateAppLinkUseCase(appLink)
            assert(isMatch)
        }

    }

    @kotlin.test.Test
    fun `malformed URLs do not match`() {
        malformedUrls.forEach { url ->
            val appLink =
                AppLink(
                    name = null,
                    scheme = AppLinkScheme.Web,
                    link = url
                )

            val isMatch = validateAppLinkUseCase(appLink)
            assertFalse(isMatch)
        }
    }

    @Test
    fun `matches phone numbers`() {
        phones.forEach { phoneNumber ->
            val appLink =
                AppLink(
                    name = null,
                    scheme = AppLinkScheme.Phone,
                    link = phoneNumber
                )

            val isMatch = validateAppLinkUseCase(appLink)
            assert(isMatch)
        }
    }

    @Test
    fun `matches emails`() {
        emails.forEach { email ->
            val appLink =
                AppLink(
                    name = null,
                    scheme = AppLinkScheme.Email,
                    link = email
                )

            val isMatch = validateAppLinkUseCase(appLink)
            assert(isMatch)
        }
    }
}