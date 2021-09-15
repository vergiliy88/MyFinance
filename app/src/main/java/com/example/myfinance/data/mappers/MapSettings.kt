package com.example.myfinance.data.mappers

import com.example.myfinance.data.entities.SettingsDB
import com.example.myfinance.domain.models.Settings

class MapSettings {
    companion object{
        fun mapFromDb(fromDB: SettingsDB): Settings {
            val domainModel = Settings()
            domainModel.id = fromDB.id
            domainModel.enabledComments = fromDB.enabledComments
            domainModel.hourlyPayment = fromDB.hourlyPayment
            domainModel.isReplayPayments = fromDB.isReplayPayments
            domainModel.paymentReceived = fromDB.paymentReceived
            return domainModel
        }

        fun mapTomDb(domainModel: Settings): SettingsDB {
            val toDB = SettingsDB()
            toDB.id = domainModel.id
            toDB.enabledComments = domainModel.enabledComments
            toDB.hourlyPayment = domainModel.hourlyPayment
            toDB.isReplayPayments = domainModel.isReplayPayments
            toDB.paymentReceived = domainModel.paymentReceived
            return toDB
        }
    }
}