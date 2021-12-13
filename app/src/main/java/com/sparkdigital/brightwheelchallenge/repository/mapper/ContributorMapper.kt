package com.sparkdigital.brightwheelchallenge.repository.mapper

import com.sparkdigital.brightwheelchallenge.domain.Contributor
import com.sparkdigital.brightwheelchallenge.repository.network.ContributorResponse

object ContributorMapper {
    fun mapFromReponse(from: ContributorResponse) = Contributor(from.login)
}