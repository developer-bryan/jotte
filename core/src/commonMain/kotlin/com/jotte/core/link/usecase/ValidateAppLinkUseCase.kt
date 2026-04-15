package com.jotte.core.link.usecase

import com.jotte.core.link.model.AppLink
import com.jotte.core.link.model.AppLinkScheme

class ValidateAppLinkUseCase(
    val urlValidationRegex: Regex,
    val phoneValidationRegex: Regex,
    val emailValidationRegex: Regex
) {

    operator fun invoke(link: AppLink): Boolean {
        val regex =
            when (link.scheme) {
                is AppLinkScheme.Web -> urlValidationRegex
                is AppLinkScheme.Phone -> phoneValidationRegex
                is AppLinkScheme.Email -> emailValidationRegex
            }

        return link.link.isNotEmpty() && regex.matches(link.link)
    }

}