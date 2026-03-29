package com.jotte.data.persistence.converter

import androidx.room.TypeConverter
import com.jotte.data.persistence.data.LinkDto

object LinkTypeConverter {

    @TypeConverter
    fun from(link: LinkDto.LinkType): String = link.name

    @TypeConverter
    fun to(link: String): LinkDto.LinkType = LinkDto.LinkType.valueOf(link)

}