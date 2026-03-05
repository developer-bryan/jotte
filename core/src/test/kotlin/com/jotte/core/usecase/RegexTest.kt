package com.jotte.core.usecase

import org.junit.Test

// TODO: Inject patterns via koin
class RegexTest {

    val links = "www.google.com"
    val phones = listOf(
        "8582302231",
        "(858)2302231",
        "(858) 2302231",
        "858-230-2231",
        "858 230-2231",
        "858 230 2231",
        "1 858 230 2231",
        "+1 858 230 2231",
    )

    val emails = listOf(
        "example@email.com",
        "123@email.com",
    )

    val linkPattern = """\b((https?|ftp)://)?(www\.)?[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}(/[^\s]*)?\b"""
    val phonePattern = """(\+?\d{1,2}[- ]?)?\(?(\d{3})\)?[- ]?(\d{3})[- ]?(\d{4})"""
    val emailPattern = """^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)+$"""

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
