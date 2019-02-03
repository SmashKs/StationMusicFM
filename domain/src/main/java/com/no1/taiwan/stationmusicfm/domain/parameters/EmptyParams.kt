package com.no1.taiwan.stationmusicfm.domain.parameters

import com.no1.taiwan.stationmusicfm.domain.AnyParameters
import com.no1.taiwan.stationmusicfm.domain.Parameters

open class EmptyParams : Parameterable {
    override fun toApiParam() = Parameters()

    override fun toApiAnyParam() = AnyParameters()
}
